package server.business.service;

import org.springframework.stereotype.Service;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.data.entity.User;
import server.data.repository.GradesRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GradesService {
    private final GradesRepository gradesRepository;

    public GradesService() throws SQLException {
        this.gradesRepository = new GradesRepository();
    }

    public Grades giveGrade(Grades grades) throws SQLException {
        grades.setId(UUID.randomUUID());
        return gradesRepository.saveGrade(grades);
    }

    public Optional<Grades> findGradeById(UUID id) throws SQLException {
        return gradesRepository.findGradeById(id);
    }

    public void removeGrade(Grades grades) throws SQLException {
        gradesRepository.removeGrade(grades.getId());
    }

    public Double calculateAverageGrade(User user, List<Lesson> lesson) throws SQLException {
        return gradesRepository.calculateAverageGradeOfSubject(user, lesson);
    }

    public void updateGrade(UUID id) throws SQLException {
        gradesRepository.updateGrade(id);
    }

    public List<String> getAllGradesOfPupil(User user, List<Lesson> lessons) throws SQLException {
        return gradesRepository.getAllGradesOfPupil(user, lessons);
    }
}
