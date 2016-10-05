package linear;

import java.util.HashMap;
import java.util.Map;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		
		Map<String, String> sub = new HashMap<>();
		Map<Integer, String> perm = new HashMap<>();
		
		// S-box substitution
		sub.put(Integer.toHexString(0), Integer.toHexString(14));
		sub.put(Integer.toHexString(1), Integer.toHexString(4));
		sub.put(Integer.toHexString(2), Integer.toHexString(13));
		sub.put(Integer.toHexString(3), Integer.toHexString(1));
		sub.put(Integer.toHexString(4), Integer.toHexString(2));
		sub.put(Integer.toHexString(5), Integer.toHexString(15));
		sub.put(Integer.toHexString(6), Integer.toHexString(11));
		sub.put(Integer.toHexString(7), Integer.toHexString(18));
		sub.put(Integer.toHexString(8), Integer.toHexString(3));
		sub.put(Integer.toHexString(9), Integer.toHexString(10));
		sub.put(Integer.toHexString(10), Integer.toHexString(6));
		sub.put(Integer.toHexString(11), Integer.toHexString(13));
		sub.put(Integer.toHexString(12), Integer.toHexString(5));
		sub.put(Integer.toHexString(13), Integer.toHexString(9));
		sub.put(Integer.toHexString(14), Integer.toHexString(0));
		sub.put(Integer.toHexString(15), Integer.toHexString(7));
		
		// Permutations
		perm.put(0, Integer.toHexString(1));
		perm.put(1, Integer.toHexString(5));
		perm.put(2, Integer.toHexString(9));
		perm.put(3, Integer.toHexString(13));
		perm.put(4, Integer.toHexString(2));
		perm.put(5, Integer.toHexString(6));
		perm.put(6, Integer.toHexString(10));
		perm.put(7, Integer.toHexString(14));
		perm.put(8, Integer.toHexString(3));
		perm.put(9, Integer.toHexString(7));
		perm.put(10, Integer.toHexString(11));
		perm.put(11, Integer.toHexString(15));
		perm.put(12, Integer.toHexString(4));
		perm.put(13, Integer.toHexString(8));
		perm.put(14, Integer.toHexString(12));
		perm.put(15, Integer.toHexString(16));
		
		Map<Integer, String> plaintext = new HashMap<>();
		Map<Integer, Map<Integer, String>> pMap = new HashMap<>();
		int row = 0;
		for (int i=1; i<160000; i++) {
			if (i % 16 == 0) {
				pMap.put(row, plaintext);
				row += 1;
				plaintext = new HashMap<>();
			}	
			plaintext.put(i, Integer.toHexString(i));
		}
//	    System.out.println(pMap);

	}

}
