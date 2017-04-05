package com.yatsukav.colorsort.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ImageData {
    private BufferedImage image;
    private ColorModel colorModel = ColorModel.RGB;

    public int[] getColors() {
        int[] result = new int[image.getHeight() * image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = colorModel.ofRGB(image.getRGB(x, y));
                result[x + y * image.getWidth()] = color;
            }
        }

        return result;
    }

    public void setColors(int[] data) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int color = colorModel.toRGB(data[x + y * image.getWidth()]);
                image.setRGB(x, y, color);
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

    public ImageData setColorModel(ColorModel colorModel) {
        this.colorModel = colorModel;
        return this;
    }
}
