package server.business.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import server.business.mapper.*;
import server.business.service.*;
import server.data.entity.*;
import server.presentation.dto.request.*;
import server.presentation.dto.response.*;
import server.utils.exception.internalerror.DatabaseOperationException;
import server.utils.exception.notfound.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
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

    public CreateUserRespDto createUser(CreateUserRqDto createUserRqDto) {
        User user = userMapper.toUser(createUserRqDto);

        if (doesUserExist(user.getId())) {
            user = userService.save(user);
        }

        return userMapper.toCreateUserRespDto(user);
    }

    public AbsenseRespDto insertAttendance(AbsenseRqDto absenseRqDto) {
        Absense absense = absenseMapper.toAttendance(absenseRqDto);

        if (doesAbsenseExist(absense.getId())) {
            absense = absenseService.insertAbsence(absense);
        }


        return absenseMapper.toAttendanceRespDto(absense);
    }

    public WeekScheduleRespDto addLessonWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) {
        WeekSchedule weekSchedule = weekScheduleMapper.toWeekSchedule(weekScheduleRqDto);

        if (doesWeekScheduleExist(weekSchedule.getId())) {
            weekSchedule = weekScheduleService.insert(weekSchedule);
        }

        return weekScheduleMapper.toWeekScheduleRespDto(weekSchedule);
    }

    public GradeRespDto giveGrade(GradeRqDto gradeRqDto) {
        Grades grades = gradesMapper.toGrade(gradeRqDto);

        if (doesGradesExist(grades.getId())) {
            grades = gradesService.giveGrade(grades);
        }

        return gradesMapper.toGradeRespDto(grades);
    }

    public LessonRespDto assignLesson(LessonRqDto lessonRqDto) {
        Lesson lesson = lessonMapper.toLesson(lessonRqDto);

        if (doesLessonExist(lesson.getId())) {
            lesson = lessonService.addLesson(lesson);
        }

        return lessonMapper.toLessonRespDto(lesson);
    }

    public SubjectRespDto createSubject(SubjectRqDto subjectRqDto) {
        Subject subject = subjectMapper.toSubject(subjectRqDto);

        if (doesSubjectExist(subject.getId())) {
            subject = subjectService.createSubject(subject);

        }
        return subjectMapper.toSubjectRespDto(subject);
    }

    public SchoolClassRespDto createSchoolClass(SchoolClassRqDto schoolClassRqDto) {
        SchoolClass schoolClass = schoolClassMapper.toSchoolClass(schoolClassRqDto);

        if (doesSchoolClassExist(schoolClass.getId())) {
            schoolClass = schoolClassService.createClass(schoolClass);

        }
        return schoolClassMapper.toSchoolClassRespDto(schoolClass);
    }

    public TeacherRespDto addTeacher(TeacherRqDto teacherRqDto) {
        TeacherOfSubject teacherOfSubject = teacherMapper.toTeacher(teacherRqDto);

        if (doesTeacherExist(teacherOfSubject.getId())) {
            teacherOfSubject = teacherOfSubjectService.addTeacher(teacherOfSubject);

        }
        return teacherMapper.toTeacherRespDto(teacherOfSubject);
    }

    public User findUserById(UUID id) {
        return userService.findUserByID(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public Grades findGradeById(UUID id) {
        return gradesService.findGradeById(id).orElseThrow(() -> new GradeNotFoundException(id));
    }

    public SchoolClass findSchoolClassById(UUID id) {
        return schoolClassService.findClassById(id).orElseThrow(() -> new SchoolClassNotFoundException(id));
    }

    public Lesson findLessonById(UUID id) {
        return lessonService.findByLessonId(id).orElseThrow(() -> new LessonNotFoundException(id));
    }

    public WeekSchedule findWeekScheduleById(UUID id) {
        return weekScheduleService.findLessonById(id).orElseThrow(() -> new WeekScheduleNotFoundException(id));
    }

    public Subject findSubjectById(UUID id) {
        return subjectService.findSubjectById(id).orElseThrow(() -> new SubjectNotFoundException(id));
    }

    public TeacherOfSubject findTeacherById(UUID id) {
        return teacherOfSubjectService.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
    }

    public Subject findSubjectByName(String name) {
        return subjectService.findSubjectByName(name).orElseThrow(() -> new SubjectNotFoundException(name));
    }

    public List<Lesson> findBySubjectsId(UUID subjectId) {
        return lessonService.findBySubjectsId(subjectId);
    }

    public List<String> findAllGradesOfPupil(UUID id, UUID subjectId) {
        return gradesService.getAllGradesOfPupil(id, findBySubjectsId(subjectId));
    }

    public List<Subject> findAllSubjects() {
        return subjectService.findAllSubjects();
    }

    public List<Lesson> findAllLessonsInADay(DayOfWeek dayOfWeek, UUID classId) {
        return weekScheduleService.findAllLessonsInADay(dayOfWeek, classId);
    }

    public List<User> findAllPupilsOfClass(UUID id) {
        return userService.findPupilsOfClass(id);
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) throws SQLException {
        return lessonService.findAllLessonsByDate(localDateTime);
    }

    public Absense findAttendance(UUID id) {
        return absenseService.findAttendanceById(id).orElseThrow(() -> new AbsenseNotFoundException(id));
    }

    public Double calculateAverageGrade(UUID id, UUID subjectId) {
        return gradesService.calculateAverageGrade(id, findBySubjectsId(subjectId));
    }

    public double calculateAttendancePercent(UUID id, UUID classId) {
        double totalHours = weekScheduleService.countTotalHours(classId);
        double attendance = absenseService.calculateAttendance(id);

        if (totalHours == 0) {
            throw new DatabaseOperationException("No hours recorded for class: " + classId);
        }

        return (attendance / totalHours) * 100;
    }

    public User findUserByLogin(String login) {
        return userService.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    public DayOfWeek findDayOfWeekById(int value) {
        return DayOfWeek.getByValue(value).orElseThrow(() -> new DayOfWeekNotFoundException(value));
    }

    public void updateUser(UUID id) {
        if (userService.findUserByID(id).isPresent()) {
            userService.update(id);
        }
    }

    public void updateAttendance(UUID id, Boolean isAbsent) {
        if (absenseService.findAttendanceById(id).isPresent()) {
            absenseService.updateAttendance(id, isAbsent);
        }
    }

    public void updateTeacher(UUID id) {
        if (teacherOfSubjectService.findById(id).isPresent()) {
            teacherOfSubjectService.updateTeacher(id);
        }
    }

    public void updateGrade(UUID id) {
        if (gradesService.findGradeById(id).isPresent()) {
            gradesService.updateGrade(id);
        }
    }

    public void deleteUser(UUID id) {
        if (userService.findUserByID(id).isPresent()) {
            userService.delete(id);
        }
    }

    public void removeGrade(UUID id) {
        if (gradesService.findGradeById(id).isPresent()) {
            gradesService.removeGrade(id);
        }
    }

    public void deleteSchoolClass(UUID id) {
        if (schoolClassService.findClassById(id).isPresent()) {
            schoolClassService.deleteClass(id);
        }
    }

    public void removeLesson(UUID id) {
        if (lessonService.findByLessonId(id).isPresent()) {
            lessonService.deleteLesson(id);
        }
    }

    public void removeLessonFromSchedule(WeekSchedule weekSchedule) {
        if (lessonService.findByLessonId(weekSchedule.getLessonId()).isPresent()) {
            weekScheduleService.delete(weekSchedule);
        }
    }

    public void deleteSubject(UUID id) {
        if (subjectService.findSubjectById(id).isPresent()) {
            subjectService.deleteSubject(id);
        }
    }

    public void deleteTeacher(UUID id) {
        if (teacherOfSubjectService.findById(id).isPresent()) {
            teacherOfSubjectService.deleteTeacher(id);
        }
    }

    public Boolean checkAttendance(UUID id) {
        if (absenseService.findAttendanceById(id).isPresent()) {
            return absenseService.checkAttendance(id);
        }
        return false;
    }

    public boolean doesUserExist(UUID id) {
        return userService.doesUserExist(id);
    }

    public boolean doesAbsenseExist(UUID id) {
        return absenseService.doesAttendanceExist(id);
    }

    public boolean doesGradesExist(UUID id) {
        return gradesService.doesGradesExist(id);
    }

    public boolean doesLessonExist(UUID id) {
        return lessonService.doesLessonExist(id);
    }

    public boolean doesSchoolClassExist(UUID id) {
        return schoolClassService.doesClassExist(id);
    }

    public boolean doesSubjectExist(UUID id) {
        return subjectService.doesSubjectExist(id);
    }

    public boolean doesTeacherExist(UUID id) {
        return teacherOfSubjectService.doesTeacherExist(id);
    }

    public boolean doesWeekScheduleExist(UUID id) {
        return weekScheduleService.doesWeekScheduleExist(id);
    }
}