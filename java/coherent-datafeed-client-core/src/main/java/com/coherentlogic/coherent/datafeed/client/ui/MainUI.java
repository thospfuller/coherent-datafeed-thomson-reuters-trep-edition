package com.coherentlogic.coherent.datafeed.client.ui;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.awt.BorderLayout;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.misc.Action;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesGatewaySpecification;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.reuters.rfa.common.Handle;

public class MainUI {

    private static final Logger log =
        LoggerFactory.getLogger(MainUI.class);

    public JFrame mainFrame;

    private final JTextArea authenticationTextArea = new JTextArea();

    private final JTextArea statusResponseTextArea = new JTextArea();

    private final TimeSeriesGatewaySpecification timeSeriesGatewaySpecification;

    private Handle loginHandle = null;

    /**
     * Create the application.
     */
    public MainUI() {
        initialize();
        timeSeriesGatewaySpecification = null;
    }

    /**
     * @param timeSeriesGatewaySpecification
     */
    public MainUI (
        TimeSeriesGatewaySpecification timeSeriesGatewaySpecification
    ) {
        this.timeSeriesGatewaySpecification = timeSeriesGatewaySpecification;
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
        mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
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
        
        JPanel statusResponsePanel = new JPanel();
        JPanel timeSeriesPanel = new JPanel();
        
        JPanel authenticationPanel = new JPanel();
        tabbedPane.addTab("Authentication", null, authenticationPanel, null);
        authenticationPanel.setLayout(new BorderLayout(0, 0));

        authenticationTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane authenticationPane = new JScrollPane(authenticationTextArea);
        authenticationPanel.add(authenticationPane, BorderLayout.CENTER);
        authenticationPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        authenticationPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
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
                    String scriptText = timeSeriesGroovyScriptTextArea.getText();

                    Binding binding = new Binding ();

                    binding.setVariable("log", log);
                    binding.setVariable("loginHandle", loginHandle);
                    binding.setVariable("timeSeriesGateway", timeSeriesGatewaySpecification);

                    GroovyShell groovyShell = new GroovyShell(binding);
                    
                    groovyShell.evaluate(scriptText);
                }
            }
        );
        
        btnEvaluate.setFont(new Font("Arial", Font.PLAIN, 12));
        timeSeriesPanel.add(btnEvaluate, "1, 4");
        
        statusResponsePanel.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane = new JScrollPane(statusResponseTextArea);
        
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        statusResponsePanel.add(scrollPane);
        
        statusResponseTextArea.setTabSize(4);
        statusResponseTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
        statusResponseTextArea.setEditable(false);
        
        statusResponseTextArea.setText("");
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

    public void addStatusResponseText (final String text) {
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
