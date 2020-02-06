package interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialFrameView implements ActionListener {
    private static MenuItem propertiesMenu;
    private final JFrame frame;
    private final ObjectManager objectManager;

    public InitialFrameView(final ObjectManager objectManager){
        this.objectManager = objectManager;
        frame = new InsideOfInitialFrameView(objectManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        MenuBar menuBar = new MenuBar();
        frame.setMenuBar(menuBar);

        // [Utils]
        Menu viewMenu = new Menu("Utils");
        viewMenu.setShortcut(new MenuShortcut('V'));
        menuBar.add(viewMenu);

        // [Utils] -> [Open object manager]
        propertiesMenu = new MenuItem("Open object manager...", new MenuShortcut('P'));
        propertiesMenu.addActionListener(this); // need extract to other class and extends Frame implements ActionListener
        viewMenu.add(propertiesMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new ObjectManagerDialogView(this.objectManager, this.frame);
    };
}
