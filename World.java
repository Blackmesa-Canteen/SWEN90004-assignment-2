/**
 * <p>
 * The world.
 * </p>
 *
 * @author Xiaotian LI
 * @author Junrong WU
 * @author xintong LIU
 * @since 28/04/2023
 */
public class World implements Observer {

    @Override
    public void init() {
        System.out.println("world init");
    }

    @Override
    public void go() {
        System.out.println("world goes");
    }

    @Override
    public void finished() {
        System.out.println("world finished");
    }


}
