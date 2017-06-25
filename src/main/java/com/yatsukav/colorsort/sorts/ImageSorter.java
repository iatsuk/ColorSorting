package com.yatsukav.colorsort.sorts;

import com.yatsukav.colorsort.StatusUpdater;
import com.yatsukav.colorsort.image.ImageData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public abstract class ImageSorter {
    protected long maxOutputImages;
    protected ImageData image;
    private String path;
    private Vector<String> images = new Vector<>();
    private int counter = 0;
    private StatusUpdater statusUpdater = null;

    public static ImageSorter of(String s) throws IOException {
        //@formatter:off
        switch (s) {
            case "Bubble Sort": return new ImageBubbleSorter();
            case "Quick Sort": return new ImageQuickSorter();
            case "Heap Sort": return new ImageHeapSorter();
            case "Counting Sort": return new ImageCountingSorter();
            case "Radix Sort": return new ImageRadixSorter();
            case "American Flag Sort": return new ImageAmericanFlagSorter();
            default: throw new IllegalArgumentException("Unknown ImageSorter type: " + s);
        }
        //@formatter:on
    }

    public ImageSorter setImage(ImageData image) {
        this.image = image;
        return this;
    }

    public ImageSorter setPath(String path) {
        this.path = path;
        return this;
    }

    public ImageSorter setStatusUpdater(StatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
        return this;
    }

    public void save(int persistStep) throws IOException {
        if (image == null) throw new IllegalStateException("Image data not defined");
        if (path == null) throw new IllegalStateException("Output path not defined");
        Files.createDirectory(Paths.get(path));
        sort(persistStep);
    }

    public Vector<String> getImages() {
        return images;
    }

    void persistStep(int[] data) {
        try {
            if (statusUpdater != null) {
                statusUpdater.setMessage("Draw frames... " + counter + "/" + statusUpdater.getMaxSteps());
                statusUpdater.setCurrStep(counter);
            }
            image.setColors(data);
            File imgPath = Paths.get(path, "i" + counter++ + ".jpg").toFile();
            image.save("jpeg", imgPath);
            images.add(imgPath.toString());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public long calcMaxOutputImages() {
        sort(image.getColors(), false, Integer.MAX_VALUE);
        return maxOutputImages;
    }

    protected void sort(int persistStep) {
        sort(image.getColors(), true, persistStep);
    }

    protected abstract void sort(int[] unsorted, boolean withPersisting, int persistStep);
}
