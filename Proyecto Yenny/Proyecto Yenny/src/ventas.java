import javax.swing.JOptionPane;
public class ventas {

    // Método para vender libros
    public void venderLibro(catálogo catalogo, String titulo, cajaYbancos caja) {
        // Buscamos el libro en el catálogo
        Libro libro = catalogo.buscarLibro(titulo);

        if (libro != null) {
            // Si encontramos el libro, pedimos la cantidad a vender
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos libros desea vender?"));
            
            // Intentamos vender la cantidad
            boolean ventaExitosa = libro.vender(cantidad);
            
            if (ventaExitosa) {
                // Si la venta es exitosa, agregamos dinero a la caja
                double ingreso = libro.getPrecio() * cantidad;
                caja.agregarDinero(ingreso); // Añadimos el dinero de la venta a la caja
                JOptionPane.showMessageDialog(null, "Venta exitosa: " + cantidad + " unidades de '" + libro.getTitulo() + "' vendidas.");
            } else {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock para esa cantidad.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El libro no se encuentra en el catálogo.");
        }
    }
}
