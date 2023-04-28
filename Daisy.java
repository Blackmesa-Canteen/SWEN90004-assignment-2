/**
 * <p>
 * Daisy turtle
 * </p>
 *
 * @author Xiaotian LI
 * @author Junrong WU
 * @author xintong LIU
 * @since 28/04/2023
 */
public class Daisy implements Turtle{

    @Override
    public void onCreat() {
        System.out.println("daisy born");
    }

    @Override
    public void tick() {
        System.out.println("daisy ticks");
    }
    @Override
    public void onDestroy() {
        System.out.println("daisy die");
    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    @Override
    public void setCoordinate(int xcor, int ycor) {

    }
}
