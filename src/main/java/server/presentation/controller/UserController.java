package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.response.CreateUserRespDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final MainFacade facade;

    @PostMapping
    public ResponseEntity<CreateUserRespDto> createAccount(@RequestBody CreateUserRqDto createUserRqDto) {
        CreateUserRespDto response = facade.createUser(createUserRqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        facade.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable UUID id) {
        User user = facade.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/login/{login}")
    public ResponseEntity<User> findUserByLogin(@PathVariable String login) {
        User user = facade.findUserByLogin(login);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID id) {
        facade.updateUser(id);
        return ResponseEntity.noContent().build();
    }
}

