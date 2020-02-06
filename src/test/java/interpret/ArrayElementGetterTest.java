package interpret;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class ArrayElementGetterTest {
    @Test
    public void testGetElementWithInitializedArray() throws Exception {
        String[] strings = new String[1];
        strings[0] = "abc";
        Object o = ArrayElementGetter.getArrayElement(strings, 0).get();
    }

    @Test
    public void testGetElementWithNotInitializedArray() throws Exception {
        String[] strings = new String[1];
        Object o = ArrayElementGetter.getArrayElement(strings, 0).orElse(null);
    }

    @Test
    public void testGetElementWithNotInitializedArray2() throws Exception {
        String[] strings = new String[1];
        Object o = Array.get(strings, 0);
        System.out.println(o);
    }
}