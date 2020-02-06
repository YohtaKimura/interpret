package interpret;


import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;

public class ArrayCreatorTest {
    @Test
    public void testCreateInstanceByNoArgument() throws Exception {
        Object object = ArrayCreator.createArray("java.lang.Object", 2).get();
        Assert.assertEquals(2, Array.getLength(object));
    }

    @Test
    public void testCreateInstanceOfMyClass() throws Exception {
        ForTestNotProduct[] array = (ForTestNotProduct[]) ArrayCreator.createArray("interpret.ForTestNotProduct", 1).get();
        Assert.assertEquals("instance", array[0].instanceField);
    }
}