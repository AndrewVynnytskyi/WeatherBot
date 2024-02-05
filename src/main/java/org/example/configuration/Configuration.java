package org.example.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final Properties properties = new Properties();
    static {
        InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("bot.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static String getConfig(String key)
    {
        String name = System.getenv(key);
        if(name!=null){return name;}
        name = properties.getProperty(key);
        return name;
    }
}
