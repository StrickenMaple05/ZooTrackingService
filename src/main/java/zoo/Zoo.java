package zoo;

import tracking.TrackingService;
import zoo.animal.Animal;
import zoo.employee.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zoo {

    private TrackingService trackingService;

    /** размер стороны (зоопарк квадратный) */
    public static final double size = 10;

    /** Название зоопарка */
    private String title;
    /** Сотрудники */
    private List<Employee> employees;
    /** Животные */
    private List<Animal> animals;

    /**
     * Зоопарк
     * @param title название
     */
    public Zoo(String title) {
        this.title = title;
        employees = new ArrayList<>();
        animals = new ArrayList<>();
        trackingService = new TrackingService();
    }

    /**
     * Добавление животного
     * @param animal животное
     * @param employee опекун
     */
    public void add(Animal animal, Employee employee) {
        if (animals.contains(animal)) {
            return;
        }
        animals.add(animal);
        trackingService.add(animal);
        if (employees.contains(employee)) {
            employee.add(animal);
            animal.add(employee);
        }
    }

    public void set(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    /**
     * Добавление сотрудников
     * @param employees сотрудники
     */
    public void add(Employee... employees) {
        this.employees.addAll(Arrays.asList(employees));
        trackingService.add(employees);
    }
    /**
     * Удаление животных
     * @param animals животные
     */
    public void remove(Animal... animals) {
        for (Animal animal : animals) {
            for (Employee employee : animal.getEmployees()) {
                    employee.remove(animal);
            }
            this.animals.remove(animal);
        }
    }
    /**
     * Удаление сотрудников
     * @param employees сотрудники
     */
    public void remove(Employee... employees) {
        for (Employee employee : employees) {
            if (!this.employees.contains(employee)) {
                continue;
            }
            for (Animal animal : employee.getAnimals()) {
                  animal.remove(employee);
            }
            this.employees.remove(employee);
        }
    }

    public String getTitle() {
        return title;
    }
    public List<Employee> getEmployees() {
        return employees;
    }
    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "title='" + title + '\'' +
                ", employees=" + employees +
                ", animals=" + animals +
                '}';
    }
}
