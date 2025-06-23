package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.User;
import server.data.repository.UserRepository;
import server.utils.exception.badrequest.InvalidColumnNameExceptionCustom;
import server.utils.exception.conflict.UserAlreadyExistsExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.UserCustomNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByLogin(String login) {
        try {
            return userRepository.findByLogin(login).orElseThrow(() -> new UserCustomNotFoundException("User not found"));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to fetch user by login: " + login);
        }
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public User findUserByID(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserCustomNotFoundException(id));
    }

    public void delete(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.delete(findUserByID(id));
        } else {
            throw new UserCustomNotFoundException(id);
        }
    }

    public User update(User incomingUser) {
        User user = userRepository.findById(incomingUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(incomingUser.getName());
        user.setLastname(incomingUser.getLastname());
        user.setLogin(incomingUser.getLogin());
        user.setPassword(incomingUser.getPassword());
        user.setBlocked(incomingUser.isBlocked());
        user.setRoleId(incomingUser.getRoleId());
        user.setClassId(incomingUser.getClassId());
        user.setAge(incomingUser.getAge());

        return userRepository.save(user);
    }



    public List<User> findPupilsOfClass(UUID id) {
        return userRepository.findByClassId(id);
    }

    public void updateClassOfStudent(UUID classId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserCustomNotFoundException(userId));

        user.setClassId(classId);

        userRepository.save(user);
    }
}
