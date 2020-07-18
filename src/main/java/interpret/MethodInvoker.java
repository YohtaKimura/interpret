package interpret;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class MethodInvoker {
    static void voidAndPublicMethodInvoke(Object o, Method m, Object... args) throws InvocationTargetException, IllegalAccessException {
            Class type = m.getReturnType();
            type.cast(m.invoke(o, args));
    }

    static Optional<Object> PublicMethodInvoke(Object o, Method m, Object... args) throws InvocationTargetException, IllegalAccessException {
        if (m.getReturnType().equals(Void.TYPE)) {
            m.invoke(o, args);
            return Optional.of(Void.TYPE);
        }
        return Optional.of(m.invoke(o, args));
    }
}
