package com.yatsukav.colorsort.sorts;

import java.util.Random;

/**
 * Quicksort is a sorting algorithm which, on average, makes O(n*log n) comparisons to sort
 * n items. In the worst case, it makes O(n^2) comparisons, though this behavior is
 * rare. Quicksort is often faster in practice than other algorithms.
 * <p>
 * Family: Divide and conquer.
 * Space: In-place.
 * Stable: False.
 * <p>
 * Average case = O(n*log n), Worst case = O(n^2), Best case = O(n) [three-way partition and equal keys]
 * <p>
 * http://en.wikipedia.org/wiki/Quick_sort
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class ImageQuickSorter extends ImageSorter {
    private static final Random RAND = new Random();
    private PIVOT_TYPE type = PIVOT_TYPE.RANDOM;
    private long maxOutputImages;

    @Override
    public long calcMaxOutputImages() {
        sort(image.getColors(), false, Integer.MAX_VALUE);
        return maxOutputImages;
    }

    @Override
    protected void sort(int persistStep) {
        sort(image.getColors(), true, persistStep);
    }

    private void sort(int[] unsorted, boolean withPersisting, int persistStep) {
        maxOutputImages = 0;
        sort(getRandom(unsorted.length), 0, unsorted.length - 1, unsorted, withPersisting, persistStep);
    }

    private void sort(int index, int start, int finish, int[] unsorted, boolean withPersisting, int persistStep) {
        int pivotIndex = start + index;
        int pivot = unsorted[pivotIndex];
        int s = start;
        int f = finish;
        while (s <= f) {
            while (unsorted[s] < pivot)
                s++;
            while (unsorted[f] > pivot)
                f--;
            if (s <= f) {
                swap(s, f, unsorted);
                s++;
                f--;
                maxOutputImages++;
                if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
            }
        }
        if (start < f) {
            pivotIndex = getRandom((f - start) + 1);
            sort(pivotIndex, start, f, unsorted, withPersisting, persistStep);
        }
        if (s < finish) {
            pivotIndex = getRandom((finish - s) + 1);
            sort(pivotIndex, s, finish, unsorted, withPersisting, persistStep);
        }
    }

    private int getRandom(int length) {
        if (type == PIVOT_TYPE.RANDOM && length > 0)
            return RAND.nextInt(length);
        if (type == PIVOT_TYPE.FIRST && length > 0)
            return 0;
        return length / 2;
    }

    private void swap(int index1, int index2, int[] unsorted) {
        int index2Element = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = index2Element;
    }

    public enum PIVOT_TYPE {
        FIRST, MIDDLE, RANDOM
    }
}