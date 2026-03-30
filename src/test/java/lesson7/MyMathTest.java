package lesson7;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MyMathTest {

    private final MyMath myMath = new MyMath();

    @Test
    public void testFactorial() {
        // В TestNG: Assert.assertEquals(фактический_результат, ожидаемый)
        Assert.assertEquals(myMath.getFactorial(3), 6);
    }

    @Test
    public void testTriangleArea() {
        Assert.assertEquals(myMath.getTriangleArea(6, 5), 15.0);
    }

    @Test
    public void testArithmetic() {
        Assert.assertEquals(myMath.calculate(5, 6, "*"), 30.0);
    }

    @Test
    public void testCompare() {
        Assert.assertEquals(myMath.compareNumbers(9, 9), "равны");
    }
}