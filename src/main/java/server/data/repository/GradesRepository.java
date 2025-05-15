package server.data.repository;

import org.springframework.stereotype.Repository;
import server.data.entity.Absense;
import server.db.ConnectionPool;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.utils.exception.badrequest.BadRequestException;
import server.utils.exception.badrequest.InvalidNumberException;
import server.utils.exception.badrequest.InvalidRequestException;
import server.utils.exception.internalerror.DatabaseOperationException;
import server.utils.exception.notfound.AbsenseNotFoundException;
import server.utils.exception.notfound.GradeNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class GradesRepository {

    private final ConnectionPool connectionPool;

    public GradesRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Grades saveGrade(Grades grades) {
        giveGrade(grades);
        return grades;
    }

    public void giveGrade(Grades grades) {
        String query = "INSERT INTO grades VALUES (?,?,?,?)";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setObject(1, grades.getId());
            statement.setObject(2, grades.getPupilId());
            statement.setObject(3, grades.getLessonId());
            statement.setObject(4, grades.getGrade());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationException("Unable to insert grades with id " + grades.getId());
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error inserting grades: ", e);
        }
    }

    public Double calculateAverageGradeOfSubject(UUID pupilId, List<Lesson> lessons) {
        List<String> grades;
        grades = getAllGradesOfPupil(pupilId, lessons);

        if (grades.isEmpty()) {
            throw new IllegalStateException("No grades found for pupil " + pupilId);
        }

        double total = 0.0;

        for (String grade : grades) {
            try {
                total += Double.parseDouble(grade);
            } catch (NumberFormatException e) {
                throw new InvalidNumberException("Invalid grade format: '" + grade + "' for pupil " + pupilId);
            }
        }

        return total / grades.size();
    }



    public List<String> getAllGradesOfPupil(UUID id, List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            throw new InvalidRequestException("Lessons list cannot be empty when fetching grades.");
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

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting grades for pupil: " + id, e);
        }

        return grades;
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


    public void removeGrade(UUID id) {
        String query = "DELETE FROM grades WHERE id = ?";
        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DatabaseOperationException("Unable to insert grades with id " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error removing grades: ", e);
        }
    }

    public Optional<Grades> findGradeById(UUID id) {
        String query = "SELECT * FROM grades WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Grades grades = new Grades();
                    grades = new Grades();
                    grades.setId(UUID.fromString(resultSet.getString("id")));
                    grades.setPupilId(UUID.fromString(resultSet.getString("pupil_id")));
                    grades.setLessonId(UUID.fromString(resultSet.getString("lesson_id")));
                    grades.setGrade(resultSet.getString("grade"));

                    return Optional.of(grades);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error getting grade: ", e);
        }
    }

    public void updateGrade(UUID id) {
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
        Grades grades = findGradeById(id).orElseThrow(() -> new GradeNotFoundException(id));

        String query = "UPDATE grades SET " + column + " = ? WHERE id = ?";

        try {
            Connection connection = connectionPool.connectToDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, value);
            preparedStatement.setObject(2, grades.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DatabaseOperationException("Unable to update grades with id " + grades.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating grade: ", e);
        }
    }

    public boolean doesGradesExist(UUID id) {
        String query = "SELECT COUNT(*) FROM grades WHERE id <> ? AND pupil_id = ? AND lesson_id = ? AND grade = ?";

        try (Connection connection = connectionPool.connectToDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Grades grades = findGradeById(id).orElseThrow(() -> new GradeNotFoundException(id));

            preparedStatement.setObject(2, grades.getPupilId());
            preparedStatement.setObject(1, grades.getLessonId());
            preparedStatement.setObject(3, grades.getGrade());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error checking grade status.", e);
        }
    }
}

