package repository.interfaces;

import model.domain.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroRepoInterface {
    void crear(Libro l);
    void actualizar(Libro l);
    void eliminar(String isbn);
    Optional<Libro> buscarPorIsbn(String isbn);
    List<String> listarConPrestamosActivos(); // JOIN
    List<String> buscarPorTituloConJoin(String titulo); // JOIN + filtro
}
