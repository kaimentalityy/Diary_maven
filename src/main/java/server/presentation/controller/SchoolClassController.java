package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.presentation.dto.request.SchoolClassRqDto;
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
    public ResponseEntity<SchoolClassRespDto> createSchoolClass(@RequestBody SchoolClassRqDto schoolClassRqDto) throws SQLException, ConstraintViolationException {
        Validator.notNull(schoolClassRqDto);
        SchoolClassRespDto schoolClassRespDto = facade.createSchoolClass(schoolClassRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolClassRespDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolClass(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        Validator.notNull(id);
        facade.deleteSchoolClass(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> findSchoolClassById(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        Validator.notNull(id);
        SchoolClass schoolClass = facade.findSchoolClassById(id);
        return ResponseEntity.ok(schoolClass);
    }

    @GetMapping("/{id}/pupils")
    public ResponseEntity<List<User>> findAllPupilsOfClass(@PathVariable UUID id) throws SQLException, ConstraintViolationException {
        Validator.notNull(id);
        List<User> users = facade.findAllPupilsOfClass(id);
        return ResponseEntity.ok(users);
    }
}