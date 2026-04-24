package base;

import java.io.InputStream;
import java.util.Properties;

/**
 * Simple config reader that loads properties from resources/config.properties if present.
 */
public class ConfigReader {
    private static Properties props = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception e) {
            // ignore - properties remain empty
        }
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
