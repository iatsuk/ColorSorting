package com.yatsukav.colorsort;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.Arrays;

public class ImageDataTest {
    private ImageData line;
    private ImageData square;

    @Before
    public void setUp() throws Exception {
        URI lineUri = AppTest.class.getClassLoader().getResource("line.bmp").toURI();
        URI squareUri = AppTest.class.getClassLoader().getResource("square.bmp").toURI();

        line = new ImageData(lineUri);
        square = new ImageData(squareUri);
    }

    @Test
    public void getColors() throws Exception {
        int[] lineColors = line.getColors();
        line.setColors(lineColors);
        line.save("png", new File("1.png"));

        Arrays.sort(lineColors);
        line.setColors(lineColors);
        line.save("png", new File("2.png"));

        Arrays.fill(lineColors, new Color(1f, 1f, 1f).getRGB());
        line.setColors(lineColors);
        line.save("png", new File("3.png"));

        int[] squareColors = square.getColors();


    }

    @Test
    public void setColors() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

}