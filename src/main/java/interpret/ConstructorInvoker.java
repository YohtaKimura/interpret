package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;

public class ConstructorInvoker {
    static Optional<Object> getNewInstance(Constructor constructor, Object ...args) {
        if (Objects.isNull(constructor)) {
            System.out.println("fail");
            return Optional.empty();
        }

        try {
            return Optional.of(constructor.newInstance(args));
        } catch (final IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println("Error is occurred in ContructorInvoker class");
        }
        return Optional.empty();
    }
}
