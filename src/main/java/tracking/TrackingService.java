package tracking;

import zoo.Position;
import zoo.Zoo;
import zoo.animal.Animal;
import zoo.employee.Employee;

import java.util.*;

/**
 * Сервис отслеживания {@link Tracked}
 */
public class TrackingService {

    /** размер стороны зоны */
    private static final double zone = Zoo.size;

    /** Множество отслеживаемых объектов */
    private Set<Tracked> trackable;
    /** Список взаимодействий между сотрудниками и животными */
    private List<Interaction> interactions;
    /** Список текущих взаимодействий между сотрудниками и животными */
    private Set<Interaction> currentInteractions;
    /** Журнал приходов и уходов сотрудников */
    private List<String> employeeActions;


    public TrackingService() {
        trackable = new HashSet<>();
        interactions = new ArrayList<>();
        currentInteractions = new HashSet<>();
        employeeActions = new ArrayList<>();
    }


    /** Обновление местоположения отслеживаемых объектов */
    public void updatePositions() {
        for (Tracked tracked : trackable) {
            /* Для животного только обновляется местоположение */
            if (tracked instanceof Animal) {
                tracked.updatePosition(Math.random() % (zone * 1.5) - zone / 2,
                                       Math.random() % (zone * 1.5) - zone / 2);
                continue;
            }

            /* Для сотрудника определяем, будет ли он находиться в
               той же части пространства по отношению к зоопарку */
            boolean inZoo = ((Employee) tracked).isInZoo();
            tracked.updatePosition(Math.random() % (zone * 1.5) - zone / 2,
                                   Math.random() % (zone * 1.5) - zone / 2);
            if (inZoo != ((Employee) tracked).isInZoo()) {
                employeeActions.add(new Date() + " | " + tracked.getId() +
                        (inZoo ? " left the zoo" : " entered the zoo"));
            }
        }
        defineInteractions();
    }

    public void updatePositions(Position... newPositions) {
        int i = 0;
        if (newPositions.length != trackable.size()) {
            return;
        }
        List<Position> positions = new ArrayList<>(Arrays.asList(newPositions));
        for (Tracked tracked : trackable) {
            /* Для животного только обновляется местоположение */
            if (tracked instanceof Animal) {
                tracked.updatePosition(positions.get(i).x, positions.get(i++).y);
                continue;
            }
            /* Для сотрудника определяем, будет ли он находиться в
               той же части пространства по отношению к зоопарку */
            boolean inZoo = ((Employee) tracked).isInZoo();
            tracked.updatePosition(positions.get(i).x, positions.get(i++).y);
            if (inZoo == ((Employee) tracked).isInZoo()) {
                continue;
            }
            employeeActions.add(new Date() + " | " + tracked.getId() +
                    (inZoo ? " left the zoo" : " entered the zoo"));
        }
        interruptedInteractions();
        defineInteractions();
    }

    /**
     * Метод, инициализирующий прерывание контактов.
     * Проверяется журнал текущих взаимодействий: если какое-то
     * из них прервалось, то запись добавляется в журнал взаимодействий
     * и удаляется из журнала текущих взаимодействий.
     */
    public void interruptedInteractions() {

        for (Interaction interaction : currentInteractions) {
            /* Продолжающиеся контакты пропускаем */
            if (isContact((Employee) interaction.trackedA(),
                    (Animal) interaction.trackedB())) {
                continue;
            }
            /* удаляем из журнала текущих контактов */
            currentInteractions.remove(interaction);
            /* устанавливаем конец контакта */
            interaction.setEnd(new Date());
            /* добавляем контакт в журнал */
            interactions.add(interaction);
        }
    }

    /**
     * Метод, определяющий взаимодействие сотрудников и животных.
     * Проводится поиск контактов "сотрудник : животное ", которые
     * добавляются в список текущих контактов
     */
    public void defineInteractions() {

        /* Для определённости первый объект - сотрудник */
        for (Tracked trackedA : trackable) {
            if (trackedA instanceof Animal) {
                continue;
            }
            /* Второй - животное */
            for (Tracked trackedB : trackable) {
                if (trackedB instanceof Employee) {
                    continue;
                }
                if (!isContact((Employee) trackedA, (Animal) trackedB)) {
                    continue;
                }
                boolean ongoingContact = false;
                for (Interaction interaction : currentInteractions) {
                    if ((interaction.trackedA() == trackedA) &&
                            (interaction.trackedB() == trackedB)) {
                        ongoingContact = true;
                        break;
                    }
                }
                if (ongoingContact) {
                    continue;
                }
                currentInteractions.add(new Interaction(
                        trackedA, trackedB, new Date())
                );
            }
        }
    }

    /**
     * Определяет, есть ли контакт между объектами
     * @param a первый объект
     * @param b второй объект
     * @return {@code true}, если есть контакт
     */
    public boolean isContact(Employee a, Animal b) {
        return Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2) <= 9;
    }

    public void add(Tracked... tracked) {
        trackable.addAll(Arrays.asList(tracked));
    }

    public void remove(Tracked... tracked) {
        for (Tracked temp : tracked) {
            trackable.remove(temp);
        }
    }

    public Set<Tracked> getTrackable() {
        return trackable;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public Set<Interaction> getCurrentInteractions() {
        return currentInteractions;
    }

    public List<String> getEmployeeActions() {
        return employeeActions;
    }
}
