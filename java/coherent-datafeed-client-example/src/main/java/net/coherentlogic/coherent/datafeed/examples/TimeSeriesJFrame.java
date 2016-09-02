package net.coherentlogic.coherent.datafeed.examples;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * A simple UI for querying the time series service by ric.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class TimeSeriesJFrame extends JFrame {

    private JPanel contentPane;
    private final JTextField ricTextField = new JTextField();
    private final JButton goButton = new JButton("Go!");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TimeSeriesJFrame frame = new TimeSeriesJFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TimeSeriesJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 100);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblGetTimeSeries = new JLabel("Get time series data for the RIC:");
        contentPane.add(lblGetTimeSeries);

        contentPane.add(ricTextField);
        ricTextField.setColumns(10);

        contentPane.add(goButton);
    }

    public JTextField getRicTextField() {
        return ricTextField;
    }

    public JButton getGoButton() {
        return goButton;
    }
}
