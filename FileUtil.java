import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util for file IO
 *
 * @author Xiaotian Li
 * @since 28/04/2023
 */
public class FileUtil {

    // gen the result file name for this run
    private final static String currentFileName = genCsvResultFileName();

    /**
     * Generate csv file name for result
     *
     * @return string file name
     */
    public static String genCsvResultFileName() {
        SimpleDateFormat dfTime = new SimpleDateFormat("yyyyMMddhhmmss");
        return dfTime.format(new Date()) + "-result" + Constants.DATA_EXTENSION;
    }


    /**
     * write a string a result file
     *
     * @param string the string need to input
     */
    public static void writeStringToResultFile(String string) {
        // get target generate path
        String targetPath = Constants.DATA_DIR_NAME + File.separator + currentFileName;
        try (PrintWriter writer = new PrintWriter(
                new FileOutputStream(
                        new File(targetPath),
                        true
                ))

        ) {
            writer.write(string);
            System.out.format("[SUCCESS] State info is dumped into result file: [%s]. \n\n",
                    targetPath);
        } catch (Exception e) {
            System.out.format("[ERROR] Error occurs when writing " +
                    "string to result file: [%s]. \n\n", e.toString());
        }
    }

    /**
     * Clear console output
     */
    @Deprecated
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ignored) {

        }
    }
}
