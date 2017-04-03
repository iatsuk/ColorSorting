package com.yatsukav.colorsort;

import com.yatsukav.colorsort.movie.MovieMaker;
import com.yatsukav.colorsort.sorts.ImageSorter;
import com.yatsukav.colorsort.ui.MainWindow;

import java.io.File;

public final class App {

    private App() {
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            MainWindow.getInstance().show();
        } else {
            start(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
        }
    }

    public static void start(String inputPath, String outputPath, String sortMethod, String videoResolution, int maxDuration) {
        try {
            ImageData imageData = new ImageData().load(new File(inputPath).toURI());
            ImageSorter imageSorter = ImageSorter.of(sortMethod).setImage(imageData);

            int maxOutputImages = imageSorter.calcMaxOutputImages();
            int frameRate = 60;
            int step = 1;
            if (maxOutputImages > frameRate * maxDuration) {
                step = maxOutputImages / (frameRate * maxDuration);
            } else {
                frameRate = maxOutputImages / maxDuration;
            }
            System.out.println("maxOutputImages: " + maxOutputImages);
            System.out.println("frameRate: " + frameRate);
            System.out.println("step: " + step);

            imageSorter.setPath("tmp").save(step);

            int width, height;
            width = Integer.parseInt(videoResolution.replaceAll("[\\D+]", ""));
            height = getNewHeight(imageData.getWidth(), imageData.getHeight(), width);

            MovieMaker.makeVideo(outputPath, imageSorter.getImages(), width, height, frameRate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static int getNewHeight(int width, int height, int newWidth) {
        return (int) (height * ((double) newWidth / width));
    }
}
