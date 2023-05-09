import java.security.SecureRandom;
import java.util.*;

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

    private final boolean isDebugMode =
            ParamsUtil.getParam(Params.DEBUG_MODE, Boolean.class);

    private final boolean isExtensionEnabled =
            ParamsUtil.getParam(Params.ENABLE_EXTENSION, Boolean.class);

    private final char note;

    private final DaisyWorld world;

    private final Coordinate coordinate;

    private final Double albedoOfGround;

    private final Double diffusionRate;

    private Daisy daisy;

    private double temperature;

    public GroundPatch(DaisyWorld world, int xcor, int ycor) {
        this.world = world;
        id = UUID.randomUUID().toString();
        coordinate = new Coordinate(xcor, ycor);
        note = Constants.GROUND_PATCH_NOTE;
        albedoOfGround = ParamsUtil.getParam(Params.ALBEDO_OF_GROUND, Double.class);
        diffusionRate = ParamsUtil.getParam(Params.DIFFUSION_RATE_OF_GROUND, Double.class);
        onCreate();
    }

    @Override
    public void onCreate() {
        if (isDebugMode) {
            System.out.format("Ground Patch [%s] was created " +
                            "in coordinate (%s, %s).\n",
                    id.substring(0,8),
                    coordinate.getXcor(),
                    coordinate.getYcor()
            );
        }
    }

    /**
     * patch update state in a tick
     */
    @Override
    public void onStateUpdate() {
        // absorb solar energy
        updateTempBySolarLuminosity();
    }

    /**
     * Update patch temperature by solar luminosity
     */
    public void updateTempBySolarLuminosity() {
        // update temps and so on
        double absorbedLuminosity;
        double localHeating;

        if (daisy == null) {
            absorbedLuminosity = (1 - albedoOfGround) * world.getSolarLuminosity();
        } else {
            // update state if has daisy
            absorbedLuminosity = (1 - daisy.getAlbedo()) * world.getSolarLuminosity();
        }

        if (absorbedLuminosity > 0) {
            localHeating = 72 * Math.log(absorbedLuminosity) + 80;
        } else {
            localHeating = 80;
        }

        temperature = (localHeating + temperature) / 2;
    }

    /**
     * Update patch temperature by Diffuse neighbors.
     * </br>
     * Tells each patch to give equal shares of (number * 100) percent of
     * the value of patch-variable to its eight neighboring patches.
     * </br>
     * number should be between 0 and 1. Regardless of topology the sum of patch
     * will be conserved across the world. (If a patch has fewer than 8 neighbors,
     * each neighbor still gets an 8 share; the patch keeps any leftover shares.)
     */
    public void updateTempByDiffuse() {

        // current patch offer out his temp with a rate
        double tempDiffused = temperature * diffusionRate;
        temperature -= tempDiffused;

        List<Patch> neighbours = getNeighbours();
        for (Patch patch : neighbours) {
            // all neighbour get 1/8 temp share, regardless of topology
            GroundPatch groundPatch = (GroundPatch) patch;
            groundPatch.doTemperatureIncrement(
                    tempDiffused *
                            Constants.DIFFUSION_SHARE_RATE_2D
            );
        }
    }

    /**
     * Handles daisy sprout, based on local temperature
     */
    public void checkDaisySurvivability() {
        if (daisy != null) {
            // tick daisy age
            daisy.onStateUpdate();
            // if daisy dead, release the field
            if (daisy.isDead()) {
                setDaisy(null);
            } else {
                // Try randomly sprout daisies by local temperature
                // formula from NetLogo Model
                double seedThreshold =
                        (0.1457 * temperature) -
                                (0.0032 * (Math.pow(temperature, 2))) - 0.6443;

                Random randomObj = new SecureRandom();
                if (randomObj.nextDouble() < seedThreshold) {
                    // get one neighbours without daisy
                    List<GroundPatch> emptyNeighbours =
                            getNeighbours()
                                    .stream()
                                    .filter(neighbour ->
                                            neighbour.getCurrentTurtle() == null)
                                    .map(emptyNeighbour -> (GroundPatch) emptyNeighbour)
                                    .toList();

                    // get one random empty neighbour
                    int neighbourSize = emptyNeighbours.size();
                    if (neighbourSize > 0) {
                        GroundPatch theRandomEmptyNeighbour =
                                emptyNeighbours.get(
                                        randomObj.nextInt(neighbourSize)
                                );

                        // check extension enabled or not
                        if (!isExtensionEnabled) {
                            // if runs original model
                            // create a new daisy on the neighbor with my daisy's color
                            theRandomEmptyNeighbour.setDaisy(
                                    new Daisy(
                                            theRandomEmptyNeighbour,
                                            this.daisy.getColor(),
                                            0
                                    )
                            );
                        } else {
                            // if runs extension model
                            reproduceMutantDaisies(theRandomEmptyNeighbour);
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles mutant daisy reproduction
     */
    private void reproduceMutantDaisies(GroundPatch theRandomEmptyNeighbour) {
        Random randomObj = new SecureRandom();
        // if the current daisy is not mutant, offsprings may be mutant
        if (!this.daisy.getColor().equals(Constants.Color.OTHER)) {
            Double mutantProb = ParamsUtil.getParam(
                    Params.MUTANT_DAISY_PROB, Double.class
            );
            boolean geneMutationTriggered = randomObj.nextDouble() < mutantProb;
            if (geneMutationTriggered) {
                // create a mutant daisy with mutant color
                theRandomEmptyNeighbour.setDaisy(
                        new Daisy(
                                theRandomEmptyNeighbour,
                                Constants.Color.OTHER,
                                0
                        )
                );

                // End the current process
                return;
            }
        } else {
            // If current daisy is a mutant, check whether it can reproduce
            Double mutantReproduceProb = ParamsUtil.getParam(
                    Params.MUTANT_REPRODUCE_PROB,
                    Double.class
            );
            boolean canMutantReproduce = randomObj.nextDouble() < mutantReproduceProb;

            if (!canMutantReproduce) {
                // if the mutant can not reproduce for now, stop the reproduce attempt
                return;
            }
        }

        // reproduce a new daisy with original color
        theRandomEmptyNeighbour.setDaisy(
                new Daisy(
                        theRandomEmptyNeighbour,
                        this.daisy.getColor(),
                        0
                )
        );
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Turtle getCurrentTurtle() {
        return daisy;
    }

    /**
     * get 8 neighbor patches of current patch
     */
    @Override
    public List<Patch> getNeighbours() {
        List<Patch> neighbours = new LinkedList<>();
        int hostX = coordinate.getXcor();
        int hostY = coordinate.getYcor();

        boolean hasLeftNeighbour = hostX > 0;
        boolean hasRightNeighbour = hostX < world.getWidth() - 1;
        boolean hasTopNeighbour = hostY > 0;
        boolean hasBottomNeighbour = hostY < world.getHeight() - 1;
        // left
        if (hasLeftNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY][hostX-1]);
        }
        // right
        if (hasRightNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY][hostX+1]);
        }
        // top
        if (hasTopNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY-1][hostX]);
        }
        // bottom
        if (hasBottomNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY+1][hostX]);
        }

        // top-left
        if (hasLeftNeighbour && hasTopNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY-1][hostX-1]);
        }
        // top-right
        if (hasRightNeighbour && hasTopNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY-1][hostX+1]);
        }
        // bottom-left
        if (hasLeftNeighbour && hasBottomNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY+1][hostX-1]);
        }
        // bottom-right
        if (hasRightNeighbour && hasBottomNeighbour) {
            neighbours.add(world.getGroundPatches()[hostY+1][hostX+1]);
        }

        return neighbours;
    }

    public String getId() {
        return id;
    }

    public Daisy getDaisy() {
        return daisy;
    }

    public char getNote() {
        return note;
    }

    /**
     * Set daisy on current patch
     * @param daisy Daisy obj
     */
    public void setDaisy(Daisy daisy) {
        this.daisy = daisy;
        if (isDebugMode) {
            if (daisy != null) {
                System.out.format(
                        "Patch [%s] at (%s, %s) now has [%s] daisy [%s].\n",
                        id.substring(0,8),
                        coordinate.getXcor(),
                        coordinate.getYcor(),
                        daisy.getColor(),
                        daisy.getId().substring(0,8)
                );
            } else {
                System.out.format(
                        "Patch [%s] at (%s, %s) now has no daisy.\n",
                        id.substring(0,8),
                        coordinate.getXcor(),
                        coordinate.getYcor()
                );
            }
        }
    }

    public double getTemperature() {
        return temperature;
    }

    /**
     * Set temperature of current patch
     * @param tempIncrement increased temperature
     */
    public void doTemperatureIncrement (double tempIncrement) {
        temperature += tempIncrement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroundPatch that = (GroundPatch) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
