package algav.tasMin;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import algav.cle.Cle;
import algav.cle.ReadKeyFromFile;
import interfaces.TasMin;


public class TasMinTab implements TasMin {
	private ArrayList<Cle> tab = new ArrayList<>();

	public static TasMin consIter(List<Cle> values) {
		TasMin tasmin = new TasMinTab();

		for (Cle c : values) {
			tasmin.ajout(c);
		}
		return tasmin;
	}

	@Override
	public void ajout(Cle cle) {
		if (this.tab.size() == 0) {
			this.tab.add(0, cle);
		} else {
			int indexCle = this.tab.size();
			int indexPere = parent(indexCle);
			Cle pere = this.tab.get(indexPere);
			this.tab.add(this.tab.size(), cle);
			if (cle.inf(pere) || cle.eg(pere)) {
				echanger(indexCle, indexPere);
				indexCle = indexPere;
				indexPere = parent(indexPere);
				while ((indexCle != indexPere) && this.tab.get(indexCle).inf(this.tab.get(indexPere))) {
					echanger(indexCle, indexPere);
					indexCle = indexPere;
					indexPere = parent(indexPere);
				}
			}

		}

	}

	/**
	 * renvoie index du pere a l'aide de l'index du noeud
	 *
	 * @param i
	 *            l'indice du noeud actuel
	 * @return l'indice du père
	 */
	private int parent(int i) {
		if ((i % 2) == 1) {
			return i / 2;
		} else {
			return (i - 1) / 2;
		}
	}

	/**
	 * renvoie index du fils gauche a l'aide de l'index du noeud
	 *
	 * @param i
	 *            l'indice du noeud actuel
	 * @return l'indice du fils gauche
	 */
	private int left(int i) {
		return ((2 * i) + 1);
	}

	/**
	 * renvoie index du fils droit a l'aide de l'index du noeud
	 *
	 * @param i
	 *            l'indice du noeud actuel
	 * @return l'indice du fils gauche
	 */
	private int right(int i) {
		return ((2 * i) + 2);
	}

	private void echanger(int indexFils, int indexPere) {
		Cle clePere = this.tab.get(indexPere);
		this.tab.set(indexPere, this.tab.get(indexFils));
		this.tab.set(indexFils, clePere);
	}

	@Override
	public ArrayList<Cle> getTab() {
		return this.tab;
	}

	@Override
	public Cle supprMin() {

		if (this.tab.size() == 0) {
			System.out.println("Tas vide");
			return null;
		} else
			if (this.tab.size() == 1) {
				return this.tab.remove(0);
			}

		Cle min = this.tab.get(0);

		Cle derniereCle = this.tab.get(this.tab.size() - 1);
		this.tab.set(0, derniereCle);
		this.tab.remove(this.tab.size() - 1);

		checkMin(0);

		return min;
	}

	public void checkMin(int index) {
		int filsGauche = left(index);
		int filsDroit = right(index);
		int min = index;

		if ((filsGauche <= (this.tab.size() - 1)) && this.tab.get(filsGauche).inf(this.tab.get(index))) {
			min = filsGauche;
		}

		if ((filsDroit <= (this.tab.size() - 1)) && this.tab.get(filsDroit).inf(this.tab.get(min))) {
			min = filsDroit;
		}

		if (min != index) {
			echanger(index, min);
			checkMin(min);
		}
	}

	@Override
	public TasMin union(TasMin other) {
		for (Cle c : ((TasMinTab) other).getTab()) {
			this.tab.add(c);
		}
		for (int i = this.tab.size() / 2 ; i >= 0 ; i--) {
			checkMin(i);
		}
		return this;
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
			TasMinTab file = (TasMinTab) TasMinTab.consIter((tab));

			long total = 0;
			for (int j = 0 ; j < tab.size() ; ++j) {
				long start = System.nanoTime();
				file.supprMin();
				long end = System.nanoTime();

				total += end - start;
			}
			stringBuilder.append("," + (total / tab.size()) + "\n");
		}
		System.out.println(stringBuilder.toString());
	}

	private static void union() {
		File dossier = new File("./JeuxDeTest/cles_alea");
		File[] listFichiers = dossier.listFiles();
		List<Cle> tab2 = ReadKeyFromFile.readKeyFromFile("./JeuxDeTest/cles_alea/jeu_5_nb_cles_50000.txt");
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0 ; i < listFichiers.length ; i++) {
			TasMinTab tas2 = (TasMinTab) TasMinTab.consIter(tab2);

			stringBuilder.append(listFichiers[i].getName());

			List<Cle> tab = ReadKeyFromFile.readKeyFromFile(listFichiers[i]);

			TasMinTab tas = (TasMinTab) TasMinTab.consIter((tab));

			long startUnion = System.nanoTime();
			tas2.union(tas);
			long endUnion = System.nanoTime();

			long resUnion = endUnion - startUnion;
			stringBuilder.append("," + resUnion + "\n");
		}
		System.out.println(stringBuilder.toString());
	}
}
