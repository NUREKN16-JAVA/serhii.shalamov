package ua.nure.kn.shalamov.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

 public class DaoFactory {
     private Properties properties;
     public DaoFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     private ConnectionFactory getConnectionFactory() {
        String driver = properties.getProperty("connection.driver");
        String url = properties.getProperty("connection.url");
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        return new ConnectionFactoryImpl(driver, url, user, password);
    }
     public UserDao getUserDao() {
        return null;
    }
}
