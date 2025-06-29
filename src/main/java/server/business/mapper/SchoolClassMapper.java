package server.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.presentation.dto.request.SchoolClassRqDto;
import server.presentation.dto.response.SchoolClassRespDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "teacherId", source = "teacher.id")
    SchoolClassRespDto toSchoolClassRespDto(SchoolClass schoolClass);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", source = "teacherId", qualifiedByName = "mapUser")
    SchoolClass toSchoolClass(SchoolClassRqDto schoolClassRqDto);

    @Named("mapUser")
    default User mapUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}
