package algav.cle;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import algav.fileBinomiale.FileBinomial;


public class ReadKeyFromFile {

	public static void printFiles() {
		File folder = new File("./");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0 ; i < listOfFiles.length ; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else
				if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory " + listOfFiles[i].getName());
				}
		}
	}

	public static Cle stringToCle(String key) {
		return new Cle(new BigInteger(key.substring(2), 16));
	}

	/**
	 * Retourne une ArrayList des cles contenus dans un fichier
	 **/
	public static ArrayList<Cle> readKeyFromFile(String filename) {
		ArrayList<Cle> cles = new ArrayList<>();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			String s;
			while ((s = bf.readLine()) != null) {
				cles.add(stringToCle(s));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cles;
	}

	public static ArrayList<Cle> readKeyFromFile(File filename) {
		ArrayList<Cle> cles = new ArrayList<>();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			String s;
			while ((s = bf.readLine()) != null) {
				cles.add(stringToCle(s));
			}

			bf.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cles;
	}

	public static void main(String[] args) {

		File dossier = new File("./JeuxDeTest/cles_alea");
		System.out.println(dossier.isDirectory());
		File[] listFichiers = dossier.listFiles();
		List<Cle> tab2 = readKeyFromFile("./JeuxDeTest/cles_alea/jeu_5_nb_cles_50000.txt");
		FileBinomial file2 = FileBinomial.consIter(tab2);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0 ; i < listFichiers.length ; i++) {
			stringBuilder.append(listFichiers[i].getName());

			List<Cle> tab = readKeyFromFile(listFichiers[i]);

			FileBinomial file = FileBinomial.consIter((tab));

			long startUnion = System.nanoTime();
			FileBinomial.unionFile(file, file2);
			long endUnion = System.nanoTime();
			long resUnion = endUnion - startUnion;
			stringBuilder.append("," + resUnion + "\n");
		}
		System.out.println(stringBuilder.toString());
	}

}
