package com.coherentlogic.coherent.datafeed.client.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesGatewaySpecification;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;

//import groovy.lang.Binding;
//import groovy.lang.GroovyShell;

public class MainUI {

    private static final Logger log =
        LoggerFactory.getLogger(MainUI.class);

    public JFrame mainFrame;

    private final JTextArea authenticationTextArea = new JTextArea();

    private final JTextArea statusResponseTextArea = new JTextArea();

    private final TimeSeriesGatewaySpecification timeSeriesGatewaySpecification;

    private final MarketPriceServiceSpecification marketPriceGatewaySpecification;

    private Handle loginHandle = null;

    /**
     * Create the application.
     */
    public MainUI() {
        timeSeriesGatewaySpecification = null;
        marketPriceGatewaySpecification = null;
        initialize();
    }

    /**
     * @param timeSeriesGatewaySpecification
     */
    public MainUI (
        TimeSeriesGatewaySpecification timeSeriesGatewaySpecification,
        MarketPriceServiceSpecification marketPriceGatewaySpecification
    ) {
        this.timeSeriesGatewaySpecification = timeSeriesGatewaySpecification;
        this.marketPriceGatewaySpecification = marketPriceGatewaySpecification;
    }

    public Handle getLoginHandle() {
        return loginHandle;
    }

    public void setLoginHandle(Handle loginHandle) {
        this.loginHandle = loginHandle;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Coherent Datafeed: Thomson Reuters (TREP) Edition");
        mainFrame.setBounds(100, 100, 450, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setSize(550, 550);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JMenuItem mntmAbout = new JMenuItem("About");
        mnHelp.add(mntmAbout);
        mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.X_AXIS));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));
        mainFrame.getContentPane().add(tabbedPane);

        JPanel throughputPanel = new JPanel();
        tabbedPane.addTab("Throughput", null, throughputPanel, null);
        throughputPanel.setLayout(new BorderLayout(0, 0));

        addLineChart (throughputPanel);

        JPanel statusResponsePanel = new JPanel();
        JPanel timeSeriesPanel = new JPanel();
        JPanel marketPricePanel = new JPanel();

        JPanel authenticationPanel = new JPanel();
        tabbedPane.addTab("Authentication", null, authenticationPanel, null);
        authenticationPanel.setLayout(new BorderLayout(0, 0));

        authenticationTextArea.setFont(new Font("Arial", Font.PLAIN, 12));

