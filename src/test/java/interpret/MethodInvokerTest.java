package interpret;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MethodInvokerTest {
    @Test
    public void testGetMethod() throws Exception {
        ForTestNotProduct test = new ForTestNotProduct();
        Method[] methods = MethodsGetter.getMethods(test).get();
        Arrays.stream(methods).forEach(m -> System.out.println(m));
        MethodInvoker.voidAndPublicMethodInvoke(test, methods[0]);
    }
}