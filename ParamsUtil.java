import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Util for parsing param file
 *
 * @author Xiaotian Li
 * @since 28/04/2023
 */
public class ParamsUtil {
    private static Properties props;

    // read the config file at startup
    static{
        readProperties(Constants.PARAMETER_FILE_NAME);
    }

    /**
     * Load config file
     *
     * @param fileName config file name
     */
    private static void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(
                    Objects.requireNonNull(
                            ParamsUtil.class
                                    .getClassLoader()
                                    .getResourceAsStream(fileName)),
                    StandardCharsets.UTF_8);
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get config filed by key
     *
     * @param key string config key
     * @return string value for that config
     */
    public static String get(String key) {
        return props.getProperty(key);
    }

    /**
     * get the config map
     *
     * @return Map of configuration
     */
    public static Map<?, ?> getParamMap() {
        Map<String, String> map = new HashMap<>();
        Enumeration<?> enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }
}
