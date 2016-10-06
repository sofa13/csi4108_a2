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
		Map<Integer, String> pMap = new HashMap<>();
		Map<Integer, String> cMap = new HashMap<>();

		// Generate 10,000 plaintext
		// int start = 22768;
		for (int i = 0; i < 10000; i++) {
			String plaintext = String.format("%16s", Integer.toBinaryString(i * 3)).replace(' ', '0');
			pMap.put(i, plaintext);
		}

		// Print plaintext table lookup size
		System.out.println("Number of plaintext generated: " + pMap.size());

		// Generate ciphertext for all plaintext

		for (int i = 0; i < pMap.size(); i++) {
			String text = pMap.get(i);

			for (int round = 1; round <= 4; round++) {
				// Encrypt with key
				String temp = "";
				for (int a = 0; a < text.length(); a++) {
					temp += (Integer.parseInt("" + text.charAt(a)) + util.key.get(round).get(a+1)) % 2;
				}
				text = temp;

				// sub
				String subString = "";
				String fullString = "";
				for (int j = 1; j <= 16; j++) {
					if (j % 4 != 0) {
						subString += text.charAt(j-1);
					} else {
						subString += text.charAt(j-1);
						String hex = Integer.toHexString(Integer.parseInt(subString, 2));
						String newHex = util.sub.get(hex);
						String newBinary = String.format("%4s", Integer.toBinaryString(Integer.parseInt(newHex, 16))).replace(' ', '0');
						fullString += newBinary;
						subString = "";
					}
				}

				// perm
				String permString = "";
				if (round < 4) {
					for (int k = 0; k < 16; k++) {
						permString += fullString.charAt(util.perm.get(k+1)-1);
					}
				} else {
					permString = fullString;
				}

				text = permString;
			}

			// Encrypt with key round 5 more
			String temp = "";
			for (int a = 0; a < text.length(); a++) {
				temp += (Integer.parseInt("" + text.charAt(a)) + util.key.get(5).get(a+1)) % 2;
			}
			text = temp;
			cMap.put(i, text);
		}

		// Print ciphertext table lookup size
		System.out.println("Number of ciphertext generated: " + cMap.size());

		// Print 10 known plaintext cipher text pairs
		System.out.println("10 known plaintext cipher text pairs: ");
		for (int i = 0; i < 10; i++) {
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
		for (int i = 0; i < 10; i++) {
			System.out.println(orderedSubkeys.get(i).keySet().toArray()[0]);
		}
		System.out.println("...");

		// Go through all ciphertext plaintext pairs
		for (int i = 0; i < cMap.size(); i++) {
			String ciphertext = cMap.get(i);
			String plaintext = pMap.get(i);

			for (int j = 0; j < orderedSubkeys.size(); j++) {
				// put ciphertext through key
				String subkey = orderedSubkeys.get(j).keySet().toArray()[0].toString();
				String firstkeyHex = "" + subkey.charAt(0);
				String secondkeyHex = "" + subkey.charAt(1);

				String firstkeyBin = String.format("%4s", Integer.toBinaryString(Integer.parseInt(firstkeyHex, 16))).replace(' ', '0');
				String secondkeyBin = String.format("%4s", Integer.toBinaryString(Integer.parseInt(secondkeyHex, 16))).replace(' ', '0');

				String temp = "";
				for (int it = 0; it < 16; it++) {
					if (it >= 4 && it <= 7) {
						int xord = (Integer.parseInt("" + ciphertext.charAt(it)) + Integer.parseInt("" + firstkeyBin.charAt(it-4))) % 2;
						temp += xord;
					} else if (it >= 12 && it <= 15) {
						int xord = (Integer.parseInt("" + ciphertext.charAt(it)) + Integer.parseInt("" + secondkeyBin.charAt(it-12))) % 2;
						temp += xord;
					} else {
						temp += ciphertext.charAt(it);
					}
				}
				ciphertext = temp;

				// Put partial decrypt ciphertext through reverse S-boxes
				String subString = "";
				String subText = "";
				for (int k = 1; k <= 16; k++) {
					if (k % 4 != 0) {
						subString += ciphertext.charAt(k-1);
					} else {
						subString += ciphertext.charAt(k-1);
						String hex = Integer.toHexString(Integer.parseInt(subString, 2));
						String newHex = util.revsub.get(hex);
						String newBinary = String.format("%4s", Integer.toBinaryString(Integer.parseInt(newHex, 16))).replace(' ', '0');
						subText += newBinary;
						subString = "";
					}
				}

				// XOR U4,6 U4,8 U4,14 U4,16 P5 P7 P8
				int u6 = Integer.parseInt("" + subText.charAt(6-1));
				int u8 = Integer.parseInt("" + subText.charAt(8-1));
				int u14 = Integer.parseInt("" + subText.charAt(14-1));
				int u16 = Integer.parseInt("" + subText.charAt(16-1));
				int p5 = Integer.parseInt("" + plaintext.charAt(5-1));
				int p7 = Integer.parseInt("" + plaintext.charAt(7-1));
				int p8 = Integer.parseInt("" + plaintext.charAt(8-1));
				int outcome = (u6 + u8 + u14 + u16 + p5 + p7 + p8) % 2;

				// If zero, increment count for subkey
				if (outcome == 0) {
					int count = orderedSubkeys.get(j).get(subkey);
					count += 1;
					orderedSubkeys.get(j).put(subkey, count);
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
