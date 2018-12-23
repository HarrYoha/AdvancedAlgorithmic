package algav.ABR;


import algav.cle.Cle;


public class Noeud {

	public Noeud gauche;
	public Noeud droit;
	public Cle cle;

	public Noeud(Cle cle) {
		this.cle = cle;
		this.droit = null;
		this.gauche = null;
	}

}
