package view;

import model.domain.Rol;
import model.domain.User;

import services.Impl.UserServiceImpl;
import services.interfaces.UserServiceInterface;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserServiceInterface userService = new UserServiceInterface() {
        @Override
        public Optional<User> login(String email, String password) {
            return Optional.empty();
        }

        @Override
        public void registrar(User u) {

        }

        @Override
        public void actualizar(User u) {

        }

        @Override
        public void eliminar(int id) {

        }

        @Override
        public Optional<User> buscarPorEmail(String email) {
            return Optional.empty();
        }

        @Override
        public Optional<User> buscarPorId(int id) {
            return Optional.empty();
        }

        @Override
        public List<String> listarConPrestamosActivos() {
            return List.of();
        }
    };

    public static void main(String[] args) {
        System.out.println("===== Biblioteca Virtual =====");
        User u = login();
        if (u == null) return;

        if (u.getRol() == Rol.ADMIN) {
            new MainAdmin().mostrar();
        } else {
            new MainUser().mostrar(u.getId());
        }
    }

    private static User login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        Optional<User> u = userService.login(email, pass);
        if (u.isPresent()) return u.get();

        System.out.println("No existe o password incorrecto.");
        System.out.print("¿Deseas registrarte? (s/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.print("Nombre: "); String nombre = sc.nextLine();
            System.out.print("Password: "); String pwd = sc.nextLine();
            User nuevo = new User(0, nombre, email, pwd, Rol.USUARIO);
            try {
                userService.registrar(nuevo);
                System.out.println("Registrado. Vuelve a iniciar sesión.");
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return null;
    }
}
