/**
 * Observer model from NetLogo
 *
 * @author Xiaotian Li
 */
public interface Observer {

    /**
     * observer can init the world
     */
    void init();
    /**
     * observer can watch the world goes
     */
    void go();

    /**
     * observer can finish the world
     */
    void finished();
}
