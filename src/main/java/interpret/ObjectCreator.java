package interpret;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ObjectCreator {
    Optional<Object> createInstanceByNoArgument(final String name) {
        try {
            Class type = Class.forName(name);
            return Optional.of(type.getConstructor().newInstance());

        } catch(final ClassNotFoundException | NoSuchMethodException e) {
            System.err.println(e);
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
