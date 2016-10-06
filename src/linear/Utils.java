package linear;

import java.util.HashMap;
import java.util.Map;

public class Utils {
	Map<Integer, Map<Integer, Integer>> key = new HashMap<>();
	Map<String, String> sub = new HashMap<>();
	Map<String, String> revsub = new HashMap<>();
	Map<Integer, Integer> perm = new HashMap<>();
	public Utils() {
		// TODO Auto-generated constructor stub
		key = new HashMap<>();
		sub = new HashMap<>();
		revsub = new HashMap<>();
		perm = new HashMap<>();

		// create keys
		Map<Integer, Integer> key1 = new HashMap<>();
		Map<Integer, Integer> key2 = new HashMap<>();
		Map<Integer, Integer> key3 = new HashMap<>();
		Map<Integer, Integer> key4 = new HashMap<>();
		Map<Integer, Integer> key5 = new HashMap<>();
		
		key1.put(1, 1);
		key1.put(2, 0);
		key1.put(3, 1);
		key1.put(4, 0);
		key1.put(5, 1);	// 1
		key1.put(6, 0);
		key1.put(7, 1); // 1
		key1.put(8, 0); // 0
		key1.put(9, 1);
		key1.put(10, 0);
		key1.put(11, 1);
		key1.put(12, 0);
		key1.put(13, 1);
		key1.put(14, 0);
		key1.put(15, 1);
		key1.put(16, 0);
		
		key2.put(1, 1);
		key2.put(2, 1);
		key2.put(3, 1);
		key2.put(4, 0);
		key2.put(5, 1);
		key2.put(6, 0); // 0
		key2.put(7, 1);
		key2.put(8, 0);
		key2.put(9, 0);
		key2.put(10, 0);
		key2.put(11, 1);
		key2.put(12, 0);
		key2.put(13, 1);
		key2.put(14, 1);
		key2.put(15, 1);
		key2.put(16, 0);
		
		key3.put(1, 0);
		key3.put(2, 1);
		key3.put(3, 1);
		key3.put(4, 0);
		key3.put(5, 0);
		key3.put(6, 1); // 1
		key3.put(7, 1);
		key3.put(8, 1);
		key3.put(9, 1);
		key3.put(10, 0);
		key3.put(11, 1);
		key3.put(12, 0);
		key3.put(13, 1);
		key3.put(14, 0); // 0
		key3.put(15, 0);
		key3.put(16, 1);
		
		key4.put(1, 0);
		key4.put(2, 1);
		key4.put(3, 0);
		key4.put(4, 1); // 1
		key4.put(5, 0);
		key4.put(6, 1);
		key4.put(7, 0);
		key4.put(8, 1); // 1
		key4.put(9, 0);
		key4.put(10, 1);
		key4.put(11, 0);
		key4.put(12, 1);
		key4.put(13, 0);
		key4.put(14, 1); // 1
		key4.put(15, 0);
		key4.put(16, 1); // 1
		
		key5.put(1, 0);
		key5.put(2, 1);
		key5.put(3, 0);
		key5.put(4, 1);
		key5.put(5, 1); // 1011 (B)
		key5.put(6, 0); // 1011 (B)
		key5.put(7, 1); // 1011 (B)
		key5.put(8, 1); // 1011 (B)
		key5.put(9, 0);
		key5.put(10, 1);
		key5.put(11, 0);
		key5.put(12, 1);
		key5.put(13, 0); // 0111 (7)
		key5.put(14, 1); // 0111 (7)
		key5.put(15, 1); // 0111 (7)
		key5.put(16, 1); // 0111 (7)
		
		key.put(1, key1);
		key.put(2, key2);
		key.put(3, key3);
		key.put(4, key4);
		key.put(5, key5);

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
