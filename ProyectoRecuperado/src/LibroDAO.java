import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private Connection con;

    public LibroDAO(Connection con) {
        this.con = con;
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM catalogo WHERE titulo LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
    
    
    public boolean agregarStock(int id, int cantidad) {
        String sql = "UPDATE catalogo SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertarLibro(Libro libro) {
        String sql = "INSERT INTO catalogo (titulo, autor, precio, stock, categoria) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setDouble(3, libro.getPrecio());
            stmt.setInt(4, libro.getStock());
            stmt.setString(5, libro.getCategoria());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean venderLibro(int id, int cantidad) {
        String sql = "UPDATE catalogo SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, id);
            stmt.setInt(3, cantidad);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM catalogo";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public boolean eliminarLibro(int id) {
        String sql = "DELETE FROM catalogo WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarLibro(Libro libro) {
        String sql = "UPDATE catalogo SET titulo = ?, autor = ?, precio = ?, stock = ?, categoria = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setDouble(3, libro.getPrecio());
            stmt.setInt(4, libro.getStock());
            stmt.setString(5, libro.getCategoria());
            stmt.setInt(6, libro.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para mapear un ResultSet a un objeto Libro
    private Libro mapearLibro(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String autor = rs.getString("autor");
        double precio = rs.getDouble("precio");
        int stock = rs.getInt("stock");
        String categoria = rs.getString("categoria");
        return new Libro(id, titulo, autor, precio, stock, categoria);
    }
}
