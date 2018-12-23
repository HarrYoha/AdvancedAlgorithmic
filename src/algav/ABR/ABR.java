package algav.ABR;


import java.util.List;

import algav.cle.Cle;


public class ABR {

	private Noeud racine;

	public ABR() {
		this.racine = null;
	}

	public Noeud getRacine() {
		return this.racine;
	}

	public void ajouterList(List<Cle> cles) {
		for (Cle cle : cles) {
			ajout(cle);
		}
	}

	/**
	 * Ajoute une clé dans l'arbre
	 *
	 * @param cle
	 *            la cle à ajouter
	 */
	public void ajout(Cle cle) {
		this.racine = ajoutNoeud(this.racine, cle);
	}

	private Noeud ajoutNoeud(Noeud racine, Cle cle) {
		if (racine == null) {
			racine = new Noeud(cle);
			return racine;
		}
		if (cle.inf(racine.cle)) {
			racine.gauche = ajoutNoeud(racine.gauche, cle);
		} else
			if (racine.cle.inf(cle)) {
				racine.droit = ajoutNoeud(racine.droit, cle);
			}
		return racine;
	}

	/**
	 * Détermine si une clé est dans l'arbre
	 *
	 * @param cleAtrouver
	 *            la cle a trouver
	 * @return true si la cle est trouvée, false sinon
	 */
	public boolean chercherCle(Cle cleAtrouver) {
		return chercher(this.racine, cleAtrouver);
	}

	private boolean chercher(Noeud noeud, Cle cleAtrouver) {

		if (noeud == null) {
			return false;
		} else {
			if (noeud.cle.eg(cleAtrouver)) {
				return true;
			}
			if (cleAtrouver.inf(noeud.cle)) {
				return chercher(noeud.gauche, cleAtrouver);
			} else {
				return chercher(noeud.droit, cleAtrouver);
			}
		}
	}

	/**
	 * appel visitNoeud recursivement dans l'ordre prefixe
	 */
	public void visit() {
		visitNoeud(this.racine);
	}

	private void visitNoeud(Noeud root) {
		if (root != null) {
			visitNoeud(root.gauche);
			System.out.println(root.cle);
			visitNoeud(root.droit);
		}
	}

}
