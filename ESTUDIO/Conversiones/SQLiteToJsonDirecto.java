import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;

public class SQLiteToJsonDirecto {
    private static final String DB_PATH = "inventario.db";
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH)) {
            // 1. Crear tabla si no existe
            crearTablaProductos(conn);
            
            // 2. Insertar datos de ejemplo
            insertarDatosEjemplo(conn);
            
            // 3. Obtener todos los productos como JSON
            JSONArray productosJson = obtenerProductosComoJson(conn);
            System.out.println("Productos en JSON:\n" + productosJson.toString(2));
            
            // 4. Insertar nuevo producto desde JSON
            JSONObject nuevoProducto = new JSONObject();
            nuevoProducto.put("nombre", "Impresora");
            nuevoProducto.put("precio", 149.99);
            nuevoProducto.put("stock", 15);
            
            insertarDesdeJson(conn, nuevoProducto);
            System.out.println("\nProductos después de inserción:\n" + 
                             obtenerProductosComoJson(conn).toString(2));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void crearTablaProductos(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nombre TEXT NOT NULL," +
                     "precio REAL NOT NULL," +
                     "stock INTEGER NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    private static void insertarDatosEjemplo(Connection conn) throws SQLException {
        // Verificar si la tabla está vacía
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM productos")) {
            if (rs.getInt(1) > 0) return;
        }
        
        // Insertar datos iniciales
        String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "Monitor");
            pstmt.setDouble(2, 199.99);
            pstmt.setInt(3, 10);
            pstmt.executeUpdate();
            
            pstmt.setString(1, "Teclado");
            pstmt.setDouble(2, 29.99);
            pstmt.setInt(3, 50);
            pstmt.executeUpdate();
        }
    }
    
    private static JSONArray obtenerProductosComoJson(Connection conn) throws SQLException {
        JSONArray array = new JSONArray();
        String sql = "SELECT * FROM productos";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id", rs.getInt("id"));
                obj.put("nombre", rs.getString("nombre"));
                obj.put("precio", rs.getDouble("precio"));
                obj.put("stock", rs.getInt("stock"));
                array.put(obj);
            }
        }
        return array;
    }
    
    private static void insertarDesdeJson(Connection conn, JSONObject jsonProducto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jsonProducto.getString("nombre"));
            pstmt.setDouble(2, jsonProducto.getDouble("precio"));
            pstmt.setInt(3, jsonProducto.getInt("stock"));
            pstmt.executeUpdate();
        }
    }
}