package com.yatsukav.colorsort;

import com.yatsukav.colorsort.movie.MovieMaker;
import com.yatsukav.colorsort.sorts.ImageSorter;
import com.yatsukav.colorsort.ui.MainWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class App {

    private App() {
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            MainWindow.getInstance().show();
        } else {
            start(args[0], args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        }
    }

    public static void start(String inputPath, String outputPath, String sortMethod, int videoResolution, int maxDuration) {
        try {
            ImageData imageData = new ImageData().load(new File(inputPath).toURI());
            ImageSorter imageSorter = ImageSorter.of(sortMethod).setImage(imageData);

            long maxOutputImages = imageSorter.calcMaxOutputImages();
            int frameRate = 30;
            int step = 1;
            if (maxOutputImages > frameRate * maxDuration) {
                step = (int) (maxOutputImages / (frameRate * maxDuration));
            } else {
                frameRate = (int) (maxOutputImages / maxDuration);
            }
            System.out.println("Max output images: " + maxOutputImages);
            System.out.println("Frame rate: " + frameRate);
            System.out.println("Step: " + step);

            imageSorter.setPath("tmp").save(step); // TODO: 04.04.17 path

            int width, height;
            width = videoResolution;
            height = getNewHeight(imageData.getWidth(), imageData.getHeight(), width);
            System.out.println("Output resolution: " + height + "x" + width);

            System.out.println("Images: ");
            imageSorter.getImages().forEach(System.out::println);

            MovieMaker.makeVideo(outputPath, imageSorter.getImages(), imageData.getWidth(), imageData.getHeight(), frameRate);

            delete(new File("tmp"));
            delete(new File("jmf.log"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static int getNewHeight(int width, int height, int newWidth) {
        return (int) (height * ((double) newWidth / width));
    }

    static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) delete(c);
        }
        if (!f.delete()) throw new FileNotFoundException("Failed to delete file: " + f);
    }
}
