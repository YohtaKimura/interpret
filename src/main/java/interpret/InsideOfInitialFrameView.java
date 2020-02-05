package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Optional;

public class InsideOfInitialFrameView extends JFrame implements ActionListener {
    private JTextField objectNameField;
    private JTextField valuableNameField;
    private ObjectManager objectManager;

    public InsideOfInitialFrameView(final ObjectManager objectManager) {
        this.objectManager = objectManager;
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
        generateButton.addActionListener(this);
        panel.add(generateButton);

        final JButton constructorsButton = new JButton("Constructors");
        constructorsButton.setActionCommand("Constructors");
        constructorsButton.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        String objectName = new String(objectNameField.getText());
        if (objectName.endsWith("[]") && Objects.equals(buttonName, "Generate")) {
            JOptionPane.showMessageDialog(
                    null,
                    objectName + " array is what you want");
            objectManager.createOneElementArrayAndSave(objectName,valuableNameField.getText());
        }
        if (!objectName.endsWith("[]") && Objects.equals(buttonName, "Generate")) {
            JOptionPane.showMessageDialog(
                    null,
                    objectName + " is what you input.");
            objectManager.createAndSave(objectNameField.getText(), valuableNameField.getText());
            Optional o = objectManager.getObjectByName(valuableNameField.getText());
            printToDialog(o.orElse(null));
        }

        if (Objects.equals(buttonName, "Constructors")) {
            new ConstructorsListView(this, objectManager, objectName);
        }
    }
}