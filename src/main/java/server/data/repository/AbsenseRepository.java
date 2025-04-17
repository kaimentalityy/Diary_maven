package server.data.repository;

import server.db.ConnectionPool;
import server.data.entity.Absense;
import server.data.entity.User;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class AbsenseRepository {

    private final ConnectionPool connectionPool;

    public AbsenseRepository() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
    }

    public Absense insertAttendance(Absense absense) throws SQLException {
        insertAbsence(absense);
        return absense;
    }

    public void insertAbsence(Absense absense) throws SQLException {
        String query = "INSERT INTO attendance VALUES (?,?,?,?,?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setObject(1, absense.getLessonId());
            statement.setObject(2, absense.getId());
            statement.setObject(3, absense.getPupilId());
            statement.setBoolean(4, true);
            statement.setObject(5, absense.getDate());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inserted " + rowsInserted + " absense");
            } else {
                System.out.println("Error inserting absense");
            }
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void updateAttendance(UUID id, boolean isAbsent) throws SQLException {
        String query = "UPDATE attendance SET is_abscent = ? WHERE id = ?";
        Connection connection = null;

        Optional<Absense> absenceOpt = findAttendanceById(id);
        if (!absenceOpt.isPresent()) {
            throw new IllegalArgumentException("No attendance record found with ID: " + id);
        }

        Absense absence = absenceOpt.get();

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBoolean(1, isAbsent);
            preparedStatement.setObject(2, absence.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Absence successfully updated.");
            } else {
                System.out.println("Failed to update absence.");
            }
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public boolean checkAttendance(Absense absense) throws SQLException {
        String query = "SELECT is_abscent FROM attendance WHERE id = ?";
        boolean result = false;
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, absense.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.getBoolean("is_abscent")) {
                    result = true;
                }
            }
            return result;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<Absense> findAttendanceById(UUID id) throws SQLException {
        String query = "SELECT * FROM attendance WHERE id = ?";
        Connection connection = null;
        Absense absense = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    absense = new Absense();
                    absense.setId(UUID.fromString(resultSet.getString("id")));
                    absense.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));
                    absense.setPupilId(UUID.fromString(resultSet.getString("pupil_id")));
                    absense.setAbsence(resultSet.getBoolean("is_absent"));
                } else {
                    System.out.println("No attendance found with id: " + id);
                }
            }
            return Optional.ofNullable(absense);

        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Double calculateAttendance(User user) throws SQLException {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or user ID cannot be null");
        }

        String query = "SELECT is_abscent FROM attendance WHERE pupil_id = ?";
        Connection connection = null;
        int totalDays = 0;
        int daysPresent = 0;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, user.getId());

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
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

    }
}
