package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.SchoolClass;
import server.utils.exception.internalerror.DatabaseOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SchoolClassRepository {

    private final ConnectionPool connectionPool;

    public SchoolClassRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public SchoolClass save(SchoolClass schoolClass) {
        insertClass(schoolClass);
        return schoolClass;
    }

    public void insertClass(SchoolClass schoolClass) {
        String insertQuery = "INSERT INTO class (id, letter, number, teacher_id) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            if (findClassById(schoolClass.getId()).isEmpty()) {
                preparedStatement.setObject(1, schoolClass.getId());
                preparedStatement.setObject(2, schoolClass.getLetter());
                preparedStatement.setObject(3, schoolClass.getNumber());
                preparedStatement.setObject(4, schoolClass.getTeacherId());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted == 0) {
                    throw new DatabaseOperationException("Could not insert class");
                }
            } else {
                throw new DatabaseOperationException("Class with id " + schoolClass.getId() + " already exists");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not insert class", e);
        }
    }

    public void deleteClass(UUID id) {
        String query = "DELETE FROM class WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationException("Class with id " + id + " has been deleted");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not delete class", e);
        }
    }

    public Optional<SchoolClass> findClassById(UUID classId) {

        try {
            Connection connection = connectionPool.connectToDataBase();

            String query = "SELECT * FROM class WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, classId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                SchoolClass schoolClass = new SchoolClass();
                schoolClass.setId(UUID.fromString(resultSet.getString("id")));
                schoolClass.setLetter(resultSet.getString("letter"));
                schoolClass.setNumber(resultSet.getString("number"));
                schoolClass.setTeacherId(UUID.fromString(resultSet.getString("teacher_id")));

                return Optional.of(schoolClass);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not find class with id " + classId, e);
        }
    }

    public boolean doesSchoolClassExist(UUID id) {
        String query = "SELECT COUNT(*) FROM class WHERE id <> ? AND letter = ? AND number = ? AND teacher_id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){

            SchoolClass schoolClass = findClassById(id).orElseThrow(() -> new DatabaseOperationException("Class with id " + id + " does not exist"));

            preparedStatement.setObject(1,schoolClass.getLetter());
            preparedStatement.setObject(2,schoolClass.getNumber());
            preparedStatement.setObject(3,schoolClass.getTeacherId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Could not find class with id " + id, e);
        }
    }
}

