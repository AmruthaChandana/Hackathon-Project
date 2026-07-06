package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    public static Properties initProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to load config.properties file from path: "
                            + CONFIG_FILE_PATH
                            + ". Error: "
                            + e.getMessage()
            );
        }
        return properties;
    }

    public static String getProperty(String key) {
        if (properties == null) {
            initProperties();
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property key not found in config.properties: " + key);
        }
        return value.trim();
    }
}