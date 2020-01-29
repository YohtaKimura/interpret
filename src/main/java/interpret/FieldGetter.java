package interpret;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class FieldGetter {
    static Optional<Field[]> getMethods(final Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getFields());
        }
        return Optional.empty();
    }
}
