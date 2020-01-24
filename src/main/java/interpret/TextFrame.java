package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class TextFrame extends JFrame {
    private JTextField textField;
    public TextFrame(final ObjectManager objectManager) {
        setTitle("Text");
        setSize(300, 120);

        textField = new JTextField();
        final JButton button = new JButton("OK");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = new String(
                  textField.getText());
                JOptionPane.showMessageDialog(
                        null,
                        textField.getText() + " is what you input.");
                ObjectCreator objectCreator = new ObjectCreator();
                objectManager.createAndSave(textField.getText());
                Optional o = objectManager.getObjectByName(textField.getText());
                printToDialog(o.orElse(null));
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("The class name", SwingConstants.LEFT));
        panel.add(textField);
        panel.add(button);

        add(panel, BorderLayout.NORTH);
    }

    String getText() {
        return textField.getText();
    }

    void printToDialog(Object object) {
        JOptionPane.showMessageDialog(
                null,
                object.toString());
    }
}
