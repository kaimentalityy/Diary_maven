package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Absense;
import server.data.entity.User;
import server.data.repository.AbsenseRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbsenseService {
    private final AbsenseRepository absenseRepository;

    public Absense insertAbsence(Absense absense) {
        absense.setId(UUID.randomUUID());
        return absenseRepository.insertAttendance(absense);
    }

    public Optional<Absense> findAttendanceById(UUID id) {
        return absenseRepository.findAttendanceById(id);
    }

    public void updateAttendance(UUID id, Boolean isAbscent) {
        absenseRepository.updateAttendance(id, isAbscent);
    }

    public boolean checkAttendance(UUID id) {
        return absenseRepository.checkAttendance(id);
    }

    public Double calculateAttendance(UUID id) {
        return absenseRepository.calculateAttendance(id);
    }

    public boolean doesAttendanceExist(UUID id) {
        return absenseRepository.doesAbsenceExist(id);
    }
}
