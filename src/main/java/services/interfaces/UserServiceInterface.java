package services.interfaces;

import model.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    Optional<User> login(String email, String password);
    void registrar(User u);
    void actualizar(User u);
    void eliminar(int id);
    Optional<User> buscarPorEmail(String email);
    Optional<User> buscarPorId(int id);
    List<String> listarConPrestamosActivos();

}
