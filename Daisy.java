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
    private final Character note;
    private final Constants.Color color;
    private int currentAge;

    private GroundPatch groundPatch;

    private boolean isDead;

    public Daisy(GroundPatch groundPatch, Constants.Color color) {
        id = UUID.randomUUID().toString();
        this.color = color;
        note = color.getNote();
        this.groundPatch = groundPatch;
        isDead = false;
        onCreat();
    }

    public Constants.Color getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public boolean isDead() {
        return isDead;
    }

    public Character getNote() {
        return note;
    }

    @Override
    public void onCreat() {
        // random start age, according to original model
        currentAge = (int)
                (Math.random() *
                        ParamsUtil.getParam(Params.DAISY_MAX_AGE_TICKS, Double.class)
                );

        System.out.format("Daisy [%s] was created with color [%s].\n",
                id.substring(0,8),
                color
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
            System.out.format("[%s] daisy [%s] died.\n", color, id);
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
