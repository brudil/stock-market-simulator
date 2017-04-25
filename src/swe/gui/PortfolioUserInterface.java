package swe.gui;

import swe.engine.Portfolio;
import swe.setup.Setup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Fin on 25/04/2017.
 */
public class PortfolioUserInterface extends JFrame {

    Setup s;
    Portfolio p;

    public PortfolioUserInterface(Portfolio portfolio, Setup setup) {
        // create setup
        s = setup;
        p = portfolio;

        // create the GUI
        createGUI();

        // set the frame size of the window
        //setSize(new Dimension(500, 400));

        // set title
        setTitle(p.getName());
        pack();
        // set start position as center of screen
        setLocationRelativeTo(null);
    }

    private void createGUI() {
        // get the main panel
        JPanel panel = getMainPanel();
        // add it to the content pane
        getContentPane().add(panel);
    }

    private JPanel getMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // info panel
        JPanel info = new JPanel();
        JLabel name = new JLabel("Name: ");
        JButton nameButton = new JButton(p.getName());
        JLabel cash = new JLabel("Cash: ");
        JButton cashButton = new JButton(p.getCash().toString());
        JLabel trader = new JLabel("Trader: ");
        JButton traderButton = new JButton(p.getTrader().toString());
        info.add(name);
        info.add(nameButton);
        info.add(cash);
        info.add(cashButton);
        info.add(trader);
        info.add(traderButton);
        panel.add(info);

        // add share table panel
        panel.add(getShareTablePanel());

        return panel;
    }

    private JPanel getShareTablePanel() {
        JPanel panel = new JPanel();
        String[] colNames = {"Company", "Shares"};
        DefaultTableModel model = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < s.Companies.size(); i++) {
            Object[] row = {s.Companies.get(i).getName(),
                    p.getShares().get(s.Companies.get(i))
            };
            model.addRow(row);
        }

        JTable tableCompanies = new JTable(model);
        panel.add(new JScrollPane(tableCompanies));

        return panel;
    }

}
