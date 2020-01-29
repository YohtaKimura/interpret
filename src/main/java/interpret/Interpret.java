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
                        final ObjectManager objectManager = new ObjectManager();
                        ObjectManagerDialog objectManagerDialog = new ObjectManagerDialog(objectManager);
                        final InitialFrame initialFrame = new InitialFrame(objectManager, objectManagerDialog);
                    }
                }
        );
    }
}
