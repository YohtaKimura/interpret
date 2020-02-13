package interpret;

import java.util.Collections;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ConstructorView extends JDialog implements ActionListener {
    private JDialog owner;
    private JTextField objectNameField;
    private JTextField valuableNameField;
    private final ObjectManager objectManager;
    private final Map<String, JComboBox> jComboBoxMap;
    private final Map<String, List<String>> valuableListsMap;


    ConstructorView(final JDialog owner, final ObjectManager objectManager, final String objectName, final String valuableName, final String parameterTypes) {
        this.owner = owner;
        this.objectManager = objectManager;
        this.jComboBoxMap = new HashMap<>();
        this.valuableListsMap = new HashMap<>();

        setTitle("Generator");
        setSize(300, 200);

        objectNameField = new JTextField();
        final Container container = getContentPane();
        container.setLayout(new GridLayout(0, 4));
        container.add(new JLabel("The class name"));
        container.add(new JLabel(objectName));
        container.add(new JLabel("Input valuable name of this"));
        valuableNameField = new JTextField(valuableName);
        container.add(valuableNameField);

        addParametersFields(container, objectName, parameterTypes);

        final JButton getButton = new JButton("Generate");
        getButton.setActionCommand("Generate");
        getButton.addActionListener(this);
        container.add(getButton);
        setVisible(true);
    }

    private void addParametersFields(final Container container, final String objectName, final String parameterTypes) {
        final String[] parameterTypesArray = parameterTypes.split(",");
        List<String> parameterNameList = this.objectManager.getParameterNameListOfConstructor(objectName, parameterTypesArray).get();
        Map<String, String> parameterTypeMap = this.objectManager.getParameterTypeMapOfConstructor(objectName, parameterTypesArray).get();
        for (String parameterName: parameterNameList) {
            container.add(new JLabel("class: " + parameterTypeMap.get(parameterName) + ", name: " +parameterName));
//            List<String> valuableList = objectManager.getObjectNamesAlreadyExistByObjectFieldType(o, parameterName);
            List<String> valuableList = Collections.EMPTY_LIST;
            valuableListsMap.put(parameterName, valuableList);
            List<String> messageList = valuableList.stream().map(val -> val + ": " + objectManager.getObjectByName(val).get().toString()).collect(Collectors.toList());
            JComboBox combo = new JComboBox(messageList.toArray());
            jComboBoxMap.put(parameterName, combo);
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
System.out.println("hey");
    }
}
