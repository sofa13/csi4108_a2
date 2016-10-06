package linear;

import java.util.HashMap;
import java.util.Map;
import linear.Utils;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Utils util = new Utils();

		System.out.println("Start linear cryptanalysis");

		// Maps for plaintext and ciphertext
		Map<Integer, Map<Integer, Integer>> pMap = new HashMap<>();
		Map<Integer, Map<Integer, Integer>> cMap = new HashMap<>();

		// Generate 10,000 plaintext
		// int start = 22768;
		for (int i = 1; i <= 10000; i++) {
			Map<Integer, Integer> plaintext = new HashMap<>();
			String pString = String.format("%16s", Integer.toBinaryString(i * 3)).replace(' ', '0');

			for (int j = 0; j < pString.length(); j++) {
				plaintext.put(j + 1, Integer.parseInt("" + pString.charAt(j)));
			}

			pMap.put(i, plaintext);
		}

		// Print plaintext table lookup size
		System.out.println("Number of plaintext generated: " + pMap.size());

		// Generate ciphertext for all plaintext

		for (int i = 1; i <= pMap.size(); i++) {
			Map<Integer, Integer> text = new HashMap<>();
			text.putAll(pMap.get(i));
			Map<Integer, Integer> subText = new HashMap<>();
			Map<Integer, Integer> permText = new HashMap<>();

			for (int round = 1; round <= 4; round++) {
				// Encrypt with key
				for (int a = 1; a <= text.size(); a++) {
					int newValue = (text.get(a) + util.key.get(round).get(a)) % 2;
					text.put(a, newValue);
				}

				// sub
				String subString = "";
				String fullString = "";
				for (int j = 1; j <= 16; j++) {
					if (j % 4 != 0) {
						subString += text.get(j);
					} else {
						subString += text.get(j);
						String hex = Integer.toHexString(Integer.parseInt(subString, 2));
						String newHex = util.sub.get(hex);
						String newBinary = String.format("%4s", Integer.toBinaryString(Integer.parseInt(newHex, 16))).replace(' ', '0');
						fullString += newBinary;
						subString = "";
					}
				}
				for (int k = 0; k < fullString.length(); k++) {
					subText.put(k + 1, Integer.parseInt("" + fullString.charAt(k)));
				}

				// perm
				if (round < 4) {
					for (int k = 1; k <= 16; k++) {
						int newKey = util.perm.get(k);
						permText.put(newKey, subText.get(k));

					}
				} else {
					permText = subText;
				}

				text = permText;
				subText = new HashMap<>();
				permText = new HashMap<>();
			}

			// Encrypt with key once more
			for (int a = 1; a <= text.size(); a++) {
				int newValue = (text.get(a) + util.key.get(5).get(a)) % 2;
				text.put(a, newValue);
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
		System.out.println("...");

		// Generate partial subkeys (256) [K5,5...K5,8 , K5,13...K5,16]
		Map<Integer, Map<String, Integer>> orderedSubkeys = new HashMap<>();
		int index = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				Map<String, Integer> partialSubkeys = new HashMap<>();
				String firstHex = Integer.toHexString(i);
				String secondHex = Integer.toHexString(j);
				partialSubkeys.put(firstHex + secondHex, 0);
				orderedSubkeys.put(index, partialSubkeys);
				index++;
			}
		}

		// Print 10 known partial subkeys
		System.out.println("10 known partial subkeys: ");
		for (int i = 1; i <= 10; i++) {
			System.out.println(orderedSubkeys.get(i).keySet().toArray()[0]);
		}
		System.out.println("...");

		// Go through all ciphertext plaintext pairs
		for (int i = 1; i <= cMap.size(); i++) {
			Map<Integer, Integer> ciphertext = new HashMap<>();
			ciphertext.putAll(cMap.get(i));
			Map<Integer, Integer> plaintext = new HashMap<>();
			plaintext.putAll(pMap.get(i));

			for (int j = 0; j < orderedSubkeys.size(); j++) {
				// put ciphertext through key
				String subkey = orderedSubkeys.get(j).keySet().toArray()[0].toString();
				String firstkeyHex = "" + subkey.charAt(0);
				String secondkeyHex = "" + subkey.charAt(1);

				String firstkeyBin = String.format("%4s", Integer.toBinaryString(Integer.parseInt(firstkeyHex, 16))).replace(' ', '0');
				String secondkeyBin = String.format("%4s", Integer.toBinaryString(Integer.parseInt(secondkeyHex, 16))).replace(' ', '0');

				for (int it = 0; it < 4; it++) {
					int xord = (ciphertext.get(it + 5) + Integer.parseInt("" + firstkeyBin.charAt(it))) % 2;
					ciphertext.put(it + 5, xord);
				}

				for (int it = 0; it < 4; it++) {
					int xord = (ciphertext.get(it + 13) + Integer.parseInt("" + secondkeyBin.charAt(it))) % 2;
					ciphertext.put(it + 13, xord);
				}

				// Put partial decrypt ciphertext through reverse S-boxes
				Map<Integer, Integer> subText = new HashMap<>();
				String subString = "";
				String fullString = "";
				for (int k = 1; k <= 16; k++) {
					if (k % 4 != 0) {
						subString += ciphertext.get(k);
					} else {
						subString += ciphertext.get(k);
						String hex = Integer.toHexString(Integer.parseInt(subString, 2));
						String newHex = util.revsub.get(hex);
						String newBinary = String.format("%4s", Integer.toBinaryString(Integer.parseInt(newHex, 16))).replace(' ', '0');
						fullString += newBinary;
						subString = "";
					}
				}
				for (int k = 0; k < fullString.length(); k++) {
					subText.put(k + 1, Integer.parseInt("" + fullString.charAt(k)));
				}

				// XOR U4,6 U4,8 U4,14 U4,16 P5 P7 P8
				int outcome = (subText.get(6) + subText.get(8) + subText.get(14) + subText.get(16) + subText.get(16)
						+ plaintext.get(5) + plaintext.get(7) + plaintext.get(8)) % 2;

				// If zero, increment count for subkey
				if (outcome == 0) {
					int count = orderedSubkeys.get(j).get(subkey);
					count += 1;
					orderedSubkeys.get(j).put(firstkeyHex + secondkeyHex, count);
				}
			}
		}

		// Print resulting count table
		System.out.println("Resulting count table: ");
		for (int i = 0; i < orderedSubkeys.size(); i++) {
			System.out.println(orderedSubkeys.get(i));
		}

	}

}
