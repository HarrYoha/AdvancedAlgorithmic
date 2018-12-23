package algav.cle;


import java.math.BigInteger;


/**
 * Classe utilisée pour représenter les clés
 */
public class Cle implements Comparable<Cle> {
	
	private BigInteger cle;
	
	public Cle(BigInteger cle) {
		this.cle = cle;
	}
	
	/**
	 * Compare deux clefs c1 et c2</br>
	 * Solution pour la question 1.1
	 *
	 * @param cle1
	 *            la première clé
	 * @param cle2
	 *            la deuxième clé
	 * @return true si cle1 est strictement inférieur à cle2
	 */
	public boolean inf(Cle other) {
		return this.cle.compareTo(other.cle) < 0;
	}
	
	public boolean eg(Cle cle2) {
		return (this.cle.compareTo(cle2.cle) == 0);
	}
	
	@Override
	public int compareTo(Cle other) {
		return this.cle.compareTo(other.cle);
	}
	
	@Override
	public String toString() {
		return this.cle.toString(16);
	}
	
}
