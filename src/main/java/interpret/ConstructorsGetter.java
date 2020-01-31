package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;

public class ConstructorsGetter {
    static Optional<Constructor[]> getConstructors(Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getConstructors());
        }
        return Optional.empty();
    }
    static Optional<Constructor> getFirstConstructorFromCtors(Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getConstructors()[0]);
        }
        return Optional.empty();
    }

    static Optional<Object> getNewInstance(Constructor constructor, Object ...args) {
        try {
            return Optional.of(constructor.newInstance(args));
        } catch (final IllegalAccessException | InvocationTargetException | InstantiationException e) {
            // TODO: some
        }
        return Optional.empty();
    }
}
