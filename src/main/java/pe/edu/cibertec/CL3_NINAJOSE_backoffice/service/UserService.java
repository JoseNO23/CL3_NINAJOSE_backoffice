package pe.edu.cibertec.CL3_NINAJOSE_backoffice.service;

import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDetailDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Obtener todos los usuarios
    List<UserDto> getAllUsers();

    // Obtener usuario por ID
    Optional<UserDetailDto> getUserById(int id);

    // Actualizar un usuario basado en un DTO
    boolean updateUser(UserDto userDto);

    // Eliminar un usuario por ID
    boolean deleteUserById(int id);

    // Crear un nuevo usuario
    boolean addUser(UserDetailDto userDetailDto) throws Exception;

    // Obtener un modelo User por ID
    Optional<User> getUserModelById(int id);

    // Guardar un modelo User
    void saveUser(User user) throws Exception;

    // Promover un usuario a ADMIN
    void promoteUserToAdmin(int id);

    // Buscar un usuario por ID (modelo User)
    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    UserDetailDto getUserDetailsById(int id);

    List<UserDetailDto> getAllUserDetails();

    void demoteUserFromAdmin(Integer id);

    boolean updateUsers(UserDetailDto userDetailDto);


}
