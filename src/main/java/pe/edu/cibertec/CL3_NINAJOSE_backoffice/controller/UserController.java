package pe.edu.cibertec.CL3_NINAJOSE_backoffice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDetailDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.User;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Listar todos los usuarios
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model) {
        try {
            List<UserDetailDto> users = userService.getAllUserDetails(); // Usa el nuevo método
            model.addAttribute("users", users); // Pasa los usuarios al modelo
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar la lista de usuarios: " + e.getMessage());
        }
        return "users";
    }


    /**
     * Mostrar el perfil de un usuario
     */
    @GetMapping("/profile")
    public String viewAuthenticatedProfile(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            UserDetailDto userDetail = userService.getUserById(userOpt.get().getId()).orElseThrow();
            model.addAttribute("user", userDetail);
            return "users-profile";
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
            return "error-page";
        }
    }




    /**
     * Mostrar el formulario para agregar un nuevo usuario
     */
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUserForm(Model model) {
        // Asegúrate de tener un constructor sin argumentos en UserDetailDto
        model.addAttribute("user", new UserDetailDto(null, "", "", "", "", "", "", null, null));
        return "users-add";
    }

    // Guardar un nuevo usuario
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveUser(@ModelAttribute UserDetailDto userDetailDto, Model model) {
        try {
            userService.addUser(userDetailDto);
            return "redirect:/users/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "users-add";
        }
    }

    // Mostrar el formulario para editar un usuario
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUserForm(@PathVariable("id") int id, Model model) {
        Optional<UserDetailDto> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "users-edit";
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
            return "redirect:/users/list";
        }
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute UserDetailDto userDetailDto, Model model) {
        try {
            // Cargar el usuario existente
            UserDetailDto existingUser = userService.getUserDetailsById(id);

            // Actualizar solo los campos requeridos
            UserDetailDto updatedUser = new UserDetailDto(
                    id,
                    existingUser.username(), // El nombre de usuario no cambia
                    userDetailDto.password().isEmpty() ? existingUser.password() : userDetailDto.password(), // Si no hay contraseña, mantener la existente
                    userDetailDto.email(),
                    userDetailDto.firstName(),
                    userDetailDto.lastName(),
                    existingUser.role(), // Mantener el rol actual
                    existingUser.createdAt(),
                    new Date() // Actualizar la fecha de modificación
            );

            boolean isUpdated = userService.updateUsers(updatedUser);
            if (!isUpdated) {
                model.addAttribute("error", "No se pudo actualizar el usuario.");
                return "users-edit";
            }

            return "redirect:/users/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el usuario: " + e.getMessage());
            return "users-edit";
        }
    }



    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        try {
            boolean deleted = userService.deleteUserById(id);
            if (!deleted) {
                model.addAttribute("error", "No se pudo eliminar el usuario.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
        }
        return "redirect:/users/list";
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String userDetails(@PathVariable("id") int id, Model model) {
        try {
            Optional<UserDetailDto> userOpt = userService.getUserById(id);
            if (userOpt.isPresent()) {
                model.addAttribute("user", userOpt.get());
                return "user-detail"; // Vista dedicada para mostrar los detalles del usuario
            } else {
                model.addAttribute("error", "Usuario no encontrado.");
                return "redirect:/users/list";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los detalles del usuario: " + e.getMessage());
            return "redirect:/users/list";
        }
    }




    @PostMapping("/promote/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String promoteUserToAdmin(@PathVariable("id") Integer id, Model model) {
        try {
            // Obtén los detalles del usuario para verificar el rol actual
            UserDetailDto userDetails = userService.getUserDetailsById(id);

            // Si ya es ADMIN, lanzar excepción
            if ("ROLE_ADMIN".equals(userDetails.role())) {
                model.addAttribute("error", "El usuario ya tiene el rol de ADMIN.");
                return "redirect:/users/list";
            }

            // Promover al usuario
            userService.promoteUserToAdmin(id);
            return "redirect:/users/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error al promover al usuario: " + e.getMessage());
            return "redirect:/users/list";
        }
    }

    @PostMapping("/demote/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String demoteUserFromAdmin(@PathVariable("id") Integer id, Model model) {
        try {
            userService.demoteUserFromAdmin(id); // Llama al servicio para cambiar el rol
            return "redirect:/users/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error al dar de baja de administrador: " + e.getMessage());
            return "redirect:/users/list";
        }
    }

    @GetMapping("/error-page")
    public String showErrorPage(Model model, HttpServletRequest request) {
        // Invalidar la sesión actual si existe
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Limpiar el contexto de autenticación de Spring Security
        SecurityContextHolder.clearContext();

        model.addAttribute("error", "No tienes permisos para acceder a esta página.");
        return "error-page";  // Retorna la plantilla error-page.html
    }



    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Invalidar la sesión actual si existe
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Limpiar el contexto de autenticación de Spring Security
        SecurityContextHolder.clearContext();

        model.addAttribute("error", "Ha ocurrido un error inesperado. Tu sesión se ha cerrado por seguridad.");

        return "error-page";
    }

}
