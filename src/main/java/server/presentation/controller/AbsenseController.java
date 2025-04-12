package server.presentation.controller;

import lombok.AllArgsConstructor;
import server.business.facade.MainFacade;
import server.data.entity.Absense;
import server.data.entity.User;
import server.presentation.dto.request.AbsenseRqDto;
import server.presentation.dto.response.AbsenseRespDto;
import server.presentation.dto.response.ErrorDto;
import server.presentation.dto.response.ResponseDto;
import server.utils.Validator;
import server.utils.exception.badrequest.ConstraintViolationException;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class AbsenseController {

    private final MainFacade facade;

    public AbsenseController() throws SQLException {
        facade = new MainFacade();
    }

    public ResponseDto<AbsenseRespDto> insertAbsence(AbsenseRqDto absenseRqDto) throws SQLException, ConstraintViolationException {

        Validator.notNull(absenseRqDto);

        return facade.insertAttendance(absenseRqDto);
    }

    public ResponseDto<Void> updateAttendance(Absense absense, Boolean isAbsent) throws SQLException, ConstraintViolationException {
        if (findAttendance(absense.getId()).getResult().isPresent()) {
            Validator.notNull(absense);
            return facade.updateAttendance(absense, isAbsent);
        }
        return new ResponseDto<>(Optional.empty(), new ErrorDto("Attendance not found"));
    }

    public Boolean checkAttendance(Absense absense) throws SQLException {
        return facade.checkAttendance(absense);
    }

    public ResponseDto<Double> calculateAttendance(User user, UUID classId) throws SQLException {
        return facade.calculateAttendancePercent(user, classId);
    }

    public ResponseDto<Absense> findAttendance(UUID id) throws SQLException {
        return facade.findAttendance(id);
    }
}
