package com.yatsukav.colorsort.sorts;

/**
 * Merge sort is an O(n log n) comparison-based sorting algorithm. Most
 * implementations produce a stable sort, which means that the implementation
 * preserves the input order of equal elements in the sorted output.
 * <p>
 * Family: Merging.
 * Space: In-place.
 * Stable: True.
 * <p>
 * Average case = O(n*log n) Worst case = O(n*log n) Best case = O(n*log n)
 * <p>
 * http://en.wikipedia.org/wiki/Merge_sort
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class ImageMergeSorter extends ImageSorter {

    private boolean withPersisting;
    private int persistStep;

    public enum SPACE_TYPE {IN_PLACE, NOT_IN_PLACE}

    @Override
    protected void sort(int[] unsorted, boolean withPersisting, int persistStep) {
        // init
        maxOutputImages = 0;
        this.withPersisting = withPersisting;
        this.persistStep = persistStep;

        // sort
        sort(SPACE_TYPE.NOT_IN_PLACE, 0, unsorted.length, unsorted);
    }

    private void sort(SPACE_TYPE type, int start, int length, int[] unsorted) {
        if (length > 2) {
            int aLength = (int) Math.floor(length / 2);
            int bLength = length - aLength;
            sort(type, start, aLength, unsorted);
            sort(type, start + aLength, bLength, unsorted);
            if (type == SPACE_TYPE.IN_PLACE) {
                mergeInPlace(start, aLength, start + aLength, bLength, unsorted);
            } else {
                mergeWithExtraStorage(start, aLength, start + aLength, bLength, unsorted);
            }

            maxOutputImages++;
            if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
        } else if (length == 2) {
            int e = unsorted[start + 1];
            if (e < unsorted[start]) {
                unsorted[start + 1] = unsorted[start];
                unsorted[start] = e;
            }
        }
    }

    private void mergeInPlace(int aStart, int aLength, int bStart, int bLength, int[] unsorted) {
        int i = aStart;
        int j = bStart;
        int aSize = aStart + aLength;
        int bSize = bStart + bLength;
        while (i < aSize && j < bSize) {
            int a = unsorted[i];
            int b = unsorted[j];
            if (b < a) {
                // Shift everything to the right one spot
                System.arraycopy(unsorted, i, unsorted, i + 1, j - i);
                unsorted[i] = b;
                i++;
                j++;
                aSize++;
            } else {
                i++;
            }

            maxOutputImages++;
            if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
        }
    }

    private void mergeWithExtraStorage(int aStart, int aLength, int bStart, int bLength, int[] unsorted) {
        int count = 0;
        Integer[] output = new Integer[aLength + bLength];
        int i = aStart;
        int j = bStart;
        int aSize = aStart + aLength;
        int bSize = bStart + bLength;
        while (i < aSize || j < bSize) {
            Integer a = null;
            if (i < aSize) {
                a = unsorted[i];
            }
            Integer b = null;
            if (j < bSize) {
                b = unsorted[j];
            }
            if (a != null && b == null) {
                output[count++] = a;
                i++;
            } else if (b != null && a == null) {
                output[count++] = b;
                j++;
            } else if (b != null && b.compareTo(a) <= 0) {
                output[count++] = b;
                j++;
            } else {
                output[count++] = a;
                i++;
            }

            maxOutputImages++;
            if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
        }
        int x = 0;
        int size = aStart + aLength + bLength;
        for (int y = aStart; y < size; y++) {
            unsorted[y] = output[x++];
        }
    }
}