package interpret;

import org.graalvm.compiler.nodes.calc.IntegerTestNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

public class DirectlySetterForParameterView extends JDialog implements ActionListener {
    private final ObjectManager objectManager;
    private final String fieldName;
    private JTextField valuableField;
    private final String parameterType;
    private final Map<String, Object> parametersMap;

    DirectlySetterForParameterView(JDialog owner, final ObjectManager objectManager, final Map<String, Object> parametersMap, final String parameterName, final String parameterType) {
        super(owner);
        this.objectManager = objectManager;
        this.fieldName = parameterName;
        this.parameterType = parameterType;
        this.parametersMap = parametersMap;
        getContentPane().setLayout(new FlowLayout());

        setTitle("Directly setter");
        setSize(300, 160);

        valuableField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Value you wanna set", SwingConstants.LEFT));
        panel.add(valuableField);
        final JButton generateButton = new JButton("Set directly");
        generateButton.setActionCommand("Direct");
        generateButton.addActionListener(this);
        panel.add(generateButton);
        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(valuableField);
        String newValueText = valuableField.getText();
        Class<?> parameterClass = this.objectManager.getType(parameterType).get();
        System.out.println(parameterClass);

        if (Objects.equals(parameterType, String.class.getName())) {
            parametersMap.put(fieldName, newValueText);
            JOptionPane.showMessageDialog(
                    null,
                    "Input " + valuableField.getText() + "! Close this window and return previous.");
            return;
        }

        if (Objects.equals(parameterClass, int.class)) {
            System.out.println(fieldName);
            parametersMap.put(fieldName, Integer.parseInt(newValueText));
                        JOptionPane.showMessageDialog(
                    null,
                    "Input " + valuableField.getText() + "! Close this window and return previous.");
            return;
        }

        System.out.println("fail");
        return;
// TODO: care primitives
//        if (Objects.equals(parameterType, String.class.getName())) {
//            objectManager.setPrimitiveIntValueDirectly(o, fieldName, newValueText);
//            return;
//        }
    }
}