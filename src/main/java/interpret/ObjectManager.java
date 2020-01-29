package interpret;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ObjectManager {
    // ObjectCreator が作成したオブジェクトの管理をするクラス．
    // Creator が作ったオブジェクトを保持する．
    private final Map<String, Optional> objectStore;
    private final List<String> objectNames;

    ObjectManager() {
        objectStore = new HashMap<>();
        objectNames = new ArrayList<>();
    }

    void createAndSave(final String objectName) {
        Optional o = ObjectCreator.createInstanceByNoArgument(objectName);
        objectStore.put(objectName, o);
    }

    void createAndSave(final String objectName, final String valuableName) {
        if (Objects.isNull(valuableName)) {
            createAndSave(objectName);
        }

        Optional o = ObjectCreator.createInstanceByNoArgument(objectName);
        this.objectNames.add(valuableName);
        objectStore.put(valuableName, o);
    }

    Optional<Object> getObjectByName(final String name) {
        if(objectStore.containsKey(name)) {
            return objectStore.get(name);
        }
        return Optional.empty();
    }

    Optional<String> getNameByName(final String name) {
        if (objectNames.contains(name)) return Optional.of(name);
        return Optional.empty();
    }

    Optional<Field[]> getFields(final Object o) {
        return FieldsGetter.getFields(o);
    }

    Optional<Method[]> getMethods(final Object o) {
        return MethodsGetter.getMethods(o);
    }

    List<String> getNames() {
        return objectNames;
    }
}
