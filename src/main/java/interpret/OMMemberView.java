package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMMemberView extends JDialog implements ActionListener {
    OMMemberView(JDialog owner) {
        super(owner);
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
        setVisible(true);
    }
}
