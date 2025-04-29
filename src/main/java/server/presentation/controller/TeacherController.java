package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.TeacherOfSubject;
import server.presentation.dto.request.TeacherRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.TeacherRespDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    private final MainFacade facade;

    @PostMapping
    public ResponseDto<TeacherRespDto> addTeacher(@RequestBody TeacherRqDto teacherRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(teacherRqDto);

        return facade.addTeacher(teacherRqDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteTeacher(@PathVariable UUID id) throws SQLException, ConstraintViolationException {

        ResponseDto<TeacherOfSubject> teacherOfSubject = facade.findTeacherById(id);

        if (getTeacherById(teacherOfSubject.getResult().orElse(null).getId()).getResult().isPresent()) {
            Validator.notNull(teacherOfSubject);
            return facade.deleteTeacher(teacherOfSubject.getResult().get());
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Teacher not found"));
    }

    @PatchMapping("/{id}")
    public ResponseDto<Void> updateTeacher(@PathVariable UUID id) throws SQLException, ConstraintViolationException {

        ResponseDto<TeacherOfSubject> teacherOfSubject = facade.findTeacherById(id);

        if (getTeacherById(teacherOfSubject.getResult().orElse(null).getId()).getResult().isPresent()) {
            Validator.notNull(teacherOfSubject);
            return facade.updateTeacher(teacherOfSubject.getResult().orElse(null).getId());
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Teacher not found"));
    }

    @GetMapping("/{id}")
    public ResponseDto<TeacherOfSubject> getTeacherById(@PathVariable UUID id) throws SQLException {
        return facade.findTeacherById(id);
    }
}
