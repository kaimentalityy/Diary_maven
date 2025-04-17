package server.data.repository;

import server.db.ConnectionPool;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.data.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GradesRepository {

    private final ConnectionPool connectionPool;

    public GradesRepository() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
    }

    public Grades saveGrade(Grades grades) throws SQLException {
        giveGrade(grades);
        return grades;
    }

    public void giveGrade(Grades grades) throws SQLException {
        String query = "INSERT INTO grades VALUES (?,?,?,?)";
        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setObject(1, grades.getId());
            statement.setObject(2, grades.getPupilId());
            statement.setObject(3, grades.getLessonId());
            statement.setObject(4, grades.getGrade());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inserted " + rowsInserted + " grades");
                connectionPool.releaseConnection(connection);
            } else {
                System.out.println("Error inserting grades");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Double calculateAverageGradeOfSubject(User pupil, List<Lesson> lesson) throws SQLException {
        List<String> grades = getAllGradesOfPupil(pupil, lesson);
        double totalGrade = 0.0;
        for (String grade : grades) {
            totalGrade += Double.parseDouble(grade);
        }
        return totalGrade / grades.size();
    }

    public List<String> getAllGradesOfPupil(User user, List<Lesson> lessons) throws SQLException {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or user ID cannot be null");
        }

        if (lessons == null || lessons.isEmpty()) {
            throw new IllegalArgumentException("Lessons list cannot be null or empty");
        }

        List<String> grades = new ArrayList<>();
        String query = "SELECT grade FROM grades WHERE pupil_id = ? AND lesson_id IN (" + getPlaceholders(lessons.size()) + ")";

        Connection connection = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, user.getId());

            int index = 2;
            for (Lesson lesson : lessons) {
                if (lesson == null || lesson.getId() == null) {
                    throw new IllegalArgumentException("Lesson or lesson ID cannot be null");
                }
                preparedStatement.setObject(index++, lesson.getId());
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String gradeValue = resultSet.getString("grade");
                    grades.add(gradeValue);
                }
            }

            return grades;
        } catch (SQLException e) {
            System.out.println("Error retrieving grades: " + e.getMessage());
            throw e;
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    private String getPlaceholders(int size) {
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < size; i++) {
            placeholders.append("?");
            if (i < size - 1) {
                placeholders.append(",");
            }
        }
        return placeholders.toString();
    }


    public void removeGrade(UUID id) throws SQLException {
        String query = "DELETE FROM grades WHERE id = ?";
        Connection connection = null;
        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (id != null) {
                preparedStatement.setObject(1, id);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Removed " + rowsInserted + " grades");
                    connectionPool.releaseConnection(connection);
                } else {
                    System.out.println("Error removing grades");
                }
            } else {
                System.out.println("Grade ID is null");
            }
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public Optional<Grades> findGradeById(UUID id) throws SQLException {
        String query = "SELECT * FROM grades WHERE id = ?";
        Connection connection = null;
        Grades grades = null;

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (id == null) {
                throw new IllegalArgumentException("ID cannot be null");
            }

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    grades = new Grades();
                    grades.setId(UUID.fromString(resultSet.getString("id")));
                    grades.setPupilId(UUID.fromString(resultSet.getString("pupil_id")));
                    grades.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));
                    grades.setGrade(resultSet.getString("grade"));
                }
            }
            return Optional.ofNullable(grades);
        }
        finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    public void updateGrade(UUID id) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> allowedColumns = new ArrayList<>();
        Connection connection = null;
        allowedColumns.add("pupil_id");
        allowedColumns.add("lesson_id");
        allowedColumns.add("grade");

        System.out.println("Please enter the column you want to update (pupil_id, lesson_id, grade): ");
        String column = scanner.nextLine();

        if (!allowedColumns.contains(column)) {
            System.out.println("Invalid column name. Allowed columns are: " + allowedColumns);
        }

        System.out.println("Please enter the updated value: ");
        String value = scanner.nextLine();
        Grades grades = findGradeById(id).orElse(null);
        if (grades != null && grades.getId() == null) {
            System.out.println("Grade not found with id: " + id);
        }

        String query = "UPDATE grades SET " + column + " = ? WHERE id = ?";

        try {
            connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, value);
            if (grades != null) {
                preparedStatement.setObject(2, grades.getId());
            }

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Grade successfully updated.");
            } else {
                System.out.println("Failed to update grade.");
            }
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}

