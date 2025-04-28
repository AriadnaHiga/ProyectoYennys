public class Libro {
    private String titulo;
    private String autor;
    private double precio;
    private int stock;
    private categoriaLibro categoria;

    // Constructor
    public Libro(String titulo, String autor, double precio, int stock, categoriaLibro categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Métodos getter
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public categoriaLibro getCategoria() {
        return categoria;
    }

    // Método para vender una cantidad de libros
    public boolean vender(int cantidad) {
        if (stock >= cantidad) {
            stock -= cantidad; // Restamos la cantidad vendida del stock
            return true; // Venta exitosa
        }
        return false; // No hay suficiente stock
    }

    // Método para agregar stock
    public void agregarStock(int cantidad) {
        stock += cantidad;
    }
}

