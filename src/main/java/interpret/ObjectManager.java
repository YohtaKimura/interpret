package interpret;

import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

import java.lang.reflect.Constructor;
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

    void createAndSave(final String objectName) { // Need refactor.
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

    Optional<List<Constructor>> getConstructors(final String objectName) {
        return Optional.of(Arrays.asList(ConstructorsGetter.getConstructors(objectName).get()));
    }

    Optional<Constructor> getFirstConstructor(final String objectName) {
        return ConstructorsGetter.getFirstConstructorFromCtors(objectName);
    }


    Optional<Field[]> getFields(final Object o) {
        return FieldsGetter.getFields(o);
    }


    Optional<List<String>> getFieldsNameList(final Object o) {
        List<String> fieldsNameList = new ArrayList<>();
        Field[] fields = FieldsGetter.getFields(o).get();
        for (Field field: fields) {
            fieldsNameList.add(field.getName());
        }
        if (fieldsNameList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fieldsNameList);
    }

    Optional<Object> getFirstFieldValue(final Object o) {
        return getFieldValueByName(o,null);
    }

    Optional<Object> getFieldValueByName(final Object o, final String name) {
        Field[] fields = getFields(o).get();
        try {
        if (Objects.isNull(name)) {
            return Optional.of(fields[0].get(o));
        }
        return Optional.of(Arrays.stream(fields).filter(f -> Objects.equals(f.getName(), name)).findFirst().get().get(o));
        } catch (final IllegalAccessException ex ) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    Optional<Method[]> getMethods(final Object o) {
        return MethodsGetter.getMethods(o);
    }

    void invokeFirstMethod(final Object o) {
        invokeMethodByName(o, null);
    }

    void invokeMethodByName(final Object o, final String name) {
        if (Objects.isNull(name)) {
            MethodInvoker.voidAndPublicMethodInvoke(o, MethodsGetter.getMethods(o).get()[0]);
            return;
        }
        MethodInvoker.voidAndPublicMethodInvoke(o, MethodsGetter.getMethods(o).get()[0]); // TODO: use name
        return;
    }

    void invokeConstructorWithNoArgsAndSave(Constructor constructor, String valuableName) {
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ConstructorInvoker.getNewInstance(constructor));
    }

    void createOneElementArrayAndSave(final String className, final String valuableName) {
        String replacedClassName = className.replace("[]","");
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ArrayCreator.createArray(replacedClassName, 1));
    }

    void createArrayAndSave(final String className, final String valuableName, final int length) {
        String replacedClassName = className.replace("[]","");
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ArrayCreator.createArray(replacedClassName, length));
    }

    Optional<Object> getArrayElement(final Object array, final int index) {
        return ArrayElementGetter.getArrayElement(array,index);
    }

    boolean isArray(Object o) {
        return o.getClass().isArray();
    }

    List<String> getNames() {
        return objectNames;
    }
}
