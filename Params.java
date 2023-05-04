/**
 * <p>
 * Param Names.
 * </p>
 * <p>
 * !!!
 * Note: This interface only records parameter names, NOT values.
 * For param values, please go to the file `params.properties`
 * !!!
 * </p>
 *
 * @author Xiaotian LI
 * @since 28/04/2023
 */
public interface Params {

    /* !!! param names, not param values !!! */
    /* For param values, goto `params.properties` file in project folder */

    /* Extension related param names */
    String DEBUG_MODE = "debug-mode";
    String DISPLAY_WORLD = "display-world";
    String TOTAL_TICKS = "total-ticks";
    String DELAY_MS = "delay-ms";
    String INIT_GLOBAL_TEMP = "init-global-temp";
    String WORLD_WIDTH = "world-width";
    String WORLD_HEIGHT = "world-height";
    String SOLAR_LUMINOSITY = "solar-luminosity";
    String IS_SOLAR_LUMINOSITY_RAMP = "solar-luminosity-ramp-up-and-down";
    String DAISY_MAX_AGE_TICKS = "daisy-max-age-ticks";
    String START_PERCENTAGE_BLACKS = "start-%-blacks";
    String START_PERCENTAGE_WHITES = "start-%-whites";
    String ALBEDO_OF_BLACKS = "albedo-of-blacks";
    String ALBEDO_OF_WHITES = "albedo-of-whites";
    String ALBEDO_OF_GROUND = "albedo-of-ground";
    String DIFFUSION_RATE_OF_GROUND = "diffusion-rate-of-ground";

    /* Extension related param names */
    String ENABLE_EXTENSION = "enable-extension";
    String MUTANT_DAISY_PROB = "mutant-daisy-prob";
    String ALBEDO_OF_MUTANTS = "albedo-of-mutants";
}
