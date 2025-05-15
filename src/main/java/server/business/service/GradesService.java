package server.business.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GradesService {
    private final GradesRepository gradesRepository;

    public Grades giveGrade(Grades grades) {
        grades.setId(UUID.randomUUID());
        return gradesRepository.saveGrade(grades);
    }

    public Optional<Grades> findGradeById(UUID id) {
        return gradesRepository.findGradeById(id);
    }

    public void removeGrade(UUID id) {
        gradesRepository.removeGrade(id);
    }

    public Double calculateAverageGrade(UUID id, List<Lesson> lesson) {
        return gradesRepository.calculateAverageGradeOfSubject(id, lesson);
    }

    public void updateGrade(UUID id) {
        gradesRepository.updateGrade(id);
    }

    public List<String> getAllGradesOfPupil(UUID id, List<Lesson> lessons) {
        return gradesRepository.getAllGradesOfPupil(id, lessons);
    }

    public boolean doesGradesExist(UUID id) {
        return gradesRepository.doesGradesExist(id);
    }
}
