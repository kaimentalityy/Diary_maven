package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.request.UpdateUserRqDto;
import server.presentation.dto.response.UserRespDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRespDto toUserRespDto(User user);

    User toUser(CreateUserRqDto createUserRqDto);

    User toUserForUpdate(UpdateUserRqDto updateUserRqDto);
}
