package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMFieldsView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    public OMFieldsView(JDialog owner, final ObjectManager objectManager, Object o) {
        this.objectManager = objectManager;
        this.o = o;
        getContentPane().setLayout(new FlowLayout());
        JButton btn = new JButton(objectManager.getFields(o).get()[0].toString());
        btn.addActionListener(this);
        getContentPane().add(btn);
        setTitle("O.M. Fields View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(objectManager.getFieldValueByName(o, null).get());
    }
}
