package zoo.animal;

import tracking.Tracked;
import zoo.Position;
import zoo.employee.Employee;

import java.time.LocalDate;
import java.util.*;

public class Animal implements Tracked {

    private static final String PREFIX = "animal-";
    private static int ID = 0;

    public double x;
    public double y;

    private List<Position> movements;

    /** Уникальный идентификатор */
    private int id;
    /** Название животного */
    private String name;
    /** Дата рождения */
    private LocalDate dateOfBirth;
    /** Опекуны */
    private Set<Employee> employees;
    /** Журнал болезней */
    private List<DiseaseNote> diseaseDiary;


    /**
     * Животное
     * @param name название
     * @param dateOfBirth дата рождения
     */
    public Animal(String name, LocalDate dateOfBirth) {
        id = ID++;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.employees = new HashSet<>();
        this.diseaseDiary = new ArrayList<>();
        this.movements = new ArrayList<>();
    }


    /**
     * Реализация интерфейсного метода: получение уникального идентификатора
     * @return id
     */
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



    /**
     * Добавление записи о болезни
     * @param diseaseNote запись о болезни
     */
    public void add(DiseaseNote diseaseNote) {
        diseaseDiary.add(diseaseNote);
    }

    /**
     * Добавление опекуна
     * @param employee опекун
     */
    public void add(Employee employee) {
        employees.add(employee);
    }

    /**
     * Снятие опекуна
     * @param employee опекун
     */
    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public List<Position> getMovements() {
        return movements;
    }
    public String getName() {
        return name;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public Set<Employee> getEmployees() {
        return employees;
    }
    public List<DiseaseNote> getDiseaseDiary() {
        return diseaseDiary;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", employees=" + employees +
                ", diseaseDiary=" + diseaseDiary +
                '}';
    }
}
