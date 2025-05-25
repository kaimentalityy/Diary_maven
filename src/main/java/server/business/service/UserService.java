package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.User;
import server.data.repository.UserRepository;
import server.utils.exception.conflict.UserAlreadyExistsExceptionCustom;
import server.utils.exception.internalerror.DatabaseOperationExceptionCustom;
import server.utils.exception.notfound.UserCustomNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByLogin(String login) {
        try {
            return userRepository.findUserByLogin(login).orElseThrow(() -> new UserCustomNotFoundException("User not found"));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to fetch user by login: " + login);
        }
    }

    public User save(User user) {
        user.setId(UUID.randomUUID());
        try {
            if (userRepository.doesUserExist(user.getId())) {
                return userRepository.save(user);
            } else {
                throw new UserAlreadyExistsExceptionCustom("User already exists");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to save user: " + user.getLogin());
        }
    }

    public User findUserByID(UUID id) {
        try {
            return userRepository.findUserById(id).orElseThrow(() -> new UserCustomNotFoundException(id));
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to fetch user by ID: " + id);
        }
    }

    public void delete(UUID id) {
        try {
            if (userRepository.doesUserExist(id)) {
                userRepository.deleteUser(id);
            } else {
                throw new UserCustomNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to delete user with ID: " + id);
        }
    }

    public User update(User user) {
        try {
            if (userRepository.doesUserExist(user.getId())) {
                return userRepository.update(user);
            } else {
                throw new UserCustomNotFoundException(user.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to update user with ID: " + user.getId());
        }
    }

    public List<User> findPupilsOfClass(UUID id) {
        try {
            return userRepository.getAllPupilsOfClass(id);
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to retrieve pupils of class: " + id);
        }
    }

    public void updateClassOfStudent(UUID id, UUID classId) {
        try {
            if (userRepository.findUserById(id).isPresent()) {
                userRepository.updateClassOfStudent(classId, id);
            } else {
                throw new UserCustomNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationExceptionCustom("Failed to update class for user: " + id);
        }
    }
}
