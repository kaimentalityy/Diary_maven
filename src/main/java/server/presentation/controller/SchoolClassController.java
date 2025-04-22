package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import server.business.facade.MainFacade;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.presentation.dto.response.SchoolClassRespDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SchoolClassController {

    private final MainFacade facade;

    public ResponseDto<SchoolClassRespDto> createSchoolClass(SchoolClassRqDto schoolClassRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(schoolClassRqDto);

        return facade.createSchoolClass(schoolClassRqDto);
    }

    public ResponseDto<Void> deleteSchoolClass(SchoolClass schoolClass) throws SQLException, ConstraintViolationException {
        if (findSchoolClassById(schoolClass.getId()).getResult().isPresent()) {
            Validator.notNull(schoolClass);
            return facade.deleteSchoolClass(schoolClass);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("School class not found"));
    }

    public ResponseDto<SchoolClass> findSchoolClassById(UUID id) throws SQLException {
        return facade.findSchoolClassById(id);
    }

    public List<User> findAllPupilsOfClass(SchoolClass schoolClass) throws SQLException {
        return facade.findAllPupilsOfClass(schoolClass);
    }
}
