package swe;

import swe.gui.SweUserInterface;

import javax.swing.*;

class Main {

    public static void main(String[] args) {
        //create simulation
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SweUserInterface().setVisible(true);
            }
        });

        System.out.println("Hey!");
    }

    public int getFive() {
        return 5;
    }
}
