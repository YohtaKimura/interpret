package interpret;

import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

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

    void invokeConstructorAndSave(final Constructor constructor, String valuableName, Object ... args) {
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ConstructorInvoker.getNewInstance(constructor, args));
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

    Optional<String> getTypeAsString(final Object o, final String field) {
        Field targetField = Arrays.stream(getFields(o).get()).filter(f -> Objects.equals(f.getName(), field)).findFirst().get();
        return Optional.of(TypeGetter.getType(targetField).getTypeName());
    }

    Type getType(final Object o, final String field) {
        Field targetField = Arrays.stream(getFields(o).get()).filter(f -> Objects.equals(f.getName(), field)).findFirst().get();
        return TypeGetter.getType(targetField);
    }


    List<String> getObjectNamesAlreadyExistByObjectFieldType(final Object o, final String field) {
        final String type = getTypeAsString(o, field).get();
        final List objectNamesAlreadyExistByObjectFieldType = new ArrayList();
        for (String key : objectStore.keySet()) {
            if (Objects.equals(objectStore.get(key).get().getClass().getTypeName(), type)) {
                objectNamesAlreadyExistByObjectFieldType.add(key);
            }
        }
        return objectNamesAlreadyExistByObjectFieldType;
    }

    Optional<Object> setValue(final Object o, final String previousValuableName, final String newValuableName) {
        Field previousField = FieldsGetter.getFieldByName(o, previousValuableName).get();
        Optional<Object> previousValue = getFieldValueByName(o, previousValuableName);
        try {
            previousField.set(o, objectStore.get(newValuableName).get());
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return previousValue;
    }

    Optional<Object> setStringValueDirectly(final Object o, final String previousValuableName, final String newValue) {
        Field previousField = FieldsGetter.getFieldByName(o, previousValuableName).get();
        Optional<Object> previousValue = getFieldValueByName(o, previousValuableName);
        try {
            previousField.set(o, newValue);
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return previousValue;
    }

    Optional<Object> setPrimitiveIntValueDirectly(final Object o, final String previousValuableName, final String newValue) {
        Field previousField = FieldsGetter.getFieldByName(o, previousValuableName).get();
        Optional<Object> previousValue = getFieldValueByName(o, previousValuableName);
        try {
            previousField.setInt(o, Integer.parseInt(newValue));
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return previousValue;
    }

    boolean isString(final Object o, final String fieldName) {
        Field field = FieldsGetter.getFieldByName(o, fieldName).get();
        return Objects.equals(field.getType().getTypeName(), "java.lang.String");
    }

    boolean isPrimitive(final Object o, final String fieldName) {
        Field field = FieldsGetter.getFieldByName(o, fieldName).get();
        return field.getType().isPrimitive();
    }

    Optional<Constructor> getConstructor(final String objectName, final String ... parameterTypes) {
        List<String> parameterTypesAsStringList = Arrays.asList(parameterTypes);
        List<Class<?>> types = getTypes(parameterTypesAsStringList);
        return Optional.of(ConstructorsGetter.getConstructorByParameterTypes(objectName, types.toArray(new Class<?>[types.size()])).get());
    }

    Optional<List<String>> getParameterNameListOfConstructor(final Constructor constructor) {
        return Optional.of(Arrays.asList(constructor.getParameters()).stream().map(p -> p.getName()).collect(Collectors.toList()));
    }

    Optional<List<String>> getParameterNameListOfConstructor(final String objectName, final String ... parameterTypes) {
        List<String> parameterTypesAsStringList = Arrays.asList(parameterTypes);
        List<Class<?>> types = getTypes(parameterTypesAsStringList);
        return getParameterNameListOfConstructor(ConstructorsGetter.getConstructorByParameterTypes(objectName, types.toArray(new Class<?>[types.size()])).get());
    }


    Optional<Map<String, String>> getParameterTypeMapOfConstructor(final String objectName, final String ... parameterTypes) {
        List<String> parameterTypesAsStringList = Arrays.asList(parameterTypes);
        List<Class<?>> types = getTypes(parameterTypesAsStringList);
        List<String> parameterNameListOfConstructor = getParameterNameListOfConstructor(ConstructorsGetter.getConstructorByParameterTypes(objectName, types.toArray(new Class<?>[types.size()])).get()).get();
        Map<String, String> parameterTypeMapOfConstructor = new HashMap<>();
        for (String parameterName: parameterNameListOfConstructor) {
            parameterTypeMapOfConstructor.put(parameterName, types.get(parameterNameListOfConstructor.indexOf(parameterName)).getTypeName());
        }
        return Optional.of(parameterTypeMapOfConstructor);
    }

    private List<Class<?>> getTypes(List<String> parameterTypesAsStringList) {
        List<Class<?>> types = new ArrayList<>();
        for (String parameterTypesAsString : parameterTypesAsStringList) {
            try {
                types.add(Class.forName(parameterTypesAsString));
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return types;
    }

    List<String> getNames() {
        return objectNames;
    }
}
