package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.User;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.SchoolClassRespDto;
import server.utils.exception.badrequest.ConstraintViolationExceptionCustom;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schoolClasses")
public class SchoolClassController {

    private final MainFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SchoolClassRespDto createSchoolClass(@Valid @RequestBody SchoolClassRqDto schoolClassRqDto) throws ConstraintViolationExceptionCustom {
        return facade.addSchoolClass(schoolClassRqDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteSchoolClass(@PathVariable UUID id) throws ConstraintViolationExceptionCustom {
        facade.deleteSchoolClass(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/pupils")
    public List<User> findAllPupilsOfClass(@PathVariable UUID id) throws ConstraintViolationExceptionCustom {
        return facade.findAllPupilsOfClass(id);
    }
}