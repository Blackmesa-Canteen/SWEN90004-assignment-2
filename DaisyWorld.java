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
    private Double globalTemp;

    // current solar luminosity
    private Double solarLuminosity;

    // least delays between ticks
    private final Long delayMs;

    // max ticks in the run
    private final Long maxTicks;

    // whether display the world in console
    private final Boolean displayWorld;

    // world width
    private final Integer width;

    // world height
    private final Integer height;

    // ground patches, coordinate starts from top left.
    private final GroundPatch[][] groundPatches;

    // patch set used for init daisies
    private final LinkedList<GroundPatch> emptyPatchList;

    // total num of ground patches
    private final Integer totalPatches;

    // tick frame counter
    private Long tickCount;

    public DaisyWorld() {
        width = ParamsUtil.getParam(Params.WORLD_WIDTH, Integer.class);
        height = ParamsUtil.getParam(Params.WORLD_HEIGHT, Integer.class);
        delayMs = ParamsUtil.getParam(Params.DELAY_MS, Long.class);
        maxTicks = ParamsUtil.getParam(Params.TOTAL_TICKS, Long.class);
        displayWorld = ParamsUtil.getParam(Params.DISPLAY_WORLD, Boolean.class);
        groundPatches = new GroundPatch[height][width];
        emptyPatchList = new LinkedList<>();
        // prevent overflow for total patches,
        // because Java LinkedList only supports INTEGER_MAX size
        int res = 0;
        try {
            res = Math.multiplyExact(width, height);
        } catch (ArithmeticException e) {
            System.out.println(
                    "[ERROR] Ground patch number overflow! " +
                            "Check width and height. Error info: "
                            + e.getMessage());
            System.exit(-1);
        }
        totalPatches = res;
    }

    /**
     * observer init
     */
    @Override
    public void onInit() {
        // create csv file head
        FileUtil.writeStringToResultFile(Constants.CSV_FILE_HEAD);

        // init business logic
        globalTemp = ParamsUtil.getParam(Params.INIT_GLOBAL_TEMP, Double.class);
        solarLuminosity = ParamsUtil.getParam(Params.SOLAR_LUMINOSITY, Double.class);
        System.out.format("World was created in temp [%s] degree.\n", globalTemp);

        // init ground patches
        initGroundPatches();

        // init daisy: randomly allocate daisies to patches
        initDaisies();

        // init temp on patches
        initTemperature();

        // reset tick frames
        tickCount = 0L;

        // sample current world state, show statistic data
        onObserve();
    }

    /**
     * observer can go along with the world
     */
    @Override
    public void onGoing() throws InterruptedException {
        while (tickCount <= maxTicks) {
            // scan and update state of each patch
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    groundPatches[y][x].onStateUpdate();
                }
            }

            // perform diffuse
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    groundPatches[y][x].updateTempByDiffuse();
                }
            }

            // perform daisy check and reproduce
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    groundPatches[y][x].checkDaisySurvivability();
                }
            }

            // update global temp
            updateGlobalTemp();
            // Sample the current world, show data
            onObserve();
            if (delayMs > 0) {
                //noinspection BusyWait
                Thread.sleep(delayMs);
            }
            tickCount++;
        }
    }


    /**
     * observer behaves when the world at an end
     */
    @Override
    public void onDestroy() {
        System.out.println("goodbye world");
    }

    /**
     * Observe current world state,
     * Sample the statistical data.
     */
    @Override
    public void onObserve() {
        StringBuilder consoleSb = new StringBuilder();
        StringBuilder fileSb = new StringBuilder();

        int whitePopulation = 0;
        int blackPopulation = 0;
        int totalPopulation = 0;

        consoleSb.append(
                String.format("============ Start of the world state ============\n")
        );

        // sample each patches
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                GroundPatch thePatch = groundPatches[y][x];
                Daisy theDaisy = thePatch.getDaisy();

                if (theDaisy != null && !theDaisy.isDead()) {

                    // calc daisy population
                    if (theDaisy.getColor().equals(Constants.Color.WHITE)) {
                        whitePopulation++;
                    } else if (theDaisy.getColor().equals(Constants.Color.BLACK)) {
                        blackPopulation++;
                    }
                    totalPopulation++;

                    if (displayWorld) {
                        line.append(theDaisy.getNote());
                        line.append(" ");
                    }
                } else {
                    if (displayWorld) {
                        line.append(thePatch.getNote());
                        line.append(" ");
                    }
                }
            }

            if (displayWorld) {
                line.append('\n');
                consoleSb.append(line);
            }
        }

        // prepare statistical data
        consoleSb.append('\n');
        consoleSb.append("World State Data: \n");
        consoleSb.append(
                String.format("Tick: %s/%s;\n", tickCount, maxTicks)
        );
        fileSb.append(tickCount).append(",");

        consoleSb.append(
                String.format("Solar Luminosity value: %s;\n", solarLuminosity)
        );
        fileSb.append(solarLuminosity).append(",");

        consoleSb.append(
                String.format("Global Temperature: %s;\n", globalTemp)
        );
        fileSb.append(globalTemp).append(",");

        consoleSb.append(
                String.format("While daisy population number: %s; \n",
                        whitePopulation)
        );
        fileSb.append(whitePopulation).append(",");

        consoleSb.append(
                String.format("Black daisy population number: %s; \n",
                        blackPopulation)
        );
        fileSb.append(blackPopulation).append(",");


        consoleSb.append(
                String.format("Daisy total population number: %s.\n",
                        totalPopulation)
        );

        consoleSb.append(
                String.format("============ End of the world state ============\n",
                        totalPopulation)
        );

        fileSb.append(totalPopulation);
        fileSb.append('\n');

        // flush and display on console
        // FileUtil.clearConsole();
        System.out.println(consoleSb);

        // dump data into csv
        FileUtil.writeStringToResultFile(fileSb.toString());
    }

    /**
     * Init ground patches
     */
    private void initGroundPatches() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                GroundPatch groundPatch =
                        new GroundPatch(this, x, y);
                groundPatches[y][x] = groundPatch;
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
    private void initTemperature() {

        // 1. update all patches temperature
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                groundPatches[y][x].updateTempBySolarLuminosity();
            }
        }

        // 2. update global patch, which is mean temp among all patches
        updateGlobalTemp();
    }

    /**
     * update global temp based on each ground patch
     */
    private void updateGlobalTemp() {
        double sum = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sum += groundPatches[y][x].getTemperature();
            }
        }

        globalTemp = sum / totalPatches;
    }

    public Double getGlobalTemp() {
        return globalTemp;
    }

    public Double getSolarLuminosity() {
        return solarLuminosity;
    }

    public GroundPatch[][] getGroundPatches() {
        return groundPatches;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
