import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * Daisy turtle
 * </p>
 *
 * @author Xiaotian LI
 * @author Junrong WU
 * @author xintong LIU
 * @since 28/04/2023
 */
public class Daisy implements Turtle{

    private final String id;

    private final boolean isDebugMode =
            ParamsUtil.getParam(Params.DEBUG_MODE, Boolean.class);

    private final Character note;
    private final Constants.Color color;

    private final Double albedo;

    private final long maxAge;
    private long currentAge;

    private GroundPatch groundPatch;

    private boolean isDead;

    public Daisy(GroundPatch groundPatch, Constants.Color color) {
        id = UUID.randomUUID().toString();
        this.color = color;
        albedo = color.equals(Constants.Color.WHITE) ?
                ParamsUtil.getParam(Params.ALBEDO_OF_WHITES, Double.class) :
                ParamsUtil.getParam(Params.ALBEDO_OF_BLACKS, Double.class);
        note = color.getNote();
        this.groundPatch = groundPatch;
        // random start age, according to original model
        currentAge = (long) (Math.random() *
                        ParamsUtil.getParam(
                                Params.DAISY_MAX_AGE_TICKS,
                                Double.class
                        ));
        maxAge = ParamsUtil.getParam(
                Params.DAISY_MAX_AGE_TICKS,
                Long.class);
        onCreat();
    }

    public Daisy(GroundPatch groundPatch, Constants.Color color, long initAge) {
        id = UUID.randomUUID().toString();
        this.color = color;
        albedo = color.equals(Constants.Color.WHITE) ?
                ParamsUtil.getParam(Params.ALBEDO_OF_WHITES, Double.class) :
                ParamsUtil.getParam(Params.ALBEDO_OF_BLACKS, Double.class);
        note = color.getNote();
        this.groundPatch = groundPatch;
        // random start age, according to original model
        currentAge = initAge;
        maxAge = ParamsUtil.getParam(
                Params.DAISY_MAX_AGE_TICKS,
                Long.class);
        onCreat();
    }

    public Constants.Color getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public long getCurrentAge() {
        return currentAge;
    }

    public boolean isDead() {
        return isDead;
    }

    public Character getNote() {
        return note;
    }

    public Double getAlbedo() {
        return albedo;
    }

    @Override
    public void onCreat() {
        isDead = false;
        if (isDebugMode) {
            System.out.format("Daisy [%s] was created with color [%s] and albedo [%s].\n",
                    id.substring(0,8),
                    color,
                    albedo
            );
        }
    }

    @Override
    public void onStateUpdate() {
        // check logic error
        if (isDead) {
            throw new RuntimeException(
                    String.format(
                            "[Exception] Dead [%s] daisy [%s] " +
                                    "should not update its state, check logic!",
                            color,
                            id.substring(0, 8))
            );
        }

        if (currentAge < maxAge) {
            currentAge++;
        } else {
            onDestroy();
        }
    }
    @Override
    public void onDestroy() {
        if (!isDead) {
            isDead = true;
            if (isDebugMode) {
                System.out.format("[%s] daisy [%s] died.\n", color, id);
            }
        } else {
            throw new RuntimeException(
                    String.format(
                            "[Exception] Dead [%s] daisy [%s] " +
                                    "has dead and should not be destroyed again, " +
                                    " logic!",
                            color,
                            id.substring(0, 8))
            );
        }
    }

    /**
     * NOTE: turtle coordinate is simplified to be the same
     *  as the located patch's according to project.
     *  So deprecated for daisy
     * @return
     */
    @Override
    @Deprecated
    public Coordinate getCoordinate() {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    @Deprecated
    public void setCoordinate(int xcor, int ycor) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Daisy daisy = (Daisy) o;
        return Objects.equals(id, daisy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
