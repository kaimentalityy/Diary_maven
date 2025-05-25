package server.data.repository;

import org.springframework.stereotype.Repository;
import server.data.entity.Subject;
import server.db.ConnectionPool;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.SubjectCustomNotFoundException;

import java.sql.*;
import java.util.*;

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

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, subject.getId());
            statement.setString(2, subject.getName());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to insert subject");
            }
        }
    }

    public Optional<Subject> findById(UUID id) throws SQLException {
        String query = "SELECT * FROM subject WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(UUID.fromString(resultSet.getString("id")));
                subject.setName(resultSet.getString("name"));

                return Optional.of(subject);
            }

            return Optional.empty();
        }
    }

    public Optional<Subject> findByName(String name) throws SQLException {
        String query = "SELECT * FROM subject WHERE name = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(UUID.fromString(resultSet.getString("id")));
                subject.setName(resultSet.getString("name"));

                return Optional.of(subject);
            }

            return Optional.empty();
        }
    }

    public List<Subject> findAllSubjects() throws SQLException {
        String query = "SELECT * FROM subject";
        List<Subject> subjects = new ArrayList<>();

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(UUID.fromString(resultSet.getString("id")));
                subject.setName(resultSet.getString("name"));
                subjects.add(subject);
            }

            return subjects;
        }
    }

    public void deleteById(UUID id) throws SQLException {
        String query = "DELETE FROM subject WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to delete subject");
            }
        }
    }

    public boolean doesSubjectExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) FROM subject WHERE id <> ? AND name = ?";

        Subject subject = findById(id).orElseThrow(() -> new SubjectCustomNotFoundException("Subject with id " + id + " does not exist"));

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            preparedStatement.setString(2, subject.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}
