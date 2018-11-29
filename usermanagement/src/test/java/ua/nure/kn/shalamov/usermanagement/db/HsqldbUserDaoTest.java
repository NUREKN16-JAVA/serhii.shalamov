package ua.nure.kn.shalamov.usermanagement.db;

import static org.junit.Assert.*;

import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.shalamov.usermanagement.User;

public class HsqldbUserDaoTest extends DatabaseTestCase{

	private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private UserDao dao;
    private ConnectionFactory connectionFactory;
    
     @Before
    public void setUp() throws Exception {
    	 super.setUp();
        dao = new HsqldbUserDao(connectionFactory);
    }
     
     @Test
    public void testCreate() {
        try {
            User testUser = new User();
            testUser.setFirstName(FIRST_NAME);
            testUser.setLastName(LAST_NAME);
            testUser.setDateOfBirth(new Date());
            
            assertNull(testUser.getId());
            testUser = dao.create(testUser);
            
            assertNotNull(testUser);
            assertNotNull(testUser.getId());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
