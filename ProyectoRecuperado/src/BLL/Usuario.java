package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario {
    private String nombre;
    private String email;
    private String password;
    private Connection con;

    // Constructor para crear un objeto con datos
    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Constructor para manejar la conexión
    public Usuario(Connection con) {
        this.con = con;
    }

    // Método para insertar un usuario en la base de datos
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
