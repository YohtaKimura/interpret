package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class OMMemberView extends JDialog implements ActionListener {
    private final static String fieldsToken = "Fields";
    private final static String methodsToken = "Methods";
    private final ObjectManager objectManager;
    private final Object o;
    OMMemberView(JDialog owner, final ObjectManager objectManager, final Object o) {
        super(owner);
        this.objectManager = objectManager;
        this.o = o;
        getContentPane().setLayout(new FlowLayout());
        JButton btn = new JButton(fieldsToken);
        btn.setActionCommand(fieldsToken);
        btn.addActionListener(this);
        getContentPane().add(btn);
        JButton btn2 = new JButton(methodsToken);
        btn2.setActionCommand(methodsToken);
        btn2.addActionListener(this);
        getContentPane().add(btn2);
        setTitle("O.M. Member View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String token = e.getActionCommand();
        if (Objects.equals(token, fieldsToken)) {
            new OMFieldsView(this, objectManager, o);
        }
        if (Objects.equals(token, methodsToken)) {
            new OMMethodsView(this, objectManager, o);
        }
    }
}
