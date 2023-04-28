/**
 * Turtle model from NetLogo
 *
 * @author Xiaotian Li
 */
public interface Turtle {

    /**
     * turtle behaviors in created life cycle
     */
    void onCreat();

    /**
     * turtle do things in a tick
     */
    void tick();

    /**
     * turtle behaviors in finished life cycle
     */
    void onDestroy();

    /**
     * Get current patch coordinate
     * NOTE: turtle coordinate is simplified to be the same
     * as the located patch's according to project.
     * So deprecated for daisy
     *
     * @return Coordinate x, y pair
     */
    Coordinate getCoordinate();

    /**
     * set coordinate
     * @param xcor
     * @param ycor
     */
    void setCoordinate(int xcor, int ycor);
}
