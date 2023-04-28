/**
 * <p>
 * The daisy world.
 * </p>
 *
 * @author Xiaotian LI
 * @author Junrong WU
 * @author xintong LIU
 * @since 28/04/2023
 */
public class DaisyWorld implements Observer {

    private double globalTemp;
    private final int width;
    private final int height;
    private final Patch[][] patches;

    private long tickCount;

    public DaisyWorld() {
        this.globalTemp = ParamsUtil.getParam(Params.INIT_GLOBAL_TEMP, Double.class);
        this.width = ParamsUtil.getParam(Params.WORLD_WIDTH, Integer.class);
        this.height = ParamsUtil.getParam(Params.WORLD_HEIGHT, Integer.class);
        this.patches = new Patch[height][width];
        this.tickCount = 0;
    }

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
