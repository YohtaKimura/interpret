package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObjectManagerDialog extends JDialog implements Runnable, ActionListener {
    private final JFrame owner;
    private final JPanel panel;

    public ObjectManagerDialog(JFrame owner, JPanel panel){
        super(owner);
        this.owner = owner;
        this.panel = panel;

        JButton btn = new JButton("Button");
        btn.addActionListener(this);

		panel.setPreferredSize(new Dimension(300, 300));
		panel.add(btn);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Object manager");
		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
	}
	public void init(){
		System.out.println("Hello");
	}

    @Override
    public void run() {
        setForeground(new Color(10,10,10));
        setBackground(new Color(255,255,255));
        new Thread(this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Use some ObjectManager's method
        System.out.println("Field");
    }
}
