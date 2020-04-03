package zoo.employee;

import tracking.Tracked;
import zoo.Position;
import zoo.Zoo;
import zoo.animal.Animal;

import java.time.LocalDate;
import java.util.*;

public class Employee implements Tracked {

    private static final String PREFIX = "employee-";
    private static int ID = 0;

    public double x;
    public double y;

    private List<Position> movements;

    /** Уникальный идентификатор */
    private int id;
    /** Имя сотрудника */
    private String name;
    /** дата рождения */
    private LocalDate dateOfBirth;
    /** Подопечные */
    private Set<Animal> animals;

    /**
     * Сотрудник
     * @param name Имя
     * @param dateOfBirth дата рождения
     */
    public Employee(String name, LocalDate dateOfBirth) {
        id = ID++;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        animals = new HashSet<>();
        movements = new ArrayList<>();
    }

    public boolean isInZoo() {
        return !(Math.abs(x) > Zoo.size || Math.abs(y) > Zoo.size);
    }

    /**
     * Добавление подопечного
     * @param animal подопечный
     */
    public void add(Animal animal) {
        animals.add(animal);
    }

    /**
     * Снятие ответственности за животное
     * @param animal животное
     */
    public void remove(Animal animal) {
        animals.remove(animal);
    }

    /**
     * Находится ли животное на попечении?
     * @param animal животное
     * @return правда, если находится
     */
    public boolean isCare(Animal animal) {
        return animals.contains(animal);
    }

    public String getId() {
        return PREFIX.concat(Integer.toString(id));
    }

    /**
     * Реализация интерфейсного метода обновления позиции
     * @param x по OX
     * @param y по OY
     */
    public void updatePosition(double x, double y) {

        this.x = x;
        this.y = y;

        Position position = new Position(x, y);

        if (movements.size() == 0) {
            movements.add(position);
            return;
        }
        Position latestPosition = movements.get(movements.size() - 1);
        if (position.x != latestPosition.x || position.y != latestPosition.y) {
            movements.add(position);
        }
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public List<Position> getMovements() {
        return movements;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", animals=" + animals +
                '}';
    }
}
