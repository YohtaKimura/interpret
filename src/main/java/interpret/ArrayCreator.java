package interpret;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ArrayCreator {
    static Optional<Object> createArray(final String className, final int length) throws ClassNotFoundException {
        try {
            Class type = Class.forName(className);
            Object[] array = (Object[]) Array.newInstance(type, length);
            for (int i = 0; i < length; i++) {
                array[i] = type.getConstructor().newInstance();
            }
            return Optional.of(array);
        } catch (final NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    static Optional<Object> createEmptyArray(final String className, final int length) throws ClassNotFoundException {
            Class type = Class.forName(className);
            Object[] array =  (Object[]) (Array.newInstance(type, length));
            return Optional.of(array);
    }
}

