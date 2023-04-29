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
            System.out.format("[ERROR] Parse params.properties error [%s], " +
                    "please check the param file.\n", e.toString());
            System.exit(-1);
        }
    }

    /**
     * get config filed by key
     *
     * @param key string config key
     * @param type value type
     * @return string value for that config
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParam(String key, Class<T> type) {
        String inputString = props.getProperty(key);
        if (inputString == null) {
            System.out.format("[ERROR] Get null param for [%s], " +
                    "please check the param file.\n", key);
            System.exit(-1);
        }
        T res = null;
        // try cast the property
        try {
            if (type.isAssignableFrom(String.class)) {
                res = (T) inputString;
            } else if (type.isAssignableFrom(Integer.class)) {
                res = (T) Integer.valueOf(inputString);

            } else if (type.isAssignableFrom(Long.class)) {
                res = (T) Long.valueOf(inputString);
            } else if (type.isAssignableFrom(Double.class)) {
                res = (T) Double.valueOf(inputString);
            } else if (type.isAssignableFrom(Boolean.class)) {
                res = (T) Boolean.valueOf(inputString);
            } else {
                System.out.format("[ERROR] Not supported data type [%s], " +
                        "please check the getParam codes for [%s].\n",
                        type.getName(),
                        key);
                System.exit(-1);
            }

        } catch (Exception e) {
            System.out.format("[ERROR] Get wrong data type for [%s], " +
                    "please check the param file value type.\n", key);
            System.exit(-1);
        }

        return res;
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
