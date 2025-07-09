package server.business.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import server.business.mapper.WeekScheduleMapper;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.data.repository.WeekScheduleRepository;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.WeekScheduleRespDto;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.WeekScheduleCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WeekScheduleService {

    private final WeekScheduleRepository weekScheduleRepository;
    private final LessonService lessonService;
    private final DayOfWeekService dayOfWeekService;
    private final WeekScheduleMapper weekScheduleMapper;

    public WeekSchedule insertLessonInSchedule(WeekSchedule schedule) {
        if (weekScheduleRepository.existsByLessonAndDayOfWeekAndLessonNumber(schedule.getLesson(), schedule.getDayOfWeek(), schedule.getLessonNumber())) {
            throw new DatabaseOperationExceptionCustom("Schedule already exists.");
        }
        return weekScheduleRepository.save(schedule);
    }

    public WeekScheduleRespDto addLessonWeekSchedule(WeekScheduleRqDto weekScheduleRqDto) {

        Lesson lesson = lessonService.findById(weekScheduleRqDto.lessonId());
        DayOfWeek day = dayOfWeekService.findDayOfWeekById(weekScheduleRqDto.dayOfWeek());
        WeekSchedule weekSchedule = weekScheduleMapper.toWeekSchedule(weekScheduleRqDto, day, lesson);

        weekSchedule = insertLessonInSchedule(weekSchedule);

        return weekScheduleMapper.toWeekScheduleRespDto(weekSchedule);
    }

    public void unscheduleLessonFromSchedule(UUID id) {
        if (!weekScheduleRepository.existsById(id)) {
            throw new WeekScheduleCustomNotFoundException("Lesson not found");
        }
        weekScheduleRepository.deleteById(id);
    }

    public Integer getLessonPlace(UUID id) {
        return weekScheduleRepository.findLessonNumberById(id)
                .orElseThrow(() -> new WeekScheduleCustomNotFoundException("Lesson not found"));
    }

    public Double countTotalHoursAWeek(UUID lessonId) {
        return (double) weekScheduleRepository.countTotalLessonsForWeek(lessonId);
    }

    public List<Lesson> getAllLessonsInADay(int dayOfWeekValue, UUID lessonId) {
        DayOfWeek dayOfWeek = dayOfWeekService.findDayOfWeekById(dayOfWeekValue);

        Lesson lesson = lessonService.findById(lessonId);

        List<WeekSchedule> entries = weekScheduleRepository.findByDayOfWeekAndLesson(dayOfWeek, lesson);

        return entries.stream()
                .map(WeekSchedule::getLesson)
                .toList();
    }
}
