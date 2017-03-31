package com.yatsukav.colorsort;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class AppTest {

    @Test
    public void bmpToArray() throws URISyntaxException, IOException {
        URL path = AppTest.class.getClassLoader().getResource("sample.bmp").toURI().toURL();
        int[] result = App.loadBmp(path);
        System.out.println(Arrays.toString(result));

        URI path2 = new File("test.bmp").toURI();
        App.saveToBmp(result, path2, 16, 2);

        Arrays.sort(result);
        URI path3 = new File("test2.bmp").toURI();
        App.saveToBmp(result, path3, 16, 2);
    }

    @Test
    public void arrayToBmp() {
    }

}
