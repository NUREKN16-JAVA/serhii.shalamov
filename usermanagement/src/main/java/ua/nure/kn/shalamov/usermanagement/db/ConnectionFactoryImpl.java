package ua.nure.kn.shalamov.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 public class ConnectionFactoryImpl implements ConnectionFactory {
	 
	 	private static final String DEFAULT_DRIVER = "org.hsqldb.jdbcDriver";
	    private static final String DEFAULT_URL = "jdbc:hsqldb:file:db/usermanagement";
	    private static final String DEFAULT_USER = "sa";
	    private static final String DEFAULT_PASSWORD = "";

	    private String driver;
	    private String url;
	    private String user;
	    private String password;

	    public ConnectionFactoryImpl() {
	        this.driver = DEFAULT_DRIVER;
	        this.url = DEFAULT_URL;
	        this.user = DEFAULT_USER;
	        this.password = DEFAULT_PASSWORD;
	    }
	    
	    public ConnectionFactoryImpl(String driver, String url, String user, String password) {
	        this.driver = driver;
	        this.url = url;
	        this.user = user;
	        this.password = password;
	    }
	     
     @Override
    public Connection createConnection() throws DatabaseException {
       
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
