package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.DayOfWeek;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.data.repository.WeekScheduleRepository;
import server.utils.exception.conflict.WeekScheduleAlreadyExistsExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.LessonCustomNotFoundException;
import server.utils.exception.notfound.SchoolClassCustomNotFoundException;
import server.utils.exception.notfound.WeekScheduleCustomNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeekScheduleService {

    private final WeekScheduleRepository weekScheduleRepository;
    private final LessonService lessonService;

    public WeekSchedule insert(WeekSchedule weekSchedule) {
        weekSchedule.setId(UUID.randomUUID());
        try {
            if (weekScheduleRepository.doesWeekScheduleExist(weekSchedule.getId())) {
                return weekScheduleRepository.save(weekSchedule);
            }  else {
                throw new WeekScheduleAlreadyExistsExceptionCustom("WeekSchedule already exists");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to insert week schedule");
        }
    }

    public void delete(WeekSchedule weekSchedule) {
        try {
            if (weekScheduleRepository.doesWeekScheduleExist(weekSchedule.getId())) {
                weekScheduleRepository.unscheduleLessonFromSchedule(weekSchedule);
            } else {
                throw new WeekScheduleCustomNotFoundException("WeekSchedule not found with ID: " + weekSchedule.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to delete week schedule");
        }
    }

    public Integer getLessonPlace(UUID id) {
        try {
            if (weekScheduleRepository.doesWeekScheduleExist(id)) {
                return weekScheduleRepository.getLessonPlace(id);
            } else {
                throw new WeekScheduleCustomNotFoundException("WeekSchedule not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to get lesson place for ID: " + id);
        }
    }

    public Double countTotalHours(UUID classId) {
        try {
            Lesson lesson = lessonService.findByClassId(classId)
                    .orElseThrow(() -> new SchoolClassCustomNotFoundException(classId));
            return weekScheduleRepository.countTotalHoursAWeek(lesson);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to count total hours for class ID: " + classId);
        }
    }

    public WeekSchedule findLessonById(UUID id) {
        try {
            return weekScheduleRepository.findLessonInSchedule(id)
                    .orElseThrow(() -> new LessonCustomNotFoundException("Lesson not found in schedule with ID: " + id));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find lesson in schedule with ID: " + id);
        }
    }

    public List<Lesson> findAllLessonsInADay(DayOfWeek dayOfWeek, UUID classId) {
        try {
            Lesson lesson = lessonService.findByClassId(classId)
                    .orElseThrow(() -> new SchoolClassCustomNotFoundException(classId));
            return weekScheduleRepository.getAllLessonsInADay(dayOfWeek, lesson);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to get lessons for day " + dayOfWeek + " and class ID: " + classId);
        }
    }
}

