/**
 * Observer model from NetLogo
 *
 * @author Xiaotian Li
 */
public interface Observer {

    /**
     * observer behaves when init
     */
    void onInit();
    /**
     * observer can go along with the world
     */
    void onGoing() throws InterruptedException;

    /**
     * Observe current world state,
     * Sample the statistical data.
     */
    void onObserve();

    /**
     * observer behaves when the world at an end
     */
    void onDestroy();
}
