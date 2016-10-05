package linear;

import java.util.HashMap;
import java.util.Map;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		
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
