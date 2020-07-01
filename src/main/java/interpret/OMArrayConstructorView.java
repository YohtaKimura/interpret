package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class OMArrayConstructorView extends JDialog implements ActionListener {
    private JDialog owner;
    private final ObjectManager objectManager;
    private List<String> parameterNameList;
    private final Map<String, JComboBox> jComboBoxMap;
    private final Map<String, List<String>> valuableListsMap;
    private Map<String, String> parameterTypeMap;
    private final Map<String, Object> parametersMap;
    private Constructor constructor;
    private final String valuableName;
    private final int index;

    OMArrayConstructorView(final JDialog owner, final ObjectManager objectManager, final String objectName, final String valuableName, final String parameterTypes, final int index) {
        this.owner = owner;
        this.objectManager = objectManager;
        this.index = index;
        this.valuableName = valuableName;
        this.jComboBoxMap = new HashMap<>();
        this.valuableListsMap = new HashMap<>();
        this.parametersMap = new HashMap<>();
        final String[] parameterTypesArray = parameterTypes.split(", ");
        try {
            this.parameterTypeMap = this.objectManager.getParameterTypeMapOfConstructor(objectName, parameterTypesArray).get();
        } catch (final ClassNotFoundException er) {
            ErrorHandleDialogView.showCause(er);
        }
        try {
            this.parameterNameList = this.objectManager.getParameterNameListOfConstructor(objectName, parameterTypesArray).get();
        } catch (final ClassNotFoundException err) {
            ErrorHandleDialogView.showCause(err);
            return;
        }

        try {
            this.constructor = objectManager.getConstructor(objectName, parameterTypes.split(", ")).get();
        } catch (final ClassNotFoundException er) {
            ErrorHandleDialogView.showCause(er);
            return;
        }

        setTitle("Generator");
        setSize(300, 200);

        final Container container = getContentPane();
        container.setLayout(new GridLayout(0, 4));
        container.add(new JLabel("The class name"));
        container.add(new JLabel(objectName));
        container.add(new JLabel("Valuable name and index"));
        container.add(new JLabel(valuableName + "[" + this.index + "]"));

        addParametersFields(container, parameterNameList);

        final JButton getButton = new JButton("Generate");
        getButton.setActionCommand("Generate");
        getButton.addActionListener(this);
        container.add(getButton);
        setVisible(true);
    }

    private void addParametersFields(final Container container, final List<String> parameterNameList) {
        for (String parameterName: parameterNameList) {
            this.parametersMap.put(parameterName, null);
        }

        for (String parameterName: parameterNameList) {
            container.add(new JLabel("class: " + this.parameterTypeMap.get(parameterName) + ", name: " +parameterName));
//            List<String> valuableList = objectManager.getObjectNamesAlreadyExistByObjectFieldType(o, parameterName);
            List<String> valuableList = Collections.EMPTY_LIST;
            this.valuableListsMap.put(parameterName, valuableList);
            List<String> messageList = valuableList.stream().map(val -> val + ": " + this.objectManager.getObjectByName(val).get().toString()).collect(Collectors.toList());
            JComboBox combo = new JComboBox(messageList.toArray());
            this.jComboBoxMap.put(parameterName, combo);
            container.add(combo);
            JButton changeButton = new JButton("Change");
            changeButton.setActionCommand("Change " + parameterName);
            changeButton.addActionListener(this);
            container.add(changeButton);
            JButton inputDirectlyButton = new JButton("Input directly");
            inputDirectlyButton.setActionCommand("InputDirectly "+ parameterName);
            inputDirectlyButton.addActionListener(this);
            container.add(inputDirectlyButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String[] command = e.getActionCommand().split(" ");
        if (command.length == 1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Generate!");
            final List<Object> parameterList = new ArrayList<>();
            for (String parameterName: this.parameterNameList) {
                System.out.println(parameterName);
                parameterList.add(parametersMap.get(parameterName));
            }
            System.out.println(this.constructor);
            try {
                objectManager.invokeArrayConstructorAndSave(this.constructor, this.valuableName, this.index, parameterList.toArray());
            } catch (Exception er) {
                ErrorHandleDialogView.showCause(er);
            }
            return;
        }
        if (Objects.equals(command[0], "Change")) {
            final String parameterName = command[1];
            final int index = jComboBoxMap.get(parameterName).getSelectedIndex();
            final String newValuableName = valuableListsMap.get(parameterName).get(index);
//            objectManager.setValue(o, parameterName, newValuableName);
            return;
        }
        if (Objects.equals(command[0], "InputDirectly")) {
            final String parameterName = command[1];
            new DirectlySetterForParameterView(this, this.objectManager, parametersMap, parameterName, parameterTypeMap.get(parameterName));
            System.out.println("DirectInputView"); //TODO: implement
            return;
        };
    }
}
