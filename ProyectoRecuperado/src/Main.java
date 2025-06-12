import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import BLL.Usuario;
import DLL.DLLUsuario;

public class Main {

    public static void main(String[] args) {
        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/libreria", "root", "");

            LibroDAO libroDAO = new LibroDAO(con);
            cajaYbancos caja = new cajaYbancos(con);
            Ventas ventas = new Ventas(con);
            stock stock = new stock(con);
            CategoriaLibroDAO categoriaDAO = new CategoriaLibroDAO(con);
            Compras compras = new Compras(con);
            DLLUsuario dllUsuario = new DLLUsuario(con);

            int opcion;
            do {
                String menu = "------ MENÚ ------\n"
                            + "1. Agregar Libro\n"
                            + "2. Ver Catálogo\n"
                            + "3. Vender Libro\n"
                            + "4. Registrar Usuario\n"
                            + "5. Comprar Libro\n"
                            + "6. Ver Stock\n"
                            + "7. Salir";

                ImageIcon icono = new ImageIcon("src/img/imagen.proyecto.jpg");

                Object input = JOptionPane.showInputDialog(
                    null,
                    menu,
                    "Menú Principal",
                    JOptionPane.QUESTION_MESSAGE,
                    icono,
                    null,
                    null
                );

                if (input == null) {
                    opcion = 7;  // Si cancela, salir
                } else {
                    try {
                        opcion = Integer.parseInt(input.toString());
                    } catch (NumberFormatException e) {
                        opcion = -1;
                    }
                }

                switch (opcion) {
                    case 1:
                        agregarLibro(libroDAO, categoriaDAO);
                        break;
                    case 2:
                        verCatalogo(libroDAO);
                        break;
                    case 3:
                        venderLibro(libroDAO);
                        break;
                    case 4:
                        registrarUsuario(dllUsuario);
                        break;
                    case 5:
                        comprarLibro(libroDAO);
                        break;
                    case 6:
                        stock.verStock();
                        break;
                    case 7:
                        JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida.");
                }
            } while (opcion != 7);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void agregarLibro(LibroDAO libroDAO, CategoriaLibroDAO categoriaDAO) {
        try {
            String titulo = JOptionPane.showInputDialog("Título del libro:");
            String autor = JOptionPane.showInputDialog("Autor del libro:");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio del libro:"));
            int stockInicial = Integer.parseInt(JOptionPane.showInputDialog("Cantidad inicial en stock:"));

            List<String> categorias = categoriaDAO.obtenerCategorias();
            if (categorias.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay categorías disponibles.");
                return;
            }

            StringBuilder opciones = new StringBuilder();
            for (int i = 0; i < categorias.size(); i++) {
                opciones.append((i + 1)).append(". ").append(categorias.get(i)).append("\n");
            }

            String entrada = JOptionPane.showInputDialog("Seleccione una categoría por número o nombre:\n" + opciones);
            String categoria = obtenerCategoriaDesdeEntrada(entrada, categorias);

            if (categoria == null) {
                JOptionPane.showMessageDialog(null, "Categoría inválida.");
                return;
            }

            Libro nuevo = new Libro(0, titulo, autor, precio, stockInicial, categoria);
            boolean agregado = libroDAO.insertarLibro(nuevo);
            JOptionPane.showMessageDialog(null, agregado ? "Libro agregado exitosamente." : "Error al agregar el libro.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el libro: " + e.getMessage());
        }
    }

    private static void verCatalogo(LibroDAO libroDAO) {
        List<Libro> libros = libroDAO.buscarPorTitulo("");
        if (libros.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay libros en el catálogo.");
            return;
        }

        StringBuilder sb = new StringBuilder("Catálogo:\n");
        for (Libro libro : libros) {
            sb.append("Título: ").append(libro.getTitulo())
              .append(" | Autor: ").append(libro.getAutor())
              .append(" | Precio: $").append(libro.getPrecio())
              .append(" | Stock: ").append(libro.getStock())
              .append(" | Categoría: ").append(libro.getCategoria()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void venderLibro(LibroDAO libroDAO) {
        try {
            String titulo = JOptionPane.showInputDialog("Título del libro a vender:");
            List<Libro> libros = libroDAO.buscarPorTitulo(titulo);
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontró el libro.");
                return;
            }

            StringBuilder opciones = new StringBuilder();
            for (int i = 0; i < libros.size(); i++) {
                opciones.append((i + 1)).append(". ").append(libros.get(i).getTitulo())
                        .append(" - Stock: ").append(libros.get(i).getStock()).append("\n");
            }

            int seleccion = Integer.parseInt(JOptionPane.showInputDialog(opciones.toString()));
            if (seleccion < 1 || seleccion > libros.size()) {
                JOptionPane.showMessageDialog(null, "Selección inválida.");
                return;
            }

            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a vender:"));
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
                return;
            }

            Libro libro = libros.get(seleccion - 1);
            boolean vendido = libroDAO.venderLibro(libro.getId(), cantidad);
            JOptionPane.showMessageDialog(null, vendido ? "Venta realizada con éxito." : "No hay suficiente stock.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la venta: " + e.getMessage());
        }
    }

    private static void comprarLibro(LibroDAO libroDAO) {
        try {
            String titulo = JOptionPane.showInputDialog("Título del libro a comprar:");
            List<Libro> libros = libroDAO.buscarPorTitulo(titulo);
            if (libros.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontró el libro.");
                return;
            }

            StringBuilder opciones = new StringBuilder();
            for (int i = 0; i < libros.size(); i++) {
                opciones.append((i + 1)).append(". ").append(libros.get(i).getTitulo())
                        .append(" - Stock: ").append(libros.get(i).getStock()).append("\n");
            }

            int seleccion = Integer.parseInt(JOptionPane.showInputDialog(opciones.toString()));
            if (seleccion < 1 || seleccion > libros.size()) {
                JOptionPane.showMessageDialog(null, "Selección inválida.");
                return;
            }

            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a comprar:"));
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida.");
                return;
            }

            Libro libro = libros.get(seleccion - 1);
            boolean actualizado = libroDAO.agregarStock(libro.getId(), cantidad);
            JOptionPane.showMessageDialog(null, actualizado ? "Compra realizada con éxito." : "Error al actualizar stock.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la compra: " + e.getMessage());
        }
    }

    private static void registrarUsuario(DLLUsuario dllUsuario) {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del usuario:");
            String email = JOptionPane.showInputDialog("Email del usuario:");
            String password = JOptionPane.showInputDialog("Contraseña:");

            // Validar campos vacíos o nulos
            if (nombre == null || email == null || password == null
                || nombre.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                return;
            }

            // Validar espacios en blanco dentro del nombre o email
            if (nombre.contains(" ") || email.contains(" ")) {
                JOptionPane.showMessageDialog(null, "El nombre y el email no deben contener espacios.");
                return;
            }

            // Validar formato del email usando expresión regular
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(null, "Formato de email inválido.");
                return;
            }

            Usuario usuario = new Usuario(nombre, email, password);
            boolean registrado = dllUsuario.insertarUsuario(usuario);

            JOptionPane.showMessageDialog(null, registrado
                ? "Usuario registrado exitosamente."
                : "Error al registrar usuario.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
        }
    }


    private static String obtenerCategoriaDesdeEntrada(String entrada, List<String> categorias) {
        if (entrada == null || entrada.isEmpty()) return null;

        try {
            int index = Integer.parseInt(entrada.trim());
            if (index >= 1 && index <= categorias.size()) {
                return categorias.get(index - 1);
            }
        } catch (NumberFormatException e) {
            // No es número, verificar si coincide con nombre
        }

        for (String cat : categorias) {
            if (cat.equalsIgnoreCase(entrada.trim())) {
                return cat;
            }
        }
        return null;
    }
}
