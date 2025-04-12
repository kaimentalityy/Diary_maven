package server.business.mapper;

import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.response.CreateUserRespDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;

import java.util.Optional;

public class UserMapper {

    public <T> ResponseDto<T> toResponseDto(T result, ErrorDto errorDto) {
        if (result == null) {
            return new ResponseDto<>(errorDto);
        }
        return new ResponseDto<>(Optional.of(result), errorDto);
    }

    public CreateUserRespDto toCreateUserRespDto(User user) {
        return new CreateUserRespDto(user.getId(), user.getLogin(), user.getName(), user.getLastname(), user.getRole_id(), user.isBlocked(), user.getClass_id());
    }

    public User toUser(CreateUserRqDto createUserRqDto) {
        User user = new User();
        user.setLogin(createUserRqDto.login());
        user.setPassword(createUserRqDto.password());
        user.setName(createUserRqDto.name());
        user.setLastname(createUserRqDto.lastname());
        user.setId(createUserRqDto.role());
        user.setRole_id(createUserRqDto.role());
        user.setClass_id(createUserRqDto.classId());
        return user;
    }

}
