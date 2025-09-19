package util;

import java.util.regex.Pattern;

public class Validation {

    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PASSWORD = Pattern.compile("^.{6,}$");
    private static final Pattern ISBN = Pattern.compile("^[A-Za-z0-9-]{3,20}$");
    private static final Pattern NOMBRE = Pattern.compile("^[\\p{L} .'-]{2,}$");

    public static String validarEmail(String email) {
        if (email == null || email.isBlank()) return "El email no puede estar vacío";
        if (!EMAIL.matcher(email).matches()) return "Email inválido";
        return null;
    }

    public static String validarPassword(String password) {
        if (password == null || password.isBlank()) return "La contraseña no puede estar vacía";
        if (!PASSWORD.matcher(password).matches()) return "Password mínimo 6 caracteres";
        return null;
    }

    public static String validarISBN(String isbn) {
        if (isbn == null || isbn.isBlank()) return "El ISBN no puede estar vacío";
        if (!ISBN.matcher(isbn).matches()) return "ISBN inválido";
        return null;
    }

    public static String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return "El nombre no puede estar vacío";
        if (!NOMBRE.matcher(nombre).matches()) return "Nombre inválido";
        return null;
    }
}

