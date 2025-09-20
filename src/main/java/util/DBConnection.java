package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DBConnection {

        private static final String URL = "jdbc:mysql://localhost:3306/bibliotecaVirtualV2";
        private static final String USER = "root"; // usuario de XAMPP
        private static final String PASSWORD = "";

        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("No se encontró el Driver de MySQL. ¿Agregaste la dependencia?", e);
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
