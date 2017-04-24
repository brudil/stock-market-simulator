package swe.gui;

import swe.engine.StockType;
import swe.setup.Setup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Fin on 24/04/2017.
 */
public class SetupUserInterface extends JFrame {

    Setup s;

    public SetupUserInterface() {
        // create setup
        s = new Setup();

        // create the GUI
        createGUI();

        // set the frame size of the window
        //setSize(new Dimension(500, 400));

        // set title
        setTitle("Setup");
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
        JPanel panel = new JPanel(new FlowLayout());

        //create menu bar
        JMenuBar menuBar = new JMenuBar();
        //create menu
        JMenu file = new JMenu("File");
        //create menu items
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem exit = new JMenuItem("Exit");
        //add to the panel
        file.add(save);
        file.add(load);
        file.add(exit);
        menuBar.add(file);
        setJMenuBar(menuBar);

        // add companies panel
        panel.add(getCompaniesPanel());

        // add clients panel
        panel.add(getClientsPanel());

        //JButton button = new JButton("Hello");
        //panel.add(button);

        return panel;
    }

    private JPanel getCompaniesPanel() {
        JPanel panelCompanies = new JPanel();
        panelCompanies.setLayout(new BoxLayout(panelCompanies, BoxLayout.PAGE_AXIS));
        String[] colNames = {"Name", "Stock Type", "Opening Price"};
//        Object[][] data = {
//                {"Test1", StockType.HARD, 100},
//                {"Test2", StockType.FOOD, 200}
//        };

        Object[][] data;

        DefaultTableModel model = new DefaultTableModel(colNames, 0);

        for (int i = 0; i < s.Companies.size(); i++) {
            Object[] row = {s.Companies.get(i).getName(),
                    s.Companies.get(i).getStockType(),
                    s.Companies.get(i).getPrice()
            };
            model.addRow(row);
        }

        JTable tableCompanies = new JTable(model);
        panelCompanies.add(new JScrollPane(tableCompanies));

        JPanel buttonPanel = new JPanel();

        // create add company button
        JButton buttonAddComp = new JButton("Add Company");
        buttonAddComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String)JOptionPane.showInputDialog(
                        JOptionPane.getRootFrame(),
                        "Company Name: ",
                        "Enter Company Name",
                        JOptionPane.PLAIN_MESSAGE);
                Object[] possibilities = {StockType.HARD, StockType.FOOD, StockType.HITECH, StockType.PROPERTY};
                StockType type = (StockType)JOptionPane.showInputDialog(
                        JOptionPane.getRootFrame(),
                        "Company Stock Type:",
                        "Choose Company Stock Type",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        StockType.HARD);
                Float price = Float.parseFloat(
                        (String)JOptionPane.showInputDialog(
                        JOptionPane.getRootFrame(),
                        "Company Opening Share Price: ",
                        "Enter Company Opening Price",
                        JOptionPane.PLAIN_MESSAGE));
                s.addCompany(name, type, price);
                JPanel panel = getMainPanel();
                setContentPane(panel);
                validate();
                repaint();
            }
        });
        // create edit company button
        JButton buttonEditComp = new JButton("Edit Company");

        // add buttons to panel
        buttonPanel.add(buttonAddComp);
        buttonPanel.add(buttonEditComp);

        panelCompanies.add(buttonPanel);

        return panelCompanies;
    }

    private JPanel getClientsPanel() {
        JPanel panelClients = new JPanel();
        panelClients.setLayout(new BoxLayout(panelClients, BoxLayout.PAGE_AXIS));
        String[] colNames = {"Name", "Trader", "Cash"};
        Object[][] data = {
                {"Bob", "Random", 5000},
                {"Geoff", "Intelligent", 2000}
        };

        JTable tableCompanies = new JTable(data, colNames);
        panelClients.add(new JScrollPane(tableCompanies));

        JPanel buttonPanel = new JPanel();
        // create add company button
        JButton buttonAddComp = new JButton("Add Client");
        // create edit company button
        JButton buttonEditComp = new JButton("Edit Client");

        // add buttons to panel
        buttonPanel.add(buttonAddComp);
        buttonPanel.add(buttonEditComp);

        panelClients.add(buttonPanel);

        return panelClients;
    }

}
