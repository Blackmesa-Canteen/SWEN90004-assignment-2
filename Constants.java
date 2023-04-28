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
        BLACK("black"), WHITE("white");

        private final String name;

        private Color(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // parameter file name
    String PARAMETER_FILE_NAME = "params.properties";

    // output csv file dir name
    String DATA_DIR_NAME = "data";

    // csv is required for the project
    String DATA_EXTENSION = ".csv";
}
