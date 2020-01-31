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
}