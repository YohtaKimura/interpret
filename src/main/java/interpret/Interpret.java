package interpret;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Interpret {
    public static void main(String[] args) {
        EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        TextFrame frame = new TextFrame();
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);
                    }
                }
        );
    }
}
