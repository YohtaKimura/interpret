package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DirectlySetterView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    private final String fieldName;
    private JTextField valuableField;

    DirectlySetterView(JDialog owner, final Object o, final ObjectManager objectManager, final String fieldName) {
        super(owner);
        this.objectManager = objectManager;
        this.o = o;
        this.fieldName = fieldName;
        getContentPane().setLayout(new FlowLayout());

        setTitle("Directly setter");
        setSize(300, 160);

        valuableField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Value you wanna set", SwingConstants.LEFT));
        panel.add(valuableField);
        final JButton generateButton = new JButton("Set directly");
        generateButton.setActionCommand("Direct");
        generateButton.addActionListener(this);
        panel.add(generateButton);
        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(valuableField);
        String newValueText = valuableField.getText();
        if (objectManager.isString(o, fieldName)) {
            objectManager.setStringValueDirectly(o, fieldName, newValueText);
        }
        if (objectManager.isPrimitive(o, fieldName)) {
            objectManager.setPrimitiveIntValueDirectly(o, fieldName, newValueText);
        }

        // TODO: care other primitive
        objectManager.setStringValueDirectly(o, fieldName, valuableField.getText());
    }
}
