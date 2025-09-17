package repository.interfaces;

import model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface {
    Optional<User> login(String email, String password);

    Optional<User> buscarPorEmail(String email);

    Optional<User> buscarPorId(int id);

    void crear(User u);

    void actualizar(User u);

    void eliminar(int id);

    List<String> listarConPrestamosActivos();
}
