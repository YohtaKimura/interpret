package interpret;

import javax.swing.*;
import java.util.Objects;

public class ErrorHandleDialogView {
    static void showCause(final Exception e) {

        System.out.println(e.getCause());
        Throwable er = e;
        while (Objects.nonNull(er.getCause())) {
            er = er.getCause();
        }
        JOptionPane.showMessageDialog(
                null, "Error: " + er.toString()
        );
    }
}
