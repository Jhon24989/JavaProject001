package model.domain;

public class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String rol; // ADMIN o USER

    public Usuario(String id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return nombre + " (" + id + ") - " + email + " [" + rol + "]";
    }
}
