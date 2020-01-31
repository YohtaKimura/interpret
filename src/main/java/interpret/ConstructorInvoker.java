package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ConstructorInvoker {
    static Optional<Object> getNewInstance(Constructor constructor, Object ...args) {
        try {
            return Optional.of(constructor.newInstance(args));
        } catch (final IllegalAccessException | InvocationTargetException | InstantiationException e) {
            // TODO: some
        }
        return Optional.empty();
    }
}
