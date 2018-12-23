package algav.fileBinomiale;


import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import algav.cle.Cle;
import algav.cle.ReadKeyFromFile;


public class FileBinomial {
	LinkedList<TournoiBinomial> tournois;

	public FileBinomial() {
		this.tournois = new LinkedList<>();
	}

	public int nbTournois() {
		return this.tournois.size();
	}

	public boolean estVide() {
		return nbTournois() == 0;
	}

	public TournoiBinomial minDeg() {
		if (!estVide()) {
			return this.tournois.getLast();
		}
		return null;
	}

	public FileBinomial reste() {
		if (!estVide()) {
			this.tournois.removeLast();
		}
		return this;
	}

	public FileBinomial ajout(Cle cle) {
		return unionFile(this, new TournoiBinomial(cle).file());
	}

	public FileBinomial ajoutMin(TournoiBinomial tournoi) {

		this.tournois.addLast(tournoi);

		return this;
	}

	public static FileBinomial consIter(List<Cle> cles) {
		FileBinomial retour = new FileBinomial();
		for (Cle cle : cles) {
			retour = retour.ajout(cle);
		}

		return retour;
	}

	public static FileBinomial unionFile(FileBinomial f1, FileBinomial f2) {
		return uFret(f1, f2, null);
	}

	public static FileBinomial uFret(FileBinomial f1, FileBinomial f2, TournoiBinomial t) {

		if (t == null) {
			if (f1.estVide()) {
				return f2;
			}
			if (f2.estVide()) {
				return f1;
			}

			TournoiBinomial T1 = f1.minDeg();
			TournoiBinomial T2 = f2.minDeg();

			if (T1.degre() < T2.degre()) {
				return unionFile(f1.reste(), f2).ajoutMin(T1);
			}
			if (T2.degre() < T1.degre()) {
				return unionFile(f2.reste(), f1).ajoutMin(T2);
			}
			if (T1.degre() == T2.degre()) {
				return uFret(f1.reste(), f2.reste(), TournoiBinomial.union(T1, T2));
			}

		} else {
			if (f1.estVide()) {
				return unionFile(t.file(), f2);
			}
			if (f2.estVide()) {
				return unionFile(t.file(), f1);
			}

			TournoiBinomial T1 = f1.minDeg();
			TournoiBinomial T2 = f2.minDeg();

			if ((t.degre() < T1.degre()) && (t.degre() < T2.degre())) {
				return unionFile(f1, f2).ajoutMin(t);
			}

			if ((t.degre() == T1.degre()) && (t.degre() == T2.degre())) {
				return uFret(f1.reste(), f2.reste(), TournoiBinomial.union(T1, T2)).ajoutMin(t);
			}

			if ((t.degre() == T1.degre()) && (t.degre() < T2.degre())) {
				return uFret(f1.reste(), f2, TournoiBinomial.union(T1, t));
			}
			if ((t.degre() == T2.degre()) && (t.degre() < T1.degre())) {
				return uFret(f2.reste(), f1, TournoiBinomial.union(T2, t));
			}
		}

		System.err.println("Erreur UFret");
		return null;
	}

	public Cle suppMin() {
		// Si la file est vide
		if (estVide()) {
			return null;
		}

		// On cherche le tournoi avec la clé la plus petite
		TournoiBinomial aRetirer = this.tournois.getFirst();
		ListIterator<TournoiBinomial> i = this.tournois.listIterator();
		while (i.hasNext()) {
			TournoiBinomial tmp = i.next();
			if (tmp.cle.inf(aRetirer.cle)) {
				aRetirer = tmp;
			}
		}

		// Ce tournoi est retiré
		this.tournois.remove(aRetirer);

		// On récupère la clé
		Cle retour = aRetirer.cle;

		// On récupère les fils
		FileBinomial corpse = aRetirer.decapite();

		// Tous les fils sont rajoutés à la file
		FileBinomial tmp = FileBinomial.unionFile(this, corpse);

		this.tournois = tmp.tournois;

		return retour;

	}

	public static void main(String[] args) {
		union();
	}

	private static void supp() {
		File dossier = new File("./JeuxDeTest/cles_alea");
		File[] listFichiers = dossier.listFiles();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0 ; i < listFichiers.length ; i++) {
			stringBuilder.append(listFichiers[i].getName());

			List<Cle> tab = ReadKeyFromFile.readKeyFromFile(listFichiers[i]);
			FileBinomial file = FileBinomial.consIter((tab));

			long total = 0;
			for (int j = 0 ; j < tab.size() ; ++j) {
				long start = System.nanoTime();
				file.suppMin();
				long end = System.nanoTime();

				total += end - start;
			}
			stringBuilder.append("," + (total / tab.size()) + "\n");
		}
		System.out.println(stringBuilder.toString());
	}

	private static void ajout() {
		File dossier = new File("./JeuxDeTest/cles_alea");
		File[] listFichiers = dossier.listFiles();
		List<Cle> tab2 = ReadKeyFromFile.readKeyFromFile("./JeuxDeTest/cles_alea/jeu_5_nb_cles_50000.txt");
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0 ; i < listFichiers.length ; i++) {
			stringBuilder.append(listFichiers[i].getName());

			List<Cle> tab = ReadKeyFromFile.readKeyFromFile(listFichiers[i]);
			FileBinomial file = FileBinomial.consIter((tab));

			long total = 0;
			for (Cle cle : tab2) {
				long start = System.nanoTime();
				file.ajout(cle);
				long end = System.nanoTime();

				total += end - start;
			}
			stringBuilder.append("," + (total / 100) + "\n");
		}
		System.out.println(stringBuilder.toString());
	}

	private static void union() {
		File dossier = new File("./JeuxDeTest/cles_alea");
		File[] listFichiers = dossier.listFiles();
		List<Cle> tab2 = ReadKeyFromFile.readKeyFromFile("./JeuxDeTest/cles_alea/jeu_5_nb_cles_50000.txt");
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0 ; i < listFichiers.length ; i++) {
			FileBinomial file2 = FileBinomial.consIter(tab2);
			System.out.print(tab2.size() + " ");
			stringBuilder.append(listFichiers[i].getName());

			List<Cle> tab = ReadKeyFromFile.readKeyFromFile(listFichiers[i]);

			FileBinomial file = FileBinomial.consIter((tab));
			System.out.println(tab.size());

			long startUnion = System.nanoTime();
			FileBinomial.unionFile(file, file2);
			long endUnion = System.nanoTime();
			long resUnion = endUnion - startUnion;
			stringBuilder.append("," + resUnion + "\n");
		}
		System.out.println(stringBuilder.toString());
	}
}
