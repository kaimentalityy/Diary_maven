package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Absense;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.AbsenseCustomNotFoundException;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AbsenseRepository {

    private final ConnectionPool connectionPool;

    public AbsenseRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Absense insert(Absense absense) throws SQLException {
        insertAttendance(absense);
        return absense;
    }

    public Absense update(Absense absense) throws SQLException {
        updateAttendance(absense);
        return absense;
    }

    public void insertAttendance(Absense absense) throws SQLException {
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
                throw new DatabaseOperationExceptionCustom("Failed to insert absence record.");
            }
        }
    }

    public void updateAttendance(Absense absense) throws SQLException {
        String query = "UPDATE attendance SET is_present = ? WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBoolean(1, absense.getIsPresent());
            preparedStatement.setObject(2, absense.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationExceptionCustom("Failed to update absence for ID: " + absense.getId());
            }
        }
    }

    public boolean checkAttendance(UUID id) throws SQLException {
        String query = "SELECT is_present FROM attendance WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("is_present");
                } else {
                    throw new AbsenseCustomNotFoundException("No attendance record found with ID: " + id);
                }
            }
        }
    }

    public Optional<Absense> findAttendanceById(UUID id) throws SQLException {
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
                    absense.setIsPresent(resultSet.getBoolean("is_present"));
                    absense.setDate(resultSet.getDate("date"));
                    return Optional.of(absense);
                }
                return Optional.empty();
            }
        }
    }

    public Double calculateAttendance(UUID pupilId) throws SQLException {
        String query = "SELECT is_present FROM attendance WHERE pupil_id = ?";
        int totalDays = 0;
        int daysPresent = 0;

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, pupilId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    totalDays++;
                    boolean isPresent = resultSet.getBoolean("is_present");
                    if (isPresent) {
                        daysPresent++;
                    }
                }
            }

            return totalDays > 0 ? (double) daysPresent / totalDays * 100 : 0.0;
        }
    }

    public boolean doesAbsenceExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) FROM attendance WHERE lesson_id = ? AND pupil_id = ? AND is_present = ? AND date = ? AND id <> ?";

        Absense absence = findAttendanceById(id)
                .orElseThrow(() -> new AbsenseCustomNotFoundException("No attendance record found with ID: " + id));

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, absence.getLessonId());
            preparedStatement.setObject(2, absence.getPupilId());
            preparedStatement.setBoolean(3, absence.getIsPresent());
            preparedStatement.setDate(4, absence.getDate());
            preparedStatement.setObject(5, absence.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }
}

