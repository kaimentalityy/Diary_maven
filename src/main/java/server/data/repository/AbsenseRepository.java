package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Absense;
import server.utils.exception.internalerror.DatabaseOperationException;
import server.utils.exception.notfound.AbsenseNotFoundException;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AbsenseRepository {

    private final ConnectionPool connectionPool;

    public AbsenseRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Absense insertAttendance(Absense absense) {
        String query = "INSERT INTO attendance VALUES (?,?,?,?,?)";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, absense.getLessonId());
            statement.setObject(2, absense.getId());
            statement.setObject(3, absense.getPupilId());
            statement.setBoolean(4, true);
            statement.setObject(5, absense.getDate());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationException("Failed to insert absence record.");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error inserting absence record.", e);
        }

        return absense;
    }

    public void updateAttendance(UUID id, boolean isPresent) {
        String query = "UPDATE attendance SET is_present = ? WHERE id = ?";

        Absense absence = findAttendanceById(id).orElseThrow(
                () -> new AbsenseNotFoundException("No attendance record found with ID: " + id)
        );

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, isPresent);
            preparedStatement.setObject(2, absence.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationException("Failed to update absence for ID: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating attendance.", e);
        }
    }

    public boolean checkAttendance(UUID id) {
        String query = "SELECT is_present FROM attendance WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("is_absent");
                }
                throw new AbsenseNotFoundException("No attendance record found with ID: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error checking attendance status.", e);
        }
    }

    public Optional<Absense> findAttendanceById(UUID id) {
        String query = "SELECT * FROM attendance WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Absense absense = new Absense();
                    absense.setId(UUID.fromString(resultSet.getString("id")));
                    absense.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));
                    absense.setPupilId(UUID.fromString(resultSet.getString("pupil_id")));
                    absense.setIsPresent(resultSet.getBoolean("is_absent"));
                    return Optional.of(absense);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error finding attendance by ID.", e);
        }
    }

    public Double calculateAttendance(UUID pupilId) {
        String query = "SELECT is_present FROM attendance WHERE pupil_id = ?";
        int totalDays = 0;
        int daysPresent = 0;

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, pupilId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    totalDays++;
                    boolean isAbsent = resultSet.getBoolean("is_absent");
                    if (!isAbsent) {
                        daysPresent++;
                    }
                }
            }

            return totalDays > 0 ? (double) daysPresent / totalDays * 100 : 0.0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error calculating attendance.", e);
        }
    }

    public boolean doesAbsenceExist(UUID id) {
        String query = "SELECT COUNT(*) FROM attendance WHERE lesson_id = ? AND pupil_id = ? AND is_present = ? AND date = ? AND id <> ?";

        try (Connection connection = connectionPool.connectToDataBase();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Absense absence = findAttendanceById(id).orElseThrow(()  -> new AbsenseNotFoundException("No attendance record found with ID: " + id));

            preparedStatement.setObject(1, absence.getLessonId());
            preparedStatement.setObject(2, absence.getPupilId());
            preparedStatement.setObject(3, absence.getIsPresent());
            preparedStatement.setDate(4, absence.getDate());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error checking attendance status.", e);
        }
    }
}
