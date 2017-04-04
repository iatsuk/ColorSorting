package com.yatsukav.colorsort.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GenerateColorNoiseImage {
    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        generate(512, 512, "img.png", "png");
    }

    public static void generate(int width, int height, String path, String format) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                image.setRGB(x, y, color.getRGB());
            }
        }
        ImageIO.write(image, format, new File(path));
    }

}
