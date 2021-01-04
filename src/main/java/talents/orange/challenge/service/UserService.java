package talents.orange.challenge.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import talents.orange.challenge.model.User;
import talents.orange.challenge.repository.UserRepository;
import talents.orange.challenge.service.exception.UniqueViolationException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) throws UniqueViolationException {
        if (userRepository.existsByCpf(user.getCpf())) {
            throw new UniqueViolationException("Provided CPF already exists.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UniqueViolationException("Provided email already exists.");
        }
        return save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(id, User.class.getSimpleName()));
    }
}
