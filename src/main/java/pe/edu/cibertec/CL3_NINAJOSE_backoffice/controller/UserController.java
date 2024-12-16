package pe.edu.cibertec.CL3_NINAJOSE_backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDetailDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.User;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.UserService;

import java.security.Principal;
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
            model.addAttribute("users", userService.getAllUsers());
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar la lista de usuarios");
        }
        return "users";
    }

    /**
     * Mostrar el perfil de un usuario
     */
    @GetMapping("/profile")
    public String viewAuthenticatedProfile(Model model, Principal principal) {
        try {
            String username = principal.getName();  // Obtener el nombre de usuario autenticado
            Optional<User> user = userService.findByUsername(username);
            if (user.isPresent()) {
                UserDetailDto userDetail = userService.getUserById(user.get().getId()).orElseThrow();
                model.addAttribute("user", userDetail);
                return "user-detail";
            } else {
                model.addAttribute("error", "Usuario no encontrado.");
                return "error-page";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el perfil del usuario: " + e.getMessage());
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
        try {
            Optional<UserDetailDto> userOpt = userService.getUserById(id);
            if (userOpt.isPresent()) {
                model.addAttribute("user", userOpt.get());
                return "users-edit";
            }
            return "redirect:/users/list?error=Usuario+no+encontrado";
        } catch (Exception e) {
            return "redirect:/users/list?error=" + e.getMessage();
        }
    }

    // Actualizar un usuario
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute UserDto userDto, Model model) {
        try {
            userDto = new UserDto(id, userDto.firstName(), userDto.lastName(), userDto.email());
            userService.updateUser(userDto);
            return "redirect:/users/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el usuario: " + e.getMessage());
            return "users-edit";
        }
    }
}
