package lesson5;

public class Main {
    public static void main(String[] args) {
        // Создаем массив 4x4
        String[][] matrix = {
                {"13", "22", "37", "4"},
                {"44", "5", "1", "8"},
                {"91", "1", "2", "35"},
                {"44", "5", "2", "7"},
        };

        // Задание 3
        try {
            int result = doWork(matrix);
            System.out.println("Результат суммы: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("Проблема с размером: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("Проблема с данными: " + e.getMessage());
        }

        // Задание 4: Ошибка индекса (ArrayIndexOutOfBounds)
        try {
            int[] nums = {1, 2, 3};
            System.out.println(nums[8]); // Специально выходим за границы
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("-----------------------------------------");
            System.out.println("Поймали ошибку: Выход за пределы массива!");
            System.out.println("-----------------------------------------");
        }
    }

    // Задания 1 и 2 в одном методе
    public static int doWork(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        // Проверяем строки (Задание 1)
        if (arr.length != 4) {
            throw new MyArraySizeException("Нужно 4 строки!");
        }

        // Проверяем столбцы (Задание 1)
        for (int i = 0; i < 4; i++) {
            if (arr[i].length != 4) {
                throw new MyArraySizeException("В строке " + i + " должно быть 4 столбца!");
            }
        }

        int totalSum = 0; // Задание 2: Считаем сумму

        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                try {
                    // Превращаем строку в число
                    totalSum = totalSum + Integer.parseInt(arr[i][k]);
                } catch (NumberFormatException e) {
                    // Если не вышло — бросаем исключение (Задание 2)
                    throw new MyArrayDataException(i, k);
                }
            }
        }
        System.out.println("-----------------------------------------");
        return totalSum;
    }
}
