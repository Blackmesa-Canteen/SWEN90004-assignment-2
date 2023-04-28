import java.util.List;

/**
 * Patch model from NetLogo
 *
 * @author Xiaotian Li
 */
public interface Patch {

    /**
     * patch do things in init stage
     */
    void onCreate();

    /**
     * patch update state in a tick
     */
    void onStateUpdate();

    /**
     * get current patch coordinate
     * Note: Patch can not move
     * @return Coordinate x, y pair
     */
    Coordinate getCoordinate();

    /**
     * get current Turtle on this patch
     *
     * @return Turtle current turtle on patch,
     * if not exist, returns NULL
     */
    Turtle getCurrentTurtle();

    /**
     * get neighbor patches of current patch
     */
    List<Patch> getNeighbors();
}
