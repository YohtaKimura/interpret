package interpret;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class MethodsGetter {
    static Optional<Method[]> getMethods(final Object o) {
        if(Objects.nonNull(o))
        return Optional.of(o.getClass().getMethods());
        return Optional.empty();
    }
}
