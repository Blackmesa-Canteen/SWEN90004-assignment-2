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
    private final Constants.Color color;
    private int currentAge;

    private GroundPatch groundPatch;

    private boolean isDead;

    public Daisy(GroundPatch groundPatch, Constants.Color color) {
        id = UUID.randomUUID().toString().substring(0,8);
        this.color = color;
        this.groundPatch = groundPatch;
        isDead = false;
        onCreat();
    }

    public Constants.Color getColor() {
        return color;
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public void onCreat() {
        // random start age, according to original model
        currentAge = (int)
                (Math.random() *
                        ParamsUtil.getParam(Params.DAISY_MAX_AGE_TICKS, Double.class)
                );

        System.out.format("daisy [%s] was created with color [%s].\n",
                id,
                color.toString()
        );
    }

    @Override
    public void onStateUpdate() {
        if (currentAge > ParamsUtil.getParam(Params.DAISY_MAX_AGE_TICKS, Double.class)) {
            onDestroy();
        } else {
            currentAge++;
        }

        // TODO temp check
    }
    @Override
    public void onDestroy() {
        if (!isDead) {
            isDead = true;
            System.out.format("[%s] daisy [%s] died.\n", color.toString(), id);
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
}
