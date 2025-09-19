package repository.impl;

import model.domain.*;
import repository.interfaces.LoanRepositoryInterface;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanRepositoryImpl implements LoanRepositoryInterface {

    @Override
    public void crear(Loan p) {
        String sql = "INSERT INTO prestamos (usuario_id, libro_isbn, fecha_prestamo, fecha_devolucion) VALUES (?,?,?,NULL)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getUsuarioId());
            ps.setString(2, p.getLibroIsbn());
            ps.setDate(3, Date.valueOf(p.getFechaPrestamo()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void marcarDevolucion(int prestamoId, LocalDate fecha) {
        String sql = "UPDATE prestamos SET fecha_devolucion=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            ps.setInt(2, prestamoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer buscarPrestamoActivo(int usuarioId, String isbn) {
        String sql = "SELECT id FROM prestamos WHERE usuario_id=? AND libro_isbn=? AND fecha_devolucion IS NULL";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Loan> listarTodosJoin() {
        List<Loan> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.usuario_id, p.libro_isbn, p.fecha_prestamo, p.fecha_devolucion, " +
                "u.id AS user_id, u.nombre, u.email, u.rol, " +
                "l.isbn, l.titulo, l.autor, l.disponible " +
                "FROM prestamos p " +
                "JOIN usuarios u ON u.id = p.usuario_id " +
                "JOIN libros l ON l.isbn = p.libro_isbn " +
                "ORDER BY p.id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Loan pr = new Loan(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getString("libro_isbn"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null
                );

                User u = new User(
                        rs.getInt("user_id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        "", // password no se necesita aquí
                        Rol.valueOf(rs.getString("rol"))
                );

                Book l = new Book(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponible")
                );

                pr.setUsuario(u);
                pr.setLibro(l);
                lista.add(pr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Loan> listarPorUsuarioJoin(int usuarioId) {
        List<Loan> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.usuario_id, p.libro_isbn, p.fecha_prestamo, p.fecha_devolucion, " +
                "l.isbn, l.titulo, l.autor, l.disponible " +
                "FROM prestamos p " +
                "JOIN libros l ON l.isbn = p.libro_isbn " +
                "WHERE p.usuario_id=? " +
                "ORDER BY p.id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Loan pr = new Loan(
                            rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getString("libro_isbn"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null
                    );

                    Book l = new Book(
                            rs.getString("isbn"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getBoolean("disponible")
                    );

                    pr.setLibro(l);
                    lista.add(pr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

