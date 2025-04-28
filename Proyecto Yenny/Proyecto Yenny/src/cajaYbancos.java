import javax.swing.JOptionPane;

public class cajaYbancos {
    private double saldo;

    public cajaYbancos(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void agregarDinero(double monto) {
        saldo += monto;
    }

    public void sacarDinero(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
        } else {
            JOptionPane.showMessageDialog(null, "Fondos insuficientes en caja.");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public void mostrarSaldo() {
        JOptionPane.showMessageDialog(null, "Saldo actual en caja: $" + saldo);
    }
}