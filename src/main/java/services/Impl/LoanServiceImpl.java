package services.Impl;

import model.domain.Book;
import model.domain.Loan;
import repository.impl.BookRepositoryImpl;
import repository.impl.LoanRepositoryImpl;
import repository.interfaces.BookRepositoryInterface;
import repository.interfaces.LoanRepositoryInterface;
import services.interfaces.LoanServiceInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanServiceImpl implements LoanServiceInterface {
    private final LoanRepositoryInterface repo = new LoanRepositoryImpl();
    private final BookRepositoryInterface libroRepo = new BookRepositoryImpl();

    @Override
    public void crearPrestamo(int usuarioId, String isbn) {
        Optional<Book> l = libroRepo.buscarPorIsbn(isbn);
        if (l.isEmpty()) throw new RuntimeException("Libro no existe");
        if (!l.get().isDisponible()) throw new RuntimeException("Libro no disponible");
        Loan p = new Loan(usuarioId, isbn, LocalDate.now());
        repo.crear(p);
        Book lib = l.get();
        lib.setDisponible(false);
        libroRepo.actualizar(lib);
    }

    @Override
    public void devolverPrestamo(int prestamoId) {
        repo.marcarDevolucion(prestamoId, LocalDate.now());
        // Para simplificar, pedimos al admin que ajuste disponibilidad desde el menú Libros.
    }

    @Override
    public Integer buscarPrestamoActivo(int usuarioId, String isbn) {
        return repo.buscarPrestamoActivo(usuarioId, isbn);
    }

    @Override
    public List<Loan> listarTodosJoin() {
        return repo.listarTodosJoin();
    }

    @Override
    public List<Loan> listarPorUsuarioJoin(int usuarioId) {
        return repo.listarPorUsuarioJoin(usuarioId);
    }
}