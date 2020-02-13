package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConstructorGetterView extends JDialog implements ActionListener {
    private JFrame owner;
    private JTextField objectNameField;
    private JTextField typesOfParametersField;
    private final String valuableName;
    private final ObjectManager objectManager;

    ConstructorGetterView(final JFrame owner, final ObjectManager objectManager, final String objectName, final String valuableName) {
        this.owner = owner;
        this.objectManager = objectManager;
        this.valuableName =valuableName;
        setTitle("ConstructorGetter");
        setSize(300, 160);

        objectNameField = new JTextField(objectName);
        typesOfParametersField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("The class name", SwingConstants.LEFT));
        panel.add(objectNameField);
        panel.add(new JLabel("Types of parameters ", SwingConstants.LEFT));
        panel.add(typesOfParametersField);
        final JButton getButton = new JButton("Get");
        getButton.setActionCommand("Get");
        getButton.addActionListener(this);
        panel.add(getButton);
        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        String objectName = objectNameField.getText();
        String typesOfParameters = typesOfParametersField.getText();
        new ConstructorView(this, objectManager, objectName, this.valuableName, typesOfParameters);
            }
}
