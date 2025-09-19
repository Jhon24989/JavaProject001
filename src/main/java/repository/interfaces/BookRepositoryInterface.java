package repository.interfaces;

import model.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepositoryInterface {
    void crear(Book l);
    void actualizar(Book l);
    void eliminar(String isbn);
    Optional<Book> buscarPorIsbn(String isbn);

    List<Book> listar();

    List<String> listarConPrestamosActivos(); // JOIN
    List<String> buscarPorTituloConJoin(String titulo); // JOIN + filtro
}
