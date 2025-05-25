package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.TeacherOfSubject;
import server.utils.exception.badrequest.InvalidColumnNameExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.TeacherCustomNotFoundException;

import java.sql.*;
import java.util.*;

@Repository
public class TeacherOfSubjectRepository {

    private final ConnectionPool connectionPool;

    public TeacherOfSubjectRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public TeacherOfSubject save(TeacherOfSubject teacherOfSubject) throws SQLException {
        insertTeacher(teacherOfSubject);
        return teacherOfSubject;
    }

    public TeacherOfSubject update(TeacherOfSubject teacherOfSubject) throws SQLException {
        updateTeacher(teacherOfSubject);
        return teacherOfSubject;
    }

    public void insertTeacher(TeacherOfSubject teacherOfSubject) throws SQLException {
        String insertQuery = "INSERT INTO teacher_of_subject VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setObject(1, teacherOfSubject.getId());
            preparedStatement.setObject(2, teacherOfSubject.getSubjectId());
            preparedStatement.setObject(3, teacherOfSubject.getTeacherId());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationExceptionCustom("Error in inserting teacher");
            }
        }
    }

    public void deleteById(UUID id) throws SQLException {
        String query = "DELETE FROM teacher_of_subject WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationExceptionCustom("Error in deleting teacher");
            }
        }
    }

    public void updateTeacher(TeacherOfSubject teacherOfSubject) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> allowedColumns = new ArrayList<>();
        allowedColumns.add("subject_id");
        allowedColumns.add("teacher_id");

        System.out.println("Please enter the column you want to update (subject_id, teacher_id): ");
        String column = scanner.nextLine();

        if (!allowedColumns.contains(column)) {
            throw new InvalidColumnNameExceptionCustom("Invalid column name. Allowed columns are: " + allowedColumns);
        }

        System.out.println("Please enter the updated value: ");
        String value = scanner.nextLine();

        String query = "UPDATE teacher_of_subject SET " + column + " = ? WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, teacherOfSubject.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationExceptionCustom("Error in updating teacher");
            }
        }
    }

    public Optional<TeacherOfSubject> findTeacherById(UUID id) throws SQLException {
        String query = "SELECT * FROM teacher_of_subject WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TeacherOfSubject teacherOfSubject = new TeacherOfSubject();
                teacherOfSubject.setId(UUID.fromString(resultSet.getString("id")));
                teacherOfSubject.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                teacherOfSubject.setTeacherId(UUID.fromString(resultSet.getString("teacher_id")));

                return Optional.of(teacherOfSubject);
            }

            return Optional.empty();
        }
    }

    public boolean doesTeacherExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) FROM teacher_of_subject WHERE id <> ? AND subject_id = ? AND teacher_id = ?";

        TeacherOfSubject teacherOfSubject = findTeacherById(id).orElseThrow(() -> new TeacherCustomNotFoundException("Teacher not found: " + id));

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, teacherOfSubject.getSubjectId());
            preparedStatement.setObject(3, teacherOfSubject.getTeacherId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}
