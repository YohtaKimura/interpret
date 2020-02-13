package interpret;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class OMFieldsView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final Object o;
    private final Map<String, JComboBox> jComboBoxMap;
    private final Map<String, List<String>> valuableListsMap;
    public OMFieldsView(JDialog owner, final ObjectManager objectManager, Object o) {
        this.objectManager = objectManager;
        this.o = o;
        this.jComboBoxMap = new HashMap<>();
        this.valuableListsMap = new HashMap<>();
        List<String> fieldsNameList = objectManager.getFieldsNameList(o).get();
        getContentPane().setLayout(new GridLayout(fieldsNameList.size(), 4));
        for (String fieldName: fieldsNameList) {
            final JButton btn = new JButton(fieldName);
            btn.setActionCommand(fieldName);
            btn.addActionListener(this);
            getContentPane().add(btn);
            List<String> valuableList = objectManager.getObjectNamesAlreadyExistByObjectFieldType(o, fieldName);
            valuableListsMap.put(fieldName, valuableList);
            List<String> messageList = valuableList.stream().map(val -> val + ": " + objectManager.getObjectByName(val).get().toString()).collect(Collectors.toList());
            JComboBox combo = new JComboBox(messageList.toArray());
            jComboBoxMap.put(fieldName, combo);
            getContentPane().add(combo);
            JButton changeButton = new JButton("Change");
            changeButton.setActionCommand("Change " + fieldName);
            changeButton.addActionListener(this);
            getContentPane().add(changeButton);
            JButton inputDirectlyButton = new JButton("Input directly");
            inputDirectlyButton.setActionCommand("InputDirectly "+ fieldName);
            inputDirectlyButton.addActionListener(this);
            getContentPane().add(inputDirectlyButton);
        }
        setTitle("O.M. Fields View");
        setSize(200, 150);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String[] command = e.getActionCommand().split(" ");
        if (command.length == 1) {
            String fieldName = command[0];
            JOptionPane.showMessageDialog(
                    null,
                    objectManager.getFieldValueByName(o, fieldName).get());
        return;
        }
        if (Objects.equals(command[0], "Change")) {
            final String fieldName = command[1];
            final int index = jComboBoxMap.get(fieldName).getSelectedIndex();
            final String newValuableName = valuableListsMap.get(fieldName).get(index);
            objectManager.setValue(o, fieldName, newValuableName);
            return;
        }
        if (Objects.equals(command[0], "InputDirectly")) {
            final String fieldName = command[1];
            new DirectlySetterView(this, o, objectManager, fieldName);
            System.out.println("DirectInputView"); //TODO: implement
            return;
        }
    }
}
