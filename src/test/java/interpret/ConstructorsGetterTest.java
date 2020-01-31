package interpret;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;

public class ConstructorsGetterTest {

    @Test
    public void getConstructorsTest() throws Exception {
        ForTestNotProduct test = new ForTestNotProduct();
        Constructor[] constructors = ConstructorsGetter.getConstructors(test).get();
        Assert.assertEquals("public interpret.ForTestNotProduct()", constructors[0].toString());
        Assert.assertEquals("public interpret.ForTestNotProduct(java.lang.String)", constructors[1].toString());
        Assert.assertEquals(0, constructors[0].getGenericParameterTypes().length);
        Assert.assertEquals(1, constructors[1].getGenericParameterTypes().length);
        Assert.assertEquals("class java.lang.String", constructors[1].getGenericParameterTypes()[0].toString());
    }

    @Test
    public void getFirstConstructorFromCtorsTest() throws Exception {
        ForTestNotProduct test = new ForTestNotProduct();
        Constructor constructor = ConstructorsGetter.getFirstConstructorFromCtors(test).get();
        Assert.assertEquals(0, constructor.getGenericParameterTypes().length);
    }
}
