package interpret;

public class ClassGetter {
    static Class getClass (final String className) {
        try {
            // TODO: care for array
            return Class.forName(className);
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
