package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;

public class ConstructorInvoker {
    static Optional<Object> getNewInstance(Constructor constructor, Object ...args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (Objects.isNull(constructor)) {
            System.out.println("fail");
            return Optional.empty();
        }
        return Optional.of(constructor.newInstance(args));
    }
}
