package interpret;

import java.lang.reflect.Array;
import java.util.Optional;

public class ArrayElementGetter {
    static Optional<Object> getArrayElement(final Object array, final int index) {
        System.out.println(array);
        return Optional.ofNullable(Array.get(array, index));
    }
}
