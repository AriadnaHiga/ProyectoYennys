public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private double precio;
    private int stock;
    private String categoria;

    public Libro(int id, String titulo, String autor, double precio, int stock, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters
    public int getId() {
        return id;
    }

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

    public String getCategoria() {
        return categoria;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Método para vender libros (disminuye stock)
    public void vender(int cantidad) {
        if (cantidad > 0 && stock >= cantidad) {
            stock -= cantidad;
        } else {
            throw new IllegalArgumentException("Cantidad inválida o stock insuficiente");
        }
    }

    // Método para agregar stock (aumenta stock)
    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            stock += cantidad;
        } else {
            throw new IllegalArgumentException("Cantidad inválida");
        }
    }
}
