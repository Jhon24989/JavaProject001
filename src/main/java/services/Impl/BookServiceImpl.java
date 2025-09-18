package services.Impl;

import model.domain.Book;
import repository.impl.BookRepositoryImpl;
import repository.interfaces.BookRepositoryInterface;
import services.interfaces.BookServiceInterface;
import services.interfaces.BookServiceInterface;
import util.Validation;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookServiceInterface {
    private final BookRepositoryInterface repo = new BookRepositoryImpl();

    @Override
    public void crear(Book l) {
        Validation.validarISBN(l.getIsbn());
        repo.crear(l);
    }

    @Override
    public void actualizar(Book l) {
        Validation.validarISBN(l.getIsbn());
        repo.actualizar(l);
    }

    @Override
    public void eliminar(String isbn) {
        Validation.validarISBN(isbn);
        repo.eliminar(isbn);
    }

    @Override
    public Optional<Book> buscarPorIsbn(String isbn) {
        Validation.validarISBN(isbn);
        return repo.buscarPorIsbn(isbn);
    }

    @Override
    public List<String> listarConPrestamosActivos() {
        return repo.listarConPrestamosActivos();
    }

    @Override
    public List<String> buscarPorTituloConJoin(String titulo) {
        return repo.buscarPorTituloConJoin(titulo);
    }
}

