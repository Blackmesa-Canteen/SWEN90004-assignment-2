/**
 * <p>
 * Daisy world sim model entry point.
 * </p>
 *
 * @author Xiaotian LI
 * @author Junrong WU
 * @author xintong LIU
 * @since 28/04/2023
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello world");
        Observer daisyWorld = new DaisyWorld();
        System.out.println("hello world");
        daisyWorld.onInit();
        daisyWorld.onGoing();
        daisyWorld.onDestroy();
        System.out.println("goodbye world");
    }
}
