package server.business.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.*;
import server.business.service.*;
import server.data.entity.*;
import server.data.enums.DayOfWeek;
import server.data.enums.Subject;
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
    private final AbsenseService absenseService;
    private final SchoolClassService schoolClassService;
    private final WeekScheduleService weekScheduleService;
    private final TeacherOfSubjectService teacherOfSubjectService;

    private final GradesMapper gradesMapper;
    private final AbsenseMapper absenseMapper;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final TeacherMapper teacherMapper;


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

    public TeacherOfSubject findTeacherById(UUID id) {
        return teacherOfSubjectService.findById(id);
    }

    public List<Lesson> findBySubjectsId(Subject subject) {
        return lessonService.findBySubjectsId(subject);
    }

    public List<String> findAllGradesOfPupil(UUID id, Subject subject) {

        User user = userService.findUserByID(id);

        return gradesService.getAllGradesOfPupil(user, findBySubjectsId(subject));
    }

    public List<Lesson> findAllLessonsInADay(Integer dayOfWeekId, UUID classId) {
        return weekScheduleService.getAllLessonsInADay(dayOfWeekId, classId);
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

    public Double calculateAverageGrade(UUID id, Subject subject) {
        return gradesService.calculateAverageGrade(id, findBySubjectsId(subject));
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

    public AbsenseRespDto updateAttendance(UpdateAbsenseRqDto updateAbsenseRqDto) {

        User user = userService.findUserByID(updateAbsenseRqDto.pupilId());

        Lesson lesson = findLessonById(updateAbsenseRqDto.lessonId());

        Attendance attendance = absenseService.updateAttendance(absenseMapper.toAttendanceForUpdate(updateAbsenseRqDto, lesson, user));

        return absenseMapper.toAttendanceRespDto(attendance);
    }

    public UserRespDto updateUser(UpdateUserRqDto updateUserRqDto) {

        Role role = roleService.findById(updateUserRqDto.roleId());

        SchoolClass schoolClass = schoolClassService.findClassById(updateUserRqDto.classId());

        User updatedUser = userService.update(userMapper.toUpdateUser(updateUserRqDto, role, schoolClass));

        return userMapper.toUserRespDto(updatedUser);
    }

    public GradeRespDto updateGrade(UpdateGradeRqDto dto) {
        Grades updatedGrade = gradesService.updateGrade(dto.id(), dto.column(),  dto.value());
        return gradesMapper.toGradeRespDto(updatedGrade);
    }

    //TODO remake this method (fix the rq and place in mapper)


    public TeacherRespDto updateTeacher(UpdateTeacherRqDto updateTeacherRqDto) {

        User user = userService.findUserByID(updateTeacherRqDto.teacherId());
        Subject subject = Subject.getById(updateTeacherRqDto.subjectId());
        TeacherOfSubject updatedTeacher = teacherOfSubjectService.updateTeacher(teacherMapper.toTeacherForUpdate(updateTeacherRqDto, subject, user));

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

    public void deleteTeacher(UUID id) {
        teacherOfSubjectService.deleteTeacher(id);
    }

    public CheckAttendanceRespDto checkAttendance(UUID id) {
        Attendance updateAttendance = absenseService.checkAttendance(id);
        return absenseMapper.toCheckAttendanceRespDto(updateAttendance);
    }

}
