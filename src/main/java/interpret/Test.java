package interpret;

import java.awt.*;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
            Class type = Class.forName("java.awt.Frame");
            Object[] array =  (Object[]) (Array.newInstance(type, 5));
            PrintStream out = System.out;
            out.println(array.length);
            out.println(array.getClass().isArray());
            out.println(array.getClass());
            out.println(array.getClass().getName());
            out.println(Objects.isNull(array[0]));
    }
}

