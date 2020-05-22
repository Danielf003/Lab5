package examples;

/**The class for LabWork's field Discipline. */
public class Discipline {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long lectureHours;

    public Discipline (String n, long lh) {
        name = n;
        lectureHours = lh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLectureHours() {
        return lectureHours;
    }

    public void setLectureHours(long lectureHours) {
        this.lectureHours = lectureHours;
    }
    @Override
    public String toString() {
        return "Discipline: [name: "+getName()+", Lecture hours: "+getLectureHours()+"]";
    }
}
