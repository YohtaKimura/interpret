package interpret;

import org.junit.Assert;
import org.junit.Test;

public class ObjectCreatorTest {
    @Test
    public void testCreateInstanceByNoArgument() throws Exception {
        Object object = ObjectCreator.createInstanceByNoArgument("java.lang.Object");
        String toStringOfObject = object.toString();
        Assert.assertTrue(toStringOfObject.contains("java.lang.Object"));
    }

    @Test
    public void testCreateInstanceByNoArgumentWithMyClass() throws Exception {
        Object object = ObjectCreator.createInstanceByNoArgument("interpret.ForTestNotProduct");
        String toStringOfObject = object.toString();
        System.out.println(toStringOfObject);
        Assert.assertTrue(toStringOfObject.contains("interpret.ForTestNotProduct"));
    }
}