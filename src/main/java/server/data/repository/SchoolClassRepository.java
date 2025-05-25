package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.SchoolClass;
import server.utils.exception.conflict.ClassAlreadyExistsExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.SchoolClassCustomNotFoundException;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SchoolClassRepository {

    private final ConnectionPool connectionPool;

    public SchoolClassRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public SchoolClass save(SchoolClass schoolClass) throws SQLException {
        insertClass(schoolClass);
        return schoolClass;
    }

    public void insertClass(SchoolClass schoolClass) throws SQLException {
        String insertQuery = "INSERT INTO class (id, letter, number, teacher_id) VALUES (?, ?, ?, ?)";

        if (findClassById(schoolClass.getId()).isEmpty()) {
            try (Connection connection = connectionPool.connectToDataBase();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setObject(1, schoolClass.getId());
                preparedStatement.setObject(2, schoolClass.getLetter());
                preparedStatement.setObject(3, schoolClass.getNumber());
                preparedStatement.setObject(4, schoolClass.getTeacherId());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted == 0) {
                    throw new DatabaseOperationExceptionCustom("Could not insert class");
                }
            }
        } else {
            throw new ClassAlreadyExistsExceptionCustom("Class with id " + schoolClass.getId() + " already exists");
        }
    }

    public void deleteClass(UUID id) throws SQLException {
        String query = "DELETE FROM class WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new SchoolClassCustomNotFoundException("No class found with id " + id + " to delete");
            }
        }
    }

    public Optional<SchoolClass> findClassById(UUID classId) throws SQLException {
        String query = "SELECT * FROM class WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
        }
    }

    public boolean doesSchoolClassExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) FROM class WHERE id <> ? AND letter = ? AND number = ? AND teacher_id = ?";

        SchoolClass schoolClass = findClassById(id)
                .orElseThrow(() -> new SchoolClassCustomNotFoundException("Class with id " + id + " does not exist"));

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, schoolClass.getLetter());
            preparedStatement.setObject(3, schoolClass.getNumber());
            preparedStatement.setObject(4, schoolClass.getTeacherId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}

