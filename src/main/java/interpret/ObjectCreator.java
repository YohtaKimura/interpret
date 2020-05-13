package interpret;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ObjectCreator {
    static Optional<Object> createInstanceByNoArgument(final String name) throws ClassNotFoundException, NoSuchMethodException {
        try {
            Class type = Class.forName(name);
            return Optional.of(type.getConstructor().newInstance());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
