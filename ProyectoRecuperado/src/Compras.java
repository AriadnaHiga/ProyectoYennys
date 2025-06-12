import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Compras {

    private Connection con;

    public Compras(Connection con) {
        this.con = con;
    }

    public void comprarLibro(Catalogo catalogo, String titulo, int cantidad, cajaYbancos caja) {
        Libro libro = catalogo.buscarLibro(titulo);

        if (libro != null) {
            double costo = libro.getPrecio() * 0.6 * cantidad; // 60% del precio como costo de compra

            if (caja.getSaldo() >= costo) {
                caja.sacarDinero(costo);
                caja.actualizarSaldoEnBD();

                // Actualiza stock en memoria
                libro.agregarStock(cantidad);

                // Actualiza stock en BD (tabla libro)
                actualizarStockEnBD(libro.getTitulo(), libro.getStock());

                JOptionPane.showMessageDialog(null, "Compra realizada de " + cantidad + " ejemplares de '" + titulo + "'.");
            } else {
                JOptionPane.showMessageDialog(null, "Fondos insuficientes en la caja.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Libro no encontrado en el catálogo.");
        }
    }

    private void actualizarStockEnBD(String titulo, int nuevoStock) {
        String sql = "UPDATE libro SET stock = ? WHERE titulo = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nuevoStock);
            stmt.setString(2, titulo);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el stock en la base de datos.");
        }
    }
}
