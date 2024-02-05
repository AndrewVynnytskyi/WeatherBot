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


    public static String getName()
    {
        String name = System.getenv("BOT_NAME");
        if(name!=null){return name;}
        name = properties.getProperty("BOT_NAME");
        return name;
    }
    public static String getKey()
    {
        String key = System.getenv("BOT_KEY");
        if(key!=null){return key;}
        key = properties.getProperty("BOT_KEY");
        return key;
    }
    public static String getID()
    {
        String key = System.getenv("CREATOR_ID");
        if(key!=null){return key;}
        key = properties.getProperty("CREATOR_ID");
        return key;
    }
    public static String getAppID(String key)
    {
        String name = System.getenv(key);
        if(name!=null){return name;}
        name = properties.getProperty(key);
        return name;
    }
}
