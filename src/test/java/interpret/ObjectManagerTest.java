package interpret;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class ObjectManagerTest {
    // TODO: make test single method in one test.
    @Test
    public void testCreateInstanceByNoArgument() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        objectManager.createAndSave("java.lang.Object");
        Optional o = objectManager.getObjectByName("java.lang.Object");
        String toStringOfObject = o.get().toString();
        Assert.assertTrue(toStringOfObject.contains("java.lang.Object"));
    }

    @Test
    public void testGetNameByName() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        objectManager.createAndSave("interpret.ForTestNotProduct", "a");
        Optional o = objectManager.getNameByName("a");
        Assert.assertEquals("a", o.get());
    }

    @Test
    public void testGetTypeOfField() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        ForTestNotProduct o = new ForTestNotProduct();
        Assert.assertEquals("java.lang.String", objectManager.getTypeAsString(o, "test").get());
    }

    @Test
    public void testGetObjectNamesAlreadyExistByObjectFieldType() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        objectManager.createAndSave("java.lang.String", "hey");
        objectManager.createAndSave("java.lang.String", "hoge");
        objectManager.createAndSave("interpret.ForTestNotProduct", "target");
        Object o = objectManager.getObjectByName("target").get();
        List<String> nameList = objectManager.getObjectNamesAlreadyExistByObjectFieldType(o, "test");
        // nameList.stream().forEach(e -> System.out.println(e));
        Assert.assertEquals("hoge", nameList.get(0));
        Assert.assertEquals("hey", nameList.get(1));
    }

    @Test
    public void testGetObjectNamesAlreadyExistByMethodParameterType() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        objectManager.createAndSave("java.lang.String", "hey");
        objectManager.createAndSave("java.lang.String", "hoge");
        objectManager.createAndSave("interpret.ForTestNotProduct", "target");
        Object o = objectManager.getObjectByName("target").get();

        List<String> nameList = objectManager.getObjectNamesAlreadyExistByParameterType("java.lang.String");
        // nameList.stream().forEach(e -> System.out.println(e));
        Assert.assertEquals("hoge", nameList.get(0));
        Assert.assertEquals("hey", nameList.get(1));
    }

    @Test
    public void test2GetObjectNamesAlreadyExistByMethodParameterType() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        objectManager.createAndSave("java.lang.String", "hey");
        objectManager.createAndSave("java.lang.String", "hoge");
        objectManager.createAndSave("interpret.ForTestNotProduct", "target");
        Object o = objectManager.getObjectByName("target").get();

        List<String> nameList = objectManager.getObjectNamesAlreadyExistByParameterType("java.lang.Object");
        // nameList.stream().forEach(e -> System.out.println(e));
        Assert.assertEquals("hoge", nameList.get(0));
        Assert.assertEquals("hey", nameList.get(1));
    }


    @Test
    public void testSetValue() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        objectManager.createAndSave("java.lang.String", "hey");
        objectManager.createAndSave("interpret.ForTestNotProduct", "target");
        ForTestNotProduct o = (ForTestNotProduct) objectManager.getObjectByName("target").get();
        objectManager.setValue(o, "instanceField", "hey");
        Assert.assertEquals("", o.instanceField);
    }
}