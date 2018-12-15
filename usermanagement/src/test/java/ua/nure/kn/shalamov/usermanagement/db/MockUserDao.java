package ua.nure.kn.shalamov.usermanagement.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.kn.shalamov.usermanagement.User;

public class MockUserDao implements UserDao {

    private Map<Long, User> users;

    public MockUserDao() {
        users = new HashMap<>();
    }

    @Override
    public User create(User user) throws DatabaseException {
        long id = users.size() + 1;
        users.put(id, user);
        user.setId(id);
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        users.remove(user.getId());
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}