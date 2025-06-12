import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class cajaYbancos {

    private double saldo;
    private Connection con;

    public cajaYbancos(Connection con) {
        this.con = con;
        this.saldo = obtenerSaldoDeBD(); // opcional
    }

    public void agregarDinero(double monto) {
        saldo += monto;
    }

    
    public double getSaldo() {
        return saldo;
    }

    public void sacarDinero(double monto) {
        saldo -= monto;
    }

    
    
    public void actualizarSaldoEnBD() {
        String sql = "UPDATE caja SET saldo = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, saldo);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar saldo en caja.");
        }
    }

    // Método para obtener el saldo actual desde la base de datos
    private double obtenerSaldoDeBD() {
        try (PreparedStatement stmt = con.prepareStatement("SELECT saldo FROM caja LIMIT 1");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

