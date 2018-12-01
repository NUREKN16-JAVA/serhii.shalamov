package ua.nure.kn.shalamov.usermanagement.db;

import static org.junit.Assert.*;

import java.util.Collection;
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
    private static final long TEST_ID = 1001L;
    
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
	
	 @Test
	    public void testFind() {
	        long id = TEST_ID;
	        try {
	            User user = dao.find(id);
	             assertNotNull(user);
	             long userId = user.getId();
	            assertEquals(id, userId);
	        } catch (DatabaseException e) {
	            fail(e.getMessage());
	        }
	     }
	     @Test
	    public void testDelete() {
	        User testUser = createUser();
	        int expectedBeforeSize = 2;
	        int expectedAfterSize = 1;
	        try {
	            int beforeSize = dao.findAll().size();
	            dao.delete(testUser);
	            int afterSize = dao.findAll().size();
	             assertEquals(expectedBeforeSize, beforeSize);
	            assertEquals(expectedAfterSize, afterSize);
	        } catch (DatabaseException e) {
	            fail(e.getMessage());
	        }
	    }
	     @Test
	    public void testUpdate() {
	        User testUser = createUser();
	        try {
	            dao.update(testUser);
	            User updatedUser = dao.find(testUser.getId());
	             assertEquals(FIRST_NAME, updatedUser.getFirstName());
	            assertEquals(LAST_NAME, updatedUser.getLastName());
	        } catch (DatabaseException e) {
	            fail(e.getMessage());
	        }
	    }
	     
	     private User createUser() {
	         return new User(TEST_ID, FIRST_NAME, LAST_NAME, new Date());
	     }     
	     
	     
	@Test
    public void testGetAll() {
        int expectedUsersNumber = 2;
        try {
            Collection<User> users = dao.findAll();
             assertNotNull(users);
            assertEquals(expectedUsersNumber, users.size());
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

}
