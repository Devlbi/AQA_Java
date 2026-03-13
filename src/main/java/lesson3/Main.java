package lesson3;

public class Main {
    public static void main(String[] args) {

        // 1. Одиночный товар
        Product myProduct = new Product("Телефон", "12.03.2026", "iPhone 17 Pro Max", "California", 1500, false);
        System.out.println("--- Одиночный товар ---");
        myProduct.printInfo();

        // 2. Массив из 5 товаров
        Product[] productsArray = new Product[5];
        productsArray[0] = new Product("Samsung S25 Ultra", "01.02.2025", "Samsung Corp.", "Korea", 1400, true);
        productsArray[1] = new Product("iPhone 16 Pro", "10.09.2024", "Apple Inc.", "USA", 1000, false);
        productsArray[2] = new Product(" Sony Bravia 8 K-55XR80", "15.05.2024", "Sony", "Japan", 2300, false);
        productsArray[3] = new Product("MacBook Air M3", "20.03.2024", "Apple Inc.", "USA", 1800, true);
        productsArray[4] = new Product("Xiaomi 14 Ultra", "12.02.2025", "Xiaomi", "China", 950, false);

        // Вывод массива через цикл
        System.out.println("\n--- Список товаров из массива ---");
        for (Product p : productsArray) {
            p.printInfo();
        }

        // 3. Работа с классом Park
        System.out.println("\n--- Информация о парке ---");
        Park myPark = new Park("Парк Горького");
        // Внутренний класс Attraction создается через объект внешнего класса Park
        Park.Attraction swing = myPark.new Attraction("Колесо обозрения", "11:00 - 22:00", 10);
        swing.showDetails();
    }

    // Класс Товар
    public static class Product {
        String name;
        String productionDate;
        String manufacturer;
        String country;
        int price;
        boolean isReserved;

        public Product(String name, String productionDate, String manufacturer, String country, int price, boolean isReserved) {
            this.name = name;
            this.productionDate = productionDate;
            this.manufacturer = manufacturer;
            this.country = country;
            this.price = price;
            this.isReserved = isReserved;
        }

        public void printInfo() {
            System.out.println("Товар: " + name);
            System.out.println("Дата производства: " + productionDate);
            System.out.println("Производитель: " + manufacturer + " (" + country + ")");
            System.out.println("Цена: " + price + "$");
            System.out.println("Статус: " + (isReserved ? "Забронирован" : "Свободен"));
            System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        }
    }

    // Класс Park
    public static class Park {
        String parkName;

        public Park(String parkName) {
            this.parkName = parkName;
        }

        // Внутренний класс
        public class Attraction {
            String title;
            String workingTime;
            int cost;

            public Attraction(String title, String workingTime, int cost) {
                this.title = title;
                this.workingTime = workingTime;
                this.cost = cost;
            }

            public void showDetails() {
                System.out.println("Парк: " + parkName + " | Аттракцион: " + title + " | Время: " + workingTime + " | Цена: " + cost + " руб.");
            }
        }
    }
}