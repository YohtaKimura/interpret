package interpret;

import java.lang.reflect.Constructor;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorView extends JDialog implements ActionListener {
    private JDialog owner;
    private JTextField valuableNameField;
    private final ObjectManager objectManager;
    private final List<String> parameterNameList;
    private final Map<String, JComboBox> jComboBoxMap;
    private final Map<String, List<String>> valuableListsMap;
    private final Map<String, String> parameterTypeMap;
    private final Map<String, Object> parametersMap;
    private final Constructor constructor;


    ConstructorView(final JDialog owner, final ObjectManager objectManager, final String objectName, final String valuableName, final String parameterTypes) {
        this.owner = owner;
        this.objectManager = objectManager;
        this.jComboBoxMap = new HashMap<>();
        this.valuableListsMap = new HashMap<>();
        final String[] parameterTypesArray = parameterTypes.split(",");
        this.parameterNameList = this.objectManager.getParameterNameListOfConstructor(objectName, parameterTypesArray).get();
        this.parametersMap = new HashMap<>();
        this.parameterTypeMap = this.objectManager.getParameterTypeMapOfConstructor(objectName, parameterTypesArray).get();
        this.constructor = objectManager.getConstructor(objectName, parameterTypes.split(",")).get();

        setTitle("Generator");
        setSize(300, 200);

        final Container container = getContentPane();
        container.setLayout(new GridLayout(0, 4));
        container.add(new JLabel("The class name"));
        container.add(new JLabel(objectName));
        container.add(new JLabel("Input valuable name of this"));
        valuableNameField = new JTextField(valuableName);
        container.add(valuableNameField);

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
                parameterList.add(parametersMap.get(parameterName));
            }
            System.out.println(valuableNameField.getText());
            System.out.println(this.constructor);
            objectManager.invokeConstructorAndSave(this.constructor, valuableNameField.getText(), parameterList.toArray());
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
            new DirectlySetterForParameterView(this, parametersMap, parameterName, parameterTypeMap.get(parameterName));
            System.out.println("DirectInputView"); //TODO: implement
            return;
        };
    }


}
