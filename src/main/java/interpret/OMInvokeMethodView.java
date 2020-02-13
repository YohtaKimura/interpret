package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class OMInvokeMethodView  extends JDialog implements ActionListener {
    private JDialog owner;
    private final ObjectManager objectManager;
    private final Object o;
    private final java.util.List<String> parameterNameList;
    private final Map<String, JComboBox> jComboBoxMap;
    private final Map<String, java.util.List<String>> valuableListsMap;
    private final Map<String, String> parameterTypeMap;
    private final Map<String, Object> parametersMap;
    private final String methodName;
    private final Method method;


    OMInvokeMethodView(final JDialog owner, final ObjectManager objectManager, final Object o, final String methodName) {
        this.owner = owner;
        this.objectManager = objectManager;
        this.o = o;
        this.methodName = methodName;
        this.method = objectManager.getFirstMethodFoundByName(o, methodName);
        this.jComboBoxMap = new HashMap<>();
        this.valuableListsMap = new HashMap<>();
        this.parameterNameList = this.objectManager.getMethodParameterNameList(o, methodName).get();
        this.parametersMap = new HashMap<>();
        this.parameterTypeMap = this.objectManager.getParameterTypeMapOfMethod(method).get();

        setTitle("Invoker");
        setSize(300, 200);

        final Container container = getContentPane();
        container.setLayout(new GridLayout(0, 4));

        container.add(new JLabel("The method name"));
        container.add(new JLabel(methodName));
        container.add(new JLabel("Input arguments"));
        container.add(new JLabel("Return Type is " + method.getReturnType().toString()));

        addParametersFields(container, parameterNameList);

        final JButton getButton = new JButton("Invoke");
        getButton.setActionCommand("Invoke");
        getButton.addActionListener(this);
        container.add(getButton);
        setVisible(true);
    }

    private void addParametersFields(final Container container, final java.util.List<String> parameterNameList) {
        for (String parameterName: parameterNameList) {
            this.parametersMap.put(parameterName, null);
        }

        for (String parameterName: parameterNameList) {
            container.add(new JLabel("class: " + this.parameterTypeMap.get(parameterName) + ", name: " +parameterName));
//            List<String> valuableList = objectManager.getObjectNamesAlreadyExistByObjectFieldType(o, parameterName);
            java.util.List<String> valuableList = Collections.EMPTY_LIST;
            this.valuableListsMap.put(parameterName, valuableList);
            java.util.List<String> messageList = valuableList.stream().map(val -> val + ": " + this.objectManager.getObjectByName(val).get().toString()).collect(Collectors.toList());
            JComboBox combo = new JComboBox(messageList.toArray());
            this.jComboBoxMap.put(parameterName, combo);
            container.add(combo);
            JButton changeButton = new JButton("Input");
            changeButton.setActionCommand("Input " + parameterName);
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
                    "Invoke!");
            final List<Object> parameterList = new ArrayList<>();
            for (String parameterName: this.parameterNameList) {
                parameterList.add(parametersMap.get(parameterName));
            }
            System.out.println(this.method);
            objectManager.invokeMethodByNameWithArgs(this.o, this.method, parameterList.toArray());
            return;
        }
        if (Objects.equals(command[0], "Input")) {
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
