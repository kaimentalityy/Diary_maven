package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.response.CreateUserRespDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;

import java.util.Optional;

@Component
public class UserMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public CreateUserRespDto toCreateUserRespDto(User user) {
        return new CreateUserRespDto(user.getId(), user.getLogin(), user.getName(), user.getLastname(), user.getRoleId(), user.isBlocked(), user.getClassId());
    }

    public User toUser(CreateUserRqDto createUserRqDto) {
        User user = new User();
        user.setLogin(createUserRqDto.login());
        user.setPassword(createUserRqDto.password());
        user.setName(createUserRqDto.name());
        user.setLastname(createUserRqDto.lastname());
        user.setRoleId(createUserRqDto.roleId());
        user.setClassId(createUserRqDto.classId());
        return user;
    }

}
