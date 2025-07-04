package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.User;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.data.repository.GradesRepository;
import server.data.repository.LessonRepository;
import server.data.repository.UserRepository;
import server.utils.exception.badrequest.InvalidNumberExceptionCustom;
import server.utils.exception.badrequest.InvalidRequestExceptionCustom;
import server.utils.exception.conflict.GradeAlreadyExistsExceptionCustom;
import server.utils.exception.notfound.GradeCustomNotFoundException;
import server.utils.exception.notfound.UserCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GradesService {
    private final GradesRepository gradesRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public Grades giveGrade(Grades grades) {
        if (gradesRepository.existsByPupilAndLessonAndGradeAndIdNot(grades.getPupil(), grades.getLesson(), grades.getGrade(), grades.getId())) {
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

        User pupil = userRepository.findById(pupilId).orElseThrow(() -> new UserCustomNotFoundException(pupilId));

        List<String> grades = getAllGradesOfPupil(pupil, lessons);

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
            case "pupilId" -> {
                User pupil = userRepository.findById(UUID.fromString(value))
                        .orElseThrow(() -> new GradeCustomNotFoundException("Pupil not found: " + value));
                grade.setPupil(pupil);
            }
            case "lessonId" -> {
                Lesson lesson = lessonRepository.findById(UUID.fromString(value))
                        .orElseThrow(() -> new GradeCustomNotFoundException("Lesson not found: " + value));
                grade.setLesson(lesson);
            }
            case "grade" -> grade.setGrade(value);
            default -> throw new InvalidRequestExceptionCustom("Invalid column: " + column);
        }

        return gradesRepository.save(grade);
    }


    public List<String> getAllGradesOfPupil(User pupil, List<Lesson> lessons) {
        List<UUID> lessonIds = lessons.stream()
                .map(Lesson::getId)
                .toList();

        return gradesRepository.findByPupilAndLesson_IdIn(pupil, lessonIds)
                .stream()
                .map(Grades::getGrade)
                .toList();
    }


}


