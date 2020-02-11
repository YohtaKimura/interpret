package interpret;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ParametersGetter {
        static Optional<List<Parameter>> getParameters(final Constructor constructor) {
            return Optional.of(Arrays.asList(constructor.getParameters()));
        }
}
