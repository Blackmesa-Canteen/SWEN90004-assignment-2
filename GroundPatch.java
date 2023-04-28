import java.util.List;
import java.util.UUID;

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
public class GroundPatch implements Patch {

    private final String id;

    private final DaisyWorld world;

    private final Coordinate coordinate;

    private Daisy daisy;

    private double temp;

    public GroundPatch(DaisyWorld world, int xcor, int ycor) {
        this.world = world;
        id = UUID.randomUUID().toString().substring(0,8);
        coordinate = new Coordinate(xcor, ycor);
        onCreate();
    }

    @Override
    public void onCreate() {
        System.out.format("Ground Patch [%s] was created " +
                        "in coordinate (%s, %s).\n",
                id,
                coordinate.getXcor(),
                coordinate.getYcor()
        );
    }

    @Override
    public void onStateUpdate() {
        // if the patch has a daisy on it
        if (daisy != null) {

            // if the daisy is dead, release the reference
            if (daisy.isDead()) {
                daisy = null;
                return;
            }
            // TODO to be continued
        }

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

    public String getId() {
        return id;
    }

    public Daisy getDaisy() {
        return daisy;
    }

    public void setDaisy(Daisy daisy) {
        this.daisy = daisy;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
