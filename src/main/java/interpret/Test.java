package interpret;

import java.awt.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class Test {
    public final Map<String,Class> builtInMap = new HashMap<String,Class>();{
       builtInMap.put("int", Integer.TYPE );
       builtInMap.put("long", Long.TYPE );
       builtInMap.put("double", Double.TYPE );
       builtInMap.put("float", Float.TYPE );
       builtInMap.put("boolean", Boolean.TYPE);
       builtInMap.put("bool", Boolean.TYPE );
       builtInMap.put("char", Character.TYPE );
       builtInMap.put("byte", Byte.TYPE );
       builtInMap.put("void", Void.TYPE );
       builtInMap.put("short", Short.TYPE );
    }

    public static void main(String[] args) {
        Test test = new Test();
        Map builtInMap = test.builtInMap;
        for (int i=0; i < 3; i++) {
         if (builtInMap.containsKey("int")){
             builtInMap.get("int");
             System.out.println(i);
         }
        }
    }
}
