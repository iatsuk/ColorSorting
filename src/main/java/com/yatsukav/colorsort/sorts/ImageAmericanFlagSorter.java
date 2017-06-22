package com.yatsukav.colorsort.sorts;

/**
 * An American flag sort is an efficient, in-place variant of radix sort that
 * distributes items into hundreds of buckets. Non-comparative sorting
 * algorithms such as radix sort and American flag sort are typically used to
 * sort large objects such as strings, for which comparison is not a unit-time
 * operation.
 * <p>
 * Family: Bucket.
 * Space: In-place.
 * Stable: False.
 * <p>
 * Average case = O(n*k/d) Worst case = O(n*k/d) Best case = O(n*k/d)
 * NOTE: n is the number of digits and k is the average bucket size
 * <p>
 * http://en.wikipedia.org/wiki/American_flag_sort
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class ImageAmericanFlagSorter extends ImageSorter {
    private static final int NUMBER_OF_BUCKETS = 10; // 10 for base 10 numbers

    @Override
    protected void sort(int[] unsorted, boolean withPersisting, int persistStep) {
        maxOutputImages = 0;
        int numberOfDigits = getMaxNumberOfDigits(unsorted); // Max number of digits
        int max = (int) Math.pow(10, numberOfDigits);
        sort(unsorted, 0, unsorted.length, max, withPersisting, persistStep);
    }

    private void sort(int[] unsorted, int start, int length, int divisor, boolean withPersisting, int persistStep) {
        // First pass - find counts
        int[] count = new int[NUMBER_OF_BUCKETS];
        int[] offset = new int[NUMBER_OF_BUCKETS];
        int digit = 0;
        for (int i = start; i < length; i++) {
            int d = unsorted[i];
            digit = getDigit(d, divisor);
            count[digit]++;
        }

        offset[0] = start + 0;
        for (int i = 1; i < NUMBER_OF_BUCKETS; i++) {
            offset[i] = count[i - 1] + offset[i - 1];
        }

        // Second pass - move into position
        for (int b = 0; b < NUMBER_OF_BUCKETS; b++) {
            while (count[b] > 0) {
                int origin = offset[b];
                int from = origin;
                int num = unsorted[from];
                unsorted[from] = -1;
                do {
                    digit = getDigit(num, divisor);
                    int to = offset[digit]++;
                    count[digit]--;
                    int temp = unsorted[to];
                    unsorted[to] = num;
                    num = temp;
                    from = to;
                } while (from != origin);
                maxOutputImages++;
                if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
            }
        }

        if (divisor > 1) {
            // Sort the buckets
            for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
                int begin = (i > 0) ? offset[i - 1] : start;
                int end = offset[i];
                if (end - begin > 1) {
                    sort(unsorted, begin, end, divisor / 10, withPersisting, persistStep);
                }
            }
        }
    }

    private int getMaxNumberOfDigits(int[] unsorted) {
        int max = Integer.MIN_VALUE;
        int temp = 0;
        for (int i : unsorted) {
            temp = (int) Math.log10(i) + 1;
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    private static int getDigit(int integer, int divisor) {
        return (integer / divisor) % 10;
    }
}