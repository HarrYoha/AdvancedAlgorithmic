package algav.fileBinomiale;


import algav.cle.Cle;


public class TournoiBinomial {
	Cle cle;

	FileBinomial fils;

	private TournoiBinomial() {
	}

	public TournoiBinomial(Cle cle) {
		this.cle = cle;
		this.fils = new FileBinomial();
	}

	public int degre() {
		return this.fils.nbTournois();
	}

	public FileBinomial decapite() {
		return this.fils;
	}

	public FileBinomial file() {
		return new FileBinomial().ajoutMin(this);

	}

	public static TournoiBinomial union(TournoiBinomial t1, TournoiBinomial t2) {
		if (t1.degre() == t2.degre()) {
			TournoiBinomial retour = new TournoiBinomial();

			if (t1.cle.inf(t2.cle)) {
				retour.cle = t1.cle;
				retour.fils = t1.fils;
				retour.fils.tournois.addFirst(t2);
			} else {
				retour.cle = t2.cle;
				retour.fils = t2.fils;
				retour.fils.tournois.addFirst(t1);
			}

			return retour;
		}

		System.err.println("Union de tournois binomial de degres differents");
		return null;
	}

}
