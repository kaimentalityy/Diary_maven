package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.AbsenseMapper;
import server.data.entity.Attendance;
import server.data.entity.Lesson;
import server.data.entity.User;
import server.data.repository.AbsenseRepository;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.utils.exception.notfound.AbsenseCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbsenseService {
    private final AbsenseMapper absenseMapper;
    private final AbsenseRepository absenseRepository;
    private final UserService userService;
    private final LessonService lessonService;

    public AbsenseRespDto insertAttendance(AbsenseRqDto absenseRqDto) {

        User user = userService.findUserByID(absenseRqDto.pupilId());
        Lesson lesson = lessonService.findById(absenseRqDto.lessonId());
        Attendance attendance = absenseMapper.toAttendance(absenseRqDto, lesson, user);

        attendance = insertAbsence(attendance);

        return absenseMapper.toAttendanceRespDto(attendance);
    }

    public Attendance insertAbsence(Attendance attendance) {
        absenseRepository.save(attendance);
        return attendance;
    }

    public Attendance findAttendanceById(UUID id) {
        return absenseRepository.findById(id).orElseThrow(()->new AbsenseCustomNotFoundException(id));
    }

    public Attendance updateAttendance(Attendance attendance) {

        Attendance attendance1 = findAttendanceById(attendance.getId());

        if (absenseRepository.existsByLessonAndPupilAndPresentAndDateAndIdNot(attendance.getLesson(), attendance.getPupil(), attendance.getPresent(), attendance.getDate(), attendance.getId())) {
            throw new AbsenseCustomNotFoundException("Duplicate attendance record exists for the given parameters.");
        }

        attendance1.setPresent(attendance.getPresent());
        attendance1.setPupil(attendance.getPupil());
        attendance1.setLesson(attendance.getLesson());
        attendance1.setDate(attendance.getDate());

        return absenseRepository.save(attendance1);
    }


    public Attendance checkAttendance(UUID id) {
        return absenseRepository.findById(id).orElseThrow(() -> new AbsenseCustomNotFoundException("No attendance record found with ID: " + id));
    }

    public double calculateAttendance(UUID pupilId) {
        List<Attendance> attendanceList = absenseRepository.findByPupilId(pupilId);

        int totalDays = attendanceList.size();
        int daysPresent = 0;

        for (Attendance attendance : attendanceList) {
            Boolean isPresent = attendance.getPresent();
            if (isPresent != null && isPresent) {
                daysPresent++;
            }
        }

        return totalDays > 0 ? ((double) daysPresent / totalDays) * 100 : 0.0;
    }



}
