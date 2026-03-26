package lesson5;

class MyArrayDataException extends Exception {
    public MyArrayDataException(int row, int column) {
        super("Ошибка в ячейке: строка " + row + ", столбец " + column);
    }
}
