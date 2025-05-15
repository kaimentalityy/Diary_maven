package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Lesson;
import server.data.entity.WeekSchedule;
import server.presentation.dto.request.WeekScheduleRqDto;
import server.presentation.dto.response.WeekScheduleRespDto;
import server.utils.Validator;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/week-schedules")
public class WeekScheduleController {

    private final MainFacade facade;

    @PostMapping
    public ResponseEntity<WeekScheduleRespDto> addWeekSchedule(@RequestBody WeekScheduleRqDto weekScheduleRqDto) {
        Validator.notNull(weekScheduleRqDto);
        WeekScheduleRespDto response = facade.addLessonWeekSchedule(weekScheduleRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeLessonFromSchedule(@PathVariable UUID id) {
        Validator.notNull(id);
        WeekSchedule schedule = facade.findWeekScheduleById(id);
        facade.removeLessonFromSchedule(schedule);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day-lessons")
    public ResponseEntity<List<Lesson>> findAllLessonsInADay(@RequestParam int dayOfWeekId, @RequestParam UUID schoolClassId) {
        Validator.notNull(dayOfWeekId);
        Validator.notNull(schoolClassId);
        List<Lesson> lessons = facade.findAllLessonsInADay(facade.findDayOfWeekById(dayOfWeekId), schoolClassId);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeekSchedule> findWeekScheduleById(@PathVariable UUID id) {
        Validator.notNull(id);
        WeekSchedule schedule = facade.findWeekScheduleById(id);
        return ResponseEntity.ok(schedule);
    }
}

