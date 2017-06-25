package com.yatsukav.colorsort.sorts;

/**
 * The Shell sort (also known as Shellsort or Shell's method) is named after its inventor, Donald Shell, who published
 * the algorithm in 1959. Shell sort is a sequence of interleaved insertion sorts based on an increment sequence.
 * The increment size is reduced after each pass until the increment size is 1.With an increment size of 1, the sort
 * is a basic insertion sort, but by this time the data is guaranteed to be almost sorted, which is insertion
 * sort's "best case".  Any sequence will sort the data as long as it ends in 1, but some work better than others.
 * Empirical studies have shown a geometric increment sequence with a ratio of about 2.2 work well in practice.
 */
public class ImageShellSorter extends ImageSorter {

    @Override
    protected void sort(int[] unsorted, boolean withPersisting, int persistStep) {
        // init
        maxOutputImages = 0;

        // sort
        int increment = unsorted.length / 2;
        while (increment > 0) {
            for (int i = increment; i < unsorted.length; i++) {
                int j = i;
                int temp = unsorted[i];
                while (j >= increment && unsorted[j - increment] > temp) {
                    unsorted[j] = unsorted[j - increment];
                    j = j - increment;

                    maxOutputImages++;
                    if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
                }
                unsorted[j] = temp;

                maxOutputImages++;
                if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }
    }
}