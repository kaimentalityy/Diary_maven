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

    public Absense insertAbsence(Absense absense) throws SQLException {
        absense.setId(UUID.randomUUID());
        return absenseRepository.insertAttendance(absense);
    }

    public Optional<Absense> findAttendanceById(UUID id) throws SQLException {
        return absenseRepository.findAttendanceById(id);
    }

    public void updateAttendance(Absense absense, Boolean isAbscent) throws SQLException {
        absenseRepository.updateAttendance(absense.getId(), isAbscent);
    }

    public boolean checkAttendance(Absense absense) throws SQLException {
        return absenseRepository.checkAttendance(absense);
    }

    public Double calculateAttendance(User user) throws SQLException {
        return absenseRepository.calculateAttendance(user);
    }
}
