package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DBConnection {

        private static final String URL = "jdbc:mysql://localhost:3306/bibliotecaVirtualV2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        private static final String USER = "root"; // usuario de XAMPP
        private static final String PASSWORD = ""; // si tu root no tiene contraseña, déjalo vacío

        public static Connection getConnection() throws SQLException {
            try {
                // Cargar el driver explícitamente (a veces es necesario en proyectos locales)
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("No se encontró el Driver de MySQL. ¿Agregaste la dependencia?", e);
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
