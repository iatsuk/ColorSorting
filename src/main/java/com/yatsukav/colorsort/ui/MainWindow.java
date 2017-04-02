package com.yatsukav.colorsort.ui;

import com.yatsukav.colorsort.ImageData;
import com.yatsukav.colorsort.movie.MovieMaker;
import com.yatsukav.colorsort.sorts.ImageSorter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainWindow {
    private static MainWindow instance = new MainWindow();
    private final JFileChooser fc = new JFileChooser();
    private final JFrame frame = new JFrame();
    private JTextField inPathTextField;
    private JButton openButton;
    private JTextField outVideoTextField;
    private JButton saveButton;
    private JComboBox widthComboBox;
    private JButton startButton;
    private JComboBox sortComboBox;
    private JPanel mainPanel;

    public static MainWindow getInstance() {
        return instance;
    }

    private MainWindow() {
        startButton.addActionListener(new ImageToMovieConverter());
        openButton.addActionListener(e -> {
            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                inPathTextField.setText(fc.getSelectedFile().getAbsolutePath());
            }
        });

        saveButton.addActionListener(e -> {
            if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                outVideoTextField.setText(fc.getSelectedFile().getAbsolutePath());
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });

        frame.setContentPane(mainPanel);
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    private class ImageToMovieConverter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ImageData imageData = new ImageData(new File(inPathTextField.getText()).toURI());
                ImageSorter imageSorter = ImageSorter.of(sortComboBox.getModel().getSelectedItem().toString(), imageData);
                imageSorter.sort(imageData.getColors());

                int width, height;
                width = Integer.parseInt(widthComboBox.getModel().getSelectedItem().toString().replaceAll("[\\D+]", ""));
                height = (int) (imageData.getHeight() * ((double) width / imageData.getWidth()));

                MovieMaker.makeVideo(outVideoTextField.getText(), imageSorter.getImages(), width, height);
            } catch (Exception e1) {
                throw new IllegalStateException(e1);
            }
        }
    }
}
