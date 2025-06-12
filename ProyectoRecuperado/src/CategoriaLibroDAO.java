import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaLibroDAO {
    private Connection con;

    public CategoriaLibroDAO(Connection con) {
        this.con = con;
    }

    // Obtener todas las categorías
    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT nombre FROM categorialibro ORDER BY nombre";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                categorias.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}
