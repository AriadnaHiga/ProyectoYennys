import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class contabilidad {
    private Connection con;

    public contabilidad(Connection con) {
        this.con = con;
    }

    // 1. Ver saldo actual
    public void verBalance() {
        String sql = "SELECT saldo FROM caja LIMIT 1";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                double saldo = rs.getDouble("saldo");
                JOptionPane.showMessageDialog(null, "Saldo actual en caja: $" + saldo);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró información en la tabla caja.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el saldo de la base de datos.");
        }
    }

    // 2. Actualizar saldo (por ejemplo, sumando o restando una cantidad)
    public boolean actualizarSaldo(double nuevoSaldo) {
        String sql = "UPDATE caja SET saldo = ? WHERE id = 1";  // Asumo que la fila principal tiene id=1

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, nuevoSaldo);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el saldo en la base de datos.");
            return false;
        }
    }

    // 3. Insertar saldo inicial (en caso que no exista)
    public boolean insertarSaldoInicial(double saldoInicial) {
        String sql = "INSERT INTO caja (id, saldo) VALUES (1, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, saldoInicial);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al insertar el saldo inicial en la base de datos.");
            return false;
        }
    }

    // 4. Consultar movimientos 
    public void verMovimientos() {
        String sql = "SELECT fecha, descripcion, monto FROM movimientos WHERE caja_id = 1 ORDER BY fecha DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            StringBuilder movimientos = new StringBuilder("Movimientos recientes:\n");
            while (rs.next()) {
                String fecha = rs.getString("fecha");
                String descripcion = rs.getString("descripcion");
                double monto = rs.getDouble("monto");
                movimientos.append(fecha).append(" - ").append(descripcion).append(": $").append(monto).append("\n");
            }

            JOptionPane.showMessageDialog(null, movimientos.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener los movimientos de la base de datos.");
        }
    }
}
