package repository.impl;

import model.domain.Book;
import repository.interfaces.BookRepositoryInterface;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepositoryInterface {

    private Book map(ResultSet rs) throws SQLException {
        return new Book(
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getBoolean("disponible")
        );
    }

    @Override
    public void crear(Book l) {
        String sql = "INSERT INTO libros (isbn, titulo, autor, disponible) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, l.getIsbn());
            ps.setString(2, l.getTitulo());
            ps.setString(3, l.getAutor());
            ps.setBoolean(4, l.isDisponible());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Book l) {
        String sql = "UPDATE libros SET titulo=?, autor=?, disponible=? WHERE isbn=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setBoolean(3, l.isDisponible());
            ps.setString(4, l.getIsbn());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // MUESTRA QUE TIPO DE ERROR SE PRESENTA
        }
    }

    @Override
    public void eliminar(String isbn) {
        String sql = "DELETE FROM libros WHERE isbn=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Book> buscarPorIsbn(String isbn) {
        String sql = "SELECT isbn, titulo, autor, disponible FROM libros WHERE isbn=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Book> listar() {
        List<Book> lista = new ArrayList<>();
        String sql = "SELECT isbn, titulo, autor, disponible FROM libros";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String> listarConPrestamosActivos() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT l.isbn, l.titulo, l.autor, l.disponible, COUNT(p.id) AS prestamos_activos " +
                "FROM libros l " +
                "LEFT JOIN prestamos p ON p.libro_isbn = l.isbn AND p.fecha_devolucion IS NULL " +
                "GROUP BY l.isbn, l.titulo, l.autor, l.disponible " +
                "ORDER BY l.titulo";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String linea = String.format("%s,%s,%s,%s,%d",
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponible") ? "SI" : "NO",
                        rs.getInt("prestamos_activos"));
                lista.add(linea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<String> buscarPorTituloConJoin(String titulo) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT l.isbn, l.titulo, l.autor, l.disponible, COUNT(p.id) AS prestamos_activos " +
                "FROM libros l " +
                "LEFT JOIN prestamos p ON p.libro_isbn = l.isbn AND p.fecha_devolucion IS NULL " +
                "WHERE l.titulo LIKE ? " +
                "GROUP BY l.isbn, l.titulo, l.autor, l.disponible";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String linea = String.format("%s,%s,%s,%s,%d",
                            rs.getString("isbn"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getBoolean("disponible") ? "SI" : "NO",
                            rs.getInt("prestamos_activos"));
                    lista.add(linea);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
