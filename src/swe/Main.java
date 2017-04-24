package swe;

import swe.engine.*;
import swe.engine.traders.RandomInnerTraders;
import swe.engine.traders.RandomTrader;
import swe.gui.SweUserInterface;

import javax.swing.*;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) {
        ArrayList<Company> companies = new ArrayList<>();
        companies.add(new Company("Monsters Inc.", StockType.HITECH, 300));

        ArrayList<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(new Portfolio("Dave's Stocks", 4844, new RandomTrader(RandomInnerTraders.BALANCED)));
        Simulation s = new Simulation(companies, portfolios);
        s.runSimulation();
        History h = s.getHistory();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SweUserInterface(h).setVisible(true);
            }
        });

        System.out.println("Hey!");
    }

    public int getFive() {
        return 5;
    }
}
