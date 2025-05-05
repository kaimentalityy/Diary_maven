package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LessonRepository {

    private final ConnectionPool connectionPool;

    public LessonRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Lesson save(Lesson lesson) throws SQLException {
        insertLesson(lesson);
        return lesson;
    }

    public void insertLesson(Lesson lesson) throws SQLException {
        String query = "INSERT INTO lesson VALUES (?,?,?,?,?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement statement = connection.prepareStatement(query);

            if (lesson.getId() != null) {
                statement.setObject(1, lesson.getId());
                statement.setObject(2, lesson.getClassId());
                statement.setObject(3, lesson.getTeacherOfSubjectId());
                statement.setObject(4, lesson.getDate());
                statement.setObject(5, lesson.getSubjectId());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Inserted " + rowsInserted + " lessons");
                } else {
                    System.out.println("Error inserting lessons");
                }
            } else {
                System.out.println("Lesson does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<Lesson> findById(UUID id) throws SQLException {
        String query = "SELECT * FROM lesson WHERE id = ?";
        Connection connection = null;
        Lesson lesson = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lesson = new Lesson();
                    lesson.setId(UUID.fromString(resultSet.getString("id")));
                    lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                    lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                    lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                    lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                } else {
                    System.out.println("No lesson found for ID: " + id);
                    return Optional.empty();
                }
            }
            return Optional.of(lesson);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<Lesson> findByClassId(UUID classId) throws SQLException {
        String query = "SELECT * FROM lesson WHERE class_id = ?";
        Lesson lesson;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (classId == null) {
                throw new IllegalArgumentException("Class_id cannot be null");
            }

            preparedStatement.setObject(1, classId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lesson = new Lesson();
                    lesson.setId(UUID.fromString(resultSet.getString("id")));
                    lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                    lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                    lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                    lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                } else {
                    System.out.println("No lesson found for ID: " + classId);
                    return Optional.empty();
                }
            }
            return Optional.of(lesson);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

    }

    public List<Lesson> findBySubjectId(UUID subjectId) throws SQLException {
        String query = "SELECT * FROM lesson WHERE subject_id = ?";
        List<Lesson> lessons = new ArrayList<>();

        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (subjectId == null) {
                throw new IllegalArgumentException("SubjectId cannot be null");
            }

            preparedStatement.setObject(1, subjectId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(UUID.fromString(resultSet.getString("id")));
                    lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                    lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                    lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                    lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                    lessons.add(lesson);
                }
            }
            return lessons;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }


    public List<Lesson> findAllByDate(LocalDateTime localDateTime) throws SQLException {
        String query = "SELECT * FROM lesson WHERE date = ?";
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (localDateTime == null) {
                throw new IllegalArgumentException("date and time cannot be null");
            }

            preparedStatement.setObject(1, localDateTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lesson = (Lesson) resultSet.getObject("date");
                    lessons.add(lesson);
                } else {
                    System.out.println("No date found at this date : " + localDateTime);
                }
            }
            return lessons;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void deleteById(UUID id) throws SQLException {
        String query = "DELETE FROM lesson WHERE id = ?";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Lesson deleted successfully: " + id);
            } else {
                System.out.println("Lesson not found: " + id);
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
