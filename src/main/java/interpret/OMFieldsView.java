package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMFieldsView extends JDialog implements ActionListener {
    public OMFieldsView() {
        getContentPane().setLayout(new FlowLayout());
        JButton btn = new JButton("test");
        btn.addActionListener(this);
        getContentPane().add(btn);
        setTitle("O.M. Member View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("hello");
    }
}
