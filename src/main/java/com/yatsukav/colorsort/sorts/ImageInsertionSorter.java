package com.yatsukav.colorsort.sorts;

/**
 * Insertion sort is a simple sorting algorithm: a comparison sort in which the
 * sorted array (or list) is built one entry at a time. It is much less
 * efficient on large lists than more advanced algorithms such as quicksort,
 * heapsort, or merge sort.
 * <p>
 * Family: Insertion.
 * Space: In-place.
 * Stable: True.
 * <p>
 * Average case = O(n^2) Worst case = O(n^2) Best case = O(n)
 * <p>
 * http://en.wikipedia.org/wiki/Insertion_sort
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class ImageInsertionSorter extends ImageSorter {

    private boolean withPersisting;
    private int persistStep;

    @Override
    protected void sort(int[] unsorted, boolean withPersisting, int persistStep) {
        // init
        maxOutputImages = 0;
        this.withPersisting = withPersisting;
        this.persistStep = persistStep;

        // sort
        int length = unsorted.length;
        for (int i = 1; i < length; i++) {
            sort(i, unsorted);
        }
    }

    private void sort(int i, int[] unsorted) {
        for (int j = i; j > 0; j--) {
            int jthElement = unsorted[j];
            int jMinusOneElement = unsorted[j - 1];
            if (jthElement < jMinusOneElement) {
                unsorted[j - 1] = jthElement;
                unsorted[j] = jMinusOneElement;

                maxOutputImages++;
                if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
            } else {
                break;
            }
        }
    }
}
