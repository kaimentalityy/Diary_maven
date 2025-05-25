package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.WeekScheduleCustomNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class WeekScheduleRepository {

    private final ConnectionPool connectionPool;

    public WeekScheduleRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public WeekSchedule save(WeekSchedule weekSchedule) throws SQLException {
        insertLessonInSchedule(weekSchedule);
        return weekSchedule;
    }

    public void insertLessonInSchedule(WeekSchedule weekSchedule) throws SQLException {
        String query = "INSERT INTO week_schedule VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, weekSchedule.getId());
            preparedStatement.setObject(2, weekSchedule.getWeekDayId());
            preparedStatement.setObject(3, weekSchedule.getLessonId());
            preparedStatement.setObject(4, weekSchedule.getLessonNumber());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to insert lesson in schedule");
            }
        }
    }

    public void unscheduleLessonFromSchedule(WeekSchedule weekSchedule) throws SQLException {
        String query = "DELETE FROM week_schedule WHERE id = ?";

        if (findLessonInSchedule(weekSchedule.getId()).isPresent()) {
            try (Connection connection = connectionPool.connectToDataBase();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setObject(1, weekSchedule.getId());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseOperationExceptionCustom("Failed to unschedule lesson from schedule");
                }
            }
        } else {
            throw new DatabaseOperationExceptionCustom("Lesson not found");
        }
    }

    public Integer getLessonPlace(UUID id) throws SQLException {
        String query = "SELECT lesson_number FROM week_schedule WHERE id = ?";
        Integer lessonNumber = null;

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lessonNumber = resultSet.getInt("lesson_number");
            }

            return lessonNumber;
        }
    }

    public Optional<WeekSchedule> findLessonInSchedule(UUID id) throws SQLException {
        String query = "SELECT * FROM week_schedule WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                WeekSchedule weekSchedule = new WeekSchedule();
                weekSchedule.setId(UUID.fromString(resultSet.getString("id")));
                weekSchedule.setLessonNumber(resultSet.getInt("lesson_number"));
                weekSchedule.setWeekDayId(resultSet.getInt("day_of_week_id"));
                weekSchedule.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));

                return Optional.of(weekSchedule);
            }

            return Optional.empty();
        }
    }

    public Double countTotalHoursAWeek(Lesson lesson) throws SQLException {
        String query = "SELECT COUNT(*) FROM week_schedule WHERE lesson_id = ?";
        double totalHoursAWeek = 0.0;

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, lesson.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalHoursAWeek = resultSet.getDouble(1);
            }

            return totalHoursAWeek;
        }
    }

    public List<Lesson> getAllLessonsInADay(DayOfWeek dayOfWeek, Lesson lesson1) throws SQLException {
        String query = "SELECT * FROM week_schedule WHERE lesson_id = ? AND day_of_week = ?";
        List<Lesson> lessons = new ArrayList<>();

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, lesson1.getId());
            preparedStatement.setObject(2, dayOfWeek.getValue());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(UUID.fromString(resultSet.getString("id")));
                lesson.setTeacherOfSubjectId(UUID.fromString(resultSet.getString("teacher_of_subject_id")));
                lesson.setClassId(lesson1.getClassId());
                lesson.setDate(LocalDateTime.parse(resultSet.getString("date")));
                lesson.setSubjectId(UUID.fromString(resultSet.getString("subject_id")));
                lessons.add(lesson);
            }
            return lessons;
        }
    }

    public boolean doesWeekScheduleExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) FROM week_schedule WHERE id <> ? AND lesson_number = ? AND day_of_week = ? AND lesson_id = ?";

        WeekSchedule weekSchedule = findLessonInSchedule(id)
                .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson " + id + " not found"));

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, weekSchedule.getLessonNumber());
            preparedStatement.setObject(3, weekSchedule.getWeekDayId());
            preparedStatement.setObject(4, weekSchedule.getLessonId());

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}
