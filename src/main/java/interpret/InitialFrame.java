package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialFrame implements ActionListener {
    private static MenuItem propertiesMenu;
    private static JPanel panel = new JPanel();
    private JFrame frame;

    public InitialFrame(){
        frame = new TextFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        MenuBar menuBar = new MenuBar();
        frame.setMenuBar(menuBar);

        // [Utils]
        Menu viewMenu = new Menu("Utils");
        viewMenu.setShortcut(new MenuShortcut('V'));
        menuBar.add(viewMenu);

        // [Utils] -> [Open objects manager]
        propertiesMenu = new MenuItem("Open objects manager...", new MenuShortcut('P'));
        propertiesMenu.addActionListener(this); // need extract to other class and extends Frame implements ActionListener
        viewMenu.add(propertiesMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ObjectManagerDialog(this.frame, panel);
    };
}
