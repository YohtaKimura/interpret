package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OMMethodsView extends JDialog implements ActionListener {
    private final JDialog owner;
    private final ObjectManager objectManager;
    private final Object o;
    private final Map<Method, String> methodParamStringMap;
    private final Map<String, Method> paramStringmethodsMap;
    private final JPanel panel;
    private final JScrollPane scrollPane;
    private JTextField searchField;



    // TODO: Add search method
    public OMMethodsView(final JDialog owner, final ObjectManager objectManager, Object o) {
        this(owner, objectManager, o, null);
    }

        // TODO: Add search method
    public OMMethodsView(final JDialog owner, final ObjectManager objectManager, Object o, final String query) {
        this.owner = owner;
        this.objectManager = objectManager;
        this.o = o;
        this.methodParamStringMap = objectManager.getMethodsParamStringMap(o).get();
        this.paramStringmethodsMap = objectManager.getMethodNameAndParamStringMethodsMap(o).get();
        this.panel = new JPanel();
        this.scrollPane = new JScrollPane();
        this.panel.setLayout(new GridLayout(0, methodParamStringMap.size()));
        this.searchField = new JTextField();
        this.panel.setLayout(new GridLayout(methodParamStringMap.size(), 2));
        this.panel.add(new JLabel("Search method of ", SwingConstants.LEFT));
        this.panel.add(searchField);

        this.panel.add(new JLabel("Search button: ", SwingConstants.LEFT));
        final JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setMinimumSize(new Dimension(100, 30));
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(this);
        panel.add(searchButton);

        if (Objects.isNull(query)) {
            makeMethodButtons(panel, methodParamStringMap);
        } else {
            final Map<Method, String> filteredMethodParamStringMap= methodParamStringMap.entrySet().stream().filter(e -> e.getKey().getName().contains(query)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            makeMethodButtons(panel, filteredMethodParamStringMap);
        }
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        this.scrollPane.setViewportView(panel);

        setTitle("O.M. Methods View");
        setSize(200, 150);
        setVisible(true);
    }

    private void makeMethodButtons(JPanel panel, Map<Method, String> methodParamStringMap) {
        methodParamStringMap.forEach((m, p) -> {
            String methodKey = m.getName() + " " + p;
            final JButton btn = new JButton(methodKey);
            btn.setPreferredSize(new Dimension(100, 30));
            btn.setMinimumSize(new Dimension(100, 30));
            btn.setActionCommand("Method:" + methodKey);
            btn.addActionListener(this);
            panel.add(btn);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] commands = command.split(":");
        if (Objects.equals(commands[0], "Method")) {
            String methodName = commands[1];
            new OMInvokeMethodView(this, objectManager, o, methodName);
            return;
        }
        if (Objects.equals(commands[0], "Search")) {
            String query = searchField.getText();
            new OMMethodsView(this.owner, this.objectManager, this.o, query);
            return;
        }
        // objectManager.invokeMethodByName(o, methodName);


    }
}