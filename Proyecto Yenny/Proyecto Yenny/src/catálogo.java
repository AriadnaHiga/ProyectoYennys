import java.util.ArrayList;
import javax.swing.JOptionPane;

public class catálogo {
    private ArrayList<Libro> libros;

    public catálogo() {
        libros = new ArrayList<>();
        // Agrego algunos libros iniciales
        libros.add(new Libro("El Principito", "Antoine de Saint-Exupéry", 1500, 10, categoriaLibro.INFANTIL));
        libros.add(new Libro("Cien Años de Soledad", "Gabriel García Márquez", 2500, 5, categoriaLibro.FICCION));
        libros.add(new Libro("1984", "George Orwell", 1800, 7, categoriaLibro.FICCION));
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
        return null; // Si no se encuentra el libro
    }

    public void mostrarCatalogo() {
        String texto = "";
        for (Libro libro : libros) {
            texto += "Título: " + libro.getTitulo() +
                     ", Autor: " + libro.getAutor() +
                     ", Precio: $" + libro.getPrecio() +
                     ", Stock: " + libro.getStock() +
                     ", Categoría: " + libro.getCategoria() + "\n";
        }
        if (texto.isEmpty()) {
            texto = "No hay libros en el catálogo.";
        }
        JOptionPane.showMessageDialog(null, texto, "Catálogo", JOptionPane.INFORMATION_MESSAGE);
    }
}

