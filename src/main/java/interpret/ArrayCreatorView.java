package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ArrayCreatorView extends JFrame implements ActionListener {
    private JTextField sizeOfArrayField;
    private String objectName;
    private String valuableName;
    private ObjectManager objectManager;

    public ArrayCreatorView(final ObjectManager objectManager, final String objectName, final String valuableName) {
        this.objectManager = objectManager;
        this.objectName = objectName;
        this.valuableName = valuableName;

        setTitle("ArrayCreator");
        setSize(300, 160);

        sizeOfArrayField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("The size of the array: ", SwingConstants.LEFT));
        panel.add(sizeOfArrayField);
        addButtonToPanel(panel, objectManager);
        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

    private void addButtonToPanel(JPanel panel, ObjectManager objectManager) {
        final JButton generateButton = new JButton("Generate");
        generateButton.setActionCommand("Generate");
        generateButton.addActionListener(this);
        final JButton emptyGenerateButton = new JButton("Empty Generate");
        emptyGenerateButton.setActionCommand("emptyGenerate");
        emptyGenerateButton.addActionListener(this);
        panel.add(generateButton);
        panel.add(emptyGenerateButton);
    }

    int getArraySize() {
        return Integer.valueOf(sizeOfArrayField.getText());
    }

    void printToDialog(Object object) {
        if (Objects.isNull(object)) {
            JOptionPane.showMessageDialog(
                    null,
                    "null");
            return;
        }
        JOptionPane.showMessageDialog(
                null,
                object.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "Generate")) {
            int arraySize = getArraySize();
            JOptionPane.showMessageDialog(
                    null,
                    objectName + " array is what you want");
            try {
                objectManager.createDefaultArrayAndSave(objectName, valuableName, Integer.valueOf(sizeOfArrayField.getText()));
            } catch (final ClassNotFoundException er) {
                ErrorHandleDialogView.showCause(er);
            }
        }
        if (Objects.equals(e.getActionCommand(), "emptyGenerate")) {
            int arraySize = getArraySize();
            JOptionPane.showMessageDialog(
                    null,
                    objectName + " array is what you want");
            try {
                objectManager.createEmptyArrayAndSave(objectName, valuableName, Integer.valueOf(sizeOfArrayField.getText()));
            } catch (final ClassNotFoundException er) {
                ErrorHandleDialogView.showCause(er);
            }
        }
    }
}