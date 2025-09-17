package services.Impl;

import model.domain.User;
import repository.impl.UserRepositoryImpl;
import repository.interfaces.UserRepositoryInterface;
import services.interfaces.UserServiceInterface;
import util.Validation;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl {

    private final UserRepositoryInterface repo = new UserRepositoryImpl();

    @Override
    public Optional<User> login(String email, String password) {
        Validation.validarEmail(email);
        Validation.validarPassword(password);
        return repo.login(email, password);
    }

    @Override
    public void registrar(User u) {
        Validation.validarNombre(u.getNombre());
        Validation.validarEmail(u.getEmail());
        Validation.validarPassword(u.getPassword());
        repo.crear(u);
    }

    @Override
    public void actualizar(User u) {
        Validation.validarNombre(u.getNombre());
        Validation.validarEmail(u.getEmail());
        Validation.validarPassword(u.getPassword());
        repo.actualizar(u);
    }

    @Override
    public void eliminar(int id) {
        repo.eliminar(id);
    }

    @Override
    public Optional<User> buscarPorEmail(String email) {
        return repo.buscarPorEmail(email);
    }

    @Override
    public Optional<User> buscarPorId(int id) {
        return repo.buscarPorId(id);
    }

    @Override
    public List<String> listarConPrestamosActivos() {
        return repo.listarConPrestamosActivos();
    }

}
