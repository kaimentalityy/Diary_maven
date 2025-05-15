package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.utils.exception.internalerror.DatabaseOperationException;
import server.utils.exception.notfound.LessonNotFoundException;

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
public class WeekScheduleRepository {

    private final ConnectionPool connectionPool;

    public WeekScheduleRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public WeekSchedule save(WeekSchedule weekSchedule) {
        insertLessonInSchedule(weekSchedule);
        return weekSchedule;
    }

    public void insertLessonInSchedule(WeekSchedule weekSchedule) {
        String query = "INSERT INTO week_schedule VALUES (?, ?, ?, ?)";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, weekSchedule.getId());
            preparedStatement.setObject(2, weekSchedule.getWeekDayId());
            preparedStatement.setObject(3, weekSchedule.getLessonId());
            preparedStatement.setObject(4, weekSchedule.getLessonNumber());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Failed to insert lesson in schedule");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to insert lesson in schedule", e);
        }
    }

    public void unscheduleLessonFromSchedule(WeekSchedule weekSchedule) {
        String query = "DELETE FROM week_schedule WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, weekSchedule.getId());

            if (findLessonInSchedule(weekSchedule.getId()).isPresent()) {
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseOperationException("Failed to unschedule lesson from schedule");
                }
            } else {
                throw new LessonNotFoundException("Lesson not found");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to unschedule lesson from schedule", e);
        }
    }

    public Integer getLessonPlace(UUID id) {
        String query = "SELECT lesson_number FROM week_schedule WHERE id = ?";
        Integer lessonNumber = null;

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lessonNumber = resultSet.getInt("lesson_number");
            } else {
                System.out.println("Lesson " + id + " not found");
            }

            return lessonNumber;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting lesson number" + e.getMessage());
        }
    }

    public Optional<WeekSchedule> findLessonInSchedule(UUID id) {
        String query = "SELECT * FROM week_schedule WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                WeekSchedule weekSchedule = new WeekSchedule();
                weekSchedule.setId(UUID.fromString(resultSet.getString("id")));
                weekSchedule.setLessonNumber(resultSet.getInt("lesson_number"));
                weekSchedule.setWeekDayId(Integer.valueOf(resultSet.getString("day_of_week_id")));
                weekSchedule.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));

                Optional.of(weekSchedule);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error finding lesson in schedule");
        }
    }

    public Double countTotalHoursAWeek(Lesson lesson) {
        String query = "SELECT COUNT(*) FROM week_schedule WHERE lesson_id = ?";
        double totalHoursAWeek = 0.0;

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, lesson.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalHoursAWeek = resultSet.getDouble(1);
            }
            return totalHoursAWeek;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error count total hours a week");
        }
    }

    public List<Lesson> getAllLessonsInADay(DayOfWeek dayOfWeek, Lesson lesson1) {
        String query = "SELECT * FROM week_schedule WHERE lesson_id = ? AND day_of_week = ?";
        List<Lesson> lessons = new ArrayList<>();

        try {
            Connection connection = connectionPool.connectToDataBase();
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
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error while getting lessons in day of week" + e.getMessage());
        }
    }

    public boolean doesWeekScheduleExist(UUID id) {
        String query = "SELECT COUNT(*) FROM week_schedule WHERE id <> ? AND lesson_number = ? AND day_of_week = ? AND lesson_id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            WeekSchedule weekSchedule = findLessonInSchedule(id).orElseThrow(() -> new DatabaseOperationException("Lesson " + id + " not found"));
            preparedStatement.setObject(1, weekSchedule.getLessonNumber());
            preparedStatement.setObject(2, weekSchedule.getWeekDayId());
            preparedStatement.setObject(3, weekSchedule.getLessonId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting lesson number" + e.getMessage());
        }
    }
}
