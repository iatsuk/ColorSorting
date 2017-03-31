package com.yatsukav.colorsort;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ImageData {
    private final BufferedImage image;

    public ImageData(URI uri) throws IOException {
        image = ImageIO.read(uri.toURL());
    }

    public ImageData(int width, int height, int imageType, IndexColorModel cm) {
        image = new BufferedImage(width, height, imageType, cm);
    }

    public int[] getColors() {
        int[] result = new int[image.getHeight() * image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                result[(y + 1) * x] = image.getRGB(x, y);
            }
        }

        return result;
    }

    public void setColors(int[] data) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, data[(y + 1) * x]);
            }
        }
    }

    public void save(String format, File path) throws IOException {
        ImageIO.write(image, format, path);
    }
}
