package zoo.animal;

import java.util.Date;

public class DiseaseNote {

    /** Болезнь */
    private String disease;
    /** Когда заболел */
    private Date time;
    /** Дополнения */
    private final String comment;

    /**
     * Запись о болезни
     * @param disease болезнь
     * @param time когда заболел
     * @param comment дополнения
     */
    public DiseaseNote(String disease, Date time, String comment) {
        this.disease = disease;
        this.time = time;
        this.comment = comment;
    }

    public String getDisease() {
        return disease;
    }

    public Date getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "DiseaseNote{" +
                "disease='" + disease + '\'' +
                ", time=" + time +
                ", comment='" + comment + '\'' +
                '}';
    }
}
