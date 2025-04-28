import javax.swing.JOptionPane;

public class compras {
    public void comprarLibro(catálogo catalogo, String titulo, int cantidad, cajaYbancos caja) {
        // Buscamos el libro en el catálogo
        Libro libro = catalogo.buscarLibro(titulo);

        if (libro != null) {
            double costo = libro.getPrecio() * 0.6 * cantidad; // Suponiendo compra al 60% del precio de venta
            caja.sacarDinero(costo); // Resta dinero de la caja (asegúrate de que sacarDinero() esté bien definido en la clase cajaYbancos)
            libro.agregarStock(cantidad); // Aumenta el stock del libro (asegúrate de que agregarStock() esté bien definido en la clase Libro)
            JOptionPane.showMessageDialog(null, "Compra realizada de " + cantidad + " ejemplares de " + titulo);
        } else {
            JOptionPane.showMessageDialog(null, "Libro no encontrado en el catálogo.");
        }
    }
}
