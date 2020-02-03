package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConstructorsListView extends JDialog implements ActionListener {
    private final static String fieldsToken = "Fields";
    private final static String methodsToken = "Methods";
    private final List<Constructor> constructorList;
    private final List<String> constructorsNameList;
    private final ObjectManager objectManager;
    ConstructorsListView(JFrame owner, final ObjectManager objectManager, final String objectName) {
        this(owner, objectManager, objectName, null);
    }

        ConstructorsListView(JFrame owner, final ObjectManager objectManager, final String objectName, final String valuableName) {
        super(owner);
        this.objectManager = objectManager;
        this.constructorList = objectManager.getConstructors(objectName).get();
        this.constructorsNameList = null; // TODO: implement

        getContentPane().setLayout(new FlowLayout());
        JButton btn = new JButton(fieldsToken);
        btn.setActionCommand(fieldsToken);
        btn.addActionListener(this);
        getContentPane().add(btn);
        JButton btn2 = new JButton(methodsToken);
        btn2.setActionCommand(methodsToken);
        btn2.addActionListener(this);
        getContentPane().add(btn2);
        setTitle("Constructors List");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // final String token = e.getActionCommand();
        System.out.println("Hello");
    }
}
