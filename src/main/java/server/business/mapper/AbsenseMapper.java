package server.business.mapper;

import org.mapstruct.Mapper;
import server.data.entity.Absense;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.request.AttendancePercentageRequest;
import server.presentation.dto.request.UpdateAbsenseRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.presentation.dto.response.AttendancePercentageResponse;
import server.presentation.dto.response.CheckAttendanceRespDto;

@Mapper(componentModel = "spring")
public interface AbsenseMapper {

    AbsenseRespDto toAttendanceRespDto(Absense absense);

    CheckAttendanceRespDto toCheckAttendanceRespDto(Absense absense);

    AttendancePercentageResponse toAttendancePercentageResponse(AttendancePercentageRequest request, double percentage);

    Absense toAttendance(AbsenseRqDto absenseRqDto);

    Absense toAttendanceForUpdate(UpdateAbsenseRqDto updateAbsenseRqDto);
}
