package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMMemberView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    OMMemberView(JDialog owner, final ObjectManager objectManager, final Object o) {
        super(owner);
        this.objectManager = objectManager;
        this.o = o;
        getContentPane().setLayout(new FlowLayout());
        JButton btn = new JButton("Fields");
        btn.addActionListener(this);
        getContentPane().add(btn);
        JButton btn2 = new JButton("Methods");
        btn2.addActionListener(this);
        getContentPane().add(btn2);
        setTitle("O.M. Member View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new OMFieldsView(this, objectManager, o);
    }
}
