package services.interfaces;

import model.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {
    void crear(Book l);
    void actualizar(Book l);
    void eliminar(String isbn);
    Optional<Book> buscarPorIsbn(String isbn);
    List<String> listarConPrestamosActivos();
    List<String> buscarPorTituloConJoin(String titulo);
}
