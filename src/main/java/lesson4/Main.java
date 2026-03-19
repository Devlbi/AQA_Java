package lesson4;

public class Main {

    public static void main(String[] args) {
        System.out.println("**** Задание 1: Животные ****");

        Dog bobik = new Dog("Бобик");
        Cat barsik = new Cat("Барсик");
        Cat peach = new Cat("Персик");

        bobik.run(150);
        bobik.swim(7);
        peach.run(210);
        peach.swim(3);

        // Создаем миску с начальным количеством еды
        Bowl bowl = new Bowl(20);
        Cat[] cats = {barsik, peach, new Cat("Снежок")};

        System.out.println("\n*** Начинаем кормить котов ***");
        int appetite = 16; // Сколько хочет съесть каждый кот

        for (Cat cat : cats) {
            // ПРОВЕРКА: Если еды в миске меньше, чем нужно коту — добавляем!
            if (bowl.getFood() < appetite) {
                System.out.println("-*-*- В миске мало еды для " + cat.name + ". Добавляем... -*-*-");
                bowl.addFood(10); // Добавляем 10 единиц
            }
            cat.eat(bowl, appetite);
        }

        System.out.println("\nРезультаты кормления:");
        for (Cat cat : cats) {
            System.out.println(cat.name + " сытость: " + (cat.isFull ? "Сыт" : "Голоден"));
        }

        System.out.println("\nВсего создано животных: " + Animal.count);
        System.out.println("Из них котов: " + Cat.catCount + ", собак: " + Dog.dogCount);

        // --- Задание 2: Фигуры ---
        System.out.println("\n=***= Задание 2: Фигуры =***=");
        Circle circle = new Circle(8.0, "Зеленый", "Черный");
        Rectangle rect = new Rectangle(6.0, 3.0, "Фиолетовый", "Белый");
        Triangle triangle = new Triangle(5, 5, 8, "Синий", "Коричневый");

        circle.printInfo();
        rect.printInfo();
        triangle.printInfo();
    }
}

// --- Классы для Задания 1 ---

class Animal {
    String name;
    static int count = 0;

    public Animal(String name) {
        this.name = name;
        count++;
    }

    public void run(int dist) {
        System.out.println(name + " пробежал " + dist + " м.");
    }

    public void swim(int dist) {
        System.out.println(name + " проплыл " + dist + " м.");
    }
}

class Cat extends Animal {
    static int catCount = 0;
    boolean isFull = false;

    public Cat(String name) {
        super(name);
        catCount++;
    }

    @Override
    public void run(int dist) {
        if (dist <= 200) {
            System.out.println("Кот " + name + " пробежал " + dist + " м.");
        } else {
            System.out.println("Кот " + name + " столько не пробежит (макс 200)!");
        }
    }

    @Override
    public void swim(int dist) {
        System.out.println("Кот " + name + " не умеет плавать!");
    }

    public void eat(Bowl bowl, int amount) {
        if (bowl.getFood() >= amount) {
            bowl.decreaseFood(amount);
            this.isFull = true;
            System.out.println(name + " покушал. В миске осталось: " + bowl.getFood());
        } else {
            System.out.println(name + " не стал есть, еды маловато...");
        }
    }
}

class Dog extends Animal {
    static int dogCount = 0;

    public Dog(String name) {
        super(name);
        dogCount++;
    }

    @Override
    public void run(int dist) {
        if (dist <= 500) {
            System.out.println("Собака " + name + " пробежала " + dist + " м.");
        } else {
            System.out.println("Собака " + name + " устала после 500 м.");
        }
    }

    @Override
    public void swim(int dist) {
        if (dist <= 10) {
            System.out.println("Собака " + name + " проплыл " + dist + " м.");
        } else {
            System.out.println("Собака " + name + " не проплывет больше 10 м.");
        }
    }
}

class Bowl {
    private int food;

    public Bowl(int food) {
        this.food = Math.max(food, 0);
    }

    // Метод для добавления еды
    public void addFood(int amount) {
        if (amount > 0) {
            this.food += amount;
            System.out.println("Миска пополнена на " + amount + ". Всего еды: " + this.food);
        }
    }

    // Метод для уменьшения
    public void decreaseFood(int amount) {
        if (this.food >= amount) {
            this.food -= amount;
        }
    }

    public int getFood() {
        return food;
    }
}

// --- Интерфейс и Классы Задания 2 ---

interface Shape {
    double getArea();
    double getPerimeter();
    String getFill();
    String getBorder();

    default void printInfo() {
        System.out.println("Фигура: " + this.getClass().getSimpleName() +
                ", Площадь: " + String.format("%.2f", getArea()) +
                ", Периметр: " + String.format("%.2f", getPerimeter()) +
                ", Цвет: " + getFill());
    }
}

class Circle implements Shape {
    double r;
    String fill, border;

    public Circle(double r, String f, String b) {
        this.r = r; this.fill = f; this.border = b;
    }
    public double getArea() { return Math.PI * r * r; }
    public double getPerimeter() { return 2 * Math.PI * r; }
    public String getFill() { return fill; }
    public String getBorder() { return border; }
}

class Rectangle implements Shape {
    double w, h;
    String fill, border;

    public Rectangle(double w, double h, String f, String b) {
        this.w = w; this.h = h; this.fill = f; this.border = b;
    }
    public double getArea() { return w * h; }
    public double getPerimeter() { return 2 * (w + h); }
    public String getFill() { return fill; }
    public String getBorder() { return border; }
}

class Triangle implements Shape {
    double a, b, c;
    String fill, border;

    public Triangle(double a, double b, double c, String f, String bdr) {
        this.a = a; this.b = b; this.c = c;
        this.fill = f; this.border = bdr;
    }
    public double getArea() {
        double p = getPerimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
    public double getPerimeter() { return a + b + c; }
    public String getFill() { return fill; }
    public String getBorder() { return border; }
}
