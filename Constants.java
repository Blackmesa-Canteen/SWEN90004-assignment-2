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
    enum Color {
        /* Black daisy color, and console display note */
        BLACK("black", 'B'),
        /* While daisy color, and console display note */
        WHITE("white", 'W'),
        /* Mutant daisy color, and console display note */
        OTHER("other", 'X');

        private final String name;
        private final char note;

        Color(String name, char note) {
            this.name = name;
            this.note = note;
        }

        @Override
        public String toString() {
            return name;
        }

        public char getNote() {
            return note;
        }
    }

    // parameter file name
    String PARAMETER_FILE_NAME = "params.properties";

    // output csv file dir name
    String DATA_DIR_NAME = "data";

    // csv is required for the project
    String DATA_EXTENSION = ".csv";

    // CSV file head
    String CSV_FILE_HEAD =
            "tick-count,solar-luminosity,global-temp," +
                    "white-population,black-population,total-population\n";

    String CSV_FILE_HEAD_EXTENSION =
            "tick-count,solar-luminosity,global-temp," +
                    "white-population,black-population,mutant-population,total-population\n";

    // Bare ground Patch Node
    Character GROUND_PATCH_NOTE = '_';

    // 2D Diffusion share rate always be 1/8, regardless of topology
    Double DIFFUSION_SHARE_RATE_2D = (1.0 / 8.0);
}
