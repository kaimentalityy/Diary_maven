package server.data.repository;

import org.springframework.stereotype.Repository;
import server.db.ConnectionPool;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.utils.exception.badrequest.InvalidNumberExceptionCustom;
import server.utils.exception.badrequest.InvalidRequestExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.GradeCustomNotFoundException;

import java.sql.*;
import java.util.*;

@Repository
public class GradesRepository {

    private final ConnectionPool connectionPool;

    public GradesRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Grades saveGrade(Grades grades) throws SQLException {
        giveGrade(grades);
        return grades;
    }

    public Grades update(Grades grades) throws SQLException {
        updateGrade(grades);
        return grades;
    }

    public void giveGrade(Grades grades) throws SQLException {
        String query = "INSERT INTO grades VALUES (?,?,?,?)";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, grades.getId());
            statement.setObject(2, grades.getPupilId());
            statement.setObject(3, grades.getLessonId());
            statement.setObject(4, grades.getGrade());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationExceptionCustom("Unable to insert grades with id " + grades.getId());
            }
        }
    }

    public Double calculateAverageGradeOfSubject(UUID pupilId, List<Lesson> lessons) throws SQLException {
        List<String> grades = getAllGradesOfPupil(pupilId, lessons);

        if (grades.isEmpty()) {
            throw new IllegalStateException("No grades found for pupil " + pupilId);
        }

        double total = 0.0;

        for (String grade : grades) {
            try {
                total += Double.parseDouble(grade);
            } catch (NumberFormatException e) {
                throw new InvalidNumberExceptionCustom("Invalid grade format: '" + grade + "' for pupil " + pupilId);
            }
        }

        return total / grades.size();
    }

    public List<String> getAllGradesOfPupil(UUID id, List<Lesson> lessons) throws SQLException {
        if (lessons.isEmpty()) {
            throw new InvalidRequestExceptionCustom("Lessons list cannot be empty when fetching grades.");
        }

        List<String> grades = new ArrayList<>();
        String query = "SELECT grade FROM grades WHERE pupil_id = ? AND lesson_id IN (" + getPlaceholders(lessons.size()) + ")";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            int index = 2;
            for (Lesson lesson : lessons) {
                preparedStatement.setObject(index++, lesson.getId());
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    grades.add(resultSet.getString("grade"));
                }
            }
        }

        return grades;
    }

    private String getPlaceholders(int size) {
        return String.join(",", Collections.nCopies(size, "?"));
    }

    public void removeGrade(UUID id) throws SQLException {
        String query = "DELETE FROM grades WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new GradeCustomNotFoundException("No grade found to remove with id " + id);
            }
        }
    }

    public Optional<Grades> findGradeById(UUID id) throws SQLException {
        String query = "SELECT * FROM grades WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Grades grades = new Grades();
                    grades.setId(UUID.fromString(resultSet.getString("id")));
                    grades.setPupilId(UUID.fromString(resultSet.getString("pupil_id")));
                    grades.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));
                    grades.setGrade(resultSet.getString("grade"));
                    return Optional.of(grades);
                }
            }

            return Optional.empty();
        }
    }

    public void updateGrade(Grades grades) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<String> allowedColumns = new ArrayList<>();
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
        String query = "UPDATE grades SET " + column + " = ? WHERE id = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, grades.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationExceptionCustom("Unable to update grades with id " + grades.getId());
            }
        }
    }

    public boolean doesGradesExist(UUID id) throws SQLException {
        String query = "SELECT COUNT(*) FROM grades WHERE id <> ? AND pupil_id = ? AND lesson_id = ? AND grade = ?";

        Grades grades = findGradeById(id).orElseThrow(() -> new GradeCustomNotFoundException(id));

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, grades.getPupilId());
            preparedStatement.setObject(3, grades.getLessonId());
            preparedStatement.setObject(4, grades.getGrade());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }
}
