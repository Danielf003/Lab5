package examples;
/**The class for LabWork's field Coordinates. */
public class Coordinates {
    private int x;
    private Long y; //Поле не может быть null
    
    public Coordinates (int eks, Long wai) {
        x = eks;
        y = wai;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates: [x="+x+", y="+y+"]";
    }
    
}