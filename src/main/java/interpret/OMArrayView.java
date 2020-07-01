package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class OMArrayView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    private final JTextField indexField;
    private final String valuableName;

    public OMArrayView(JDialog owner, final ObjectManager objectManager, Object o, String valuableName) {
        this.objectManager = objectManager;
        this.o = o;
        this.indexField = new JTextField();
        this.valuableName = valuableName;
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
        int index = Integer.valueOf(indexField.getText());
        Object el = objectManager.getArrayElement(o, index).orElse(null);
        if (Objects.isNull(el)) {
            new OMAssignView(this, this.objectManager, this.o.getClass().getName(), this.valuableName, index);
        return;
        }
        new OMMemberView(this, objectManager, el);
    }
}
