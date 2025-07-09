package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.GradesMapper;
import server.data.entity.User;
import server.data.entity.Grades;
import server.data.entity.Lesson;
import server.data.repository.GradesRepository;
import server.data.repository.LessonRepository;
import server.data.repository.UserRepository;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.response.GradeRespDto;
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
    private final UserService  userService;
    private final LessonService lessonService;
    private final GradesMapper gradesMapper;


    public GradeRespDto giveGrade(GradeRqDto gradeRqDto) {

        User user = userService.findUserByID(gradeRqDto.pupilId());
        Lesson lesson = lessonService.findById(gradeRqDto.lessonId());
        Grades grades = gradesMapper.toGrade(gradeRqDto, user, lesson);

        grades = giveGrade(grades);

        return gradesMapper.toGradeRespDto(grades);
    }

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

        User pupil = userService.findUserByID(pupilId);

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
                User pupil = userService.findUserByID(UUID.fromString(value));
                grade.setPupil(pupil);
            }
            case "lessonId" -> {
                Lesson lesson = lessonService.findById(UUID.fromString(value));
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


