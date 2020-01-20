package interpret;

import java.lang.reflect.InvocationTargetException;

public class CreateObject {
    void createInstanceByNoArgument(final String name) {
        try {
            Class type = Class.forName(name);
            Object o = type.getConstructor().newInstance();

        } catch(final ClassNotFoundException | NoSuchMethodException e) {
            System.err.println(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
