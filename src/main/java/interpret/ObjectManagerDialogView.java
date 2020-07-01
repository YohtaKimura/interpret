package interpret;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectManagerDialogView extends JDialog implements Runnable, ActionListener {
    private JFrame owner;
    private JPanel panel;
    private final ObjectManager objectManager;
    private List<String> objectNames;


    public ObjectManagerDialogView(ObjectManager objectManager) {
        this(objectManager, null);
    }

    public ObjectManagerDialogView(ObjectManager objectManager, JFrame owner){
        super(owner);
        this.owner = owner;
        this.panel = new JPanel();
        this.objectManager = objectManager;
        objectNames = objectManager.getNames();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Object manager");
		this.setMinimumSize(new Dimension(300, 160));
		this.pack();
		if (Objects.nonNull(owner)) {
      		setPanel(panel);
        }
	}

    public void setOwner(JFrame owner) {
        this.owner = owner;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
        setObjectButtonList();
        this.panel.setSize(new Dimension(300, 300));
        this.setContentPane(this.panel);
        this.setVisible(true);
    }

    private void setObjectButtonList() {
        for (String name : objectNames) {
            JButton btn = new JButton(name);
            btn.setActionCommand(name);
            btn.addActionListener(this);
            panel.add(btn);
        }
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
        String name = e.getActionCommand();
        System.out.println(objectManager.getObjectByName(name).get().getClass());
        Object o = objectManager.getObjectByName(name).get();
        if (objectManager.isArray(o)) {
            new OMArrayView(this, objectManager, o, e.getActionCommand());
            return; // TODO: implement OMMemberView for array
        }
        Field[] fields = objectManager.getFields(o).get();
        Method[] methods = objectManager.getMethods(o).get();
        new OMMemberView(this, objectManager, o);
    }
}
