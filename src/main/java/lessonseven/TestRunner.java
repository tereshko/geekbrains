package lessonseven;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    public static void main(String[] args) {

        Class testClassOne = TestOne.class;
        Class testClassTwo = TestTwo.class;

        start(testClassOne);
//        start(testClassTwo);
    }

    public static void start(Class obj) {

        //Check for BeforeSuite
        Method[] methodsBeforeSuite = obj.getMethods();
        int countOfBeforeSuite = 0;
        for (Method m : methodsBeforeSuite) {
            if (m.getAnnotation(BeforeSuite.class) != null) {
                countOfBeforeSuite++;
            }
        }

        //Check for AfterSuite
        Method[] methodsAfterSuite = obj.getMethods();
        int countOfAfterSuite = 0;
        for (Method m : methodsAfterSuite) {
            if (m.getAnnotation(AfterSuite.class) != null) {
                countOfAfterSuite++;
            }
        }

        if (countOfAfterSuite > 1 || countOfBeforeSuite > 1) {
            if (countOfAfterSuite > 1) {
                throw new RuntimeException("Found more that one of @AfterSuite annotation in the: " + obj.getName());
            }
            if (countOfBeforeSuite > 1) {
                throw new RuntimeException("Found more that one of @BeforeSuite annotation in the: " + obj.getName());
            }

        } else {

            Object instance = null;
            try {
                instance = obj.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }




            //Run the @BeforeSuite methods
            for (Method m : methodsBeforeSuite) {
                if (m.getAnnotation(BeforeSuite.class) != null) {
                    try {
                        m.invoke(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            //Run the @Test methods
            //Sort and run
            Method[] methodsTest = obj.getMethods();

            List<Method> list = Arrays.stream(methodsTest)
                    .filter(m -> m.getAnnotation(Test.class) != null)
                    .sorted(Comparator.comparingInt(m -> m.getAnnotation(Test.class).value()))
                    .collect(Collectors.toList());

            for (Method m: list) {
                try {
                    m.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            //Run the @AfterSuite methods
            for (Method m : methodsAfterSuite) {
                if (m.getAnnotation(AfterSuite.class) != null) {
                    try {
                        m.invoke(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
