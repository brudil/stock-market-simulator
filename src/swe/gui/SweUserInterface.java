package swe.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

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
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // add the index chart to the panel
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 10;
        c.ipady = 10;
        ChartPanel panelIndexChart = createIndexChart();
        panel.add(panelIndexChart, c);

        // add the client chart to the panel
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 10;
        c.ipady = 10;
        ChartPanel panelClientChart = createClientChart();
        panel.add(panelClientChart, c);

        // start button
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        JButton buttonStart = new JButton("Start Simulation");
        panel.add(buttonStart, c);

        // setup button
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        JButton buttonSetup = new JButton("Setup");
        panel.add(buttonSetup, c);

        //panel.setBackground(Color.BLACK);
        getContentPane().add(panel);
    }

    private ChartPanel createIndexChart() {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Stock Market Index",
                "Time","Index",
                createIndexDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        return chartPanel;
        //chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        //setContentPane( chartPanel );
    }

    private ChartPanel createClientChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Client Worth",
                "Category",
                "Score",
                createClientDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( barChart );
        return chartPanel;
    }

    private CategoryDataset createClientDataset() {
        final String fiat = "FIAT";
        final String audi = "AUDI";
        final String ford = "FORD";
        final String speed = "Speed";
        final String millage = "Millage";
        final String userrating = "User Rating";
        final String safety = "safety";
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );

        dataset.addValue( 1.0 , fiat , speed );
        dataset.addValue( 3.0 , fiat , userrating );
        dataset.addValue( 5.0 , fiat , millage );
        dataset.addValue( 5.0 , fiat , safety );

        dataset.addValue( 5.0 , audi , speed );
        dataset.addValue( 6.0 , audi , userrating );
        dataset.addValue( 10.0 , audi , millage );
        dataset.addValue( 4.0 , audi , safety );

        dataset.addValue( 4.0 , ford , speed );
        dataset.addValue( 2.0 , ford , userrating );
        dataset.addValue( 3.0 , ford , millage );
        dataset.addValue( 6.0 , ford , safety );

        return dataset;
    }

    private DefaultCategoryDataset createIndexDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        dataset.addValue( 15 , "index" , "1970" );
        dataset.addValue( 30 , "index" , "1980" );
        dataset.addValue( 60 , "index" ,  "1990" );
        dataset.addValue( 120 , "index" , "2000" );
        dataset.addValue( 240 , "index" , "2010" );
        dataset.addValue( 300 , "index" , "2014" );
        return dataset;
    }
}
