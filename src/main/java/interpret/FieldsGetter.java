package interpret;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class FieldsGetter {
    static Optional<Field[]> getFields(final Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getFields());
        }
        return Optional.empty();
    }
}
