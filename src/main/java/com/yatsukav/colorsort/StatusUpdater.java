package com.yatsukav.colorsort;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

public class StatusUpdater extends Thread {
    @Getter @Setter private String message = "Attention! The video resolution will be equal to the image size.";
    @Getter @Setter private int maxSteps = 100;
    @Getter @Setter private int currStep = 0;
    private boolean update = true;

    @Getter @Setter private JProgressBar progressBar = null;
    @Getter @Setter private JLabel statusLabel = null;

    @Override
    public void run() {
        super.run();
        while (update) {
            try {
                if (statusLabel != null) statusLabel.setText(message);
                if (progressBar != null) {
                    progressBar.setMaximum(maxSteps);
                    progressBar.setValue(currStep);
                }
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (statusLabel != null) statusLabel.setText(message);
    }

    public void stopUpdate() {
        update = false;
    }

    public void startUpdate() {
        update = true;
        start();
    }

    @Override
    public synchronized void start() {
        update = true;
        super.start();
    }
}
