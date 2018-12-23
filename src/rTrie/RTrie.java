package rTrie;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import algav.ABR.ABR;
import algav.MD5._MD5;
import algav.cle.ReadKeyFromFile;


public class RTrie {

	private static int nb_mot = 0;

	private boolean finMot = false;
	private int nbOccurence = 0;
	private int noApparition = 0;

	private Map<Character, RTrie> next;

	public RTrie() {
		this.next = new HashMap<>();
	}

	/**
	 * Ajoute un mot dans le RTrie
	 *
	 * @param mot
	 *            le mot a ajouter
	 */
	public void ajouterMot(String mot) {

		// Cas terminal
		if (mot.equals("")) {
			// Si le mot n'est pas encore dans le RTrie
			if (!this.finMot) {
				this.noApparition = nb_mot++;
				this.finMot = true;
			}
			++this.nbOccurence;
			return;
		}

		if (this.next.containsKey(mot.charAt(0))) { // La lettre est déjà dans le RTrie
			// On ajoute dans le RTrie suivant le mot sans le premier caractere
			this.next.get(mot.charAt(0)).ajouterMot(mot.substring(1));
		} else { // La lettre n'est pas dans le RTrie
			this.next.put(mot.charAt(0), new RTrie());
			this.next.get(mot.charAt(0)).ajouterMot(mot.substring(1));
		}
	}

	/**
	 * Détermine si un mot est dans le RTrie
	 *
	 * @param mot
	 *            le mot a chercher
	 * @return true si le mot est dans le RTrie, false sinon
	 */
	public boolean contient(String mot) {
		// On est arrivé à la fin du mot
		if (mot.equals("")) {
			return this.finMot;
		}

		// Il y a encore des lettres dans le mot
		// la lettre suivante existe dans le RTrie
		if (this.next.containsKey(mot.charAt(0))) {
			return this.next.get(mot.charAt(0)).contient(mot.substring(1));
		}

		// La lettre suivante n'est pas répertorié dans le RTrie
		return false;
	}

	public String[] getMots() {
		String[] tabTmp = new String[nb_mot];
		return getMotsAux(tabTmp, "");
	}

	private String[] getMotsAux(String[] tabTmp, String string) {
		// Si on tombe sur la fin d'un mot
		if (this.finMot) {
			tabTmp[this.noApparition] = string;
		}

		for (Character c : this.next.keySet()) {
			this.next.get(c).getMotsAux(tabTmp, string.concat(c.toString()));
		}

		return tabTmp;
	}

	public static void main(String[] args) {

		File dossier = new File("./Shakespeare");
		File[] listFichiers = dossier.listFiles();
		ABR arbre = new ABR();
		RTrie trie = new RTrie();

		for (int i = 0 ; i < listFichiers.length ; i++) {

			try {
				File fichier = new File(listFichiers[i] + "mot_unique.txt");
				PrintWriter printer = new PrintWriter(new FileWriter(fichier));

				BufferedReader reader = new BufferedReader(new FileReader(listFichiers[i]));

				String mot;

				while ((mot = reader.readLine()) != null) {
					trie.ajouterMot(mot);
				}

				String[] mots = trie.getMots();
				// System.out.println("length " + mots.length);
				for (String string : mots) {
					printer.println(string);
					arbre.ajout(ReadKeyFromFile.stringToCle(_MD5.MD5(string)));
				}

				reader.close();
				printer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		arbre.visit();

	}

}
