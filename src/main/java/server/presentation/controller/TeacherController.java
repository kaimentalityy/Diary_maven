package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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

@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final MainFacade facade;

    public ResponseDto<TeacherRespDto> addTeacher(TeacherRqDto teacherRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(teacherRqDto);

        return facade.addTeacher(teacherRqDto);
    }

    public ResponseDto<Void> deleteTeacher(TeacherOfSubject teacherOfSubject) throws SQLException, ConstraintViolationException {
        if (getTeacherById(teacherOfSubject.getId()).getResult().isPresent()) {
            Validator.notNull(teacherOfSubject);
            return facade.deleteTeacher(teacherOfSubject);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Teacher not found"));
    }

    public ResponseDto<Void> updateTeacher(TeacherOfSubject teacherOfSubject) throws SQLException, ConstraintViolationException {
        if (getTeacherById(teacherOfSubject.getId()).getResult().isPresent()) {
            Validator.notNull(teacherOfSubject);
            return facade.updateTeacher(teacherOfSubject.getId());
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Teacher not found"));
    }

    public ResponseDto<TeacherOfSubject> getTeacherById(UUID id) throws SQLException {
        return facade.findTeacherById(id);
    }


}
