package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Lesson;
import server.utils.exception.internalerror.DatabaseOperationException;

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

    public Lesson save(Lesson lesson) {
        insertLesson(lesson);
        return lesson;
    }

    public void insertLesson(Lesson lesson) {
        String query = "INSERT INTO lesson VALUES (?,?,?,?,?)";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setObject(1, lesson.getId());
            statement.setObject(2, lesson.getClassId());
            statement.setObject(3, lesson.getTeacherOfSubjectId());
            statement.setObject(4, lesson.getDate());
            statement.setObject(5, lesson.getSubjectId());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationException("Failed to insert lesson");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to insert lesson", e);
        }
    }

    public Optional<Lesson> findById(UUID id) {
        String query = "SELECT * FROM lesson WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(UUID.fromString(resultSet.getString("id")));
                    lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                    lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                    lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                    lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));

                    return Optional.of(lesson);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find lesson", e);
        }
    }

    public Optional<Lesson> findByClassId(UUID classId) {
        String query = "SELECT * FROM lesson WHERE class_id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, classId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(UUID.fromString(resultSet.getString("id")));
                    lesson.setClassId(UUID.fromString(resultSet.getString("class_id")));
                    lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                    lesson.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                    lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));

                    return Optional.of(lesson);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find lesson", e);
        }
    }

    public List<Lesson> findBySubjectId(UUID subjectId) {
        String query = "SELECT * FROM lesson WHERE subject_id = ?";
        List<Lesson> lessons = new ArrayList<>();

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

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
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find lessons", e);
        }
    }


    public List<Lesson> findAllByDate(LocalDateTime localDateTime) {
        String query = "SELECT * FROM lesson WHERE date = ?";
        List<Lesson> lessons = new ArrayList<>();

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, localDateTime);

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

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find lessons for date: " + localDateTime, e);
        }
    }


    public void deleteById(UUID id) {
        String query = "DELETE FROM lesson WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new DatabaseOperationException("Failed to delete lesson");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete lesson", e);
        }
    }

    public boolean doesLessonExist(UUID id) {
        String query = "SELECT COUNT(*) FROM lesson WHERE id <> ? AND date = ? AND subject_id = ? AND teacher_of_subject_id = ? AND class_id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Lesson lesson = findById(id).orElseThrow(() -> new DatabaseOperationException("Lesson with id " + id + " does not exist."));

            preparedStatement.setObject(1, lesson.getDate());
            preparedStatement.setObject(2, lesson.getSubjectId());
            preparedStatement.setObject(3, lesson.getTeacherOfSubjectId());
            preparedStatement.setObject(4, lesson.getClassId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return preparedStatement.executeQuery().getInt(1) > 0;
            }
            return  false;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to find lesson with id: " + id, e);
        }
    }
}
