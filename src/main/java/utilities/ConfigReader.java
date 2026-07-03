package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    public static Properties initProperties() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties file: " + e.getMessage());
        }
        return properties;
    }

    public static String getProperty(String key) {
        if (properties == null) {
            initProperties();
        }
        return properties.getProperty(key);
    }
}