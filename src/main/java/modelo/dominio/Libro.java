package modelo.dominio;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private boolean disponible;

    public Libro() {}

    public Libro(String isbn, String titulo, String autor, boolean disponible) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.disponible = disponible;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return String.format("%s - %s (%s) %s", isbn, titulo, autor, disponible? "[Disponible]":"[Prestado]");
    }
}
