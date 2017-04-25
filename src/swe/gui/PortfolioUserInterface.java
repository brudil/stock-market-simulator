package swe.gui;

import swe.engine.Company;
import swe.engine.Portfolio;
import swe.engine.Share;
import swe.engine.StockType;
import swe.engine.traders.RandomTrader;
import swe.engine.traders.Trader;
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
        panel.add(getButtonPanel());

        return panel;
    }

    private JPanel getButtonPanel() {
        JPanel panel = new JPanel();
        JButton addShareButton = new JButton("Add Share");
        addShareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = new Object[s.Companies.size()];
                for (int i = 0; i < s.Companies.size(); i++) {
                    possibilities[i] = s.Companies.get(i);
                }
                Company company = (Company)JOptionPane.showInputDialog(
                        JOptionPane.getRootFrame(),
                        "Company:",
                        "Choose Company",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        null);
                int noOfShares = Integer.parseInt(
                        (String)JOptionPane.showInputDialog(
                                JOptionPane.getRootFrame(),
                                "Number Of Shares: ",
                                "Enter Number Of Shares",
                                JOptionPane.PLAIN_MESSAGE));
                s.Shares.setShares(company, p, noOfShares);
                JPanel panel = getMainPanel();
                setContentPane(panel);
                validate();
                repaint();
            }
        });

        panel.add(addShareButton);

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
            Share share = s.Shares.getSharesForCompanyInPortfolio(s.Companies.get(i), p);
            int n = share.getNumberOfShares();
            Object[] row = {s.Companies.get(i).getName(), n};
            model.addRow(row);
        }

        JTable tableCompanies = new JTable(model);
        panel.add(new JScrollPane(tableCompanies));

        return panel;
    }

}
