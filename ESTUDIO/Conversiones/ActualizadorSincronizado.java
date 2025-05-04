import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class ActualizadorSincronizado {
    private static final String DB_PATH = "productos.db";
    private static final String JSON_PATH = "productos.json";
    
    public static void main(String[] args) {
        actualizarProducto(2, "Teclado Mecánico", 59.99, 25);
    }
    
    // [Resto de métodos de la clase...]
}