package interpret;

import javax.swing.text.html.Option;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectManager {
    // ObjectCreator が作成したオブジェクトの管理をするクラス．
    // Creator が作ったオブジェクトを保持する．
    private final Map<String, Optional> objectStore;
    private final List<String> objectNames;
    private final Map<String,Class> builtInMap = new HashMap<String,Class>();{
       builtInMap.put("int", Integer.TYPE );
       builtInMap.put("long", Long.TYPE );
       builtInMap.put("double", Double.TYPE );
       builtInMap.put("float", Float.TYPE );
       builtInMap.put("boolean", Boolean.TYPE);
       builtInMap.put("bool", Boolean.TYPE );
       builtInMap.put("char", Character.TYPE );
       builtInMap.put("byte", Byte.TYPE );
       builtInMap.put("void", Void.TYPE );
       builtInMap.put("short", Short.TYPE );
    }


    ObjectManager() {
        objectStore = new HashMap<>();
        objectNames = new ArrayList<>();
    }

    void createAndSave(final String objectName) throws NoSuchMethodException, ClassNotFoundException { // Need refactor.
        Optional o = ObjectCreator.createInstanceByNoArgument(objectName);
        objectStore.put(objectName, o);
    }

    void createAndSave(final String objectName, final String valuableName) throws NoSuchMethodException, ClassNotFoundException {
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

    Optional<List<Constructor>> getConstructors(final String objectName) throws ClassNotFoundException {
        return Optional.of(Arrays.asList(ConstructorsGetter.getConstructors(objectName).get()));
    }

    Optional<Constructor> getFirstConstructor(final String objectName) throws ClassNotFoundException {
        return ConstructorsGetter.getFirstConstructorFromCtors(objectName);
    }


    Optional<Field[]> getFields(final Object o) {
        return FieldsGetter.setAccessible(FieldsGetter.getFields(o).get());
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

    Optional<Map<Method, List<Parameter>>> getMethodsParamListMap(final Object o) {
        Method[] methods = getMethods(o).get();
        Map<Method, List<Parameter>> methodsParamListMap = new HashMap<>();
        Arrays.stream(methods).forEach(me -> {
            System.out.println(me.getName());
            List plis = Arrays.stream(me.getParameters()).map(p -> p.getType().getName()).collect(Collectors.toList());
            methodsParamListMap.put(me, plis);
        });
        return Optional.of(methodsParamListMap);
    }

    Optional<Map<Method, String>> getMethodsParamStringMap(final Object o) {
        Method[] methods = getMethods(o).get();
        Map<Method, String> methodsParamListMap = new HashMap<>();
        Arrays.stream(methods).forEach(me -> {
            System.out.println(me.getName());
            StringBuilder params = new StringBuilder("");
            Arrays.stream(me.getParameters()).map(p -> p.getType().getName()).forEach(n -> params.append(n + ", "));
            if (params.length() != 0) {
                params.delete(params.length() - 2, params.length() - 1);
            }
            methodsParamListMap.put(me, params.toString());
        });
        return Optional.of(methodsParamListMap);
    }

    Optional<Map<String, Method>> getMethodNameAndParamStringMethodsMap(final Object o) {
        Method[] methods = getMethods(o).get();
        Map<String, Method> methodsParamListMap = new HashMap<>();
        Arrays.stream(methods).forEach(me -> {
            System.out.println(me.getName());
            StringBuilder params = new StringBuilder("");
            Arrays.stream(me.getParameters()).map(p -> p.getType().getName()).forEach(n -> params.append(n + ", "));
            if (params.length() != 0) {
                params.delete(params.length() - 2, params.length() - 1);
            }
            methodsParamListMap.put(me.getName() + " " + params.toString(), me);
        });
        return Optional.of(methodsParamListMap);
    }


    Optional<List<String>> getMethodNamesList(final Object o) {
        return Optional.of(Arrays.asList(MethodsGetter.getMethods(o).get()).stream().map(m -> m.getName()).collect(Collectors.toList()));
    }

    Optional<List<String>> getMethodParameterNameList(final Object o, final String name) throws InvocationTargetException, IllegalAccessException {
        return Optional.of(Arrays.asList(getFirstMethodFoundByName(o, name).getParameters()).stream().map(p -> p.getName()).collect(Collectors.toList()));
    }

    Optional<Map<String, String>> getParameterTypeMapOfMethod(final Method method) {
        List<String> parameterNameList = Arrays.asList(method.getParameters()).stream().map(p -> p.getName()).collect(Collectors.toList());
        List<String> parameterTypesAsStringList = Arrays.asList(method.getParameterTypes()).stream().map(t -> t.getTypeName()).collect(Collectors.toList());
        Map<String, String> parameterTypeMapOfMethod = new HashMap<>();
        for (String parameterName: parameterNameList) {
            parameterTypeMapOfMethod.put(parameterName, parameterTypesAsStringList.get(parameterNameList.indexOf(parameterName)));
        }
        return Optional.of(parameterTypeMapOfMethod);
    }

    void invokeMethodByName(final Object o, final String name) throws InvocationTargetException, IllegalAccessException {
        final Method firstMethodFoundByName = getFirstMethodFoundByName(o, name);
        if (firstMethodFoundByName == null) return;
        MethodInvoker.voidAndPublicMethodInvoke(o, firstMethodFoundByName);
    }

    public Method getFirstMethodFoundByName(Object o, String name) throws InvocationTargetException, IllegalAccessException {
        final Method[] methods = MethodsGetter.getMethods(o).get();
        final List<Method> methodsList = Arrays.asList(methods);
        if (Objects.isNull(name)) {
            MethodInvoker.voidAndPublicMethodInvoke(o, methodsList.get(0));
            return null;
        }
        final Method firstMethodFoundByName = methodsList.stream().filter(m -> Objects.equals(m.getName(), name)).findFirst().get();
        return firstMethodFoundByName;
    }

    void invokeVoidMethodByNameWithArgs(final Object o, final Method name, final Object ... args) throws InvocationTargetException, IllegalAccessException {
        final Method[] methods = MethodsGetter.getMethods(o).get();
        final List<Method> methodsList = Arrays.asList(methods);
        if (Objects.isNull(name)) {
            MethodInvoker.voidAndPublicMethodInvoke(o, methodsList.get(0), args);
            return;
        }
        final Method firstMethodFoundByName = methodsList.stream().filter(m -> Objects.equals(m.getName(), name.getName())).findFirst().get();
        MethodInvoker.voidAndPublicMethodInvoke(o, firstMethodFoundByName, args);
    }

    Optional<Object> invokeMethodByNameWithArgs(final Object o, final Method name, final Object ... args) throws InvocationTargetException, IllegalAccessException { //
        final Method[] methods = MethodsGetter.getMethods(o).get();
        final List<Method> methodsList = Arrays.asList(methods);
        if (Objects.isNull(name)) {
            return MethodInvoker.PublicMethodInvoke(o, methodsList.get(0), args);
        }
        final Method firstMethodFoundByName = methodsList.stream().filter(m -> Objects.equals(m.getName(), name.getName())).findFirst().get();
        return MethodInvoker.PublicMethodInvoke(o, firstMethodFoundByName, args);
    }

   Optional<Object> invokeMethodWithArgs(final Object o, final Method method, final Object ... args) throws InvocationTargetException, IllegalAccessException {
        if (Objects.isNull(method)) {
            return null;
        }
        return MethodInvoker.PublicMethodInvoke(o, method, args);
    }

    Optional<Object> invokeMethodWithNoArgs(final Object o, final Method method) throws InvocationTargetException, IllegalAccessException {
        if (Objects.isNull(method)) {
            return null;
        }
        return MethodInvoker.PublicMethodInvoke(o, method);
    }


    void invokeConstructorWithNoArgsAndSave(Constructor constructor, String valuableName) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        this.objectNames.add(valuableName);
    }

    void invokeConstructorAndSave(final Constructor constructor, String valuableName, Object ... args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ConstructorInvoker.getNewInstance(constructor, args));
    }

    void invokeArrayConstructorAndSave(final Constructor constructor, String valuableName, int index, Object ... args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = this.objectStore.get(valuableName).get();
        Array.set(o, index, ConstructorInvoker.getNewInstance(constructor, args).get());
    }

    void createOneElementArrayAndSave(final String className, final String valuableName) throws ClassNotFoundException {
        String replacedClassName = className.replace("[]","");
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ArrayCreator.createArray(replacedClassName, 1));
    }

    void createDefaultArrayAndSave(final String className, final String valuableName, final int length) throws ClassNotFoundException {
        String replacedClassName = className.replace("[]","");
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ArrayCreator.createArray(replacedClassName, length));
    }

    void createEmptyArrayAndSave(final String className, final String valuableName, final int length) throws ClassNotFoundException {
        String replacedClassName = className.replace("[]","");
        this.objectNames.add(valuableName);
        this.objectStore.put(valuableName, ArrayCreator.createEmptyArray(replacedClassName, length));
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

    Class getClass(final Object o, final String field) {
        Field targetField = Arrays.stream(getFields(o).get()).filter(f -> Objects.equals(f.getName(), field)).findFirst().get();
        return targetField.getType();
    }

    Class getClass(final Method m, final Class clazz) {
        Parameter[] ps = m.getParameters();
        return Arrays.stream(m.getParameters()).filter(p -> p.getClass().isAssignableFrom(clazz)).findFirst().getClass();
    }

    List<String> getObjectNamesAlreadyExistByObjectFieldType (final Object o, final String field) {
        final Class clazz = getClass(o, field);
        final List objectNamesAlreadyExistByObjectFieldType = new ArrayList();
        for (String key : objectStore.keySet()) {
            if (clazz.isAssignableFrom(objectStore.get(key).get().getClass())) {
                objectNamesAlreadyExistByObjectFieldType.add(key);
            }
        }
        return objectNamesAlreadyExistByObjectFieldType;
    }

    Method getMethod(final Object o, final String methodName, final Class ... classes) {
        try {
            return o.getClass().getDeclaredMethod(methodName, null);
        } catch (final NoSuchMethodException e ) {
            e.printStackTrace();
        }
        // this is shit. use optional.
        return null;
    }
    List<String> getObjectNamesAlreadyExistByParameterType (final String typeAsString) {
        final Class clazz;
        if (builtInMap.containsKey(typeAsString)) {
            clazz = builtInMap.get(typeAsString);
        } else if (typeAsString.endsWith("[]")) {
            // care for array
            clazz = ClassGetter.getClass("[L" + typeAsString.replace("[]", "") + ";");
        } else {
            clazz = ClassGetter.getClass(typeAsString);
        }
        final List objectNamesAlreadyExistByObjectFieldType = new ArrayList();
        for (String key : objectStore.keySet()) {
            Class a = objectStore.get(key).get().getClass();
            if (clazz.isAssignableFrom(objectStore.get(key).get().getClass())) {
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
        int modifiers = previousField.getModifiers();
        try {
            if (Modifier.isPrivate(modifiers) && Modifier.isFinal(modifiers)) { // https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection/31268945#31268945
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                int modifier = previousField.getModifiers() & ~Modifier.FINAL & ~Modifier.PRIVATE;
                System.out.println(modifier);
                modifiersField.setInt(previousField, modifier);
                previousField.set(o, Integer.parseInt(newValue));
                return previousValue;
            }
        } catch (final IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
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

    Optional<Constructor> getConstructor(final String objectName, final String ... parameterTypes) throws ClassNotFoundException {
        List<String> parameterTypesAsStringList = Arrays.asList(parameterTypes);
        List<Class<?>> types = getTypes(parameterTypesAsStringList);
        return Optional.of(ConstructorsGetter.getConstructorByParameterTypes(objectName, types.toArray(new Class<?>[types.size()])).get());
    }

    Optional<List<String>> getParameterNameListOfConstructor(final Constructor constructor) {
        return Optional.of(Arrays.asList(constructor.getParameters()).stream().map(p -> p.getName()).collect(Collectors.toList()));
    }

    Optional<List<String>> getParameterNameListOfConstructor(final String objectName, final String ... parameterTypes) throws ClassNotFoundException {
        List<String> parameterTypesAsStringList = Arrays.asList(parameterTypes);
        List<Class<?>> types = getTypes(parameterTypesAsStringList);
        return getParameterNameListOfConstructor(ConstructorsGetter.getConstructorByParameterTypes(objectName, types.toArray(new Class<?>[types.size()])).get());
    }


    Optional<Map<String, String>> getParameterTypeMapOfConstructor(final String objectName, final String ... parameterTypes) throws ClassNotFoundException {
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
        if (parameterTypesAsStringList.contains("")){
            return Collections.emptyList();
        }
        List<Class<?>> types = new ArrayList<>();
        for (String parameterTypesAsString : parameterTypesAsStringList) {
            try {
                if (builtInMap.containsKey(parameterTypesAsString)) {
                    types.add(builtInMap.get(parameterTypesAsString));
                    continue;
                }
                types.add(Class.forName(parameterTypesAsString));
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return types;
    }

    public Optional<Class<?>> getType(String parameterTypeAsString) {
        final Class<?> type;
            try {
                if (builtInMap.containsKey(parameterTypeAsString)) {
                    type = builtInMap.get(parameterTypeAsString);
                } else {
                    type = Class.forName(parameterTypeAsString);
                }
                return Optional.of(type);
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
            return Optional.empty();
    }

    List<String> getNames() {
        return objectNames;
    }
    public void setValue(Map parametersMap, String parameterName, String newValuableName) {
        parametersMap.put(parameterName, objectStore.get(newValuableName).get());
    }
}
