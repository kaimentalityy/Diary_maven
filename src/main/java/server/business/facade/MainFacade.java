package server.business.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.*;
import server.business.service.*;
import server.data.entity.*;
import server.presentation.dto.request.*;
import server.presentation.dto.response.*;
import server.utils.exception.badrequest.ConstraintViolationExceptionCustom;
import server.utils.exception.badrequest.InvalidNumberExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainFacade {

    private final UserService userService;
    private final GradesService gradesService;
    private final LessonService lessonService;
    private final SubjectService subjectService;
    private final AbsenseService absenseService;
    private final DayOfWeekService dayOfWeekService;
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

    public UserRespDto createUser(CreateUserRqDto createUserRqDto) {
        User user = userMapper.toUser(createUserRqDto);

        user = userService.save(user);

        return userMapper.toUserRespDto(user);
    }

    public AbsenseRespDto insertAttendance(AbsenseRqDto absenseRqDto) {
        Attendance attendance = absenseMapper.toAttendance(absenseRqDto);

        attendance = absenseService.insertAbsence(attendance);

        return absenseMapper.toAttendanceRespDto(attendance);
    }

    public WeekScheduleRespDto addLessonWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) {
        WeekSchedule weekSchedule = weekScheduleMapper.toWeekSchedule(weekScheduleRqDto);

        weekSchedule = weekScheduleService.insertLessonInSchedule(weekSchedule);

        return weekScheduleMapper.toWeekScheduleRespDto(weekSchedule);
    }

    public GradeRespDto giveGrade(GradeRqDto gradeRqDto) {
        Grades grades = gradesMapper.toGrade(gradeRqDto);

        grades = gradesService.giveGrade(grades);

        return gradesMapper.toGradeRespDto(grades);
    }

    public LessonRespDto assignLesson(LessonRqDto lessonRqDto) {
        Lesson lesson = lessonMapper.toLesson(lessonRqDto);

        lesson = lessonService.addLesson(lesson);

        return lessonMapper.toLessonRespDto(lesson);
    }

    public SubjectRespDto createSubject(SubjectRqDto subjectRqDto) {
        Subject subject = subjectMapper.toSubject(subjectRqDto);

        subject = subjectService.createSubject(subject);

        return subjectMapper.toSubjectRespDto(subject);
    }

    public SchoolClassRespDto createSchoolClass(SchoolClassRqDto schoolClassRqDto) {
        SchoolClass schoolClass = schoolClassMapper.toSchoolClass(schoolClassRqDto);

        schoolClass = schoolClassService.createClass(schoolClass);

        return schoolClassMapper.toSchoolClassRespDto(schoolClass);
    }

    public TeacherRespDto addTeacher(TeacherRqDto teacherRqDto) {
        TeacherOfSubject teacherOfSubject = teacherMapper.toTeacher(teacherRqDto);

        teacherOfSubject = teacherOfSubjectService.addTeacher(teacherOfSubject);

        return teacherMapper.toTeacherRespDto(teacherOfSubject);
    }

    public void assignPupilToClass(UUID userId, UUID classId) {
        User user = findUserById(userId);
        SchoolClass schoolClass = findSchoolClassById(classId);

        int minAge = getMinimumAgeForClass(classId);
        if (user.getAge() < minAge) {
            throw new ConstraintViolationExceptionCustom("Pupil must be at least " + minAge + " years old.");
        }
        if (isClassOverCapacity(schoolClass)) {
            throw new ConstraintViolationExceptionCustom("Class is over capacity");
        }

        updateClassOfStudent(classId, userId);
    }

    public boolean isClassOverCapacity(SchoolClass schoolClass) {
        return findAllPupilsOfClass(schoolClass.getId()).size() > schoolClass.getMaxCapacity();
    }

    public void updateClassOfStudent(UUID classId, UUID studentId) {
        userService.updateClassOfStudent(classId, studentId);
    }

    public Integer getMinimumAgeForClass(UUID classId) {
        String classNumber = findSchoolClassById(classId).getNumber();
        try {
            int number = Integer.parseInt(classNumber);
            return 5 + number;
        } catch (NumberFormatException e) {
            throw new InvalidNumberExceptionCustom(classNumber);
        }
    }

    public User findUserById(UUID id) {
        return userService.findUserByID(id);
    }

    public Grades findGradeById(UUID id) {
        return gradesService.findGradeById(id);
    }

    public SchoolClass findSchoolClassById(UUID id) {
        return schoolClassService.findClassById(id);
    }

    public Lesson findLessonById(UUID id) {
        return lessonService.findById(id);
    }

    public Subject findSubjectById(UUID id) {
        return subjectService.findById(id);
    }

    public TeacherOfSubject findTeacherById(UUID id) {
        return teacherOfSubjectService.findById(id);
    }

    public Subject findSubjectByName(String name) {
        return subjectService.findSubjectByName(name);
    }

    public List<Lesson> findBySubjectsId(UUID subjectId) {
        return lessonService.findBySubjectsId(subjectId);
    }

    public List<String> findAllGradesOfPupil(UUID id, UUID subjectId) {

        User user = userService.findUserByID(id);

        return gradesService.getAllGradesOfPupil(user, findBySubjectsId(subjectId));
    }

    public List<Subject> findAllSubjects() {
        return subjectService.findAllSubjects();
    }

    public List<Lesson> findAllLessonsInADay(DayOfWeek dayOfWeek, UUID classId) {
        return weekScheduleService.getAllLessonsInADay(dayOfWeek.getId(), classId);
    }

    public List<User> findAllPupilsOfClass(UUID id) {
        return userService.findPupilsOfClass(id);
    }

    public List<Lesson> findAllLessonsByDate(LocalDateTime localDateTime) {
        return lessonService.findAllLessonsByDate(localDateTime);
    }

    public Attendance findAttendance(UUID id) {
        return absenseService.findAttendanceById(id);
    }

    public Double calculateAverageGrade(UUID id, UUID subjectId) {
        return gradesService.calculateAverageGrade(id, findBySubjectsId(subjectId));
    }

    public AttendancePercentageResponse calculateAttendancePercent(AttendancePercentageRequest attendancePercentageRequest) {
        double totalHours = weekScheduleService.countTotalHoursAWeek(attendancePercentageRequest.classId());
        double attended = absenseService.calculateAttendance(attendancePercentageRequest.userId());

        if (totalHours == 0) {
            throw new DatabaseOperationExceptionCustom("No hours recorded for class: " + attendancePercentageRequest.classId());
        }

        double percentage = (attended / totalHours) * 100;
        
        return absenseMapper.toAttendancePercentageResponse(attendancePercentageRequest, percentage);
    }


    public User findUserByLogin(String login) {
        return userService.findUserByLogin(login);
    }

    public DayOfWeek findDayOfWeekById(int value) {
        return  dayOfWeekService.findDayOfWeekById(value);
    }

    public AbsenseRespDto updateAttendance(UpdateAbsenseRqDto updateAbsenseRqDto) {

        Attendance attendance = absenseService.updateAttendance(absenseMapper.toAttendanceForUpdate(updateAbsenseRqDto));

        return absenseMapper.toAttendanceRespDto(attendance);
    }

    public UserRespDto updateUser(UpdateUserRqDto updateUserRqDto) {

        User updatedUser = userService.update(userMapper.toUpdateUser(updateUserRqDto));

        return userMapper.toUserRespDto(updatedUser);
    }

    public GradeRespDto updateGrade(UpdateGradeRqDto dto) {
        Grades updatedGrade = gradesService.updateGrade(dto.id(), dto.column(),  dto.value());
        return gradesMapper.toGradeRespDto(updatedGrade);
    }

    //TODO remake this method (fix the rq and place in mapper)


    public TeacherRespDto updateTeacher(UpdateTeacherRqDto updateTeacherRqDto) {

        TeacherOfSubject updatedTeacher = teacherOfSubjectService.updateTeacher(teacherMapper.toTeacherForUpdate(updateTeacherRqDto));

        return teacherMapper.toTeacherRespDto(updatedTeacher);
    }

    public void deleteUser(UUID id) {
        userService.delete(id);
    }

    public void removeGrade(UUID id) {
        gradesService.removeGrade(id);
    }

    public void deleteSchoolClass(UUID id) {
        schoolClassService.deleteClass(id);
    }

    public void removeLesson(UUID id) {
        lessonService.deleteLesson(id);
    }

    public void removeLessonFromSchedule(UUID id) {
        weekScheduleService.unscheduleLessonFromSchedule(id);
    }

    public void deleteSubject(UUID id) {
        subjectService.deleteSubject(id);
    }

    public void deleteTeacher(UUID id) {
        teacherOfSubjectService.deleteTeacher(id);
    }

    public CheckAttendanceRespDto checkAttendance(UUID id) {
        Attendance updateAttendance = absenseService.checkAttendance(id);
        return absenseMapper.toCheckAttendanceRespDto(updateAttendance);
    }

}
