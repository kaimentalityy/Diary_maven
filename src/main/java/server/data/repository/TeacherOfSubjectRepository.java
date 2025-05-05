package server.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.TeacherOfSubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TeacherOfSubjectRepository {

    private final ConnectionPool connectionPool;

    @Autowired
    public TeacherOfSubjectRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public TeacherOfSubject save(TeacherOfSubject teacherOfSubject) throws SQLException {
        insertTeacher(teacherOfSubject);
        return teacherOfSubject;
    }

    public void insertTeacher(TeacherOfSubject teacherOfSubject) throws SQLException {
        String insertQuery = "INSERT INTO teacher_of_subject VALUES (?, ?, ?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setObject(1, teacherOfSubject.getId());
            preparedStatement.setObject(2, teacherOfSubject.getSubjectId());
            preparedStatement.setObject(3, teacherOfSubject.getTeacherId());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Teacher successfully inserted");
            } else {
                System.out.println("Failed to insert absence user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void deleteById(UUID id) throws SQLException {
        String query = "DELETE FROM teacher_of_subject WHERE id = ?";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Lesson deleted successfully");
            } else {
                System.out.println("Lesson not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void updateTeacher(UUID id) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> allowedColumns = new ArrayList<>();
        allowedColumns.add("subject_id");
        allowedColumns.add("teacher_id");
        Connection connection = null;

        System.out.println("Please enter the column you want to update (subject_id, teacher_id): ");
        String column = scanner.nextLine();

        if (!allowedColumns.contains(column)) {
            System.out.println("Invalid column name. Allowed columns are: " + allowedColumns);
        }

        System.out.println("Please enter the updated value: ");
        String value = scanner.nextLine();
        TeacherOfSubject teacherOfSubject = findTeacherById(id).orElse(null);

        String query = "UPDATE teacher_of_subject SET " + column + " = ? WHERE id = ?";

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, value);
            if (teacherOfSubject != null) {
                preparedStatement.setObject(2, teacherOfSubject.getId());
            }

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Teacher successfully updated.");
            } else {
                System.out.println("Failed to update teacher.");
            }
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<TeacherOfSubject> findTeacherById(UUID id) throws SQLException {
        TeacherOfSubject teacherOfSubject = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            String query = "SELECT * FROM teacher_of_subject WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacherOfSubject = new TeacherOfSubject();
                teacherOfSubject.setId(UUID.fromString(resultSet.getString("id")));
                teacherOfSubject.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                teacherOfSubject.setTeacherId(UUID.fromString(resultSet.getString("teacher_id")));
            }

            return Optional.ofNullable(teacherOfSubject);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}
