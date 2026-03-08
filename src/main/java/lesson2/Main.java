package lesson2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n--- Задание 1 ---");
        printThreeWords();

        System.out.println("\n--- Задание 2 ---");
        checkSumSign();

        System.out.println("\n--- Задание 3 ---");
        printColor();

        System.out.println("\n--- Задание 4 ---");
        compareNumbers();

        System.out.println("\n--- Задание 5 ---");
        System.out.println(isSumInBetween(11, 7));

        System.out.println("\n--- Задание 6 ---");
        printIsPositiveOrNegative(-10);

        System.out.println("\n--- Задание 7 ---");
        System.out.println(isNegative(10));

        System.out.println("\n--- Задание 8 ---");
        printStringMultipleTimes("Java — это круто", 3);

        System.out.println("\n--- Задание 9 ---");
        System.out.println("2026 год високосный? " + isLeapYear(2026));

        System.out.println("\n--- Задание 10 ---");
        invertArray();

        System.out.println("\n--- Задание 11 ---");
        fillArrayWithSequence();

        System.out.println("\n--- Задание 12 ---");
        multiplySmallNumbers();

        System.out.println("\n--- Задание 13 ---");
        fillDiagonal();

        System.out.println("\n--- Задание 14 ---");
        int[] customArr = createArray(5, 100);
        System.out.println(Arrays.toString(customArr));
    }

    // 1. Печать слов Orange, Banana, Apple в столбик
    public static void printThreeWords() {
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    // 2. Проверка суммы чисел
    public static void checkSumSign() {
        int a = 34;
        int b = -15;
        if (a + b >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

    // 3. Выбор цвета
    public static void printColor() {
        int value = 30;
        if (value <= 0) {
            System.out.println("Красный");
        } else if (value > 0 && value <= 100) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }
    // 4. Сравнение чисел
    public static void compareNumbers() {
        int a = 20;
        int b = 60;
        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }
    // 5. Сумма в пределах от 10 до 20
    public static boolean isSumInBetween(int a, int b) {
        int sum = a + b;
        return sum >= 10 && sum <= 20;
    }
    // 6. Положительное или отрицательное чило
    public static void printIsPositiveOrNegative(int x) {
        if (x >= 0) {
            System.out.println("Число положительное");
        } else {
            System.out.println("Число отрицательное");
        }
    }
    // 7. Возврат true, если отрицательное
    public static boolean isNegative(int x) {
        return x < 0;
    }

    // 8. Печать строки n раз
    public static void printStringMultipleTimes(String str, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(str);
        }
    }
    // 9. Определение високосного года
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    // 10. Инверсия массива (0 в 1, 1 в 0)
    public static void invertArray() {
        int[] arr = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (arr[i] == 1) ? 0 : 1;
        }
        System.out.println(Arrays.toString(arr));
    }
    // 11. Массив от 1 до 100
    public static void fillArrayWithSequence() {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        System.out.println(Arrays.toString(arr));
    }

    // 12. Числа меньше 6 умножить на 2
    public static void multiplySmallNumbers() {
        int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    // 13. Заполнение диагоналей квадрата
    public static void fillDiagonal() {
        int size = 5;
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1; // Основная диагональ
            matrix[i][size - 1 - i] = 1; // Побочная диагональ
        }

        // Вывод для наглядности
        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    // 14. Создание массива с заданным значением
    public static int[] createArray(int len, int initialValue) {
        int[] arr = new int[len];
        Arrays.fill(arr, initialValue); // Или циклом for
        return arr;
    }
}
