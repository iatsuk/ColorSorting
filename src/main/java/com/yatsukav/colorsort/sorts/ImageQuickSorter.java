package com.yatsukav.colorsort.sorts;

import com.yatsukav.colorsort.ImageData;

import java.io.IOException;
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
    public PIVOT_TYPE type = PIVOT_TYPE.RANDOM;

    public ImageQuickSorter(ImageData image) throws IOException {
        super(image);
    }


    @Override
    public int[] sort(int[] unsorted) {
        return sort(PIVOT_TYPE.RANDOM, unsorted);
    }

    public int[] sort(PIVOT_TYPE pivotType, int[] unsorted) {
        int pivot = 0;
        if (pivotType == PIVOT_TYPE.MIDDLE) {
            pivot = unsorted.length / 2;
        } else if (pivotType == PIVOT_TYPE.RANDOM) {
            pivot = getRandom(unsorted.length);
        }
        sort(pivot, 0, unsorted.length - 1, unsorted);
        return unsorted;
    }

    private void sort(int index, int start, int finish, int[] unsorted) {
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
                persistStep(unsorted);
            }
        }
        if (start < f) {
            pivotIndex = getRandom((f - start) + 1);
            sort(pivotIndex, start, f, unsorted);
        }
        if (s < finish) {
            pivotIndex = getRandom((finish - s) + 1);
            sort(pivotIndex, s, finish, unsorted);
        }
    }

    private final int getRandom(int length) {
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