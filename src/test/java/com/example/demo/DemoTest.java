package com.example.demo;

/**
 * 泛型调试
 */
public class DemoTest {

    public static void main(String []args){
        //未使用泛型  ---java.lang.ClassCastException
//        TestDemoPoint testDemoPoint = new TestDemoPoint();
//        testDemoPoint.setX(10);
//        testDemoPoint.setY(20);
//        int x = (Integer)testDemoPoint.getX();  // 必须向下转型
//        int y = (Integer)testDemoPoint.getY();
//        System.out.println("This point is：" + x + ", " + y);
//
//        testDemoPoint.setX(25.4);  // double -> Integer -> Object
//        testDemoPoint.setY("东京180度");
//        double m = (Double)testDemoPoint.getX();  // 必须向下转型
//        double n = (Double)testDemoPoint.getY();  // 运行期间抛出异常
//        System.out.println("This point is：" + m + ", " + n);

        // 使用泛型
        TestDemoPoint2<Integer,Integer> testDemoPoint2 = new TestDemoPoint2<>();
        testDemoPoint2.setX(10);
        testDemoPoint2.setY(20);
        int x1 = testDemoPoint2.getX();
        int y1 = testDemoPoint2.getY();
//        System.out.println("x1:"+x1+"  ,"+"y1:"+y1);

        TestDemoPoint2<Double,String> testDemoPoint21 = new TestDemoPoint2<>();
        testDemoPoint21.setY("测试10度");
        testDemoPoint21.setX(20.2);
        System.out.println("x2:"+testDemoPoint21.getX()+";"+"y2:"+testDemoPoint21.getY());




    }


}
