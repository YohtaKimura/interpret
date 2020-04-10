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
    private final JPanel panel;
    private final JScrollPane scrollPane;


    // TODO: Add search method
    public OMMethodsView(JDialog owner, final ObjectManager objectManager, Object o) {
        this.objectManager = objectManager;
        this.o = o;
        this.methodNamesList = objectManager.getMethodNamesList(o).get();
        this.panel = new JPanel();
        this.scrollPane = new JScrollPane();
//        frame.getContentPane().setLayout(new GridLayout(methodNamesList.size(), 0));

//        setBounds(100, 100, 450, 300);
        this.panel.setLayout(new GridLayout(methodNamesList.size(), 0));
//        getContentPane().setLayout(new GridLayout(methodNamesList.size(), 0));

        for (final String methodName: methodNamesList) {
            final JButton btn = new JButton(methodName);
            btn.setPreferredSize(new Dimension(100, 30));
            btn.setMinimumSize(new Dimension(100, 30));
            btn.setActionCommand(methodName);
            btn.addActionListener(this);
//            scrollPane.setViewportView(btn);
            panel.add(btn);
//            frame.getContentPane().add(btn);
        }
// getContentPane().add(scrollPane, BorderLayout.CENTER);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        this.scrollPane.setViewportView(panel);

        setTitle("O.M. Methods View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String methodName = e.getActionCommand();
        // objectManager.invokeMethodByName(o, methodName);
        new OMInvokeMethodView(this, objectManager, o, methodName);
    }
}
