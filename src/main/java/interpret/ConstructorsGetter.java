package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

public class ConstructorsGetter {
    static Optional<Constructor[]> getConstructors(final Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getConstructors());
        }
        return Optional.empty();
    }

    static Optional<Constructor[]> getConstructors(final String objectName) throws ClassNotFoundException {
        if(Objects.nonNull(objectName)) {
            return Optional.of(Class.forName(objectName).getConstructors());
        }
        throw new ClassNotFoundException("null is input as class name");
    }


    static Optional<Constructor> getFirstConstructorFromCtors(final Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getConstructors()[0]);
        }
        return Optional.empty();
    }


    static Optional<Constructor> getFirstConstructorFromCtors(final String objectName) throws ClassNotFoundException{
        if(Objects.nonNull(objectName)) {
                return Optional.of(Class.forName(objectName).getConstructors()[0]);
        }
        throw new ClassNotFoundException("null is input as class name");
    }

    static Optional<Constructor> getConstructorByParameterTypes(final String objectName, final Class... types) throws ClassNotFoundException {
        // Reference: https://docs.oracle.com/javase/tutorial/reflect/member/ctorLocation.html
            final Constructor[] allConstructors = Class.forName(objectName).getConstructors(); // If you need all constructors, use getDeclaredConstructors method. See https://stackoverflow.com/questions/8249173/what-is-the-difference-between-getdeclaredconstructors-and-getconstructors-in-th .
            for (Constructor constructor : allConstructors) {
                Class<?>[] pTypes = constructor.getParameterTypes();
                if (!Objects.equals(types.length, pTypes.length)) {
                    continue;
                }
                boolean allEqual = true;
                for (int i = 0; i < pTypes.length; i++) {
                    if (!Objects.equals(pTypes[i], types[i])) {
                        allEqual = false;
                    }
                }
                if (allEqual) {
                    return Optional.of(constructor);
                }
            }
        return Optional.empty();
    }

    static Optional<Object> getNewInstance(Constructor constructor, Object ...args) {
        try {
            return Optional.of(constructor.newInstance(args));
        } catch (final IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
