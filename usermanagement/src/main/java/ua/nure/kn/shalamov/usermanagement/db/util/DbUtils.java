package ua.nure.kn.shalamov.usermanagement.db.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import ua.nure.kn.shalamov.usermanagement.db.DatabaseException;

public class DbUtils {

    private DbUtils() {
    }

    public static void closeStatements(Statement... statements) throws DatabaseException {
        try {
            if (Objects.nonNull(statements)) {
                for (Statement statement : statements) {
                    if (Objects.nonNull(statement)) {
                        statement.close();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet resultSet) throws DatabaseException {
        try {
            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}