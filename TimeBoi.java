package d1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;

public class TimeBoi {
    int time;
    static final int halfMin = 30000;
    static final int oneMin = 60000;
    static final int twoMin = 120000;
    ActionListener timeAlloted;


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final int FIELD_WIDTH = 20;
        JTextField textField = new JTextField(FIELD_WIDTH);
        frame.setLayout(new FlowLayout());
        frame.add(textField);
        ActionListener listener = event -> textField.setText(Instant.now().toString());
        final int DELAY = 1000;
        // Milliseconds between timer ticks
        Timer t = new Timer(halfMin, listener);
        t.start();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
