package interpret;

import java.lang.reflect.Constructor;
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

    static Optional<Object> getNewInstance()
    {
        // return Optional.of(o.getClass().getConstructors()[0].newInstance(args));
        return null;
    }
}
