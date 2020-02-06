package interpret;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ArrayCreator {
    static Optional<Object> createArray(final String className, final int length){
        try {
            Class type = Class.forName(className);
            Object[] array = (Object[]) Array.newInstance(type, length);
            for (int i = 0; i < array.length; i++) {
                array[i] = type.getConstructor().newInstance();
            }
            return Optional.of(array);
        } catch (final ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
