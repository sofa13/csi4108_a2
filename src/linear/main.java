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
		Map<Integer, Map<Integer, String>> pMap = new HashMap<>();
		Map<Integer, Map<Integer, String>> cMap = new HashMap<>();

		// Generate 10,000 plaintext
		int start = 22768;
		for (int i = 1; i <= 10000; i++) {
			Map<Integer, String> plaintext = new HashMap<>();
			String pString = String.format("%16s", Integer.toBinaryString(start+i)).replace(' ', '0');

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
				// Encrypt with key
				for (int a = 1; a <= text.size(); a++) {
					int newValue = (Integer.parseInt(text.get(a)) + util.key.get(a)) % 2;
					text.put(a, "" + newValue);
				}
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
						String newHex = util.sub.get(hex);
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
						int newKey = util.perm.get(k);
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
			
			// Encrypt with key once more
			for (int a = 1; a <= text.size(); a++) {
				int newValue = (Integer.parseInt(text.get(a)) + util.key.get(a)) % 2;
				text.put(a, "" + newValue);
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
		Map<Integer, Map<String, Integer>> orderedSubkeys = new HashMap<>();
		int count = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				Map<String, Integer> partialSubkeys = new HashMap<>();
				String firstHex = Integer.toHexString(i);
				String secondHex = Integer.toHexString(j);
				partialSubkeys.put(firstHex+secondHex, 0);
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
			Map<Integer, String> plaintext = pMap.get(i);
			
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
				Map<Integer, String> text = ciphertext;
				Map<Integer, String> subText = new HashMap<>();
				String subString = "";
				String fullString = "";
				for (int k = 1; k <= 16; k++) {
					if (k % 4 != 0) {
						subString += text.get(k);
					} else {
						subString += text.get(k);
						int foo2 = Integer.parseInt(subString, 2);
						String hex = Integer.toHexString(foo2);
						String newHex = util.revsub.get(hex);
						foo2 = Integer.parseInt(newHex, 16);
						String newBinary = String.format("%4s", Integer.toBinaryString(foo2)).replace(' ', '0');
						fullString += newBinary;
						subString = "";
					}
				}
				for (int k = 0; k < fullString.length(); k++) {
					String c = "" + fullString.charAt(k);
					subText.put(k + 1, c);
				}
				
				// XOR U4,6 U4,8 U4,14 U4,16 P5 P7 P8
				int outcome = (Integer.parseInt(subText.get(6)) + Integer.parseInt(subText.get(8)) + Integer.parseInt(subText.get(14))
							+ Integer.parseInt(subText.get(16)) + Integer.parseInt(subText.get(8)) + Integer.parseInt(plaintext.get(5))
							+ Integer.parseInt(plaintext.get(7) + Integer.parseInt(plaintext.get(8)))) % 2;
				
				// If zero increment count for subkey
				if (outcome == 0) {
					int count2 = orderedSubkeys.get(j).get(firstkeyHex+secondkeyHex);
					count2 += 1;
					orderedSubkeys.get(j).put(firstkeyHex+secondkeyHex, count2);
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
