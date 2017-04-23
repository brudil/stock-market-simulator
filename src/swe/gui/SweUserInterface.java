package swe.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Fin on 23/04/2017.
 */
public class SweUserInterface extends JFrame {

    private JButton buttonStart;

    public SweUserInterface() {
        // create the GUI
        createGUI();

        // set the frame size of the window
        setSize(new Dimension(500, 400));
        // set a default close action
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set title
        setTitle("Wolf & Gecko Stock Market Simulator");
        pack();
        // set start position as center of screen
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SweUserInterface().setVisible(true);
            }
        });
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        getContentPane().add(panel);

        JButton buttonStart = new JButton("Start Simulation");
        panel.add(buttonStart);
    }

}
