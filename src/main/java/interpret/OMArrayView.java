package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMArrayView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    private final JTextField indexField;

    public OMArrayView(JDialog owner, final ObjectManager objectManager, Object o) {
        this.objectManager = objectManager;
        this.o = o;
        this.indexField = new JTextField();
        add(new JLabel("Index of " + o.getClass().getName(), SwingConstants.LEFT));
        add(new JLabel("Array size:  " + o.getClass().cast(o), SwingConstants.LEFT));
        add(indexField);
        getContentPane().setLayout(new GridLayout(3, 2));
        JButton btn = new JButton("Look into");
        btn.addActionListener(this);
        getContentPane().add(btn);
        setTitle("O.M. Array View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new OMMemberView(this, objectManager, objectManager.getArrayElement(o, Integer.valueOf(indexField.getText())).get());
    }
}
