package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ConstructorsListView extends JDialog implements ActionListener {
    private final String objectName;
    private final String valuableName;
    private List<Constructor> constructorList;
    private List<String> constructorNameList;
    private final ObjectManager objectManager;

    ConstructorsListView(JDialog owner, final ObjectManager objectManager, final String objectName, final String valuableName) {
        super(owner);
        this.objectManager = objectManager;
        this.objectName = objectName;
        this.valuableName = valuableName;
        try {
            this.constructorList = objectManager.getConstructors(objectName).get();
        } catch (final ClassNotFoundException er) {
            ErrorHandleDialogView.showCause(er);
            return;
        }

        this.constructorNameList = constructorList.stream().map(c -> c.getName()).collect(Collectors.toList());
//        this.constructorNameList.stream().forEach(name -> System.out.println(name));
// TODO: make container of parameters
        final Container container = getContentPane();
        container.setLayout(new GridLayout(0, 2));
        for (Constructor constructor: constructorList) {
            JButton btn = new JButton(constructor.getName());
            StringBuilder stringLabel = new StringBuilder();
            Parameter[] parameters = constructor.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                stringLabel.append(parameters[i].getType().getName());
                if (i < parameters.length - 1) {
                    stringLabel.append(", ");
                }
            };
            String parametersList = stringLabel.toString();
            btn.setActionCommand(parametersList);
            btn.addActionListener(this);
            container.add(btn);
            container.add(new Label(parametersList));
        }
        setTitle("Constructors List");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ConstructorView(this, this.objectManager, this.objectName, this.valuableName, e.getActionCommand());
    }
}
