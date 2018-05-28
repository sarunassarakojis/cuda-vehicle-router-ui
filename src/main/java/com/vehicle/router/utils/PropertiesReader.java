package com.vehicle.router.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

    private final File propertiesFile;

    public PropertiesReader() {
        String dir = System.getProperty("user.dir");
        propertiesFile = new File(dir + "/saro.properties");
        if (!propertiesFile.exists()) {
            try {
                propertiesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Map<String, String> readPropertiesFile() {
        Properties properties = new Properties();
        Map<String, String> propertiesMap = new HashMap<>();
        try (InputStream input = new FileInputStream(propertiesFile)) {
            properties.load(input);
            Enumeration<Object> p = properties.keys();
            while (p.hasMoreElements()) {
                String propKey = (String) p.nextElement();
                String propValue = properties.getProperty(propKey);
                propertiesMap.put(propKey, propValue);
            }

        } catch (IOException | NullPointerException ex) {
        }
        return propertiesMap;
    }


    public void writeProperties(String key, String value) {
        Properties properties = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream(propertiesFile);
            properties.setProperty(key, value);
            properties.store(output, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
