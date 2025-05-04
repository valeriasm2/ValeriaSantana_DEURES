import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;

public class SistemaFacturacion {
    private static final String DB_PATH = "facturacion.db";
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH)) {
            // Inicializar base de datos
            inicializarDB(conn);
            
            // 1. Crear factura desde JSON
            JSONObject facturaJson = new JSONObject();
            facturaJson.put("cliente", "Juan Pérez");
            facturaJson.put("fecha", "2023-05-15");
            
            JSONArray items = new JSONArray();
            
            JSONObject item1 = new JSONObject();
            item1.put("producto_id", 1);
            item1.put("cantidad", 2);
            
            JSONObject item2 = new JSONObject();
            item2.put("producto_id", 2);
            item2.put("cantidad", 3);
            
            items.put(item1);
            items.put(item2);
            
            facturaJson.put("items", items);
            
            // 2. Procesar factura
            procesarFactura(conn, facturaJson);
            
            // 3. Obtener reporte en JSON
            JSONObject reporte = generarReporte(conn);
            System.out.println("Reporte de ventas:\n" + reporte.toString(2));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // [Resto de métodos de la clase...]
}