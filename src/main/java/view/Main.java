package view;

import model.domain.Rol;
import model.domain.User;
import services.Impl.UserServiceImpl;
import services.interfaces.UserServiceInterface;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserServiceInterface userService = new UserServiceImpl();

    public static void main(String[] args) {
        System.out.println("===== Biblioteca Virtual =====");

        User u = null;
        while (u == null) {
            u = login();
            if (u == null) {
                System.out.print("¿Deseas intentar de nuevo? (s = sí / otra tecla = salir): ");
                String r = sc.nextLine();
                if (!r.equalsIgnoreCase("s")) {
                    System.out.println("Saliendo...");
                    return;
                }
            }
        }

        if (u.getRol() == Rol.ADMIN) {
            new MainAdmin().mostrar();
        } else {
            new MainUser().mostrar(u.getId());
        }
    }

    private static User login() {
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine().trim();

        try {
            Optional<User> uOpt = userService.login(email, pass);
            if (uOpt.isPresent()) {
                System.out.println("Login exitoso. Bienvenido " + uOpt.get().getNombre());
                return uOpt.get();
            }

            System.out.println("No existe o password incorrecto.");
            System.out.print("¿Deseas registrarte con este email? (s/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("s")) {
                System.out.print("Nombre: ");
                String nombre = sc.nextLine().trim();
                System.out.print("Password: ");
                String pwd = sc.nextLine().trim();

                Rol rol = email.endsWith("@admin.com") ? Rol.ADMIN : Rol.USUARIO;

                User nuevo = new User(0, nombre, email, pwd, rol);
                userService.registrar(nuevo);
                System.out.println("Registrado correctamente como " + rol + ". Intentando iniciar sesión automáticamente...");

                Optional<User> nuevoLog = userService.login(email, pwd);
                if (nuevoLog.isPresent()) {
                    System.out.println("Inicio de sesión automático exitoso.");
                    return nuevoLog.get();
                } else {
                    System.out.println("No se pudo iniciar sesión automáticamente. Intenta iniciar sesión manualmente.");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("Error durante la operación: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
