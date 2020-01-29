package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class TextFrame extends JFrame {
    private JTextField objectNameField;
    private JTextField valuableNameField;
    public TextFrame(final ObjectManager objectManager) {
        setTitle("ObjectGenerator");
        setSize(300, 160);

        objectNameField = new JTextField();
        valuableNameField = new JTextField();
        final JButton button = new JButton("Generate");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = new String(
                  objectNameField.getText());
                JOptionPane.showMessageDialog(
                        null,
                        objectNameField.getText() + " is what you input.");
                ObjectCreator objectCreator = new ObjectCreator();
                objectManager.createAndSave(objectNameField.getText(), valuableNameField.getText());
                Optional o = objectManager.getObjectByName(valuableNameField.getText());
                printToDialog(o.orElse(null));
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("The class name", SwingConstants.LEFT));
        panel.add(objectNameField);
        panel.add(new JLabel("The valuable name", SwingConstants.LEFT));
        panel.add(valuableNameField);
        panel.add(button);

        add(panel, BorderLayout.NORTH);
    }

    String getText() {
        return objectNameField.getText();
    }

    void printToDialog(Object object) {
        JOptionPane.showMessageDialog(
                null,
                object.toString());
    }
}
