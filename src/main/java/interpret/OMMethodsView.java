package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OMMethodsView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    private final List<String> methodNamesList;

    public OMMethodsView(JDialog owner, final ObjectManager objectManager, Object o) {
        this.objectManager = objectManager;
        this.o = o;
        this.methodNamesList = objectManager.getMethodNamesList(o).get();

        getContentPane().setLayout(new GridLayout(methodNamesList.size(), 0));
        for (String methodName: methodNamesList) {
            final JButton btn = new JButton(methodName);
            btn.setActionCommand(methodName);
            btn.addActionListener(this);
            getContentPane().add(btn);
        }

        setTitle("O.M. Methods View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String methodName = e.getActionCommand();
        objectManager.invokeMethodByName(o, methodName);
    }
}
