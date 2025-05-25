package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Absense;
import server.data.repository.AbsenseRepository;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.AbsenseCustomNotFoundException;

import java.sql.SQLException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbsenseService {
    private final AbsenseRepository absenseRepository;

    public Absense insertAbsence(Absense absense) {
        absense.setId(UUID.randomUUID());
        try {
            return absenseRepository.insert(absense);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to insert attendance");
        }
    }

    public Absense findAttendanceById(UUID id) {
        try {
            return absenseRepository.findAttendanceById(id)
                    .orElseThrow(() -> new AbsenseCustomNotFoundException("Absense not found with ID: " + id));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to find attendance with ID: " + id);
        }
    }

    public Absense updateAttendance(Absense absense) {
        try {
            if (absenseRepository.doesAbsenceExist(absense.getId())) {
                return absenseRepository.update(absense);
            } else {
                throw new AbsenseCustomNotFoundException("Absense not found with ID: " + absense.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to update attendance for ID: " + absense.getId());
        }
    }

    public boolean checkAttendance(UUID id) {
        try {
            if (absenseRepository.doesAbsenceExist(id)) {
                return absenseRepository.checkAttendance(id);
            } else {
                throw new AbsenseCustomNotFoundException("Absense not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to check attendance for ID: " + id);
        }
    }

    public Double calculateAttendance(UUID id) {
        try {
            return absenseRepository.calculateAttendance(id);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to calculate attendance for user: " + id);
        }
    }
}

