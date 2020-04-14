package interpret;

import com.sun.tools.javac.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OMMethodsView extends JDialog implements ActionListener {
    private final JDialog owner;
    private final ObjectManager objectManager;
    private final Object o;
    private final List<String> methodNamesList;
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
        this.methodNamesList = objectManager.getMethodNamesList(o).get();
        this.panel = new JPanel();
        this.scrollPane = new JScrollPane();
        this.panel.setLayout(new GridLayout(0, methodNamesList.size()));
        this.searchField = new JTextField();
        this.panel.setLayout(new GridLayout(methodNamesList.size(), 2));
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
            makeMethodButtons(panel, methodNamesList);
        } else {
            final List<String> filteredMethodNamesList = methodNamesList.stream().filter(mn -> Objects.equals(query, mn)).collect(Collectors.toList());
            makeMethodButtons(panel, filteredMethodNamesList);
        }
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        this.scrollPane.setViewportView(panel);

        setTitle("O.M. Methods View");
        setSize(200, 150);
        setVisible(true);
    }

    private void makeMethodButtons(JPanel panel, List<String> filteredMethodNamesList) {
        for (final String methodName: filteredMethodNamesList) {
            final JButton btn = new JButton(methodName);
            btn.setPreferredSize(new Dimension(100, 30));
            btn.setMinimumSize(new Dimension(100, 30));
            btn.setActionCommand("Method " + methodName);
            btn.addActionListener(this);
            panel.add(btn);
//            this.panel.add(new JLabel("Return value type: , Arguments: ,", SwingConstants.LEFT));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String[] commands = command.split(" ");
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
