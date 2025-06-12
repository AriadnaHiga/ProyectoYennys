import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class stock {
    private Connection con;

    public stock(Connection con) {
        this.con = con;
    }

    public void verStock() {
        String sql = "SELECT titulo, autor, stock FROM catalogo";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            StringBuilder sb = new StringBuilder("Stock actual de libros:\n\n");
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int stock = rs.getInt("stock");

                sb.append("Título: ").append(titulo)
                  .append(" | Autor: ").append(autor)
                  .append(" | Stock: ").append(stock).append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el stock: " + e.getMessage());
        }
    }
}
