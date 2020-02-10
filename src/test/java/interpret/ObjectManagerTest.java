package interpret;

import org.junit.Assert;
import org.junit.Test;

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
        objectManager.createAndSave("interpret.ForTestNotProduct");
        Optional o = objectManager.getNameByName("interpret.ForTestNotProduct");
        Assert.assertEquals("interpret.ForTestNotProduct", o.get());
    }

    @Test
    public void testGetTypeOfField() throws Exception {
        ObjectManager objectManager = new ObjectManager();
        ForTestNotProduct o = new ForTestNotProduct();
        Assert.assertEquals("java.lang.String", objectManager.getTypeAsString(o, "test").get());
    }
}