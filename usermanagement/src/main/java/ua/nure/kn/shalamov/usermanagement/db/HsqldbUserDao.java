package ua.nure.kn.shalamov.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import ua.nure.kn.shalamov.usermanagement.User;

public class HsqldbUserDao implements UserDao{

	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private ConnectionFactory connectionFactory;
	
    public HsqldbUserDao(ConnectionFactory connectionFactory) {
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
             preparedStatement.setDate(count, (Date) user.getDateOfBirth());
             int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows != 1) {
                throw new DatabaseException("Number of the inserted rows: " + insertedRows);
            }
            callableStatement = connection.prepareCall("call IDENTITY()");
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                callableStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
    }

	@Override
	public void update(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User find(Long id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
