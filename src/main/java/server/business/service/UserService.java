package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.SchoolClass;
import server.data.entity.User;
import server.data.repository.SchoolClassRepository;
import server.data.repository.UserRepository;
import server.utils.exception.notfound.SchoolClassCustomNotFoundException;
import server.utils.exception.notfound.UserCustomNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserCustomNotFoundException("User not found"));
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
        user.setRole(incomingUser.getRole());
        user.setSchoolClass(incomingUser.getSchoolClass());
        user.setAge(incomingUser.getAge());

        return userRepository.save(user);
    }

    public List<User> findPupilsOfClass(UUID id) {
        return userRepository.findBySchoolClassId(id);
    }

    public void updateClassOfStudent(UUID classId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserCustomNotFoundException(userId));

        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new SchoolClassCustomNotFoundException(classId));

        user.setSchoolClass(schoolClass);
        userRepository.save(user);
    }

}
