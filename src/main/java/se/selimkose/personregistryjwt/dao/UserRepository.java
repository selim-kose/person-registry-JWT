package se.selimkose.personregistryjwt.dao;

import org.springframework.data.repository.CrudRepository;
import se.selimkose.personregistryjwt.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
