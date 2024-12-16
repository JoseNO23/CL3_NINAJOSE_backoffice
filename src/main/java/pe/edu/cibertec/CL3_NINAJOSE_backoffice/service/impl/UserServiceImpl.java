package pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDetailDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.User;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.repository.UserRepository;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() throws Exception {
        List<UserDto> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(
                new UserDto(user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail())
        ));
        return users;
    }

    @Override
    public Optional<UserDetailDto> getUserById(int id) throws Exception {
        return userRepository.findById(id).map(user -> new UserDetailDto(
                user.getId(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getFirstName(), user.getLastName(),
                user.getRole(), user.getCreatedAt(), user.getUpdatedAt()
        ));
    }

    @Override
    public boolean updateUser(UserDto userDto) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userDto.id());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userDto.firstName());
            user.setLastName(userDto.lastName());
            user.setEmail(userDto.email());
            user.setUpdatedAt(new Date());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUserById(int id) throws Exception {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(UserDetailDto userDetailDto) throws Exception {
        if (userRepository.findByUsername(userDetailDto.username()).isPresent()) {
            throw new Exception("El nombre de usuario ya existe.");
        }
        User user = new User();
        user.setUsername(userDetailDto.username());
        user.setPassword(passwordEncoder.encode(userDetailDto.password()));
        user.setEmail(userDetailDto.email());
        user.setFirstName(userDetailDto.firstName());
        user.setLastName(userDetailDto.lastName());
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> getUserModelById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void promoteUserToAdmin(int id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }
        User updatedUser = user.get();
        updatedUser.setRole("ROLE_ADMIN");
        userRepository.save(updatedUser);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}