package interpret;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class MethodInvoker {
    static void voidAndPublicMethodInvoke(Object o, Method m, Object... args) {
        try {
            Class type = m.getReturnType();
            type.cast(m.invoke(o, args));
        } catch (final InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getStackTrace());
        }
    }

    static Optional<Object> PublicMethodInvoke(Object o, Method m, Object... args) {
        try {
            Class type = m.getReturnType();
            return Optional.of(m.invoke(o, args));
        } catch (final InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getStackTrace());
        }
        return Optional.empty();
    }

}
