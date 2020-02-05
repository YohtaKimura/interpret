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
}