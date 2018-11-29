package ua.nure.kn.shalamov.usermanagement.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
		try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            int count = 1;
            preparedStatement.setString(count++, user.getFirstName());
            preparedStatement.setString(count++, user.getLastName());
            preparedStatement.setDate(count, (Date) user.getDateOfBirth());
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows != 1) {
                throw new DatabaseException("Number of the inserted rows: " + insertedRows);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
		return null;
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
