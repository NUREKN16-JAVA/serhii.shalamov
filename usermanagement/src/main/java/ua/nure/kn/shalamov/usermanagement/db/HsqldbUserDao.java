package ua.nure.kn.shalamov.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.shalamov.usermanagement.User;
import ua.nure.kn.shalamov.usermanagement.db.util.DbUtils;

public class HsqldbUserDao implements UserDao{

	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ?  WHERE id = ?";
    private static final String CALL_IDENTITY = "CALL IDENTITY()";

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao() {
    }

    HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection()) {
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            int count = 1;
            preparedStatement.setString(count++, user.getFirstName());
            preparedStatement.setString(count++, user.getLastName());
            preparedStatement.setDate(count, new Date(user.getDateOfBirth().getTime()));
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows != 1) {
                throw new DatabaseException("Number of the inserted rows: " + insertedRows);
            }
            callableStatement = connection.prepareCall(CALL_IDENTITY);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DbUtils.closeResultSet(resultSet);
            DbUtils.closeStatements(preparedStatement, callableStatement);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = connectionFactory.createConnection()) {
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            int count = 1;
            preparedStatement.setString(count++, user.getFirstName());
            preparedStatement.setString(count++, user.getLastName());
            preparedStatement.setDate(count++, new Date(user.getDateOfBirth().getTime()));
            preparedStatement.setLong(count, user.getId());
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows != 1) {
                throw new DatabaseException("Exception while update operation. Effected rows: " + updatedRows);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DbUtils.closeStatements(preparedStatement);
        }
    }

    @Override
    public void delete(User user) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = connectionFactory.createConnection()) {
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, user.getId());
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows != 1) {
                throw new DatabaseException("Exception while delete operation. Effected rows: " + updatedRows);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DbUtils.closeStatements(preparedStatement);
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection()) {
            preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapUser(resultSet);
            }
            throw new DatabaseException("Can not find user by id: " + id);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DbUtils.closeStatements(preparedStatement);
            DbUtils.closeResultSet(resultSet);
        }
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Statement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection()) {
            Collection<User> users = new LinkedList<>();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DbUtils.closeStatements(statement);
            DbUtils.closeResultSet(resultSet);
        }
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        java.util.Date dateOfBirth = new java.util.Date(resultSet.getDate(4).getTime());
        return new User(id, firstName, lastName, dateOfBirth);
    }

}
