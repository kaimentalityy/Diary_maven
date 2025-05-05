package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SubjectRepository {

    private final ConnectionPool connectionPool;

    public SubjectRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Subject save(Subject subject) throws SQLException {
        insertSubject(subject);
        return subject;
    }

    public void insertSubject(Subject subject) throws SQLException {
        String query = "INSERT INTO subject (id, name) VALUES (?, ?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement statement = connection.prepareStatement(query);

            if (subject.getId() != null) {
                statement.setObject(1, subject.getId());
                statement.setString(2, subject.getName());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Inserted " + rowsInserted + " subject(s)");
                } else {
                    System.out.println("Error inserting subject");
                }
            } else {
                System.out.println("Subject does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<Subject> findById(UUID id) throws SQLException {
        String query = "SELECT * FROM subject WHERE id = ?";
        Subject subject = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (id == null) {
                throw new SQLException("Subject id is null");
            }

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    subject = new Subject();
                    subject.setId(UUID.fromString(resultSet.getString("id")));
                    subject.setName(resultSet.getString("name"));
                } else {
                    System.out.println("No subject found for ID: " + id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return Optional.ofNullable(subject);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

    }

    public Optional<Subject> findByName(String name) throws SQLException {
        String query = "SELECT * FROM subject WHERE name = ?";
        Subject subject = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (name == null) {
                throw new IllegalArgumentException("ID cannot be null");
            }

            preparedStatement.setObject(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    subject = new Subject();
                    subject.setId(UUID.fromString(resultSet.getString("id")));
                    subject.setName(resultSet.getString("name"));
                }
            }

            return Optional.ofNullable(subject);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public List<Subject> findAllSubjects() throws SQLException {
        String query = "SELECT * FROM subject";
        List<Subject> subjects = new ArrayList<>();
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(UUID.fromString(resultSet.getString("id")));
                subject.setName(resultSet.getString("name"));
                subjects.add(subject);
            }
            return subjects;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void deleteById(UUID id) throws SQLException {
        String query = "DELETE FROM subject WHERE id = ?";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Subject deleted successfully: " + id);
            } else {
                System.out.println("Subject not found: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}