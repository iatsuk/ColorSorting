package com.yatsukav.colorsort;

import com.yatsukav.colorsort.image.ImageData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;

public class ImageDataTest {
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();
    private ImageData line;
    private ImageData square;
    private ImageData rgbw;

    @Before
    public void setUp() throws Exception {
        URI lineUri = AppTest.class.getClassLoader().getResource("line.bmp").toURI();
        URI squareUri = AppTest.class.getClassLoader().getResource("square.bmp").toURI();
        URI rgbwUri = AppTest.class.getClassLoader().getResource("rgbw.bmp").toURI();

        line = new ImageData().load(lineUri);
        square = new ImageData().load(squareUri);
        rgbw = new ImageData().load(rgbwUri);
    }

    @Test
    public void getColors() throws Exception {
        int[] lineColors = line.getColors();
        Assert.assertArrayEquals(new int[]{
                        -15921907, -8946558, -12610842, -7879438, -4542728, -7441678, -1464371, -883798,
                        -550235, -40411, -346467, -5676757, -934906, -331337, -1350, -7748523,
                        -2302756, -1906450, -14383645, -12208655, -3021570, -7178509, -2308368, -611899,
                        -480581, -364483, -6019836, -6799610, -1723895, -2436567, -725099, -2232885},
                lineColors);

        int[] squareColors = square.getColors();
        Assert.assertArrayEquals(new int[]{
                        -16777216, -44490, -44490, -44490,
                        -15453185, -15453185, -44490, -44490,
                        -15453185, -15453185, -15453185, -44490,
                        -15453185, -15453185, -15453185, -1},
                squareColors);

        int[] rgbwColors = rgbw.getColors();
        Assert.assertArrayEquals(new int[]{
                        -53965, -12517585,
                        -1, -14728734},
                rgbwColors);
    }

    @Test
    public void setColors() throws Exception {
        int[] whiteSquare = new int[]{Color.WHITE.getRGB(), Color.WHITE.getRGB(), Color.WHITE.getRGB(), Color.WHITE.getRGB()};
        rgbw.setColors(whiteSquare);
        Assert.assertArrayEquals(whiteSquare, rgbw.getColors());
    }

    @Test
    public void save() throws Exception {
        File file = folder.newFile();
        rgbw.save("bmp", file);
        Assert.assertTrue(Files.exists(file.toPath()));

        Assert.assertArrayEquals(rgbw.getColors(), new ImageData().load(file.toURI()).getColors());
    }

}