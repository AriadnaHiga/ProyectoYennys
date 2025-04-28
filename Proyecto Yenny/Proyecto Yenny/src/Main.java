import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        catálogo catalogo = new catálogo(); // Instanciamos el catálogo de libros
        cajaYbancos caja = new cajaYbancos(10000); // Instanciamos la caja con un saldo inicial
        compras compras = new compras(); // Instanciamos la clase de compras
        ventas ventas = new ventas(); // Instanciamos la clase de ventas

        // Agregar algunos libros al catálogo
        catalogo.agregarLibro(new Libro("1984", "George Orwell", 1500, 20, categoriaLibro.FICCION));
        catalogo.agregarLibro(new Libro("Sapiens", "Yuval Noah Harari", 2000, 15, categoriaLibro.NO_FICCION));

        // Código para gestionar el menú
        int opcion;

        do {
            String menu = "1. Agregar Libro\n2. Ver Catálogo\n3. Comprar Libros\n4. Vender Libros\n5. Salir";
            opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcion) {
                case 1:  // Opción para agregar libros
                    String titulo = JOptionPane.showInputDialog("Título del libro:");
                    String autor = JOptionPane.showInputDialog("Autor del libro:");
                    double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio del libro:"));
                    int stock = Integer.parseInt(JOptionPane.showInputDialog("Cantidad inicial en stock:"));
                    categoriaLibro categoria = categoriaLibro.valueOf(JOptionPane.showInputDialog("Categoría del libro (FICCION, NO_FICCION, INFANTIL, ACADEMICO, AUTOAYUDA):"));
                    catalogo.agregarLibro(new Libro(titulo, autor, precio, stock, categoria));
                    break;

                case 2:  // Opción para ver el catálogo
                    catalogo.mostrarCatalogo();
                    break;

                case 3:  // Opción para comprar libros
                    String tituloCompra = JOptionPane.showInputDialog("Título del libro a comprar:");
                    int cantidadCompra = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a comprar:"));
                    compras.comprarLibro(catalogo, tituloCompra, cantidadCompra, caja);
                    break;

                case 4:  // Opción para vender libros
                    String tituloVenta = JOptionPane.showInputDialog("Título a vender:");
                    int cantidadVenta = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a vender:"));
                    ventas.venderLibro(catalogo, tituloVenta, caja);
                    break;

                case 5:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }
        } while (opcion != 5);
    }
}
