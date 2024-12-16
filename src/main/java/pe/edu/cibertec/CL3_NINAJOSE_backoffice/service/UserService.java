package pe.edu.cibertec.CL3_NINAJOSE_backoffice.service;

import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDetailDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Obtener todos los usuarios
    List<UserDto> getAllUsers() throws Exception;

    // Obtener usuario por ID
    Optional<UserDetailDto> getUserById(int id) throws Exception;

    // Actualizar un usuario basado en un DTO
    boolean updateUser(UserDto userDto) throws Exception;

    // Eliminar un usuario por ID
    boolean deleteUserById(int id) throws Exception;

    // Crear un nuevo usuario
    boolean addUser(UserDetailDto userDetailDto) throws Exception;

    // Obtener un modelo User por ID
    Optional<User> getUserModelById(int id) throws Exception;

    // Guardar un modelo User
    void saveUser(User user) throws Exception;

    // Promover un usuario a ADMIN
    void promoteUserToAdmin(int id) throws Exception;

    // Buscar un usuario por ID (modelo User)
    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

}
