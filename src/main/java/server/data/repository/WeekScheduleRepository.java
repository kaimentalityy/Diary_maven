package server.data.repository;

import server.db.ConnectionPool;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WeekScheduleRepository {

    private final ConnectionPool connectionPool;

    public WeekScheduleRepository() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
    }

    public WeekSchedule save(WeekSchedule weekSchedule) throws SQLException {
        insertLessonInSchedule(weekSchedule);
        return weekSchedule;
    }

    public void insertLessonInSchedule(WeekSchedule weekSchedule) throws SQLException {
        String query = "INSERT INTO week_schedule VALUES (?, ?, ?, ?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, weekSchedule.getId());
            preparedStatement.setObject(2, weekSchedule.getWeekDayId());
            preparedStatement.setObject(3, weekSchedule.getLessonId());
            preparedStatement.setObject(4, weekSchedule.getLessonNumber());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Lesson " + weekSchedule.getLessonId() + " has been scheduled to " + weekSchedule.getWeekDayId());
                } else {
                    System.out.println("Error inserting day in schedule");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public void unscheduleLessonFromSchedule(WeekSchedule weekSchedule) throws SQLException {
        String query = "DELETE FROM week_schedule WHERE id = ?";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, weekSchedule.getId());

            if (weekSchedule.getLessonNumber() != null) {
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Lesson " + weekSchedule.getLessonId() + " has been unscheduled");
                } else {
                    System.out.println("Error unscheduling lesson");
                }
            } else {
                System.out.println("Lesson place is not occupied");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Integer getLessonPlace(UUID id) throws SQLException {
        String query = "SELECT lesson_number FROM week_schedule WHERE id = ?";
        Integer lessonNumber = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lessonNumber = resultSet.getInt("lesson_number");
            } else {
                System.out.println("Lesson " + id + " not found");
            }

            return lessonNumber;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

    }

    public Optional<WeekSchedule> findLessonInSchedule(UUID id) throws SQLException {
        String query = "SELECT * FROM week_schedule WHERE id = ?";
        WeekSchedule weekSchedule = null;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                weekSchedule = new WeekSchedule();
                weekSchedule.setId(UUID.fromString(resultSet.getString("id")));
                weekSchedule.setLessonNumber(resultSet.getInt("lesson_number"));
                weekSchedule.setWeekDayId(Integer.valueOf(resultSet.getString("day_of_week_id")));
                weekSchedule.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));
            }

            return Optional.ofNullable(weekSchedule);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Double countTotalHoursAWeek(Lesson lesson) throws SQLException {
        String query = "SELECT COUNT(*) FROM week_schedule WHERE lesson_id = ?";
        double totalHoursAWeek = 0.0;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, lesson.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalHoursAWeek = resultSet.getDouble(1);
            }
            return totalHoursAWeek;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public List<Lesson> getAllLessonsInADay(DayOfWeek dayOfWeek, Lesson lesson1) throws SQLException {
        String query = "SELECT * FROM week_schedule WHERE lesson_id = ? AND day_of_week_id = ?";
        List<Lesson> lessons = new ArrayList<Lesson>();
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);


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
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}
