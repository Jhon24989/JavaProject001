package services.interfaces;

import model.domain.User;
import java.util.List;
import java.util.Optional;

public class UserServiceInterface {

    Optional<User> login(String email, String password);
    public void registrar(User u);
    public void actualizar(User u);
    public void eliminar(int id);
    Optional<User> buscarPorEmail(String email);
    public Optional<User> buscarPorId(int id);
    public List<String> listarConPrestamosActivos();

}
