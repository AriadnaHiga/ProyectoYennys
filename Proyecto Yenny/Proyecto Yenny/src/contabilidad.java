import javax.swing.JOptionPane;

public class contabilidad {
    private cajaYbancos caja;

    public contabilidad(cajaYbancos caja) {
        this.caja = caja;
    }

    public void verBalance() {
        caja.mostrarSaldo();
    }
}