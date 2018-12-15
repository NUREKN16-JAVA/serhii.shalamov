package ua.nure.kn.shalamov.usermanagement.db;

import org.junit.Test;

import static org.junit.Assert.*;
 
 public class DaoFactoryTest {
     @Test
    public void getUserDao() {
        DaoFactory daoFactory = DaoFactory.getInstance();
         assertNotNull("DaoFactory is null",daoFactory);
         UserDao userDao = daoFactory.getUserDao();
         assertNotNull("UserDao is null",userDao);
    }
}
