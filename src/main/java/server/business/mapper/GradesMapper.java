package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.Grades;
import server.presentation.dto.request.GradeRqDto;
import server.presentation.dto.request.UpdateGradeRqDto;
import server.presentation.dto.response.GradeRespDto;

@Mapper(componentModel = "spring")
public interface GradesMapper {

    GradeRespDto toGradeRespDto(Grades grades);

    Grades toGrade(GradeRqDto gradeRqDto);

    Grades toGradeForUpdate(UpdateGradeRqDto updateGradeRqDto);
}
