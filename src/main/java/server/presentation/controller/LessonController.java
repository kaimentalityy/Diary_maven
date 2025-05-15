package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.Lesson;
import server.presentation.dto.request.LessonRqDto;
import server.presentation.dto.response.LessonRespDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final MainFacade facade;

    @PostMapping
    public ResponseEntity<LessonRespDto> createLesson(@RequestBody LessonRqDto lessonRqDto) throws SQLException, ConstraintViolationException {
        Validator.notNull(lessonRqDto);
        LessonRespDto lessonRespDto = facade.assignLesson(lessonRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonRespDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        Validator.notNull(id);
        facade.removeLesson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{localDateTime}")
    public ResponseEntity<List<Lesson>> findAllLessonsByDate(@PathVariable LocalDateTime localDateTime) throws SQLException, ConstraintViolationException {
        Validator.notNull(localDateTime);
        facade.findAllLessonsByDate(localDateTime);
        List<Lesson> lessons = facade.findAllLessonsByDate(localDateTime);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> findLessonById(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        Validator.notNull(id);
        Lesson lesson = facade.findLessonById(id);
        return ResponseEntity.ok(lesson);
    }
}
