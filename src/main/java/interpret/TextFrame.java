package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFrame extends JFrame {
    public TextFrame() {
        setTitle("Text");
        setSize(300, 120);

        final JTextField textField = new JTextField();
        final JButton button = new JButton("OK");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = new String(
                  textField.getText());
                JOptionPane.showMessageDialog(
                        null,
                        textField.getText() + " is what you input.");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("The class name", SwingConstants.LEFT));
        panel.add(textField);
        panel.add(button);

        add(panel, BorderLayout.NORTH);
    }
}
