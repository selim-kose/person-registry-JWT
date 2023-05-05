package se.selimkose.personregistryjwt.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.selimkose.personregistryjwt.dao.UserRepository;
import se.selimkose.personregistryjwt.entity.User;
import se.selimkose.personregistryjwt.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUser(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException(404));
    }

    public User getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

}
