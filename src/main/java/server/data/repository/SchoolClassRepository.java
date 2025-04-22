package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.SchoolClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SchoolClassRepository {

    private final ConnectionPool connectionPool;

    public SchoolClassRepository() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
    }

    public SchoolClass save(SchoolClass schoolClass) throws SQLException {
        insertClass(schoolClass);
        return schoolClass;
    }

    public void insertClass(SchoolClass schoolClass) throws SQLException {
        String insertQuery = "INSERT INTO class (id, letter, number, teacher_id) VALUES (?, ?, ?, ?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            if (findClassById(schoolClass.getId()).isEmpty()) {
                preparedStatement.setObject(1, schoolClass.getId());
                preparedStatement.setObject(2, schoolClass.getLetter());
                preparedStatement.setObject(3, schoolClass.getNumber());
                preparedStatement.setObject(4, schoolClass.getTeacherId());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Class successfully inserted: " + schoolClass.getLetter() + " " + schoolClass.getNumber());
                    connectionPool.releaseConnection(connection);
                } else {
                    System.out.println("Failed to insertAbsense class: " + schoolClass.getLetter() + " " + schoolClass.getNumber());
                    connectionPool.releaseConnection(connection);
                }
            } else {
                System.out.println("Class already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void deleteClass(UUID id) throws SQLException {
        String query = "DELETE FROM class WHERE id = ?";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Class deleted successfully");
                connectionPool.releaseConnection(connection);
            } else {
                System.out.println("Class not found");
                connectionPool.releaseConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<SchoolClass> findClassById(UUID classId) throws SQLException {
        SchoolClass schoolClass = null;
        Connection connection = null;

        try {

            connection = connectionPool.connectToDataBase();

            String query = "SELECT * FROM class WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, classId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                schoolClass = new SchoolClass();
                schoolClass.setId(UUID.fromString(resultSet.getString("id")));
                schoolClass.setLetter(resultSet.getString("letter"));
                schoolClass.setNumber(resultSet.getString("number"));
                schoolClass.setTeacherId(UUID.fromString(resultSet.getString("teacher_id")));
                connectionPool.releaseConnection(connection);
            }

            return Optional.ofNullable(schoolClass);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}

