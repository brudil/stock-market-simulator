package swe.gui;

import javafx.beans.value.ChangeListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import swe.engine.History;
import swe.engine.HistoryState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Fin on 23/04/2017.
 */
public class SweUserInterface extends JFrame {

    private JButton buttonStart;
    private int currentDay = 1;
    private int[] data = new int[364];

    public SweUserInterface(History h) {
        data = initialiseIndexDataset(h);
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

    private int[] initialiseIndexDataset(History h) {
        Random rand = new Random();
        int[] d = new int[364];
        for(int i = 0; i < 364; i++) {
            d[i] = h.getStateForTick(i*28).shareIndex;
            //d[i] = rand.nextInt(100) + 1;
        }
        return d;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new SweUserInterface().setVisible(true);
//            }
//        });
//    }

    private void createGUI() {
        // get the main panel
        JPanel panel = getMainPanel();
        // add it to the content pane
        getContentPane().add(panel);
    }

    private JPanel getMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // add the index chart to the panel
        ChartPanel panelIndexChart = createIndexChart();
        panel.add(panelIndexChart, BorderLayout.LINE_START);

        // add the client chart to the panel
        ChartPanel panelClientChart = createClientChart();
        panel.add(panelClientChart, BorderLayout.LINE_END);

        // create toolbar at bottom of GUI
        JPanel toolbar = new JPanel();

        // start button
        JButton buttonStart = new JButton("Start Simulation");
        toolbar.add(buttonStart);

        // setup button
        JButton buttonSetup = new JButton("Setup");
        toolbar.add(buttonSetup);

        // day back button
        JButton buttonBack = new JButton("<<");
        toolbar.add(buttonBack);
        // add action listener to button
        buttonBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDay--;
                JPanel panel = getMainPanel();
                setContentPane(panel);
                validate();
                repaint();
            }
        });

        // day label
        JLabel labelDay = new JLabel("Day: "+currentDay);
        toolbar.add(labelDay, BorderLayout.LINE_END);

        // day advance button
        JButton buttonAdvance = new JButton(">>");
        toolbar.add(buttonAdvance);
        // add action listener to button
        buttonAdvance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDay++;
                JPanel panel = getMainPanel();
                setContentPane(panel);
                validate();
                repaint();
            }
        });

        panel.add(toolbar, BorderLayout.PAGE_END);
        return panel;
    }

    private ChartPanel createIndexChart() {
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Stock Market Index",
                "Time","Index",
                createIndexDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        // get the xyplot
        XYPlot xyPlot = lineChart.getXYPlot();
        ValueAxis xAxis = xyPlot.getDomainAxis();
        ValueAxis yAxis = xyPlot.getRangeAxis();

        // set the range of the axis
        xAxis.setRange(0.0, 365);
        yAxis.setRange(0.0, 100);

        // set the tick amount (units)
        XYPlot plot = (XYPlot) lineChart.getPlot();
        final NumberAxis numAxis = (NumberAxis) plot.getDomainAxis();
        numAxis.setTickUnit(new NumberTickUnit(20));

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
        final String client = "CLIENT WORTH";
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        dataset.addValue( 1.0 , client,"Bob" );
        dataset.addValue( 3.0 , client,"Joe" );
        dataset.addValue( 5.0 , client,"Fred" );
        dataset.addValue( 5.0 , client,"Greg" );

        return dataset;
    }

    private XYDataset createIndexDataset() {
        XYSeries dataset = new XYSeries("Stock Index Chart");
        for (int i = 0; i < currentDay-1; i++) {
            //add to dataset
            dataset.add(i + 1, data[i]);
        }

        XYDataset data = new XYSeriesCollection(dataset);
        return data;
    }
}
