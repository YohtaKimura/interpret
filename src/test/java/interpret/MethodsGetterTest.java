package interpret;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodsGetterTest {
    @Test
    public void testGetMethod() throws Exception {
        String test = "test";
        Method[] methods = MethodsGetter.getMethods(test).get();
        String methodName = methods[0].toString();
        Arrays.stream(methods).forEach(m -> System.out.println(m));
        Assert.assertTrue(methodName.contains("equals"));
    }

    @Test
    public void testGetMethodOfMyClass() throws Exception {
        ForTestNotProduct test = new ForTestNotProduct();
        Method[] methods = MethodsGetter.getMethods(test).get();
        String methodName = methods[0].toString();
        Arrays.stream(methods).forEach(m -> System.out.println(m));
        Assert.assertTrue(methodName.contains("Hello"));
    }
}