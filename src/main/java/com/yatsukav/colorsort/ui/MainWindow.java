package com.yatsukav.colorsort.ui;

import com.yatsukav.colorsort.App;
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

    private void createUIComponents() {
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(15, 1, Integer.MAX_VALUE, 1);
        durationSecSpinner = new JSpinner(spinnerModel);
        JFormattedTextField txt = ((JSpinner.NumberEditor) durationSecSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    }

    private class ImageToMovieConverter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String inputPath = inPathTextField.getText();
            String outputPath = outVideoTextField.getText();
            String sortMethod = sortComboBox.getModel().getSelectedItem().toString();
            int duration = (int) durationSecSpinner.getValue();
            App.start(inputPath, outputPath, sortMethod, duration);
        }
    }
}
