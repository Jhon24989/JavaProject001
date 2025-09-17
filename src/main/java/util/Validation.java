package util;

import java.util.regex.Pattern;

public class Validation {

    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PASSWORD = Pattern.compile("^.{6,}$");
    private static final Pattern ISBN = Pattern.compile("^[A-Za-z0-9-]{3,20}$");
    private static final Pattern NOMBRE = Pattern.compile("^[\\p{L} .'-]{2,}$");

    public static void validarEmail(String email) {
        if (email == null || !EMAIL.matcher(email).matches())
            throw new IllegalArgumentException("Email inválido");
    }

    public static void validarPassword(String password) {
        if (password == null || !PASSWORD.matcher(password).matches())
            throw new IllegalArgumentException("Password mínimo 6 caracteres");
    }

    public static void validarISBN(String isbn) {
        if (isbn == null || !ISBN.matcher(isbn).matches())
            throw new IllegalArgumentException("ISBN inválido");
    }

    public static void validarNombre(String nombre) {
        if (nombre == null || !NOMBRE.matcher(nombre).matches())
            throw new IllegalArgumentException("Nombre inválido");
    }

}
