package server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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

@Controller
@RequiredArgsConstructor
public class UserController {

    private final MainFacade mainFacade;

    public ResponseDto<CreateUserRespDto> createAccount(CreateUserRqDto createUserRqDto) throws ConstraintViolationException, SQLException {
        Validator.notNull(createUserRqDto.login());
        Validator.length(createUserRqDto.login(), 0, 20);

        return mainFacade.createUser(createUserRqDto);
    }

    public ResponseDto<Void> deleteUser(User user) throws SQLException, ConstraintViolationException {
        if (findUserByLogin(user.getLogin()).getResult().isPresent()) {
            Validator.notNull(user);
            return mainFacade.deleteUser(user);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("User not found"));
    }

    public ResponseDto<User> findUserById(UUID id) throws SQLException {
         return mainFacade.findUserById(id);
    }

    public ResponseDto<User> findUserByLogin(String login) throws SQLException {
        return mainFacade.findUserByLogin(login);
    }

    public ResponseDto<Void> updateUser(String login) throws SQLException, ConstraintViolationException {
        if (findUserByLogin(login).getResult().isPresent()) {
            Validator.notNull(login);
            return mainFacade.updateUser(login);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("User not found"));
    }
}

