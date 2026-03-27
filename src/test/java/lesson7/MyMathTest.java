package lesson7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyMathTest {

    @Test
    void testFactorial() {
        assertEquals(6, MyMath.getFactorial(3));
    }

    @Test
    void testTriangle() {
        assertEquals(15.0, MyMath.getTriangleArea(6, 5));
    }

    @Test
    void testArithmetic() {
        assertEquals(30, MyMath.calculate(5, 6, "*"));
    }

    @Test
    void testCompare() {
        assertEquals("равны", MyMath.compareNumbers(9, 9));
    }
}