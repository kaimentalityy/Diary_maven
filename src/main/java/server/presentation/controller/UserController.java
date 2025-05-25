package server.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.request.UpdateUserRqDto;
import server.presentation.dto.response.UserRespDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final MainFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserRespDto createAccount(@Valid @RequestBody CreateUserRqDto createUserRqDto) {
        return facade.createUser(createUserRqDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/assign/{userId}/to/{classId}")
    public void assignPupilToClass(@PathVariable UUID userId, @PathVariable UUID classId) {
        facade.assignPupilToClass(classId, userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable UUID id) {
        facade.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public UserRespDto updateUser(@Valid @RequestBody UpdateUserRqDto updateUserRqDto) {
        return facade.updateUser(updateUserRqDto);
    }
}

