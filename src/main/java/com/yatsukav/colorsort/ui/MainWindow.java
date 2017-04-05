package com.yatsukav.colorsort.ui;

import com.yatsukav.colorsort.App;
import com.yatsukav.colorsort.image.ColorModel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {
    @Getter private static MainWindow instance = new MainWindow();
    private final JFileChooser fc = new JFileChooser();
    private final JFrame frame = new JFrame();
    private JTextField inPathTextField;
    private JButton openButton;
    private JTextField outVideoTextField;
    private JButton saveButton;
    private JButton startButton;
    private JComboBox sortComboBox;
    private JPanel mainPanel;
    private JSpinner durationSecSpinner;
    @Getter private JProgressBar progressBar;
    @Getter private JLabel statusBarLabel;
    private JComboBox colorModeComboBox;

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
        frame.setResizable(false);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void createUIComponents() {
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(15, 1, Integer.MAX_VALUE, 1);
        durationSecSpinner = new JSpinner(spinnerModel);
        JFormattedTextField txt = ((JSpinner.NumberEditor) durationSecSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }

    private class ImageToMovieConverter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // disable ui
            disableUI();

            // run in thread
            String inputPath = inPathTextField.getText();
            String outputPath = outVideoTextField.getText();
            ColorModel colorModel = ColorModel.valueOf(colorModeComboBox.getModel().getSelectedItem().toString());
            String sortMethod = sortComboBox.getModel().getSelectedItem().toString();
            int duration = (int) durationSecSpinner.getValue();
            new Thread(() -> {
                App.start(inputPath, outputPath, colorModel, sortMethod, duration);
                enableUI();
            }).start();
        }

        private void disableUI() {
            inPathTextField.setEnabled(false);
            openButton.setEnabled(false);
            outVideoTextField.setEnabled(false);
            saveButton.setEnabled(false);
            startButton.setEnabled(false);
            colorModeComboBox.setEnabled(false);
            sortComboBox.setEnabled(false);
            durationSecSpinner.setEnabled(false);
        }

        private void enableUI() {
            inPathTextField.setEnabled(true);
            openButton.setEnabled(true);
            outVideoTextField.setEnabled(true);
            saveButton.setEnabled(true);
            startButton.setEnabled(true);
            colorModeComboBox.setEnabled(true);
            sortComboBox.setEnabled(true);
            durationSecSpinner.setEnabled(true);
        }
    }
}
