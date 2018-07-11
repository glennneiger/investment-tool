package com.cloud99.invest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Solution {

	// sort by frequency of occurrence
	// sort by value (asc)

	public static void customSort(int[] arr) {
		// {3,1,2,2,4};
		
		// key = value, value = value count
		Map<Integer, Integer> map = new HashMap<>();
		
		for (int i = 0; i < arr.length; i++) {
			if (!map.containsKey(arr[i])) {
				map.put(arr[i], 1);
			} else {
				// map has value, get and increment the count
				int count = map.get(arr[i]) + 1;
				map.put(arr[i], count);
			}
		}

		// custom comparator to help sort by frequency count
		final class CountComparator implements Comparator<Integer> {

			Map<Integer, Integer> map;

			public CountComparator(Map<Integer, Integer> array) {
				this.map = array;
			}

			@Override
			public int compare(Integer o1, Integer o2) {
				Integer val1 = map.get(o1);
				Integer val2 = map.get(o2);

				int compValue = val1.compareTo(val2);

				return compValue == 0 ? o1.compareTo(o2) : compValue;
			}
		}

		CountComparator comp = new CountComparator(map);
		TreeMap<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(comp);
		sortedMap.putAll(map);

		for (Integer i : sortedMap.keySet()) {
			int frequencey = sortedMap.get(i);
			for (int count = 1; count <= frequencey; count++) {
				System.out.println(i);
			}
		}

	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// int arrCount = scanner.nextInt();
		// scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
		//
		// int[] arr = new int[arrCount];
		//
		// for (int i = 0; i < arrCount; i++) {
		// int arrItem = scanner.nextInt();
		// scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
		// arr[i] = arrItem;
		// }
		//
		// customSort(arr);
		//
		// scanner.close();
		// }

		Solution s = new Solution();
		int arry[] = { 3, 1, 2, 2, 4 };
		Solution.customSort(arry);

		// group values by count
		// List<Entry<Integer, Integer>> entriesList = new ArrayList<>(map.entrySet());
		//
		// // sort the new array by comparing the count number
		// entriesList.sort(Entry.comparingByValue());
		//
		// // final fully sorted array
		// int finalArray[] = new int[arr.length];
		//
		// entriesList.stream().forEach(val -> {
		// for (int i = 0; i < val.getValue().intValue(); i++) {
		//
		// }
		// });
	}

}
