package lesson6;

import java.util.*;

class Student {
    String fullName;
    String group;
    int course;
    List<Integer> grades;

    public Student(String fullName, String group, int course, List<Integer> grades) {
        this.fullName = fullName;
        this.group = group;
        this.course = course;
        this.grades = grades;
    }

    // Считаем средний балл
    public double getAverageGrade() {
        if (grades.isEmpty()) return 0;
        double sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("\n--- Задание 1 ---");
        List<Student> students = new ArrayList<>();
        students.add(new Student("Иван Мороз", "А3", 1, List.of(3, 5, 3)));
        students.add(new Student("Юля Васильченко", "Б1", 1, List.of(2, 3, 2)));
        students.add(new Student("Алексей Сойко", "А2", 1, List.of(5, 5, 5)));
        students.add(new Student("Александр Петров", "А5", 1, List.of(4, 5, 2)));
        students.add(new Student("Максим Седюкевич", "А3", 2, List.of(4, 3, 3)));

        processStudents(students);
        printStudents(new HashSet<>(students), 2);

        System.out.println("\n--- Задание 2 ---");
        Phonebook myBook = new Phonebook();
        myBook.add("Петров", "+375-29-666-24-30");
        myBook.add("Сидоров", "+375-33-725-25-25");
        myBook.add("Петров", "+375-29-666-24-30");
        myBook.add("Смирнова", "+375-29-666-24-31");
        myBook.add("Иванов", "+375-29-225-24-30");

        myBook.get("Петров");
        myBook.get("Сидоров");
        myBook.get("Иванов");
        myBook.get("Смирнова");
    }

    public static void processStudents(List<Student> students) {
        Iterator<Student> iter = students.iterator();
        while (iter.hasNext()) {
            Student s = iter.next();
            double avg = s.getAverageGrade();

            if (avg < 3) {
                iter.remove(); // Отчисляем студента
            } else {
                s.course++; // Переводим на следующий курс
            }
        }
    }

    public static void printStudents(Set<Student> students, int course) {
        System.out.println("Студенты на " + course + " курсе:");
        for (Student s : students) {
            if (s.course == course) {
                System.out.println("- " + s.fullName);
            }
        }
    }
}
class Phonebook {
    // Карта: Фамилия -> Список телефонов
    private Map<String, List<String>> data = new HashMap<>();

    public void add(String surname, String phoneNumber) {
        // Если фамилии нет, создаем новый список
        if (!data.containsKey(surname)) {
            data.put(surname, new ArrayList<>());
        }
        // Добавляем номер в список этой фамилии
        data.get(surname).add(phoneNumber);
    }

    public void get(String surname) {
        if (data.containsKey(surname)) {
            List<String> numbers = data.get(surname);
            System.out.println("Фамилия: " + surname + " | Номера: " + numbers);
        } else {
            System.out.println("Запись с фамилией " + surname + " не найдена.");
        }
    }
}
