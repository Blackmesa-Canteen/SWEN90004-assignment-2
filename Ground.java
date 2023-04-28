import java.util.List;

/**
 * <p>
 * ground patch.
 * </p>
 *
 * @author Xiaotian LI
 * @author Junrong WU
 * @author xintong LIU
 * @since 28/04/2023
 */
public class Ground implements Patch {

    @Override
    public void onCreate() {
        System.out.println("Ground init");
    }

    @Override
    public void tick() {
        System.out.println("Ground patch ticks");
    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }

    @Override
    public Turtle getCurrentTurtle() {
        return null;
    }

    @Override
    public List<Patch> getNeighbors() {
        return null;
    }
}
