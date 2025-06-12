import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password;

    // Constructores
    public Usuario(int id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario() {}

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", password=" + password + "]";
    }

    // Conexión estática inicializada en bloque estático para mejor manejo de errores
    private static Connection con;

    static {
        try {
            con = ConexionMariaDB.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            // Opcional: mostrar mensaje gráfico
            // JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos:\n" + e.getMessage(),
            //       "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener todos los usuarios
    public static LinkedList<Usuario> mostrarUsuarios() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("password")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para agregar un usuario
    public static void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario agregado correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

	
	
	
