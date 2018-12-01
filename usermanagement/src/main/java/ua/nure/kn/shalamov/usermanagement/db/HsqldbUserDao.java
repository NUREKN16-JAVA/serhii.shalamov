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

public class HsqldbUserDao implements UserDao{

	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
	
	private ConnectionFactory connectionFactory;
	
	public HsqldbUserDao() {
    }
	
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
            
            java.sql.Date sqlStartDate = new java.sql.Date(user.getDateOfBirth().getTime());
            preparedStatement.setDate(count, sqlStartDate);
            int insertedRows = preparedStatement.executeUpdate();
            if (insertedRows != 1) {
                throw new DatabaseException("Number of the inserted rows: " + insertedRows);
            }
            callableStatement = connection.prepareCall("call IDENTITY()");
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            preparedStatement.close();
            callableStatement.close();
            resultSet.close();
            connection.close();
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
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
		try {
            Collection<User> users = new LinkedList<>();
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
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
        Date dateOfBirth = resultSet.getDate(4);
        return new User(id, firstName, lastName, dateOfBirth);
	}

}
