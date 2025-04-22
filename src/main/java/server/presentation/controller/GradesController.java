package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import server.business.facade.MainFacade;
import server.data.entity.Grades;
import server.data.entity.User;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.GradeRespDto;
import server.presentation.dto.response.ResponseDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class GradesController {

    private final MainFacade facade;

    public ResponseDto<GradeRespDto> giveGrade(GradeRqDto gradeRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(gradeRqDto);

        return facade.giveGrade(gradeRqDto);
    }

    public ResponseDto<Void> removeGrade(Grades grades) throws SQLException, ConstraintViolationException {
        if (findGradeById(grades.getId()).getResult().isPresent()) {
            Validator.notNull(grades);
            return facade.removeGrade(grades);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Grades not found"));
    }

    public ResponseDto<Void> updateGrade(UUID id) throws SQLException, ConstraintViolationException {
        if (findGradeById(id).getResult().isPresent()) {
            Validator.notNull(id);
            return facade.updateGrade(id);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Grades not found"));
    }

    public ResponseDto<Grades> findGradeById(UUID id) throws SQLException {
        return facade.findGradeById(id);
    }

    public List<String> findAllGradesOfPupil(User user, UUID subjectId) throws SQLException {
        return facade.findAllGradesOfPupil(user, subjectId);
    }

    public ResponseDto<Double> calculateAverageGrade(User user, UUID subjectId) throws SQLException {
        return facade.calculateAverageGrade(user, subjectId);
    }

}
