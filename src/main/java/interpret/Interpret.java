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
                        ObjectManager objectManager = new ObjectManager();
                        InitialFrame initialFrame = new InitialFrame(objectManager);
                    }
                }
        );
    }
}
