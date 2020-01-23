package interpret;

import javax.swing.*;
import java.awt.*;

public class ObjectManagerDialog extends JDialog implements Runnable{
    private Dimension dimension;
    private final JFrame owner;
    private final JPanel panel;
    public ObjectManagerDialog(JFrame owner, JPanel panel){
        super(owner);
        this.owner = owner;
        this.panel = panel;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Object manager");
		this.setContentPane(panel);
		panel.setPreferredSize(new Dimension(300, 300));
		this.pack();
		this.setVisible(true);
	}
	public void init(){
		System.out.println("Hello");
	}

    @Override
    public void run() {
        dimension = new Dimension(100, 100);
        setForeground(new Color(10,10,10));
        setBackground(new Color(255,255,255));
        new Thread(this).start();
    }
}
