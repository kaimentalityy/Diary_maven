package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import server.data.entity.TeacherProfile;
import server.data.entity.User;
import server.presentation.dto.request.CreateTeacherProfileRqDto;
import server.presentation.dto.request.UpdateTeacherProfileRqDto;
import server.presentation.dto.response.TeacherProfileRespDto;

@Mapper(componentModel = "spring")
public interface TeacherProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(source = "dto.firstName", target = "firstName")
    @Mapping(source = "dto.lastName", target = "lastName")
    @Mapping(source = "dto.hireDate", target = "hireDate")
    TeacherProfile toEntity(CreateTeacherProfileRqDto dto, User user);

    @Mapping(source = "id", target = "id")
    TeacherProfileRespDto toDto(TeacherProfile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(UpdateTeacherProfileRqDto dto, @MappingTarget TeacherProfile entity);
}