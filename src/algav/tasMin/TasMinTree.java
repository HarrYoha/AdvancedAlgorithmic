package algav.tasMin;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import algav.cle.Cle;
import algav.cle.ReadKeyFromFile;
import interfaces.TasMin;


public class TasMinTree implements TasMin {

	private Cle cle;

	private TasMinTree pere;
	private TasMinTree filsDroit;
	private TasMinTree filsGauche;

	public TasMinTree() {
	}

	private TasMinTree(TasMinTree pere, Cle cle) {
		this.pere = pere;
		this.cle = cle;
	}

	/**
	 * Première version de consIter</br>
	 * Compléxité en O(n*log(n)), l'ajout est en O(log(n)) et est appellé n fois
	 *
	 * @param cles
	 *            la liste des clés
	 * @return le tas min construit avec ces clés
	 */
	public static TasMin consIter(List<Cle> cles) {
		TasMin tasmin = new TasMinTree();
		int nb = 0;

		for (Cle cle : cles) {
			if ((nb % 1000) == 0) {
				System.out.println(nb);
			}
			++nb;

			tasmin.ajout(cle);
		}
		return tasmin;
	}

	/**
	 * Deuxième version de consIter</br>
	 *
	 * @param cles
	 * @return
	 */
	public static TasMin consIter2(List<Cle> cles) {
		TasMinTree retour = new TasMinTree();

		for (Cle cle : cles) {
			retour.ajouterSansTri(cle, retour);
		}

		triConstIter(retour);

		return retour;
	}

	private static void triConstIter(TasMinTree tree) {
		int hauteur = tree.hauteurMax(tree);

		triConsIterAux(tree, hauteur);

	}

	private static void triConsIterAux(TasMinTree tree, int hauteur) {
		System.out.println(hauteur);
		if (hauteur > 0) {
			// On commence a voir du côté droit
			triConsIterAux(tree.filsDroit, hauteur - 1);
			triConsIterAux(tree.filsGauche, hauteur - 1);
		}

		if (tree != null) {
			tree.percolateDown(tree);
		}

	}

	/**
	 * Supprime l'élément de plus petit de la liste</br>
	 *
	 * @return la clé la plus petite
	 */
	@Override
	public Cle supprMin() {
		// S'il n'y a rien dans le tas
		if (this.cle == null) {
			return null;
		}

		if ((this.filsDroit == null) && (this.filsGauche == null)) {
			Cle retour = this.cle;
			this.cle = null;
			return retour;
		}

		Cle retour = this.cle;
		TasMinTree current = this;

		// recherche du dernier element
		while ((current.filsGauche != null) && (current.filsDroit != null)) {
			current = hauteurMax(current.filsDroit) < hauteurMax(current.filsGauche) ? current.filsGauche
					: current.filsDroit;
		}

		// Remonter la valeur et on le retire le noeud de l'arbre
		if (current.filsDroit != null) {
			this.cle = current.filsDroit.cle;
			current.filsDroit = null;
		} else {
			if (current.filsGauche != null) {
				this.cle = current.filsGauche.cle;
				current.filsGauche = null;
			} else {
				this.cle = current.cle;
				current.pere.filsDroit = null;
			}
		}

		current = this;

		current = percolateDown(current);

		return retour;
	}

	/**
	 * Range un élément à la bonne place dans l'arbre si l'élément est plus grand
	 * que ses fils
	 *
	 * @param current
	 *            l'élément a ranger
	 * @return l'arbre rangé
	 */
	private TasMinTree percolateDown(TasMinTree current) {
		// Redescendre la clé si besoin
		while (true) {
			// S'il a deux fils
			if ((current.filsDroit != null) && (current.filsGauche != null)) {
				// l'element doit aller a gauche
				if (current.filsGauche.cle.inf(current.cle) && current.filsGauche.cle.inf(current.filsDroit.cle)) {
					Cle tmp = current.cle;
					current.cle = current.filsGauche.cle;
					current.filsGauche.cle = tmp;

					current = current.filsGauche;
				} else {
					// l'element doir aller a droite
					if (current.filsDroit.cle.inf(current.cle)) {
						Cle tmp = current.cle;
						current.cle = current.filsDroit.cle;
						current.filsDroit.cle = tmp;

						current = current.filsDroit;
					} else {
						// l'element de doit pas bouger
						break;
					}

				}
			} else {

				// un seul fils et doit aller a gauche
				if ((current.filsGauche != null) && current.filsGauche.cle.inf(current.cle)) {
					Cle tmp = current.cle;
					current.cle = current.filsGauche.cle;
					current.filsGauche.cle = tmp;
				}
				// il n'y a plus rien en dessous
				break;
			}
		}
		return current;
	}

