package repository.interfaces;

import model.domain.Loan;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepositoryInterface {
    void crear(Loan p);

    void marcarDevolucion(int prestamoId, LocalDate fecha);

    Integer buscarPrestamoActivo(int usuarioId, String isbn);

    List<Loan> listarTodosJoin();

    List<Loan> listarPorUsuarioJoin(int usuarioId);
}
