package interpret;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OMFieldsView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    public OMFieldsView(JDialog owner, final ObjectManager objectManager, Object o) {
        this.objectManager = objectManager;
        this.o = o;
        getContentPane().setLayout(new FlowLayout());
        List<String> fieldsNameList = objectManager.getFieldsNameList(o).get();
        for (String fieldName: fieldsNameList) {
            final JButton btn = new JButton(fieldName);
            btn.setActionCommand(fieldName);
            btn.addActionListener(this);
            getContentPane().add(btn);
        }
        setTitle("O.M. Fields View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fieldName = e.getActionCommand();
        JOptionPane.showMessageDialog(
                    null,
                    objectManager.getFieldValueByName(o, fieldName).get());
    }
}
