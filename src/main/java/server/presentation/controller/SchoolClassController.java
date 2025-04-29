package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schoolClasses")
public class SchoolClassController {

    private final MainFacade facade;

    @PostMapping
    public ResponseDto<SchoolClassRespDto> createSchoolClass(@RequestBody SchoolClassRqDto schoolClassRqDto) throws SQLException, ConstraintViolationException {
        Validator.notNull(schoolClassRqDto);
        return facade.createSchoolClass(schoolClassRqDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteSchoolClass(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        ResponseDto<SchoolClass> schoolClassResponse = facade.findSchoolClassById(id);

        if (schoolClassResponse.getResult().isPresent()) {
            SchoolClass schoolClass = schoolClassResponse.getResult().get();
            return facade.deleteSchoolClass(schoolClass);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("School class not found"));
    }

    @GetMapping("/{id}")
    public ResponseDto<SchoolClass> findSchoolClassById(@PathVariable UUID id) throws SQLException {
        return facade.findSchoolClassById(id);
    }

    @GetMapping("/{id}/pupils")
    public ResponseDto<List<User>> findAllPupilsOfClass(@PathVariable UUID id) throws SQLException {
        ResponseDto<SchoolClass> schoolClassResponse = facade.findSchoolClassById(id);

        if (schoolClassResponse.getResult().isPresent()) {
            SchoolClass schoolClass = schoolClassResponse.getResult().get();
            List<User> pupils = facade.findAllPupilsOfClass(schoolClass);
            return new ResponseDto<>(Optional.of(pupils), null);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("School class not found"));
    }
}