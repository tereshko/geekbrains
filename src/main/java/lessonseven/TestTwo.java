package lessonseven;

public class TestTwo {

    @BeforeSuite
    public void test0() {
        System.out.println("BeforeSuite");
    }

    @Test
    public void test1() {
        System.out.println("test 2");
    }

    @AfterSuite
    public void test2() {
        System.out.println("AfterSuite 1");
    }

    @AfterSuite
    public void test3() {
        System.out.println("AfterSuite 2");
    }
}
