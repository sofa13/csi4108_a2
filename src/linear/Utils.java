package linear;

import java.util.HashMap;
import java.util.Map;

public class Utils {
	Map<Integer, Integer> key = new HashMap<>();
	Map<String, String> sub = new HashMap<>();
	Map<String, String> revsub = new HashMap<>();
	Map<Integer, Integer> perm = new HashMap<>();
	public Utils() {
		// TODO Auto-generated constructor stub
		key = new HashMap<>();
		sub = new HashMap<>();
		revsub = new HashMap<>();
		perm = new HashMap<>();

		// create key
		key.put(1, 1);
		key.put(2, 0);
		key.put(3, 1);
		key.put(4, 0);
		key.put(5, 1);
		key.put(6, 0);
		key.put(7, 1);
		key.put(8, 0);
		key.put(9, 1);
		key.put(10, 0);
		key.put(11, 1);
		key.put(12, 0);
		key.put(13, 1);
		key.put(14, 0);
		key.put(15, 1);
		key.put(16, 0);

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
		sub.put(Integer.toHexString(11), Integer.toHexString(12));
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
		revsub.put(Integer.toHexString(12), Integer.toHexString(11));
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

	}

}
