package com.yatsukav.colorsort.movie;

import javax.media.MediaLocator;
import java.net.MalformedURLException;
import java.util.Vector;

public class MovieMaker {

    public static void makeVideo(String fileName, Vector<String> imgLst, int width, int height) throws MalformedURLException {
        JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
        MediaLocator oml;
        if ((oml = JpegImagesToMovie.createMediaLocator(fileName)) == null) {
            System.err.println("Cannot build media locator from: " + fileName);
            System.exit(0);
        }
        imageToMovie.doIt(width, height, 60, imgLst, oml);
    }
}
