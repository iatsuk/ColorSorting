package com.yatsukav.colorsort.image;

import java.awt.*;

public enum ColorModel {
    RGB, HSB, HBS;

    public int ofRGB(int value) {
        switch (this) {
            case RGB:
                return value;
            case HSB: {
                Color color = new Color(value);
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                return color3dTo1d(hsb[0], hsb[1], hsb[2]);
            }
            case HBS: {
                Color color = new Color(value);
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                return color3dTo1d(hsb[0], hsb[2], hsb[1]);
            }
            default:
                throw new IllegalArgumentException("Unknown color model: " + this);
        }
    }

    public int toRGB(int value) {
        switch (this) {
            case RGB:
                return value;
            case HSB: {
                float[] hsb = color1dTo3d(value);
                return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
            }
            case HBS: {
                float[] hbs = color1dTo3d(value);
                return Color.HSBtoRGB(hbs[0], hbs[2], hbs[1]);
            }
            default:
                throw new IllegalArgumentException("Unknown color model: " + this);
        }
    }

    private int color3dTo1d(float d1, float d2, float d3) {
        int head = (int) (d1 * 100) * 1_000_000;
        int secondary = (int) (d2 * 100) * 1_000;
        int tail = (int) (d3 * 100);
        return head + secondary + tail;
    }

    private float[] color1dTo3d(int color) {
        int head = color / 1000000;
        int secondary = color / 1000 % 1000;
        int tail = color % 1000;
        return new float[]{head / 100f, secondary / 100f, tail / 100f};
    }
}