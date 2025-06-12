import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

import java.util.ArrayList;

public class Catalogo {

    private ArrayList<Libro> libros = new ArrayList<>();
    private static Connection con;

    static {
        try {
            con = ConexionMariaDB.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos:\n" + e.getMessage());
        }
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public Libro buscarLibro(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    public void mostrarCatalogo() {
        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El catálogo está vacío.");
            return;
        }

        StringBuilder sb = new StringBuilder("Libros en catálogo:\n\n");
        for (Libro libro : libros) {
            sb.append(libro.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Método para guardar un libro en la base de datos
    public static void guardarEnBD(Libro libro) {
        try {
            String sql = "INSERT INTO catalogo (titulo, autor, precio, stock, categoria) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setDouble(3, libro.getPrecio());
            ps.setInt(4, libro.getStock());
            ps.setString(5, libro.getCategoria());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Libro guardado en la base de datos.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar libro en la base de datos:\n" + e.getMessage());
        }
    }
}

