package server.business.mapper;

import org.springframework.stereotype.Component;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.request.UpdateUserRqDto;
import server.presentation.dto.response.UserRespDto;

@Component
public class UserMapper {

    public UserRespDto toUserRespDto(User user) {
        return new UserRespDto(user.getId(), user.getLogin(), user.getName(), user.getLastname(), user.getRoleId(), user.isBlocked(), user.getClassId(), user.getAge());
    }

    public User toUser(CreateUserRqDto createUserRqDto) {
        User user = new User();
        user.setLogin(createUserRqDto.login());
        user.setPassword(createUserRqDto.password());
        user.setName(createUserRqDto.name());
        user.setLastname(createUserRqDto.lastname());
        user.setRoleId(createUserRqDto.roleId());
        user.setClassId(createUserRqDto.classId());
        user.setAge(createUserRqDto.age());
        return user;
    }

    public User toUserForUpdate(UpdateUserRqDto updateUserRqDto) {
        User user = new User();
        user.setId(updateUserRqDto.id());
        user.setLogin(updateUserRqDto.login());
        user.setPassword(updateUserRqDto.password());
        user.setName(updateUserRqDto.name());
        user.setLastname(updateUserRqDto.lastname());
        user.setRoleId(updateUserRqDto.roleId());
        user.setClassId(updateUserRqDto.classId());
        user.setAge(updateUserRqDto.age());
        return user;
    }

}
