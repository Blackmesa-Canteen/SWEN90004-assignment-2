import java.security.SecureRandom;
import java.util.*;

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

    // global average temp
    private double globalTemp;

    // current solar luminosity
    private double solarLuminosity;

    // least delays between ticks
    private final long delayMs;

    // max ticks in the run
    private final long maxTicks;

    // world width
    private final int width;

    // world height
    private final int height;

    // ground patches, coordinate starts from top left.
    private final GroundPatch[][] groundPatches;

    // patch set used for init daisies
    private final LinkedList<GroundPatch> emptyPatchList;

    // total num of ground patches
    private final int totalPatches;

    // tick frame counter
    private long tickCount;

    public DaisyWorld() {
        width = ParamsUtil.getParam(Params.WORLD_WIDTH, Integer.class);
        height = ParamsUtil.getParam(Params.WORLD_HEIGHT, Integer.class);
        delayMs = ParamsUtil.getParam(Params.DELAY_MS, Long.class);
        maxTicks = ParamsUtil.getParam(Params.TOTAL_TICKS, Long.class);
        groundPatches = new GroundPatch[height][width];
        emptyPatchList = new LinkedList<>();
        // prevent overflow for total patches,
        // because Java LinkedList only supports INTEGER_MAX size
        int res = 0;
        try {
            res = Math.multiplyExact(width, height);
        } catch(ArithmeticException e) {
            System.out.println(
                    "[ERROR] Ground patch number overflow! " +
                            "Check width and height. Error info: "
                            + e.getMessage());
            System.exit(-1);
        }
        totalPatches = res;
    }

    @Override
    public void onInit() {
        // init business logic
        globalTemp = ParamsUtil.getParam(Params.INIT_GLOBAL_TEMP, Double.class);
        solarLuminosity = ParamsUtil.getParam(Params.SOLAR_LUMINOSITY, Double.class);
        System.out.format("World was created in temp [%s] degree.\n", globalTemp);

        // init ground patches
        initGroundPatches();

        // init daisy: randomly allocate daisies to patches
        initDaisies();

        // init temp on patches
        initTemp();

        // reset tick frames
        tickCount = 0;

        // sample current world state, show statistic data
        onObserve();

        System.out.println("world init finished!");
    }

    @Override
    public void onGoing() throws InterruptedException {
        while (tickCount <= maxTicks) {
            System.out.println("[DEBUG] world goes");

            if (delayMs > 0) {
                //noinspection BusyWait
                Thread.sleep(delayMs);
            }
        }
    }


    @Override
    public void onDestroy() {
        System.out.println("world finished");
    }

    /**
     * Observe current world state,
     * Sample the statistical data.
     */
    @Override
    public void onObserve() {
        System.out.println("sample data.");
    }

    /**
     * Init ground patches
     */
    private void initGroundPatches() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                GroundPatch groundPatch =
                        new GroundPatch(this, x, y);
                groundPatches[x][y] = groundPatch;
                emptyPatchList.offer(groundPatch);
            }
        }
    }

    /**
     * init Daisies randomly in existing empty patches
     */
    private void initDaisies() {
        // shuffle collection to get random patch list
        Collections.shuffle(emptyPatchList, new SecureRandom());

        // calc total daisies needed
        long numWhiteDaisy = (long)
                (totalPatches *
                        ParamsUtil.getParam(
                                Params.START_PERCENTAGE_WHITES, Double.class));
        long numBlackDaisy = (long)
                (totalPatches *
                        ParamsUtil.getParam(
                                Params.START_PERCENTAGE_BLACKS, Double.class));

        // add black daisy
        for (long i = 0; i < numBlackDaisy; i++) {
            GroundPatch randomPatch = emptyPatchList.poll();
            if (randomPatch != null) {
                Daisy daisy = new Daisy(randomPatch, Constants.Color.BLACK);
                randomPatch.setDaisy(daisy);
            }
        }

        // add white daisy
        for (long i = 0; i < numWhiteDaisy; i++) {
            GroundPatch randomPatch = emptyPatchList.poll();
            if (randomPatch != null) {
                Daisy daisy = new Daisy(randomPatch, Constants.Color.WHITE);
                randomPatch.setDaisy(daisy);
            }
        }
    }

    /**
     * init temp
     */
    private void initTemp() {
        // TODO init temp on patches

        // 1. update patch temp
        // 2. update global patch, which is mean temp among all patches
    }
}
