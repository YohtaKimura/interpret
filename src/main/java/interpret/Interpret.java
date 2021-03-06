package interpret;

import java.awt.*;

public class Interpret {
    public static void main(String[] args) {
        EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        final ObjectManager objectManager = new ObjectManager();
                        final InitialFrameView initialFrame = new InitialFrameView(objectManager);
                    }
                }
        );
    }
}
