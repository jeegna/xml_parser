package com.esf.xmlParser.util;

import java.util.List;

/**
 * @author jeegna
 *
 */
public class ListUtilities {

	/**
	 * Uses a binary search algorithm to search through the given list. The
	 * array must be full to capacity and sorted in ascending order
	 * 
	 * @param list
	 *            The list in which to search
	 * @param key
	 *            The search key
	 * @return The index at which the key occurs in the list. Will return
	 *         negative insertion point + 1 if the key is not found in the list
	 */
	public static <T> int binarySearch(List<? extends Comparable<T>> list, T key) {

		int low = 0;
		int high = list.size() - 1;

		while (high >= low) {
			int middle = (low + high) / 2;

			if (list.get(middle).compareTo(key) == 0) {
				return middle;
			}
			if (list.get(middle).compareTo(key) < 0) {
				low = middle + 1;
			}
			if (list.get(middle).compareTo(key) > 0) {
				high = middle - 1;
			}
		}
		return -(low + 1);
	}
}
