package interpret;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class FieldsGetter {
    static Optional<Field[]> getFields(final Object o) {
        if(Objects.nonNull(o)) {
            return Optional.of(o.getClass().getDeclaredFields());
        }
        return Optional.empty();
    }

    static Optional<Field[]> setAccessible(final Field[] fields) {
        for (final Field field: fields) {
            field.setAccessible(true);
        }
        return Optional.of(fields);
    }

    static Optional<Field> getFieldByName(final Object o, final String name) {
        try {
            if(Objects.nonNull(o)) {
                return setAccessible(o.getClass().getDeclaredField(name));
            }
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    static Optional<Field> setAccessible(final Field field) {
        field.setAccessible(true);
        return Optional.of(field);
    }

}
