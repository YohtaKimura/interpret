package interpret;

import org.junit.Assert;
import org.junit.Test;

public class CreateObjectTest {
    @Test
    public void testCreateInstanceByNoArgument() throws Exception {
        CreateObject createObject = new CreateObject();
        Object object = createObject.createInstanceByNoArgument("java.lang.Object");
        String toStringOfObject = object.toString();
        Assert.assertTrue(toStringOfObject.contains("java.lang.Object"));
    }

    @Test
    public void testCreateInstanceByNoArgumentWithMyClass() throws Exception {
        CreateObject createObject = new CreateObject();
        Object object = createObject.createInstanceByNoArgument("interpret.ForTestNotProduct");
        String toStringOfObject = object.toString();
        System.out.println(toStringOfObject);
        Assert.assertTrue(toStringOfObject.contains("interpret.ForTestNotProduct"));
    }
}