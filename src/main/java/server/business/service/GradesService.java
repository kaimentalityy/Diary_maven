package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.data.repository.GradesRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.GradeCustomNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GradesService {
    private final GradesRepository gradesRepository;

    public Grades giveGrade(Grades grades) {
        grades.setId(UUID.randomUUID());
        try {
            return gradesRepository.saveGrade(grades);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to give grade to pupil: " + grades.getPupilId());
        }
    }

    public Grades findGradeById(UUID id) {
        try {
            return gradesRepository.findGradeById(id)
                    .orElseThrow(() -> new GradeCustomNotFoundException("Grade not found with ID: " + id));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find grade with ID: " + id);
        }
    }

    public void removeGrade(UUID id) {
        try {
            if (gradesRepository.doesGradesExist(id)) {
                gradesRepository.removeGrade(id);
            } else {
                throw new GradeCustomNotFoundException("Grade not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to remove grade with ID: " + id);
        }
    }

    public Double calculateAverageGrade(UUID pupilId, List<Lesson> lessons) {
        try {
            return gradesRepository.calculateAverageGradeOfSubject(pupilId, lessons);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to calculate average grade for pupil: " + pupilId);
        }
    }

    public Grades updateGrade(Grades grades) {
        try {
            if (gradesRepository.doesGradesExist(grades.getId())) {
                 return gradesRepository.update(grades);
            } else {
                throw new GradeCustomNotFoundException("Grade not found with ID: " + grades.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to update grade with ID: " + grades.getId());
        }
    }

    public List<String> getAllGradesOfPupil(UUID id, List<Lesson> lessons) {
        try {
            return gradesRepository.getAllGradesOfPupil(id, lessons);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to get grades for pupil: " + id);
        }
    }
}

