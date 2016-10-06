package linear;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start linear cryptanalysis");

		Map<String, String> sub = new HashMap<>();
		Map<String, String> revsub = new HashMap<>();
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
		
		revsub.put(Integer.toHexString(14), Integer.toHexString(0));
		revsub.put(Integer.toHexString(4), Integer.toHexString(1));
		revsub.put(Integer.toHexString(13), Integer.toHexString(2));
		revsub.put(Integer.toHexString(1), Integer.toHexString(3));
		revsub.put(Integer.toHexString(2), Integer.toHexString(4));
		revsub.put(Integer.toHexString(15), Integer.toHexString(5));
		revsub.put(Integer.toHexString(11), Integer.toHexString(6));
		revsub.put(Integer.toHexString(8), Integer.toHexString(7));
		revsub.put(Integer.toHexString(3), Integer.toHexString(8));
		revsub.put(Integer.toHexString(10), Integer.toHexString(9));
		revsub.put(Integer.toHexString(6), Integer.toHexString(10));
		revsub.put(Integer.toHexString(13), Integer.toHexString(11));
		revsub.put(Integer.toHexString(5), Integer.toHexString(12));
		revsub.put(Integer.toHexString(9), Integer.toHexString(13));
		revsub.put(Integer.toHexString(0), Integer.toHexString(14));
		revsub.put(Integer.toHexString(7), Integer.toHexString(15));

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

		// Maps for plaintext and ciphertext
		Map<Integer, Map<Integer, String>> pMap = new HashMap<>();
		Map<Integer, Map<Integer, String>> cMap = new HashMap<>();

		// Generate 10,000 plaintext
		for (int i = 1; i <= 10000; i++) {
			Map<Integer, String> plaintext = new HashMap<>();
			String pString = String.format("%16s", Integer.toBinaryString(i)).replace(' ', '0');

			for (int j = 0; j < pString.length(); j++) {
				String c = "" + pString.charAt(j);
				plaintext.put(j + 1, c);
			}

			pMap.put(i, plaintext);
		}

		// Print plaintext table lookup size
		System.out.println("Number of plaintext generated: " + pMap.size());

		// Generate ciphertext for all plaintext

		for (int i = 1; i <= pMap.size(); i++) {
			Map<Integer, String> text = pMap.get(i);
			Map<Integer, String> subText = new HashMap<>();
			Map<Integer, String> permText = new HashMap<>();
			for (int round = 1; round <= 4; round++) {
				// System.out.println("text");
				// System.out.println(text);
				// sub
				String subString = "";
				String fullString = "";
				for (int j = 1; j <= 16; j++) {
					if (j % 4 != 0) {
						subString += text.get(j);
					} else {
						subString += text.get(j);
						int foo = Integer.parseInt(subString, 2);
						String hex = Integer.toHexString(foo);
						String newHex = sub.get(hex);
						foo = Integer.parseInt(newHex, 16);
						String newBinary = String.format("%4s", Integer.toBinaryString(foo)).replace(' ', '0');
						fullString += newBinary;
						subString = "";
					}
				}
				for (int k = 0; k < fullString.length(); k++) {
					String c = "" + fullString.charAt(k);
					subText.put(k + 1, c);
				}

				// System.out.println("subText");
				// System.out.println(subText);

				// perm
				if (round < 4) {
					for (int k = 1; k <= 16; k++) {
						int newKey = perm.get(k);
						permText.put(newKey, subText.get(k));

					}
				} else {
					permText = subText;
				}

				// System.out.println("permText");
				// System.out.println(permText);

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
		
		// Generate partial subkeys (256) [K5,5...K5,8 , K5,13...K5,16]
		Map<Integer, Map<String, Double>> orderedSubkeys = new HashMap<>();
		int count = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				Map<String, Double> partialSubkeys = new HashMap<>();
				String firstHex = Integer.toHexString(i);
				String secondHex = Integer.toHexString(j);
				partialSubkeys.put(firstHex+secondHex, 0.0000);
				orderedSubkeys.put(count, partialSubkeys);
				count++;
			}
		}
		
		// Print 10 known partial subkeys
		System.out.println("10 known partial subkeys: ");
		for (int i = 1; i <= 20; i++) {
			 System.out.println(orderedSubkeys.get(i).keySet().toArray()[0]);
		}		
		
		// Go through all ciphertext plaintext pairs
		for (int i = 1; i <= cMap.size(); i++) {
			Map<Integer, String> ciphertext = cMap.get(i);
//			Map<Integer, String> decryptext = new HashMap<>();
			for (int j = 0; j < orderedSubkeys.size(); j++) {
				//put ciphertext through key
				String subkey = orderedSubkeys.get(j).keySet().toArray()[0].toString();
				String firstkeyHex = "" + subkey.charAt(0);
				String secondkeyHex = "" + subkey.charAt(1);
				
				int foo = Integer.parseInt(firstkeyHex, 16);
				String firstkeyBin = String.format("%4s", Integer.toBinaryString(foo)).replace(' ', '0');
				foo = Integer.parseInt(secondkeyHex, 16);
				String secondkeyBin = String.format("%4s", Integer.toBinaryString(foo)).replace(' ', '0');
				
				for (int it = 0; it < 4; it++) {
					int preValue = Integer.parseInt(ciphertext.get(it+5));
					int xord = (preValue + Integer.parseInt("" + firstkeyBin.charAt(it))) % 2;
					ciphertext.put(it+5, "" + xord);
				}
				
				for (int it = 0; it < 4; it++) {
					int preValue = Integer.parseInt(ciphertext.get(it+13));
					int xord = (preValue + Integer.parseInt("" + secondkeyBin.charAt(it))) % 2;
					ciphertext.put(it+13, "" + xord);
				}
				
				// Put partial decrypt ciphertext through reverse S-boxes
				
				// XOR U4,6 U4,8 U4,14 U4,16 P5 P7 P8
				
				// If zero increment count for subkey
			}
		}

	}

}
