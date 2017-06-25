package com.yatsukav.colorsort.sorts;

import java.util.Arrays;

/**
 * Counting sort is an algorithm for sorting a collection of objects according
 * to keys that are small integers; that is, it is an integer sorting algorithm.
 * It operates by counting the number of objects that have each distinct key
 * value, and using arithmetic on those counts to determine the positions of
 * each key value in the output sequence.
 * <p>
 * Family: Counting.
 * Space: An Array of length r.
 * Stable: True.
 * <p>
 * Average case = O(n+r) Worst case = O(n+r) Best case = O(n+r)
 * NOTE: r is the range of numbers (0 to r) to be sorted.
 * <p>
 * http://en.wikipedia.org/wiki/Counting_sort
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class ImageCountingSorter extends ImageSorter {

    @Override
    protected void sort(int[] unsorted, boolean withPersisting, int persistStep) {
        maxOutputImages = 0;

        int maxValue = Arrays.stream(unsorted)
                .max()
                .orElseThrow(() -> new IllegalArgumentException("No max value in unsorted array"));

        // updateCounts
        int[] counts = new int[maxValue + 1];
        for (int e : unsorted) {
            counts[e]++;
        }

        // populateCounts
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            int e = counts[i];
            while (e > 0) {
                unsorted[index++] = i;
                e--;

                maxOutputImages++;
                if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
            }
        }
    }
}