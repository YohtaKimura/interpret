package interpret;

import java.lang.reflect.Array;
import java.util.Optional;

public class ArrayCreator {
    static Optional<Object> createArray(final String className, final int length){
        try {
            return Optional.of(Array.newInstance(Class.forName(className), length));
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
