package tracking;

/** Отслеживаемый объект */
public interface Tracked {

    double x = 0;
    double y = 0;

    /**
     * Уникальный индекс отслеживания
     * @return id
     */
    String getId();

    /**
     * Передача текущего местоположения в теукщий момент времени
     * @param x по OX
     * @param y по OY
     */
    void updatePosition(double x, double y);
}