//        statusResponse.addPropertyChangeListener(
//             new PropertyChangeListener() {
//                @Override
//                public void propertyChange(PropertyChangeEvent evt) {
//
//                    String oldText = authenticationTextArea.getText();
//
//                    if (oldText == null)
//                        oldText = "";
//
//                    Date now = Calendar.getInstance().getTime();
//
//                    String newText = "[" + now + "] " + statusResponse;
//
//                    SwingUtilities.invokeLater(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                authenticationTextArea.setText(newText);
//                            }
//                        }
//                    );
//                }
//            }
//        );

        JScrollPane authenticationPane = new JScrollPane(authenticationTextArea);
        authenticationPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        authenticationPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        authenticationPanel.add(authenticationPane, BorderLayout.CENTER);

        tabbedPane.addTab("Status Response", statusResponsePanel);
        tabbedPane.addTab("Time Series", timeSeriesPanel);
        timeSeriesPanel.setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("262px:grow"),
                ColumnSpec.decode("4px"),},
            new RowSpec[] {
                FormSpecs.LINE_GAP_ROWSPEC,
                RowSpec.decode("26px:grow"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        final JTextArea timeSeriesGroovyScriptTextArea = new JTextArea();
        timeSeriesGroovyScriptTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        timeSeriesGroovyScriptTextArea.setText(
            "Enter your Groovy script here (in context: log, loginHandle, timeSeriesGateway).");
        timeSeriesPanel.add(timeSeriesGroovyScriptTextArea, "1, 2, fill, fill");
        
        JButton btnEvaluate = new JButton("Evaluate");
        
        btnEvaluate.addActionListener(
            new ActionListener () {
                @Override
                public void actionPerformed(ActionEvent arg0) {
//                    String scriptText = timeSeriesGroovyScriptTextArea.getText();
//
//                    Binding binding = new Binding ();
//
//                    binding.setVariable("log", log);
//                    binding.setVariable("loginHandle", loginHandle);
//                    binding.setVariable("timeSeriesGateway", timeSeriesGatewaySpecification);
//
//                    GroovyShell groovyShell = new GroovyShell(binding);
//
//                    groovyShell.evaluate(scriptText);
                }
            }
        );

        btnEvaluate.setFont(new Font("Arial", Font.PLAIN, 12));
        timeSeriesPanel.add(btnEvaluate, "1, 4");

        // -----

        tabbedPane.addTab("Market Price", marketPricePanel);
        marketPricePanel.setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("262px:grow"),
                ColumnSpec.decode("4px"),},
            new RowSpec[] {
                FormSpecs.LINE_GAP_ROWSPEC,
                RowSpec.decode("26px:grow"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,}));
        
        final JTextArea marketPriceGroovyScriptTextArea = new JTextArea();
        marketPriceGroovyScriptTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        marketPriceGroovyScriptTextArea.setText(
            "Enter your Groovy script here (in context: log, loginHandle, marketPriceGateway).");
        marketPricePanel.add(marketPriceGroovyScriptTextArea, "1, 2, fill, fill");

        JButton evaluateMarketPriceScriptBtn = new JButton("Evaluate");

        evaluateMarketPriceScriptBtn.addActionListener(
            new ActionListener () {
                @Override
                public void actionPerformed(ActionEvent arg0) {
//                    String scriptText = marketPriceGroovyScriptTextArea.getText();
//
//                    Binding binding = new Binding ();
//
//                    binding.setVariable("log", log);
//                    binding.setVariable("loginHandle", loginHandle);
//                    binding.setVariable("marketPriceGateway", marketPriceGatewaySpecification);
//
//                    GroovyShell groovyShell = new GroovyShell(binding);
//                    
//                    groovyShell.evaluate(scriptText);
                }
            }
        );

        evaluateMarketPriceScriptBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        marketPricePanel.add(evaluateMarketPriceScriptBtn, "1, 4");

        statusResponsePanel.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane(statusResponseTextArea);
        
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        statusResponsePanel.add(scrollPane);
        
        statusResponseTextArea.setTabSize(4);
        statusResponseTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        statusResponseTextArea.setEditable(false);

        statusResponseTextArea.setText("");

        RefineryUtilities.centerFrameOnScreen(mainFrame);
    }

    final DefaultCategoryDataset throughputDataset
        = new DefaultCategoryDataset();

    void addLineChart (JPanel target) {

        final JFreeChart throughputChart = ChartFactory.createLineChart(
            "Throughput",
            "Time",
            "Updates",
            throughputDataset,
            PlotOrientation.VERTICAL,
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

        ChartPanel throughputChartPanel = new ChartPanel (throughputChart);

        target.add(throughputChartPanel);

        final CategoryPlot plot = (CategoryPlot) throughputChart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(0, 1000);
        rangeAxis.setAutoRangeIncludesZero(true);

        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

        renderer.setSeriesStroke(
            0,
            new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {10.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            1,
            new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 6.0f}, 0.0f
            )
        );
        renderer.setSeriesStroke(
            2,
            new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {2.0f, 6.0f}, 0.0f
            )
        );
    }

    public void addAuthenticationResponseText (final String text) {
        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    String currentText = authenticationTextArea.getText();
                    final String newText = currentText + "\n" + text;
                    authenticationTextArea.setText(newText);
                }
            }
        );
    }

    public void onStatusResponseUpdate (StatusResponse statusResponse) {
        addStatusResponseText (statusResponse.toString());
    }

    void addStatusResponseText (final String text) {

        log.warn("addStatusResponseText: method begins; text: " + text);

        SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    String currentText = statusResponseTextArea.getText();
                    final String newText = currentText + "\n" + text;
                    statusResponseTextArea.setText(newText);
                }
            }
        );

        log.warn("addStatusResponseText: method ends.");
    }

    public void onInitializationSuccessful (Message<Event> message) {

    }

    int marketPriceUpdatesTotal = 0;

    public void onMarketPriceUpdate (MarketPrice marketPrice) {

        marketPriceUpdatesTotal++;

        if (System.currentTimeMillis() % 10 == 0) {

            System.out.println("marketPriceUpdatesTotal: " + marketPriceUpdatesTotal);

            SwingUtilities.invokeLater(
                new Runnable() {

                    final long value = marketPriceUpdatesTotal;

                    @Override
                    public void run() {

                        marketPriceUpdatesTotal = 0;

                        throughputDataset.addValue(value, "marketPrice", "blah" + value);

                        if (value % 1000 == 0)
                            throughputDataset.removeColumn(0);
                    }
                }
            );
        }
    }

    private long marketMakerCtr = 0;

    public void onMarketMakerUpdate (MarketMaker marketMaker) {

        log.info("mainUI.onMarketMakerUpdate; method called with marketMaker[" + marketMakerCtr + "]" + marketMaker);

        marketMakerCtr++;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        final MainUI window = new MainUI();
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                window.mainFrame.pack();
                window.mainFrame.setVisible(true);
            }
        });

        for (int ctr = 1; ctr < 5000; ctr++) {
            window.addStatusResponseText("New text:" + ctr);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to add the text with id " + ctr, e);
            }
        }
    }
}
//final Random rand = new Random ();
//
//Thread thread = new Thread (
//  new Runnable () {
//
//      int ctr = 0;
//
//      @Override
//      public void run() {
//          for (int x = 0; x < 5000; x++) {
//              int value = rand.nextInt(100);
//
//              ctr++;
//
//              System.out.println("ctr: " + ctr + ", value: " + value);
//
//              throughputDataset.addValue(value, "marketPrice.getDisplayName ()", "blah"+ctr);
//
//              if (25 < ctr)
//                  throughputDataset.removeColumn(0);
//
//              try {
//                  Thread.sleep(250);
//              } catch (InterruptedException e) {
//                  e.printStackTrace();
//              }
//          }
//      }
//  }
//);
//thread.start();
//}
