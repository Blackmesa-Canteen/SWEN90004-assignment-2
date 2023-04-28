/**
 * Coordinate pair obj
 *
 * @author Xiaotian Li
 */
public class Coordinate {

    // x coordinate
    private int xcor;

    // y coordinate
    private int ycor;

    public Coordinate() {
    }

    public Coordinate(int xcor, int ycor) {
        this.xcor = xcor;
        this.ycor = ycor;
    }

    public int getXcor() {
        return xcor;
    }

    public void setXcor(int xcor) {
        this.xcor = xcor;
    }

    public int getYcor() {
        return ycor;
    }

    public void setYcor(int ycor) {
        this.ycor = ycor;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "xcor=" + xcor +
                ", ycor=" + ycor +
                '}';
    }
}
