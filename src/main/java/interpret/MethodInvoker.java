package interpret;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {
    static void voidAndPublicMethodInvoke(Object o, Method m, Object... args) {
        try {
            m.invoke(o, args);
        } catch (final InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
