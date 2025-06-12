package DLL;

import BLL.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DLLUsuario {
    private Connection con;

    public DLLUsuario(Connection con) {
        this.con = con;
    }

    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, contrasenia) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
