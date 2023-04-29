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
        BLACK("black", 'B'), WHITE("white", 'W');

        private final String name;
        private final char note;

        private Color(String name, char note) {
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

    // Bare ground Patch Node
    Character GROUND_PATCH_NOTE = '_';

    // 2D Diffusion share rate always be 1/8, regardless of topology
    Double DIFFUSION_SHARE_RATE_2D = 1 / 8.0;
}
