package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;

public class InvokeConstructorView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private Constructor constructor;
    private JTextField valuableNameField;

    InvokeConstructorView(JDialog owner, final ObjectManager objectManager, final Constructor constructor) {
        super(owner);
        this.objectManager = objectManager;
        this.constructor = constructor;
        getContentPane().setLayout(new FlowLayout());

        setTitle("Constructor invoker");
        setSize(300, 160);

        valuableNameField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("The valuable name", SwingConstants.LEFT));
        panel.add(valuableNameField);
        final JButton generateButton = new JButton("Generate");
        generateButton.setActionCommand("Generate");
        generateButton.addActionListener(this);
        panel.add(generateButton);
        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(valuableNameField);
        System.out.println(constructor);
        objectManager.invokeConstructorWithNoArgsAndSave(constructor, valuableNameField.getText());
    }
}
