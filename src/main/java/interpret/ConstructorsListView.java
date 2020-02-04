package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConstructorsListView extends JDialog implements ActionListener {
    private final List<Constructor> constructorList;
    private final List<String> constructorNameList;
    private final ObjectManager objectManager;
    ConstructorsListView(JFrame owner, final ObjectManager objectManager, final String objectName) {
        this(owner, objectManager, objectName, null);
    }

        ConstructorsListView(JFrame owner, final ObjectManager objectManager, final String objectName, final String valuableName) {
        super(owner);
        this.objectManager = objectManager;
        this.constructorList = objectManager.getConstructors(objectName).get();
        this.constructorNameList = constructorList.stream().map(c -> c.toString()).collect(Collectors.toList());
//        this.constructorNameList.stream().forEach(name -> System.out.println(name));

        getContentPane().setLayout(new FlowLayout());
        for (String constructorsName: constructorNameList) {
            JButton btn = new JButton(constructorsName);
            btn.setActionCommand(constructorsName);
            btn.addActionListener(this);
            getContentPane().add(btn);
        }
        setTitle("Constructors List");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Constructor constructor: constructorList) {
           if (Objects.equals(e.getActionCommand(), constructor.toString())) {
               new InvokeConstructorView(this, objectManager, constructor);
            }
        }
    }
}
