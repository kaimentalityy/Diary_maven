package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import server.data.entity.Role;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.request.UpdateUserRqDto;
import server.presentation.dto.response.UserRespDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    @Mapping(target = "name", source = "dto.name")
    User toUser(CreateUserRqDto dto, Role role, SchoolClass schoolClass);

    /*@Mapping(target = "id", ignore = true)
    @Mapping(source = "roleId", target = "role")
    @Mapping(source = "classId", target = "schoolClass")
    User toUpdateUser(UpdateUserRqDto dto);*/

    @Mapping(target = "role", source = "role.id")
    @Mapping(target = "classId", source = "schoolClass.id")
    UserRespDto toUserRespDto(User user);
}