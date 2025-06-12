

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMariaDB {

    private static ConexionMariaDB instancia;
    private Connection connection;
    private final String URL = "jdbc:mysql://localhost:3306/libreriasuseSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "";

    private ConexionMariaDB() throws SQLException {
        try {
            // Cargar el driver de MySQL 
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver no encontrado");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConexionMariaDB getInstance() throws SQLException {
        if (instancia == null || instancia.getConnection().isClosed()) {
            instancia = new ConexionMariaDB();
        }
        return instancia;
    }
}
