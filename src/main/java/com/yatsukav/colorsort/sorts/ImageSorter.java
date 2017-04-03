package com.yatsukav.colorsort.sorts;

import com.yatsukav.colorsort.ImageData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Vector;

public abstract class ImageSorter {
    protected ImageData image;
    private String path;
    private Vector<String> images = new Vector<>();
    private int counter = 0;

    public static ImageSorter of(String s) throws IOException {
        switch (s) {
            case "Bubble Sort": return new ImageBubbleSorter();
            case "Quick Sort": return new ImageQuickSorter();
            default: throw new IllegalArgumentException("Unknown ImageSorter type: " + s);
        }
    }

    public ImageSorter setImage(ImageData image) {
        this.image = image;
        return this;
    }

    public ImageSorter setPath(String path) {
        this.path = path;
        return this;
    }

    public void save(int persistStep) {
        if (image == null) throw new IllegalStateException("Image data not defined");
        if (path == null) throw new IllegalStateException("Output path not defined");
        sort(persistStep);
    }

    public Vector<String> getImages() {
        return images;
    }

    protected void persistStep(int[] data) {
        try {
            image.setColors(data);
            File imgPath = Paths.get(path, "i" + counter++ + ".jpg").toFile();
            image.save("jpeg", imgPath);
            images.add(path);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public abstract int calcMaxOutputImages();

    protected abstract void sort(int persistStep);
}
