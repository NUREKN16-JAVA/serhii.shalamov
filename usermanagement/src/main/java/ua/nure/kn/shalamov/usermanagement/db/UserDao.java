package ua.nure.kn.shalamov.usermanagement.db;

import java.util.Collection;

import ua.nure.kn.shalamov.usermanagement.User;

public interface UserDao {
	User create(User user) throws DatabaseException;

    void update(User user) throws DatabaseException;

    void delete(User user) throws DatabaseException;

    User find(Long id) throws DatabaseException;
    
    void setConnectionFactory(ConnectionFactory connectionFactory);

    Collection<User> findAll() throws DatabaseException;
    
    Collection<User> find(String firstName, String lastName) throws DatabaseException;
}
