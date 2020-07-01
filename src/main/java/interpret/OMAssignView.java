package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMAssignView extends JDialog implements ActionListener {
    private final JDialog owner;
    private final ObjectManager objectManager;
    private final String objectName;
    private final String valuableName;
    private final int index;
    OMAssignView(JDialog owner, ObjectManager objectManager, String objectName, String valuableName, int index) {
        this.owner = owner;
        this.objectManager = objectManager;
        this.objectName = objectName;
        this.valuableName = valuableName;
        this.index = index;
        add(new JLabel("An element of " + index + " index is null"));
        getContentPane().setLayout(new GridLayout(2, 2));
        JButton btn = new JButton("Assign by constructor");
        btn.setActionCommand("");
        btn.addActionListener(this);
        getContentPane().add(btn);
        setTitle("O.M. null element of array View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new OMArrayConstructorListView(this, this.objectManager, this.objectName, this.valuableName, this.index);
    }
}
