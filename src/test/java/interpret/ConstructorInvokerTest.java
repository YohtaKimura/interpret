package interpret;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;

public class ConstructorInvokerTest {
    @Test
    public void getConstructorsTest() throws Exception {
        ForTestNotProduct test = new ForTestNotProduct();
        Constructor constructor = ConstructorsGetter.getFirstConstructorFromCtors(test).get();
        Object newOne = ConstructorInvoker.getNewInstance(constructor).get();
        Assert.assertTrue(newOne instanceof ForTestNotProduct);
    }

    @Test
    public void invokeStringConstructorTest() throws Exception {
        Constructor constructor = String.class.getConstructor(String.class);
        Object newOne = ConstructorInvoker.getNewInstance(constructor, "a").get();
        Assert.assertEquals("a", newOne);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invokeStringConstructorFails() throws Exception {
        Constructor constructor = String.class.getConstructor(String.class);
        Object newOne = ConstructorInvoker.getNewInstance(constructor, null).get();
    }

}