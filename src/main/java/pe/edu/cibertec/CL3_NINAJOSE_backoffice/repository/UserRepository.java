package pe.edu.cibertec.CL3_NINAJOSE_backoffice.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository <User, Integer>{
    Optional<User> findByUsername(String username);
    Optional<User> findById(Integer id);

}
