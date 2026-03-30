package lesson7;

public class MyMath {

    // 1. Факториал
    public long getFactorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Число должно быть >= 0");
        long res = 1;
        for (int i = 2; i <= n; i++) res *= i;
        return res;
    }

    // 2. Площадь треугольника
    public double getTriangleArea(double base, double height) {
        return 0.5 * base * height;
    }

    // 3. Арифметика
    public double calculate(int a, int b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new ArithmeticException("Деление на 0");
                yield (double) a / b;
            }
            default -> throw new IllegalArgumentException("Операция не поддерживается");
        };
    }

    // 4. Сравнение
    public String compareNumbers(int a, int b) {
        if (a > b) return "больше";
        if (a < b) return "меньше";
        return "равны";
    }
}
