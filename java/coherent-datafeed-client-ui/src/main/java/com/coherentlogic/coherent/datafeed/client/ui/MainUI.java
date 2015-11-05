package com.coherentlogic.coherent.datafeed.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BoxLayout;
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

public class MainUI {

	public JFrame frame;

	private final JTextArea statusResponseTextArea = new JTextArea();

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.setSize(250, 250);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		
		tabbedPane.addTab("Status Response", panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane(statusResponseTextArea);
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(scrollPane);
		
		statusResponseTextArea.setTabSize(4);
		statusResponseTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
		statusResponseTextArea.setEditable(false);
		
		statusResponseTextArea.setText("Hello world!");
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
				window.frame.pack();
				window.frame.setVisible(true);
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
