package com.yatsukav.colorsort;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    static int[] loadBmp(URL path) throws IOException {
        BufferedImage image = ImageIO.read(path);
        int[] result = new int[image.getHeight() * image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                result[(y + 1) * x] = image.getRGB(x, y);
            }
        }

        return result;
    }

    static void saveToBmp(int[] data, URI path, int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, data[(y + 1) * x]);
            }
        }

        ImageIO.write(image, "bmp", new File(path));
    }
}
