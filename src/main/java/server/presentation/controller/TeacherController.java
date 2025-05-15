package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.TeacherOfSubject;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.response.TeacherRespDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    private final MainFacade facade;

    @PostMapping
    public ResponseEntity<TeacherRespDto> addTeacher(@RequestBody TeacherRqDto teacherRqDto) throws ConstraintViolationException {
        Validator.notNull(teacherRqDto);
        TeacherRespDto teacherRespDto = facade.addTeacher(teacherRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherRespDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable UUID id) throws ConstraintViolationException {
        Validator.notNull(id);
        facade.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTeacher(@PathVariable UUID id) throws ConstraintViolationException {
        Validator.notNull(id);
        facade.updateTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherOfSubject> getTeacherById(@PathVariable UUID id) throws ConstraintViolationException {
        Validator.notNull(id);
        facade.findTeacherById(id);
        return ResponseEntity.noContent().build();
    }
}