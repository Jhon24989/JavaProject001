package services.interfaces;

import model.domain.Loan;

import java.util.List;

public interface LoanServiceInterface {
    void crearPrestamo(int usuarioId, String isbn);

    void devolverPrestamo(int prestamoId);

    Integer buscarPrestamoActivo(int usuarioId, String isbn);

    List<Loan> listarTodosJoin();

    List<Loan> listarPorUsuarioJoin(int usuarioId);
}
