import javax.swing.JOptionPane;

public class stock {
    private catálogo catalogo;

    public stock(catálogo catalogo) {
        this.catalogo = catalogo;
    }

    public void verStock() {
        catalogo.mostrarCatalogo();
    }
}
