/**
 * <p>
 * Daisy world sim model entry point.
 * </p>
 *
 * <div>
 * How to run the app:
 * 1. Go to terminal, cd to the current project dir, execute `javac *.java`
 *    to compile;
 * </br>
 * 2. Check & modify parameter file `./params.properties` if needed;
 * </br>
 * 3. Run the model with `java Main`;
 * </br>
 * 4. See the experiment result `.csv` files from `./data` directory;
 * </br>
 * 5. Change the parameter file ./params.properties to do more experiments,
 * and repeat step 3 & 4. No need to recompile source codes;
 * </br>
 * 6. If finished, then execute `rm *.class` to clean compiled artifacts.
 * </div>
 *
 * @author Xiaotian LI
 * @author Junrong WU
 * @author xintong LIU
 * @since 28/04/2023
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Observer daisyWorld = new DaisyWorld();
        daisyWorld.onInit();
        daisyWorld.onGoing();
        daisyWorld.onDestroy();
    }
}
