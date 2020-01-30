package interpret;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ObjectManagerDialog extends JDialog implements Runnable, ActionListener {
    private JFrame owner;
    private JPanel panel;
    private final ObjectManager objectManager;
    private List<String> objectNames;
    private List<JButton> objectButtons;

    public ObjectManagerDialog(ObjectManager objectManager) {
        this(objectManager, null, null);
    }

    public ObjectManagerDialog(ObjectManager objectManager, JFrame owner, JPanel panel){
        super(owner);
        this.owner = owner;
        this.panel = panel;
        this.objectManager = objectManager;
        this.objectButtons = new ArrayList<>();
        objectNames = objectManager.getNames();

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Object manager");
		this.setMinimumSize(new Dimension(300, 160));
		this.pack();
		if(Objects.nonNull(panel)) {
            setPanel(panel);
        }
	}

    private void setObjectsButton() {
        for (JButton btn : objectButtons) {
            panel.add(btn);
        }
        for (JButton btn : objectButtons) {
            panel.add(btn);
        }
    }

    public void setOwner(JFrame owner) {
        this.owner = owner;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
        setObjectButtonList();
        setObjectsButton();
        this.panel.setSize(new Dimension(300, 300));
        this.setContentPane(this.panel);
        this.setVisible(true);
    }

    private void setObjectButtonList() {
        for (String name : objectNames) {
            JButton btn = new JButton(name);
            btn.setActionCommand(name);
            btn.addActionListener(this);
            objectButtons.add(btn);
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
        Field[] fields = objectManager.getFields(o).get();
        Method[] methods = objectManager.getMethods(o).get();
        System.out.println(fields[0]);
        System.out.println(methods[0]);
        new OMMemberView(this);
    }
}
