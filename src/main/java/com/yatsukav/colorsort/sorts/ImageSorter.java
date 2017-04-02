package com.yatsukav.colorsort.sorts;

import com.yatsukav.colorsort.ImageData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Vector;

public abstract class ImageSorter {
    protected final ImageData image;
    private final Path tmpFolder;
    private Vector<String> images = new Vector<>();
    private int counter = 0;

    public ImageSorter(ImageData image) throws IOException {
        this.image = image;
        tmpFolder = Files.createTempDirectory("color_sort");
        System.out.println(tmpFolder.toAbsolutePath());
    }

    public abstract int[] sort(int[] unsorted);

    protected void persistStep(int[] data) {
        try {
            image.setColors(data);
            String path = tmpFolder.toAbsolutePath().toString() + File.separator + "i" + counter++;
            image.save("jpeg", new File(path));
            images.add(path);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Vector<String> getImages() {
        return images;
    }

    public static ImageSorter of(String s, ImageData image) throws IOException {
        switch (s) {
            case "Bubble Sort": return new ImageBubbleSorter(image);
            case "Quick Sort": return new ImageQuickSorter(image);
            default: throw new IllegalArgumentException("Unknown ImageSorter type: " + s);
        }
    }
}
