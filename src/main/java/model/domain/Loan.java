package model.domain;

import java.time.LocalDate;

public class Loan {
    private int id;
    private int usuarioId;
    private String libroIsbn;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion; // puede ser null

    // Asociación / composición
    private User usuario;
    private Book libro;

    public Loan(){}

    public Loan(int id, int usuarioId, String libroIsbn, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroIsbn = libroIsbn;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Loan(int usuarioId, String libroIsbn, LocalDate fechaPrestamo) {
        this.usuarioId = usuarioId;
        this.libroIsbn = libroIsbn;
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public String getLibroIsbn() { return libroIsbn; }
    public void setLibroIsbn(String libroIsbn) { this.libroIsbn = libroIsbn; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
    public Book getLibro() { return libro; }
    public void setLibro(Book libro) { this.libro = libro; }

    @Override
    public String toString() {
        return String.format("#%d | %s -> %s | %s / %s",
                id,
                usuario != null ? usuario.getNombre()+" <"+usuario.getEmail()+">" : ("u:"+usuarioId),
                libro != null ? libro.getTitulo()+" ("+libroIsbn+")" : ("libro:"+libroIsbn),
                fechaPrestamo,
                fechaDevolucion);
    }
}
