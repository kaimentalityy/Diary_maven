package server.business.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import server.business.mapper.*;
import server.business.service.*;
import server.data.entity.*;
import server.presentation.dto.request.*;
import server.presentation.dto.response.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MainFacade {

    private final UserService userService;
    private final GradesService gradesService;
    private final LessonService lessonService;
    private final SubjectService subjectService;
    private final AbsenseService absenseService;
    private final SchoolClassService schoolClassService;
    private final WeekScheduleService weekScheduleService;
    private final TeacherOfSubjectService teacherOfSubjectService;

    private final UserMapper userMapper;
    private final GradesMapper gradesMapper;
    private final LessonMapper lessonMapper;
    private final SubjectMapper subjectMapper;
    private final AbsenseMapper absenseMapper;
    private final TeacherMapper teacherMapper;
    private final SchoolClassMapper schoolClassMapper;
    private final WeekScheduleMapper weekScheduleMapper;

    public ResponseDto<CreateUserRespDto> createUser(CreateUserRqDto createUserRqDto) throws SQLException {
        User user = userMapper.toUser(createUserRqDto);
        user = userService.save(user);
        CreateUserRespDto createUserRespDto = userMapper.toCreateUserRespDto(user);
        if (findUserById(user.getId()).getResult().isPresent()) {
            return new ResponseDto<>(new ErrorDto("User with login %s already exists".formatted(user)));
        }
        return userMapper.toResponseDto(createUserRespDto, null);
    }

    public ResponseDto<AbsenseRespDto> insertAttendance(AbsenseRqDto absenseRqDto) throws SQLException {
        Absense absense = absenseMapper.toAttendance(absenseRqDto);
        absense = absenseService.insertAbsence(absense);
        AbsenseRespDto absenseRespDto = absenseMapper.toAttendanceRespDto(absense);
        if (findAttendance(absense.getId()).getResult().isPresent()) {
            return new ResponseDto<>(new ErrorDto("Absense already exists"));
        }
        return absenseMapper.toResponseDto(absenseRespDto, null);
    }

    public ResponseDto<WeekScheduleRespDto> addLessonWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) throws SQLException {
        WeekSchedule weekSchedule = weekScheduleMapper.toWeekSchedule(weekScheduleRqDto);
        weekSchedule = weekScheduleService.insert(weekSchedule);
        WeekScheduleRespDto weekScheduleRespDto = weekScheduleMapper.toWeekScheduleRespDto(weekSchedule);
        if (findWeekScheduleById(weekSchedule.getId()).getResult().isPresent()) {
            return new ResponseDto<>(new ErrorDto("Lesson already exists"));
        }
        return weekScheduleMapper.toResponseDto(weekScheduleRespDto, null);
    }

    public ResponseDto<GradeRespDto> giveGrade(GradeRqDto gradeRqDto) throws SQLException {
        Grades grades = gradesMapper.toGrade(gradeRqDto);
        grades = gradesService.giveGrade(grades);
        GradeRespDto gradeRespDto = gradesMapper.toGradeRespDto(grades);
        if (findGradeById(grades.getId()).getResult().isPresent()) {
            return new ResponseDto<>(new ErrorDto("Grade already exists"));
        }
        return gradesMapper.toResponseDto(gradeRespDto, null);
    }

    public ResponseDto<LessonRespDto> assignLesson(LessonRqDto lessonRqDto) throws SQLException {
        Lesson lesson = lessonMapper.toLesson(lessonRqDto);
        lesson = lessonService.addLesson(lesson);
        LessonRespDto lessonRespDto = lessonMapper.toLessonRespDto(lesson);
        if (findLessonById(lesson.getId()).getResult().isEmpty()) {
            return new ResponseDto<>(new ErrorDto("Lesson already exists"));
        }
        return lessonMapper.toResponseDto(lessonRespDto, null);
    }

    public ResponseDto<SubjectRespDto> createSubject(SubjectRqDto subjectRqDto) throws SQLException {
        Subject subject = subjectMapper.toSubject(subjectRqDto);
        subject = subjectService.createSubject(subject);
        SubjectRespDto subjectRespDto = subjectMapper.toSubjectRespDto(subject);
        if (findSubjectById(subject.getId()).getResult().isPresent()) {
            return new ResponseDto<>(new ErrorDto("Subject already exists"));
        }
        return subjectMapper.toResponseDto(subjectRespDto, null);
    }

    public ResponseDto<SchoolClassRespDto> createSchoolClass(SchoolClassRqDto schoolClassRqDto) throws SQLException {
        SchoolClass schoolClass = schoolClassMapper.toSchoolClass(schoolClassRqDto);
        schoolClass = schoolClassService.createClass(schoolClass);
        SchoolClassRespDto schoolClassRespDto = schoolClassMapper.toSchoolClassRespDto(schoolClass);
        if (findSchoolClassById(schoolClass.getId()).getResult().isPresent()) {
            return new ResponseDto<>(new ErrorDto("SchoolClass already exists"));
        }
        return schoolClassMapper.toResponseDto(schoolClassRespDto, null);
    }

    public ResponseDto<TeacherRespDto> addTeacher(TeacherRqDto teacherRqDto) throws SQLException {
        TeacherOfSubject teacherOfSubject = teacherMapper.toTeacher(teacherRqDto);
        if (findUserById(teacherOfSubject.getId()).getResult().isPresent()) {
            return new ResponseDto<>(new ErrorDto("Teacher already exists"));
        }
        teacherOfSubject = teacherOfSubjectService.addTeacher(teacherOfSubject);
        TeacherRespDto teacherRespDto = teacherMapper.toTeacherRespDto(teacherOfSubject);
        return teacherMapper.toResponseDto(teacherRespDto, null);
    }

    public ResponseDto<User> findUserById(UUID id) throws SQLException {
        return new ResponseDto<>(userService.findUserByID(id),
                new ErrorDto("User not found with ID: " + id));
    }

    public ResponseDto<Grades> findGradeById(UUID id) throws SQLException {
        return new ResponseDto<>(gradesService.findGradeById(id),
                new ErrorDto("Grade record not found with ID: " + id));
    }

    public ResponseDto<SchoolClass> findSchoolClassById(UUID id) throws SQLException {
        return new ResponseDto<>(schoolClassService.findClassById(id),
                new ErrorDto("School class not found with ID: " + id));
    }

    public ResponseDto<Lesson> findLessonById(UUID id) throws SQLException {
        return new ResponseDto<>(lessonService.findByLessonId(id),
                new ErrorDto("Lesson not found with ID: " + id));
    }

    public ResponseDto<WeekSchedule> findWeekScheduleById(UUID id) throws SQLException {
        return new ResponseDto<>(weekScheduleService.findLessonById(id),
                new ErrorDto("Week schedule not found with ID: " + id));
    }

    public ResponseDto<Subject> findSubjectById(UUID id) throws SQLException {
        return new ResponseDto<>(subjectService.findSubjectById(id),
                new ErrorDto("Subject not found with ID: " + id));
    }

    public ResponseDto<TeacherOfSubject> findTeacherById(UUID id) throws SQLException {
        return new ResponseDto<>(teacherOfSubjectService.findById(id),
                new ErrorDto("Teacher not found with ID: " + id));
    }

    public ResponseDto<Subject> findSubjectByName(String name) throws SQLException {
        return new ResponseDto<>(subjectService.findSubjectByName(name),
                new ErrorDto("Subject not found with name: " + name));
    }

    public List<Lesson> findBySubjectsId(UUID subjectId) throws SQLException{
        return lessonService.findBySubjectsId(subjectId);
    }

    public List<String> findAllGradesOfPupil(User user, UUID subjectId) throws SQLException {
        return gradesService.getAllGradesOfPupil(user, findBySubjectsId(subjectId));
    }

    public List<Subject> findAllSubjects() throws SQLException {
        return subjectService.findAllSubjects();
    }

    public List<Lesson> findAllLessonsInADay(DayOfWeek dayOfWeek, UUID classId) throws SQLException {
        return weekScheduleService.findAllLessonsInADay(dayOfWeek, classId);
    }

    public List<User> findAllPupilsOfClass(SchoolClass schoolClass) throws SQLException {
        return userService.findPupilsOfClass(schoolClass);
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) throws SQLException {
        return lessonService.findAllLessonsByDate(localDateTime);
    }

    public ResponseDto<Absense> findAttendance(UUID id) throws SQLException {
        return new ResponseDto<>(absenseService.findAttendanceById(id),
                new ErrorDto("Attendance record not found with ID: " + id));
    }

    public ResponseDto<Double> calculateAverageGrade(User user, UUID subjectId) throws SQLException {
        return new ResponseDto<>(Optional.ofNullable(gradesService.calculateAverageGrade(user, findBySubjectsId(subjectId))),
                new ErrorDto("Unable to calculate average grade for user: " + user.getId()));
    }

    public ResponseDto<Double> calculateTotalHours(UUID classId) throws SQLException {
        return new ResponseDto<>(Optional.of(weekScheduleService.countTotalHours(classId)),
                new ErrorDto("Unable to calculate total hours for class: " + classId));
    }

    public ResponseDto<Double> calculateAttendance(User user) throws SQLException {
        return new ResponseDto<>(Optional.of(absenseService.calculateAttendance(user)),
                new ErrorDto("Unable to calculate attendance for user: " + user.getId()));
    }

    public ResponseDto<Double> calculateAttendancePercent(User user, UUID classId) throws SQLException {
        Optional<Double> totalHours = calculateTotalHours(classId).getResult();
        Optional<Double> attendance = calculateAttendance(user).getResult();

        if (totalHours.orElse(null) == 0 || totalHours.isEmpty()) {
            return new ResponseDto<>(Optional.empty(), new ErrorDto("No hours recorded for class: " + classId));
        }

        Double percentage = (attendance.orElse(null) / totalHours.orElse(null)) * 100;

        return new ResponseDto<>(Optional.of(percentage),
                new ErrorDto("Cannot calculate attendance percentage for user: " + user.getId() + " in class: " + classId));
    }

    public ResponseDto<User> findUserByLogin(String login) throws SQLException {
        return new ResponseDto<>(userService.findUserByLogin(login),
                new ErrorDto("User not found with login: " + login));
    }

    public ResponseDto<Void> updateUser(String login) throws SQLException {
        userService.update(login);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to update user with login: " + login));
    }

    public ResponseDto<Void> updateAttendance(Absense absense, Boolean isAbsent) throws SQLException {
        absenseService.updateAttendance(absense, isAbsent);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to update attendance record with ID: " + absense.getId()));
    }

    public ResponseDto<Void> updateTeacher(UUID id) throws SQLException {
        teacherOfSubjectService.updateTeacher(id);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to update teacher with ID: " + id));
    }

    public ResponseDto<Void> updateGrade(UUID id) throws SQLException {
        gradesService.updateGrade(id);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to update grade with ID: " + id));
    }

    public ResponseDto<Void> deleteUser(User user) throws SQLException {
        userService.delete(user);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to delete user with ID: " + user.getId()));
    }

    public ResponseDto<Void> removeGrade(Grades grade) throws SQLException {
        gradesService.removeGrade(grade);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to remove grade with ID: " + grade.getId()));
    }

    public ResponseDto<Void> deleteSchoolClass(SchoolClass schoolClass) throws SQLException {
        schoolClassService.deleteClass(schoolClass);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to delete school class with ID: " + schoolClass.getId()));
    }

    public ResponseDto<Void> removeLesson(Lesson lesson) throws SQLException {
        lessonService.deleteLesson(lesson.getId());
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to remove lesson with ID: " + lesson.getId()));
    }

    public ResponseDto<Void> removeLessonFromSchedule(WeekSchedule weekSchedule) throws SQLException {
        weekScheduleService.delete(weekSchedule);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to remove lesson from schedule with ID: " + weekSchedule.getId()));
    }

    public ResponseDto<Void> deleteSubject(Subject subject) throws SQLException {
        subjectService.deleteSubject(subject);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to delete subject with ID: " + subject.getId()));
    }

    public ResponseDto<Void> deleteTeacher(TeacherOfSubject teacherOfSubject) throws SQLException {
        teacherOfSubjectService.deleteTeacher(teacherOfSubject);
        return new ResponseDto<>(Optional.empty(),
                new ErrorDto("Failed to delete teacher of subject with ID: " + teacherOfSubject.getId()));
    }

    public Boolean checkAttendance(Absense absense) throws SQLException {
        return absenseService.checkAttendance(absense);
    }
}
