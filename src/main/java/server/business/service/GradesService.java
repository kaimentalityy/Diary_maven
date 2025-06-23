package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.data.repository.GradesRepository;
import server.utils.exception.badrequest.InvalidNumberExceptionCustom;
import server.utils.exception.badrequest.InvalidRequestExceptionCustom;
import server.utils.exception.conflict.GradeAlreadyExistsExceptionCustom;
import server.utils.exception.notfound.GradeCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GradesService {
    private final GradesRepository gradesRepository;

    public Grades giveGrade(Grades grades) {
        if (gradesRepository.doesGradesExist(grades.getId(), grades.getPupilId(), grades.getLessonId(), grades.getGrade())) {
            throw new GradeAlreadyExistsExceptionCustom("Duplicate grade for pupil and lesson.");
        }
        return gradesRepository.save(grades);
    }

    public Grades findGradeById(UUID id) {
        return gradesRepository.findById(id).orElseThrow(() -> new GradeCustomNotFoundException(id));
    }

    public void removeGrade(UUID id) {
        if (findGradeById(id) == null) {
            throw new GradeCustomNotFoundException(id);
        } else {
            gradesRepository.deleteById(id);
        }
    }

    public Double calculateAverageGrade(UUID pupilId, List<Lesson> lessons) {
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


    public Grades updateGrade(UUID id, String column, String value) {
        Grades grade = gradesRepository.findById(id)
                .orElseThrow(() -> new GradeCustomNotFoundException("Grade not found: " + id));

        switch (column) {
            case "pupil_id" -> grade.setPupilId(UUID.fromString(value));
            case "lesson_id" -> grade.setLessonId(UUID.fromString(value));
            case "grade" -> grade.setGrade(value);
            default -> throw new InvalidRequestExceptionCustom("Invalid column: " + column);
        }

        return gradesRepository.save(grade);
    }

    public List<String> getAllGradesOfPupil(UUID pupilId, List<Lesson> lessons) {
        List<UUID> lessonIds = lessons.stream()
                .map(Lesson::getId)
                .toList();

        return gradesRepository.getAllGradesOfPupil(pupilId, lessonIds);
    }

}


