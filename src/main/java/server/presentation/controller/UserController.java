package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.business.facade.MainFacade;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.response.CreateUserRespDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final MainFacade facade;

    @PostMapping
    public ResponseDto<CreateUserRespDto> createAccount(@RequestBody CreateUserRqDto createUserRqDto) throws ConstraintViolationException, SQLException {
        Validator.notNull(createUserRqDto.login());
        Validator.length(createUserRqDto.login(), 0, 20);

        return facade.createUser(createUserRqDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<User> findUserById(@PathVariable UUID id) throws SQLException {
        return facade.findUserById(id);
    }

    @GetMapping("/login/{login}")
    public ResponseDto<User> findUserByLogin(@PathVariable String login) throws SQLException {
        return facade.findUserByLogin(login);
    }

    @PatchMapping("/{login}")
    public ResponseDto<Void> updateUser(@PathVariable String login) throws SQLException, ConstraintViolationException {
        ResponseDto<User> userResponse = findUserByLogin(login);

        if (userResponse.getResult() != null) {
            Validator.notNull(login);
            return facade.updateUser(login);
        } else {
            return new ResponseDto<>(null, new ErrorDto("User not found"));
        }
    }
}
