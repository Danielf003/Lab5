package examples;
import java.time.LocalDateTime;
import java.util.*;
/**The class which objects are supposed to be stored in the collection. */
public class LabWork {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long minimalPoint; //Поле может быть null, Значение поля должно быть больше 0
    private int averagePoint; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Discipline discipline; //Поле не может быть null
    private static HashSet<Integer> ids = new HashSet<>();

    /**Assigns given values to the fields and writes used id to the list for later check. */
    public LabWork (String n, Coordinates c, Long mp, int ap, Difficulty dif, Discipline dis) {
        id = findNewId();
        ids.add(id);
        name = n;
        coordinates = c;
        minimalPoint = mp;
        averagePoint = ap;
        difficulty = dif;
        discipline = dis;
        creationDate = LocalDateTime.now();
    }

    /**Returns the first free id for next element. */
    public static Integer findNewId(){
        Integer cntr = 1;
        while(ids.contains(cntr)){
            cntr++;
        }
        return cntr;
    }
    
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public Long getMinimalPoint() {
        return minimalPoint;
    }
    public void setMinimalPoint(Long minimalPoint) {
        this.minimalPoint = minimalPoint;
    }
    public int getAveragePoint() {
        return averagePoint;
    }
    public void setAveragePoint(int averagePoint) {
        this.averagePoint = averagePoint;
    }
    public Discipline getDiscipline() {
        return discipline;
    }
    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }
    /**Returns the list of used IDs. */
    public static HashSet<Integer> getIds() {
        return ids;
    }
    @Override
    public String toString() {
        return "LabWork #"+getId()+" '"+getName()+"'\nCreated: "+getCreationDate()+"\n"+getCoordinates()+
        "\nMinimal Point: "+getMinimalPoint()+",  Average Point: "+getAveragePoint()+
        "\nDifficulty: "+getDifficulty()+"\n"+getDiscipline()+"\n____";
    }
}
