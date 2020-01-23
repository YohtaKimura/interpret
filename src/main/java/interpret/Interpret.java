package interpret;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Interpret {
    private static MenuItem propertiesMenu;
    public static void main(String[] args) {
        EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        // TODO: extract to another class
                        TextFrame frame = new TextFrame();
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);

                        MenuBar menuBar = new MenuBar();
                        frame.setMenuBar(menuBar);
                        // [View]
                        Menu viewMenu = new Menu("Utils");
                        viewMenu.setShortcut(new MenuShortcut('V'));
                        menuBar.add(viewMenu);
                        // [View] -> [Properties...]
                        propertiesMenu = new MenuItem("Open objects manager...", new MenuShortcut('P'));
                        propertiesMenu.addActionListener(null); // need extract to other class and extends Frame implements ActionListener
                        viewMenu.add(propertiesMenu);
                    }
                }
        );
    }
}
