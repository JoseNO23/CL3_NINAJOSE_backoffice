package pe.edu.cibertec.CL3_NINAJOSE_backoffice.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Contraseña ingresada en el formulario
        String rawPassword = "123456"; // Reemplazar con la contraseña que deseas probar
        // Contraseña almacenada en la base de datos
        String encodedPassword = "$2a$10$lkDBnthqFIeeFhPhcLn4LOOe/6fM.47eNqf/.Jx9Gx6RTkUou2mR."; // Reemplazar con el hash desde la base de datos

        // Verificar si coinciden
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("¿Coinciden las contraseñas? " + matches);
    }
}

