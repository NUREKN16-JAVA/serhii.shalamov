package ua.nure.kn.shalamov.usermanagement.db;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.shalamov.usermanagement.User;

public class HsqldbUserDaoTest {

	private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private UserDao dao;
    private ConnectionFactory connectionFactory;
    
    
     @Before
    public void setUp() {
    	connectionFactory = new ConnectionFactoryImpl();
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

}
