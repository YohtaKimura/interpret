package interpret;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class TypeGetter {
    static Type getType(final Field field) {
        return field.getType();
    }

    static Type getType(final Parameter parameter){
        return parameter.getType();
    }
}
