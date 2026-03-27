package lesson7;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MyMathTest {

    @Test
    public void testFactorial() {
        // В TestNG: Assert.assertEquals(фактический_результат, ожидаемый)
        Assert.assertEquals(MyMath.getFactorial(3), 6);
    }

    @Test
    public void testTriangleArea() {
        Assert.assertEquals(MyMath.getTriangleArea(6, 5), 15.0);
    }

    @Test
    public void testArithmetic() {
        Assert.assertEquals(MyMath.calculate(5, 6, "*"), 30.0);
    }

    @Test
    public void testCompare() {
        Assert.assertEquals(MyMath.compareNumbers(9, 9), "равны");
    }
}