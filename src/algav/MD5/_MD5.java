package algav.MD5;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;


public class _MD5 {

	public static final int MD5_CHUNK_LENGTH = 512;
	public static final int MD5_LENGTH_LENGTH = 64;

	public static String MD5(String message) {
		//
		// int[] r = { 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 5, 9,
		// 14, 20, 5, 9, 14, 20, 5, 9, 14,
		// 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
		// 6, 10, 15, 21, 6, 10, 15,
		// 21, 6, 10, 15, 21, 6, 10, 15, 21 };
		//
		// int[] k = new int[64];
		//
		// for (int i = 0 ; i < 63 ; i++) {
		// k[i] = (int) (long) Math.floor(Math.abs(Math.sin(i + 1)) * Math.pow(2, 32));
		// }
		//
		// int a0 = 0x67452301;// A
		// int b0 = 0xefcdab89;// B
		// int c0 = 0x98badcfe;// C
		// int d0 = 0x10325476;// D
		//
		// // Prepare un tableau de bits pour contenir le message
		// BitSet message_bit = BitSet.valueOf(message.getBytes());
		//
		// System.out.println(message_bit.length());
		// // System.out.println(message_bit);
		//
		// int messageLength = message_bit.length();
		// System.out.println("little endian avant " + convert(message_bit));
		// // pour coder la taille
		// BitSet messageLengthBit = BitSet.valueOf(intToLittleEndian(messageLength));
		// // BitSet messageLengthBit = BitSet.valueOf(toBytes(messageLength));
		//
		// System.out.println("little endian apres " + convert(messageLengthBit));
		//
		// // calcul du nombre de 0 a ajouter pour le padding ; -1 pour le bit 1 en fin
		// int paddingNeeded = MD5_CHUNK_LENGTH - MD5_LENGTH_LENGTH - 1 -
		// (message_bit.length() % 512);
		//
		// System.out.println("messageLenghtBit " + messageLengthBit.length());
		// System.out.println("paddingneeded " + paddingNeeded);
		// System.out.println("message_bit.length " + message_bit.length());
		//
		// BitSet messagePadded = new BitSet(message_bit.length() + 1 + paddingNeeded +
		// 64);
		// int taille_messagePadded = message_bit.length() + 1 + paddingNeeded + 64;//
		// =512
		// System.out.println(" taille messagePAdded " + taille_messagePadded);
		//
		// // Copie du message
		// int i;
		// for (i = 0 ; i < messageLength ; ++i) {
		// if (message_bit.get(i)) {
		// messagePadded.set(i);
		// }
		// }
		//
		// // ajout du 1 en fin
		// // messagePadded.get(i) = false si i++ a l'interieur du set
		// messagePadded.set(i);
		//
		// System.out.println("i++ " + messagePadded.get(i));
		// int j;
		//
		// System.out.println("mess padded avant copie de messageLengthBit" +
		// messagePadded);
		// for (j = 0, i = messagePadded.length() - messageLengthBit.length() ; (i <
		// messagePadded.length())
		// && (j < messageLengthBit.length()) ; ++i, ++j) {
		// if (messageLengthBit.get(j)) {
		// messagePadded.set(i);
		// }
		// }
		// System.out.println("message lenghtbit " + messageLengthBit);
		//
		// System.out.println("taille final du message padded =k*512 normalement " +
		// messagePadded.size());
		//
		// int nb_bloc = messagePadded.size() / 512;
		// System.out.println("nb_blocs " + nb_bloc);
		//
		// // decoupage en bloc de 512 bits
		// BitSet bloc = new BitSet(512);
		// BitSet ww = new BitSet(32);
		// List<BitSet> listBitset = new ArrayList<>();
		// List<Integer> listint = new ArrayList<>();
		// int a = a0;
		// int b = b0;
		// int c = c0;
		// int d = d0;
		// for (int p = 0 ; p < messagePadded.size() ; p += 512) {
		// for (int x = p, n = 0 ; (x < (p + 512)) && (n < 512) ; x++, n++) {
		// // init bloc
		// if (messagePadded.get(x)) {
		// bloc.set(n);
		// }
		// }
		// /*
		// * System.out.println("bloc " + bloc); System.out.println("bloc length " +
		// * bloc.cardinality());
		// */
		// // decoupage en 16 mot de 32 bits
		// for (int l = 0 ; l < 512 ; l += 32) {
		// for (int x = l, n = 0 ; (x < (l + 32)) && (n < 32) ; x++, n++) {
		// // init w[i]
		// if (bloc.get(x)) {
		// ww.set(n);
		// }
		// }
		//
		// /*
		// * System.out.println("ww " + ww.cardinality()); System.out.println("ww.length
		// "
		// * + ww.length()); System.out.println("ww.size " + ww.size());
		// */
		// // conversion en little endian du bitset
		// long sum = convert(ww);
		// byte[] bytes = ww.toByteArray();
		// // System.out.println("bytes legnth "+bytes.length);
		// int value = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
		//
		// // System.out.println("convert "+ ww.toByteArray());
		// BitSet www = BitSet.valueOf(intToLittleEndian(convert(ww)));
		// listBitset.add(www);
		// listint.add(value);
		// // listint.add(convert(www));
		// // System.out.println("convert www "+convert(www));
		// }
		//
		// // System.out.println("size list " +listint.size());
		//
		// /*
		// * for (Integer lm : listint) System.out.println("int " + lm.intValue());
		// */
		// // Boucle Principale
		// // i = m
		// int g = 0;
		// for (int m = 0 ; m < 64 ; m++) {
		// int f = 0;
		// if ((m >= 0) && (m <= 15)) {
		// System.out.println("f " + f + "g " + g + " b " + b + " c " + c + " d " + d);
		// f = (b & c) | (~b & d);
		// g = m;
		// if ((m == 3) || (m == 2) || (m == 3)) {
		// System.out.println("apres affec f " + f + " g " + g + " b " + b + " c " + c +
		// " d " + d);
		// }
		// } else
		// if ((m >= 16) && (m <= 31)) {
		// f = (d & b) | (~d & c);
		// g = ((5 * m) + 1) % 16;
		// } else
		// if ((m >= 32) && (m <= 47)) {
		// f = b ^ c ^ d;
		// g = ((3 * m) + 5) % 16;
		// } else
		// if ((m >= 48) && (m <= 63)) {
		// f = c ^ (b | ~d);
		// g = (7 * m) % 16;
		// }
		// // ordre d'affectaion selon wiki anglais
		//
		// int temp = d;
		// int conv = (listint.get(g)).intValue();
		// // System.out.println(" g og "+g);
		// // f = f + a + k[m] + (int) convert(listBitset.get(g));
		// d = c;
		// c = b;
		// b = b + Integer.rotateLeft(a + f + k[m] + conv, r[m]);
		// // System.out.println("conversion du int " + conv);
		// a = temp;
		// // System.out.println("iteration n " + m);
		//
		// /*
		// * System.out.println("b " +a); System.out.println("c " +a);
		// * System.out.println("d " +a); System.out.println("a " +a);
		// */
		// }
		//
		// a += a0;
		// b += b0;
		// c += c0;
		// d += d0;
		//
		// }
		//
		// int[] abcd = new int[4];
		// abcd[0] = fromByteArray(intToLittleEndian((a)));
		// abcd[1] = fromByteArray(intToLittleEndian((b)));
		// abcd[2] = fromByteArray(intToLittleEndian((c)));
		// abcd[3] = fromByteArray(intToLittleEndian((d)));
		// /*
		// * System.out.println("a "+a); System.out.println("b "+b);
		// * System.out.println("c "+c); System.out.println("d "+d);
		// */

		String s = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			s = toHexadecimal(messageDigest.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return s;

	}

	public static int leftrotate(int x, int c) {
		return (x << c) | (x >> (32 - c));
	}

	public static long convert(BitSet bits) {
		long value = 0L;
		for (int i = 0 ; i < bits.length() ; ++i) {
			value += bits.get(i) ? (1L << i) : 0L;
		}
		return value;
	}

	public static String toHexadecimal(int[] message) {
		StringBuilder sb = new StringBuilder(message.length * 2);
		for (int b : message) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static String toHexadecimal(byte[] message) {
		StringBuilder sb = new StringBuilder(message.length * 2);
		for (byte b : message) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static int fromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static byte[] toBytes(int i) {
		byte[] result = new byte[4];

		result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i /* >> 0 */);

		return result;
	}

	private static byte[] intToLittleEndian(long numero) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt((int) numero);
		return bb.array();
	}

	public static void main(String[] args) {
		System.out.println(MD5("Test"));
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			System.out.println(toHexadecimal(messageDigest.digest("Test".getBytes())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
