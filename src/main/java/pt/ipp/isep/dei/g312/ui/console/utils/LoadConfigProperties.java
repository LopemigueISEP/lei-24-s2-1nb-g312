package pt.ipp.isep.dei.g312.ui.console.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadConfigProperties {

    private static final Properties properties = new Properties();
    private static final String PATH = "config.properties";

    static {
        loadFile();
    }

    private LoadConfigProperties() {}

    private static void loadFile() {
        try (InputStream inputStream = new FileInputStream(PATH)) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not Found", e);
        } catch (IOException e) {
            throw new RuntimeException("error in loadFile in LoadConfigProperties ", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}