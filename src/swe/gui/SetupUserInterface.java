package swe.gui;

import swe.engine.StockType;
import swe.engine.traders.RandomTrader;
import swe.engine.traders.Trader;
import swe.setup.Setup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Fin on 24/04/2017.
 */
public class SetupUserInterface extends JDialog {

    Setup s;

    public Setup getS() {
        return s;
    }

    public SetupUserInterface(Setup setup) {

        s = setup;
        // create the GUI
        createGUI();

        // set the frame size of the window
        //setSize(new Dimension(500, 400));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(SetupUserInterface.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        s.saveToFile(file);
                    } catch (IOException err) {
                        System.out.println("Setup load failed.");
                    }
                }
            }
        });
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(SetupUserInterface.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        s = Setup.loadFromFile(file.getPath());
                    } catch (IOException err) {
                        System.out.println("Setup load failed.");
                    }
                }
                // TODO: catch error in gui
                JPanel panel = getMainPanel();
                setContentPane(panel);
                validate();
                repaint();
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog currentFrame = (JDialog) SwingUtilities.getWindowAncestor(panel);
                currentFrame.dispose();
            }
        });
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

        // make the table un-editable
        DefaultTableModel model = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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

        // add buttons to panel
        buttonPanel.add(buttonAddComp);

        panelCompanies.add(buttonPanel);

        return panelCompanies;
    }

    private JPanel getClientsPanel() {
        JPanel panelClients = new JPanel();
        panelClients.setLayout(new BoxLayout(panelClients, BoxLayout.PAGE_AXIS));
        String[] colNames = {"Name", "Trader", "Cash", ""};

        //make the table un-editable
        DefaultTableModel model = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        for (int i = 0; i < s.Clients.size(); i++) {
            Object[] row = {s.Clients.get(i).getName(),
                    s.Clients.get(i).getTrader(),
                    s.Clients.get(i).getCash(),
                    "View Portfolio"
            };
            model.addRow(row);
        }

        JTable tableClients = new JTable(model);
        panelClients.add(new JScrollPane(tableClients));

        // add action to view portfolio
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new PortfolioUserInterface(s.Clients.get(modelRow), s).setVisible(true);
                    }
                });
                //((DefaultTableModel)table.getModel()).removeRow(modelRow);
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(tableClients, delete, 3);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        JPanel buttonPanel = new JPanel();
        // create add company button
        JButton buttonAddClient = new JButton("Add Client");
        buttonAddClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String)JOptionPane.showInputDialog(
                        JOptionPane.getRootFrame(),
                        "Client Name: ",
                        "Enter Client Name",
                        JOptionPane.PLAIN_MESSAGE);
                Object[] possibilities = {new RandomTrader()};
                Trader trader = (Trader)JOptionPane.showInputDialog(
                        JOptionPane.getRootFrame(),
                        "Client Trader Type:",
                        "Choose Client Trader Type",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        new RandomTrader().toString());
                Double cash = Double.parseDouble(
                        (String)JOptionPane.showInputDialog(
                                JOptionPane.getRootFrame(),
                                "Client Cash: ",
                                "Enter Client Cash",
                                JOptionPane.PLAIN_MESSAGE));
                s.addClient(name, trader, cash);
                JPanel panel = getMainPanel();
                setContentPane(panel);
                validate();
                repaint();
            }
        });

        // add buttons to panel
        buttonPanel.add(buttonAddClient);

        panelClients.add(buttonPanel);

        return panelClients;
    }

}
