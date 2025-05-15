package server.data.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Subject;
import server.utils.exception.internalerror.DatabaseOperationException;

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

    public Subject save(Subject subject) {
        insertSubject(subject);
        return subject;
    }

    public void insertSubject(Subject subject) {
        String query = "INSERT INTO subject (id, name) VALUES (?, ?)";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setObject(1, subject.getId());
            statement.setString(2, subject.getName());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationException("Failed to insert subject");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in inserting subject", e);
        }
    }

    public Optional<Subject> findById(UUID id) {
        String query = "SELECT * FROM subject WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(UUID.fromString(resultSet.getString("id")));
                subject.setName(resultSet.getString("name"));

                return Optional.of(subject);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in fetching subject by ID", e);
        }
    }

    public Optional<Subject> findByName(String name) {
        String query = "SELECT * FROM subject WHERE name = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Subject subject = new Subject();
                    subject.setId(UUID.fromString(resultSet.getString("id")));
                    subject.setName(resultSet.getString("name"));

                    return Optional.of(subject);
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in fetching subject by name", e);
        }
    }

    public List<Subject> findAllSubjects() {
        String query = "SELECT * FROM subject";
        List<Subject> subjects = new ArrayList<>();

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setId(UUID.fromString(resultSet.getString("id")));
                subject.setName(resultSet.getString("name"));
                subjects.add(subject);
            }
            return subjects;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in fetching all subjects", e);
        }
    }

    public void deleteById(UUID id) {
        String query = "DELETE FROM subject WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationException("Failed to delete subject");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in deleting subject", e);
        }
    }

    public boolean doesSubjectExist(UUID id) {
        String query = "SELECT COUNT(*) FROM subject WHERE id <> ? AND name = ?";

        try (Connection connection = connectionPool.connectToDataBase();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){

            Subject subject = findById(id).orElseThrow(() -> new DatabaseOperationException("Subject with id " + id + " does not exist"));

            preparedStatement.setObject(1, subject.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in fetching subject by ID", e);
        }
    }
}