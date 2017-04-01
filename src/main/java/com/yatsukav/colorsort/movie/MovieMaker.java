package com.yatsukav.colorsort.movie;

import javax.media.MediaLocator;
import java.net.MalformedURLException;
import java.util.Vector;

public class MovieMaker {

    public static void makeVideo(String fileName) throws MalformedURLException {
        Vector<String> imgLst = new Vector<>(); // paths

        JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
        MediaLocator oml;
        if ((oml = JpegImagesToMovie.createMediaLocator(fileName)) == null) {
            System.err.println("Cannot build media locator from: " + fileName);
            System.exit(0);
        }
        int interval = 50;
        imageToMovie.doIt(1920, 1080, 60, imgLst, oml);
    }
}
