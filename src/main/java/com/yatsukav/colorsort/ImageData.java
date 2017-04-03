package com.yatsukav.colorsort;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ImageData {
    private BufferedImage image;

    public int[] getColors() {
        int[] result = new int[image.getHeight() * image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                result[x + y * image.getWidth()] = image.getRGB(x, y);
            }
        }

        return result;
    }

    public void setColors(int[] data) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, data[x + y * image.getWidth()]);
            }
        }
    }

    public void save(String format, File path) throws IOException {
        ImageIO.write(image, format, path);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public ImageData load(URI uri) throws IOException {
        image = ImageIO.read(uri.toURL());
        return this;
    }
}