	private int hauteurMax(TasMinTree tree) {
		if (tree.filsGauche != null) {
			return hauteurMax(tree.filsGauche) + 1;
		}

		return 0;
	}

	private int hauteurMin(TasMinTree tree) {
		if (tree == null) {
			return 0;
		}
		return Math.min(hauteurMin(tree.filsGauche) + 1, hauteurMin(tree.filsDroit) + 1);
	}

	@Override
	public void ajout(Cle item) {

		if (this.cle == null) {
			this.cle = item;
		} else {

			TasMinTree current = this;

			current = ajouterSansTri(item, current);

			// Remonter si necessaire
			while ((current.pere != null) && current.cle.inf(current.pere.cle)) {
				Cle tmp = current.cle;
				current.cle = current.pere.cle;
				current.pere.cle = tmp;

				current = current.pere;
			}
		}

	}

	private TasMinTree ajouterSansTri(Cle item, TasMinTree current) {
		// Trouver où ajouter
		while ((current.filsDroit != null) && (current.filsGauche != null)) {
			current = hauteurMin(current.filsDroit) < hauteurMin(current.filsGauche) ? current.filsDroit
					: current.filsGauche;
		}

		// Ajouter du bon cote
		if (current.filsGauche == null) {
			current.filsGauche = new TasMinTree(current, item);
			current = current.filsGauche;
		} else {
			current.filsDroit = new TasMinTree(current, item);
			current = current.filsDroit;
		}
		return current;
	}

	@Override
	public TasMin union(TasMin other) {
		List<Cle> cles = this.getTab();
		cles.addAll(other.getTab());

		return TasMinTree.consIter(cles);
	}

	@Override
	public List<Cle> getTab() {
		List<Cle> retour = new ArrayList<>();

		getTabAux(retour, this);

		return retour;

	}

	/**
	 * Récupération des clés de l'arbre avec un parcours infixe : O(n)
	 *
	 * @param list
	 *            la liste dans laquelle ajouter les clés
	 * @param tree
	 *            l'arbre dont il faut récupérer les clés
	 */
	private void getTabAux(List<Cle> list, TasMinTree tree) {

		if (tree.filsGauche != null) {
			getTabAux(list, tree.filsGauche);
		}
		list.add(tree.cle);
		if (tree.filsDroit != null) {
			getTabAux(list, tree.filsDroit);
		}

	}

	public static void main(String[] args) {

		File dossier = new File("./JeuxDeTest/cles_alea");
		File[] listFichiers = dossier.listFiles();
		List<Cle> tab2 = ReadKeyFromFile.readKeyFromFile("./JeuxDeTest/cles_alea/jeu_5_nb_cles_50000.txt");
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0 ; i < listFichiers.length ; i++) {
			TasMinTree tas2 = (TasMinTree) TasMinTree.consIter(tab2);

			stringBuilder.append(listFichiers[i].getName());

			List<Cle> tab = ReadKeyFromFile.readKeyFromFile(listFichiers[i]);

			TasMinTree tas = (TasMinTree) TasMinTree.consIter((tab));

			long startUnion = System.nanoTime();
			tas.union(tas2);
			long endUnion = System.nanoTime();
			long resUnion = endUnion - startUnion;
			stringBuilder.append("," + resUnion + "\n");
		}
		System.out.println(stringBuilder.toString());

	}

}
