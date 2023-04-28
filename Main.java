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

    public static void main(String[] args) {
        System.out.println("hello world");
        FileUtil.writeStringToResultFile("hello! 1 \n");
        FileUtil.writeStringToResultFile("hello! 2 \n");
        System.out.println(ParamsUtil.getParam(Params.DIFFUSION_RATE_OF_GROUND, Double.class));
    }
}
