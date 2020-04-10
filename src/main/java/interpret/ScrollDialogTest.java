package interpret;

import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JLabel;

import java.util.List;

public class ScrollDialogTest extends JDialog {
    private final JScrollPane scrollPane = new JScrollPane();
    //I added the breaks to the label below to be able to scroll down.
    // set here buttons view
    //private final JLabel lblContent = new JLabel("<html><body><p>Content<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>Content<br><br><br><br><br><br>Content</p></body></html>");
    //private final JDialog test = new JDialog();
    private final JPanel panel = new JPanel();

    public static void main(String[] args) {
                    ScrollDialogTest dialog = new ScrollDialogTest();
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
    }

    public ScrollDialogTest() {

        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        panel.setLayout(new GridLayout(100, 0));
        for (int i = 0; i < 100; i++) {
            panel.add(new JButton("number: " + i));
        }
        scrollPane.setViewportView(panel);
    }
}
