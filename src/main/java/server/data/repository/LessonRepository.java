package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Lesson;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.LessonCustomNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

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

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, lesson.getId());
            statement.setObject(2, lesson.getClassId());
            statement.setObject(3, lesson.getTeacherOfSubjectId());
            statement.setObject(4, lesson.getDate());
            statement.setObject(5, lesson.getSubjectId());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to insert lesson");
            }
        }
    }

    public Optional<Lesson> findById(UUID id) throws SQLException {
        String query = "SELECT * FROM lesson WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(UUID.fromString(resultSet.getString("id")));
                lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                return Optional.of(lesson);
            }

            return Optional.empty();
        }
    }

    public Optional<Lesson> findByClassId(UUID classId) throws SQLException {
        String query = "SELECT * FROM lesson WHERE class_id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, classId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(UUID.fromString(resultSet.getString("id")));
                lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                return Optional.of(lesson);
            }

            return Optional.empty();
        }
    }

    public List<Lesson> findBySubjectId(UUID subjectId) throws SQLException {
        String query = "SELECT * FROM lesson WHERE subject_id = ?";
        List<Lesson> lessons = new ArrayList<>();

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, subjectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(UUID.fromString(resultSet.getString("id")));
                lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                lessons.add(lesson);
            }

            return lessons;
        }
    }

    public List<Lesson> findAllByDate(LocalDateTime localDateTime) throws SQLException {
        String query = "SELECT * FROM lesson WHERE date = ?";
        List<Lesson> lessons = new ArrayList<>();

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, localDateTime);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(UUID.fromString(resultSet.getString("id")));
                lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                lessons.add(lesson);
            }

            return lessons;
        }
    }


    public void deleteById(UUID id) throws SQLException {
        String query = "DELETE FROM lesson WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to delete lesson");
            }
        }
    }

    public boolean doesLessonExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) FROM lesson WHERE id <> ? AND date = ? AND subject_id = ? AND teacher_of_subject_id = ? AND class_id = ?";

        Lesson lesson = findById(id)
                .orElseThrow(() -> new LessonCustomNotFoundException("Lesson with id " + id + " does not exist."));

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, lesson.getDate());
            preparedStatement.setObject(3, lesson.getSubjectId());
            preparedStatement.setObject(4, lesson.getTeacherOfSubjectId());
            preparedStatement.setObject(5, lesson.getClassId());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}
