package server.business.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.WeekScheduleMapper;
import server.data.entity.Schedule;
import server.data.enums.DayOfWeek;
import server.data.entity.Lesson;
import server.data.repository.ScheduleRepository;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.WeekScheduleRespDto;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.WeekScheduleCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WeekScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LessonService lessonService;
    private final WeekScheduleMapper weekScheduleMapper;

    public Schedule insertLessonInSchedule(Schedule schedule) {
        if (scheduleRepository.existsByLessonAndDayOfWeekAndLessonNumber(schedule.getLesson(), schedule.getDayOfWeek(), schedule.getLessonNumber())) {
            throw new DatabaseOperationExceptionCustom("Schedule already exists.");
        }
        return scheduleRepository.save(schedule);
    }

    public WeekScheduleRespDto addLessonWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) {

        Lesson lesson = lessonService.findById(weekScheduleRqDto.lessonId());
        DayOfWeek day = DayOfWeek.getById(weekScheduleRqDto.dayOfWeek());
        Schedule schedule = weekScheduleMapper.toWeekSchedule(weekScheduleRqDto, day, lesson);

        schedule = insertLessonInSchedule(schedule);

        return weekScheduleMapper.toWeekScheduleRespDto(schedule);
    }

    public void unscheduleLessonFromSchedule(UUID id) {
        if (!scheduleRepository.existsById(id)) {
            throw new WeekScheduleCustomNotFoundException("Lesson not found");
        }
        scheduleRepository.deleteById(id);
    }

    public Integer getLessonPlace(UUID id) {
        return scheduleRepository.findLessonNumberById(id)
                .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson not found"));
    }

    public Double countTotalHoursAWeek(UUID lessonId) {
        return (double) scheduleRepository.countTotalLessonsForWeek(lessonId);
    }

    public List<Lesson> getAllLessonsInADay(int dayOfWeekValue, UUID lessonId) {
        DayOfWeek dayOfWeek = DayOfWeek.getById(dayOfWeekValue);

        Lesson lesson = lessonService.findById(lessonId);

        List<Schedule> entries = scheduleRepository.findByDayOfWeekAndLesson(dayOfWeek, lesson);

        return entries.stream()
                .map(Schedule::getLesson)
                .toList();
    }
}
