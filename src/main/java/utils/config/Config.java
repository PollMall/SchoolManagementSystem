package utils.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.Properties;

public class Config {
    public static String CONFIG_LOCATION= ObjectInputFilter.Config.class.getClassLoader().getResource("config.properties").getFile();

    public static Properties getProperties() {
        Properties properties=new Properties();
        try{
            properties.load(new FileReader(CONFIG_LOCATION));
            return properties;
        } catch (IOException ioe){
            throw new RuntimeException("Cannot load config properties");
        }
    }
}
