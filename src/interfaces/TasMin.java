package interfaces;


import java.util.List;

import algav.cle.Cle;


public interface TasMin {
	/**
	 * Retirer le minimim du tas min tout en gardant une structure de tas mon
	 * correcte
	 *
	 * @return l'e plus petit element
	 */
	Cle supprMin();
	
	/**
	 * Ajout une clé le tas min tout en gardant une structure de tas mon correcte
	 *
	 * @param item
	 */
	void ajout(Cle item);
	
	/**
	 * Construit l'union d'un un tas min avec un autre
	 *
	 * @param other
	 *            l'autre tas min
	 * @return l'union des deux tas min
	 */
	TasMin union(TasMin other);

	/**
	 * @return une liste contenant la liste des cles dans un ordre arbitraire
	 */
	List<Cle> getTab();
	
}
