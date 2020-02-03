package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Optional;

public class TextFrame extends JFrame {
    private JTextField objectNameField;
    private JTextField valuableNameField;
    public TextFrame(final ObjectManager objectManager) {
        setTitle("ObjectGenerator");
        setSize(300, 160);

        objectNameField = new JTextField();
        valuableNameField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("The class name", SwingConstants.LEFT));
        panel.add(objectNameField);
        panel.add(new JLabel("The valuable name", SwingConstants.LEFT));
        panel.add(valuableNameField);
        addButtonToPanel(panel, objectManager);
        add(panel, BorderLayout.NORTH);
    }

    private void addButtonToPanel(JPanel panel, ObjectManager objectManager) {
        final JButton generateButton = new JButton("Generate");
        generateButton.setActionCommand("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(e.getActionCommand(), "Generate")) {
                String objectName = new String(
                  objectNameField.getText());
                JOptionPane.showMessageDialog(
                        null,
                        objectName + " is what you input.");
                objectManager.createAndSave(objectNameField.getText(), valuableNameField.getText());
                Optional o = objectManager.getObjectByName(valuableNameField.getText());
                printToDialog(o.orElse(null));
            }
            }
        });
        panel.add(generateButton);

        final JButton constructorsButton = new JButton("Constructors");
        constructorsButton.setActionCommand("Constructors");
        constructorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(e.getActionCommand(), "Constructors")) {
                String objectName = new String(
                  objectNameField.getText());
                JOptionPane.showMessageDialog(
                        null,
                         "Hello");
            }
            }
        });
        panel.add(constructorsButton);
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
