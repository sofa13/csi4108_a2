package linear;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start linear cryptanalysis");

		Map<String, String> sub = new HashMap<>();
		Map<Integer, Integer> perm = new HashMap<>();

		// S-box substitution
		sub.put(Integer.toHexString(0), Integer.toHexString(14));
		sub.put(Integer.toHexString(1), Integer.toHexString(4));
		sub.put(Integer.toHexString(2), Integer.toHexString(13));
		sub.put(Integer.toHexString(3), Integer.toHexString(1));
		sub.put(Integer.toHexString(4), Integer.toHexString(2));
		sub.put(Integer.toHexString(5), Integer.toHexString(15));
		sub.put(Integer.toHexString(6), Integer.toHexString(11));
		sub.put(Integer.toHexString(7), Integer.toHexString(8));
		sub.put(Integer.toHexString(8), Integer.toHexString(3));
		sub.put(Integer.toHexString(9), Integer.toHexString(10));
		sub.put(Integer.toHexString(10), Integer.toHexString(6));
		sub.put(Integer.toHexString(11), Integer.toHexString(13));
		sub.put(Integer.toHexString(12), Integer.toHexString(5));
		sub.put(Integer.toHexString(13), Integer.toHexString(9));
		sub.put(Integer.toHexString(14), Integer.toHexString(0));
		sub.put(Integer.toHexString(15), Integer.toHexString(7));

		// Permutations
		perm.put(1, 1);
		perm.put(2, 5);
		perm.put(3, 9);
		perm.put(4, 13);
		perm.put(5, 2);
		perm.put(6, 6);
		perm.put(7, 10);
		perm.put(8, 14);
		perm.put(9, 3);
		perm.put(10, 7);
		perm.put(11, 11);
		perm.put(12, 15);
		perm.put(13, 4);
		perm.put(14, 8);
		perm.put(15, 12);
		perm.put(16, 16);

		Map<Integer, String> plaintext = new HashMap<>();
		Map<Integer, String> ciphertext = new HashMap<>();

		Map<Integer, Map<Integer, String>> pMap = new HashMap<>();
		Map<Integer, Map<Integer, String>> cMap = new HashMap<>();

		// Generate 10,000 plaintext
		for (int i = 1; i <= 10000; i++) {
			for (int j = 1; j <= 16; j++) {
				Random rand = new Random();
				int randomNum = rand.nextInt((15 - 0) + 1);
				plaintext.put(j, Integer.toHexString(randomNum));
			}
			pMap.put(i, plaintext);
			plaintext = new HashMap<>();
		}

		// Print plaintext table lookup size
		System.out.println("Number of plaintext generated: " + pMap.size());

		// Generate ciphertext for all plaintext

		for (int i = 1; i <= pMap.size(); i++) {
			Map<Integer, String> text = pMap.get(i);
			Map<Integer, String> subText = new HashMap<>();
			Map<Integer, String> permText = new HashMap<>();
			for (int a = 1; a <= 4; a++) {
//				System.out.println("text");
//				System.out.println(text);
				// sub
				for (int j = 1; j <= 16; j++) {
					String preValue = text.get(j);
					String newValue = sub.get(preValue);
					subText.put(j, newValue);
				}
				
//				System.out.println("subText");
//				System.out.println(subText);

				// perm
				if (a < 4) {
					for (int k = 1; k <= 16; k++) {
						int newKey = perm.get(k);
						permText.put(newKey, subText.get(k));

					}
				} else {
					permText = subText;
				}
				
//				System.out.println("permText");
//				System.out.println(permText);
				
				text = permText;
				subText = new HashMap<>();
				permText = new HashMap<>();
			}
			cMap.put(i, text);
		}

		// Print ciphertext table lookup size
		System.out.println("Number of ciphertext generated: " + cMap.size());

		// Print 10 known plaintext cipher text pairs
		System.out.println("10 known plaintext cipher text pairs: ");
		for (int i = 1; i <= 10; i++) {
			System.out.println(pMap.get(i) + " => " + cMap.get(i));
		}

	}

}
