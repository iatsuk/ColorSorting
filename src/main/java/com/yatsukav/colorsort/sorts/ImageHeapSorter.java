package com.yatsukav.colorsort.sorts;

/**
 * Heapsort is a comparison-based sorting algorithm to create a sorted array (or
 * list), and is part of the selection sort family. Although somewhat slower in
 * practice on most machines than a well-implemented quicksort, it has the
 * advantage of a more favorable worst-case O(n log n) runtime.
 * <p>
 * Family: Selection.
 * Space: In-place.
 * Stable: False.
 * <p>
 * Average case = O(n*log n) Worst case = O(n*log n) Best case = O(n*log n)
 * <p>
 * http://en.wikipedia.org/wiki/Heap_sort
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class ImageHeapSorter extends ImageSorter {

    private boolean withPersisting;
    private int persistStep;

    @Override
    protected void sort(int[] unsorted, boolean withPersisting, int persistStep) {
        // init
        maxOutputImages = 0;
        this.withPersisting = withPersisting;
        this.persistStep = persistStep;

        // sort
        createHeap(unsorted);
        sortHeap(unsorted);
    }

    private void sortHeap(int[] unsorted) {
        int length = unsorted.length;
        for (int index = length - 1; index > 0; index--) {
            swap(0, index, unsorted); // swap root with the last heap element
            int i = 0; // index of the element being moved down the tree
            while (true) {
                int left = (i * 2) + 1;
                if (left >= index) // node has no left child
                    break;
                int right = left + 1;
                if (right >= index) { // node has a left child, but no right child
                    if (unsorted[left] > unsorted[i])
                        swap(left, i, unsorted); // if left child is greater than node
                    break;
                }
                int ithElement = unsorted[i];
                int leftElement = unsorted[left];
                int rightElement = unsorted[right];
                if (ithElement < leftElement) { // (left > i)
                    if (unsorted[left] > rightElement) { // (left > right)
                        swap(left, i, unsorted);
                        i = left;
                        continue;
                    }
                    // (left > i)
                    swap(right, i, unsorted);
                    i = right;
                    continue;
                }
                // (i > left)
                if (rightElement > ithElement) {
                    swap(right, i, unsorted);
                    i = right;
                    continue;
                }
                // (n > left) & (n > right)
                break;
            }
        }
    }

    private void createHeap(int[] unsorted) {
        // Creates a max heap
        int size = 0;
        int length = unsorted.length;
        for (int i = 0; i < length; i++) {
            int e = unsorted[i];
            size = add(size, e, unsorted);
        }
    }

    private int add(int size, int element, int[] unsorted) {
        int length = size;
        int i = length;
        unsorted[length++] = element;
        int e = unsorted[i];
        int parentIndex = ((i - 1) / 2);
        int parent = unsorted[parentIndex];
        while (e > parent) {
            swap(parentIndex, i, unsorted);
            i = parentIndex;
            e = unsorted[i];
            parentIndex = ((i - 1) / 2);
            parent = unsorted[parentIndex];
        }
        return length;
    }

    private void swap(int parentIndex, int childIndex, int[] unsorted) {
        int parent = unsorted[parentIndex];
        unsorted[parentIndex] = unsorted[childIndex];
        unsorted[childIndex] = parent;

        maxOutputImages++;
        if (withPersisting && maxOutputImages % persistStep == 0) persistStep(unsorted);
    }
}