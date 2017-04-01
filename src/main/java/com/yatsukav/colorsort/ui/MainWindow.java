package com.yatsukav.colorsort.ui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        // TODO: place custom component creation code here
    }
}
