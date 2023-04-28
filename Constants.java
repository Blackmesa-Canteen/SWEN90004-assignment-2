/**
 * <p>
 * System global constants
 * </p>
 *
 * @author Xiaotian LI
 * @since 28/04/2023
 */
public interface Constants {
    // daisy colors
    enum DAISY_COLOUR { BLACK, WHITE }

    // parameter file name
    String PARAMETER_FILE_NAME = "params.properties";

    // output csv file dir name
    String DATA_DIR_NAME = "data";

    // csv is required for the project
    String DATA_EXTENSION = ".csv";

    // param names
    String TOTAL_TICKS = "total-ticks";
    String WORLD_WIDTH = "world-width";
    String WORLD_HEIGHT = "world-height";
    String SOLAR_LUMINOSITY = "solar-luminosity";
    String DAISY_MAX_AGE_TICKS = "daisy-max-age-ticks";
    String START_PERCENTAGE_BLACKS = "start-%-blacks";
    String START_PERCENTAGE_WHITES = "start-%-whites";
    String ALBEDO_OF_BLACKS = "albedo-of-blacks";
    String ALBEDO_OF_WHITES = "albedo-of-whites";
    String ALBEDO_OF_SURFACE = "albedo-of-surface";
    String DIFFUSION_RATE = "diffusion_rate";
}
