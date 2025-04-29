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
import java.util.Optional;
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

    @DeleteMapping("/{login}")
    public ResponseDto<Void> deleteUser(@PathVariable String login) throws SQLException, ConstraintViolationException {
        Validator.notNull(login);

        ResponseDto<User> userDto = facade.findUserByLogin(login);
        Optional<User> userOpt = userDto.getResult();

        if (userOpt.isPresent()) {
            return facade.deleteUser(userOpt.get());
        }

        return new ResponseDto<>(Optional.empty(), new ErrorDto("User not found"));
    }


    @GetMapping("/{id}")
    public ResponseDto<User> findUserById(@PathVariable UUID id) throws SQLException {
         return facade.findUserById(id);
    }

    @GetMapping("/{login}")
    public ResponseDto<User> findUserByLogin(@PathVariable String login) throws SQLException {
        return facade.findUserByLogin(login);
    }

    @PatchMapping("/{login}")
    public ResponseDto<Void> updateUser(@PathVariable String login) throws SQLException, ConstraintViolationException {
        if (findUserByLogin(login).getResult().isPresent()) {
            Validator.notNull(login);
            return facade.updateUser(login);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("User not found"));
    }
}

