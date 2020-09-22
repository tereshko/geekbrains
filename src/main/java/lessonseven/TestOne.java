package lessonseven;

public class TestOne {

    @BeforeSuite
    public void test0(){
        System.out.println("BeforeSuite");
    }


    @Test
    public void test1() {
        System.out.println("test 1");
    }

    @AfterSuite
    public void test2() {
        System.out.println("AfterSuite");
    }


    public void test3() {
        System.out.println("test 3");
    }


    public void test4() {
        System.out.println("test 4");
    }
}
