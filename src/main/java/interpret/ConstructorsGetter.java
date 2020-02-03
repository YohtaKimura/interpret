package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;

public class ConstructorsGetter {
    static Optional<Constructor[]> getConstructors(final Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getConstructors());
        }
        return Optional.empty();
    }

    static Optional<Constructor[]> getConstructors(final String objectName) {
        if(Objects.nonNull(objectName)) {
            try {
                return Optional.of(Class.forName(objectName).getConstructors());
            } catch (final ClassNotFoundException e) {
                System.out.println(objectName + " cannot be found.");
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    static Optional<Constructor> getFirstConstructorFromCtors(final Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getConstructors()[0]);
        }
        return Optional.empty();
    }


    static Optional<Constructor> getFirstConstructorFromCtors(final String objectName) {
        if(Objects.nonNull(objectName)) {
            try {
                return Optional.of(Class.forName(objectName).getConstructors()[0]);
            } catch (final ClassNotFoundException e) {
                System.out.println(objectName + " cannot be found.");
                e.printStackTrace();
                return Optional.empty();
            }
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
