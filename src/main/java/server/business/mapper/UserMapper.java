package server.business.mapper;

import org.mapstruct.*;
import server.data.entity.Role;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.presentation.dto.request.CreateUserRqDto;
import server.presentation.dto.request.UpdateUserRqDto;
import server.presentation.dto.response.UserRespDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    @Mapping(source = "roleId", target = "role", qualifiedByName = "mapRole")
    @Mapping(source = "classId", target = "schoolClass", qualifiedByName = "mapSchoolClass")
    User toUser(CreateUserRqDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "roleId", target = "role", qualifiedByName = "mapRole")
    @Mapping(source = "classId", target = "schoolClass", qualifiedByName = "mapSchoolClass")
    User toUpdateUser(UpdateUserRqDto dto);

    @Mapping(target = "role", source = "role.id")
    @Mapping(target = "classId", source = "schoolClass.id")
    UserRespDto toUserRespDto(User user);

    @Named("mapRole")
    default Role mapRole(UUID roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID cannot be null");
        }
        Role role = new Role();
        role.setId(roleId);
        return role;
    }

    @Named("mapSchoolClass")
    default SchoolClass mapSchoolClass(UUID classId) {
        if (classId == null) {
            return null;
        }
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(classId);
        return schoolClass;
    }
}