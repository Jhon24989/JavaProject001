package model.domain;

public class User extends Person {
    private String email;
    private String password;
    private Rol rol;

    public User() {}

    public User(int id, String nombre, String email, String password, Rol rol) {
        super(id, nombre);
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public User(String nombre, String email, String password, Rol rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return String.format(
                "ID: %d\nNombre: %s\nEmail: %s\nRol: %s",
                id, nombre, email, rol
        );
    }
}
