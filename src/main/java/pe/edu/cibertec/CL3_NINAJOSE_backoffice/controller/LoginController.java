package pe.edu.cibertec.CL3_NINAJOSE_backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.dto.UserDetailDto;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.entity.User;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.repository.UserRepository;
import pe.edu.cibertec.CL3_NINAJOSE_backoffice.service.UserService;

@Controller
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Devuelve la vista login.html
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/users/login?logout";
    }

    @GetMapping("/home")
    public String homePage(@AuthenticationPrincipal UserDetails userDetails) {
        String role = userDetails.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";  // Si es admin, redirigir al dashboard de administración
        } else if (role.contains("ROLE_USER")) {
            return "redirect:/user/cars";  // Si es usuario, redirigir al listado de carros
        }

        return "redirect:/users/login?error";  // Si el rol no es válido, redirigir al login
    }
}
