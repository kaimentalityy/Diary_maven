package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.response.CreateUserRespDto;
import server.presentation.dto.response.ErrorDto;

import java.util.Optional;

@Component
public class UserMapper {

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
