package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.TeacherOfSubject;
import server.utils.exception.badrequest.InvalidColumnNameException;
import server.utils.exception.internalerror.DatabaseOperationException;
import server.utils.exception.notfound.TeacherNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TeacherOfSubjectRepository {

    private final ConnectionPool connectionPool;

    public TeacherOfSubjectRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public TeacherOfSubject save(TeacherOfSubject teacherOfSubject) {
        insertTeacher(teacherOfSubject);
        return teacherOfSubject;
    }

    public void insertTeacher(TeacherOfSubject teacherOfSubject) {
        String insertQuery = "INSERT INTO teacher_of_subject VALUES (?, ?, ?)";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setObject(1, teacherOfSubject.getId());
            preparedStatement.setObject(2, teacherOfSubject.getSubjectId());
            preparedStatement.setObject(3, teacherOfSubject.getTeacherId());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationException("Error in inserting teacher");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in inserting teacher", e);
        }
    }

    public void deleteById(UUID id) {
        String query = "DELETE FROM teacher_of_subject WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationException("Error in deleting teacher");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error in deleting teacher", e);
        }
    }

    public void updateTeacher(UUID id) {
        Scanner scanner = new Scanner(System.in);
        List<String> allowedColumns = new ArrayList<>();
        allowedColumns.add("subject_id");
        allowedColumns.add("teacher_id");

        System.out.println("Please enter the column you want to update (subject_id, teacher_id): ");
        String column = scanner.nextLine();

        if (!allowedColumns.contains(column)) {
            throw new InvalidColumnNameException("Invalid column name. Allowed columns are: " + allowedColumns);
        }

        System.out.println("Please enter the updated value: ");
        String value = scanner.nextLine();

        try {
            TeacherOfSubject teacherOfSubject = findTeacherById(id).orElseThrow(() -> new TeacherNotFoundException(id));
            String query = "UPDATE teacher_of_subject SET " + column + " = ? WHERE id = ?";

            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, teacherOfSubject.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationException("Error in updating teacher");
            }
        } catch (SQLException e) {
            throw  new DatabaseOperationException("Failed to update teacher", e);
        }
    }

    public Optional<TeacherOfSubject> findTeacherById(UUID id) {
        String query = "SELECT * FROM teacher_of_subject WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
        } catch (SQLException e) {
            throw  new DatabaseOperationException("Failed to find teacher", e);
        }
    }

    public boolean doesTeacherExist(UUID id) {
        String query = "SELECT COUNT(*) FROM teacher_of_subject WHERE id <> ? AND subject_id = ? AND teacher_id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){

            TeacherOfSubject teacherOfSubject = findTeacherById(id).orElseThrow(() -> new TeacherNotFoundException(id));

            preparedStatement.setObject(1, teacherOfSubject.getSubjectId());
            preparedStatement.setObject(2, teacherOfSubject.getTeacherId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw  new DatabaseOperationException("Failed to find teacher", e);
        }
    }
}
