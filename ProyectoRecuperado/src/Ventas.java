import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class Ventas {

    private Connection con;

    public Ventas(Connection con) {
        this.con = con;
    }

    public void venderLibro(Catalogo catalogo, String titulo, cajaYbancos caja) {
        Libro libro = catalogo.buscarLibro(titulo);

        if (libro != null) {
            try {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos libros desea vender?"));

                if (libro.getStock() >= cantidad) {
                    // Reducimos stock en memoria
                    libro.vender(cantidad);

                    // Calculamos ingreso y actualizamos caja
                    double ingreso = libro.getPrecio() * cantidad;
                    caja.agregarDinero(ingreso);
                    caja.actualizarSaldoEnBD();

                    // Actualizamos stock en BD
                    actualizarStockEnBD(libro.getTitulo(), libro.getStock());

                    JOptionPane.showMessageDialog(null, "Venta exitosa: " + cantidad + " unidades de '" + libro.getTitulo() + "' vendidas.");
                } else {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El libro no se encuentra en el catálogo.");
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
