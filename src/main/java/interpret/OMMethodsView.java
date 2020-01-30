package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMMethodsView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    public OMMethodsView(JDialog owner, final ObjectManager objectManager, Object o) {
        this.objectManager = objectManager;
        this.o = o;
        getContentPane().setLayout(new FlowLayout());
        JButton btn = new JButton(objectManager.getMethods(o).get()[0].toString());
        btn.addActionListener(this);
        getContentPane().add(btn);
        setTitle("O.M. Methods View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        objectManager.invokeMethodByName(o, null);
    }
}
