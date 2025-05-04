import org.json.JSONArray;
import org.json.JSONObject;

public class JsonConversiones {
    public static void main(String[] args) {
        // Ejemplo 1: Crear JsonObject y convertirlo a JsonArray
        JSONObject objeto1 = new JSONObject();
        objeto1.put("id", 1);
        objeto1.put("nombre", "Monitor");
        objeto1.put("precio", 199.99);
        
        JSONObject objeto2 = new JSONObject();
        objeto2.put("id", 2);
        objeto2.put("nombre", "Teclado");
        objeto2.put("precio", 29.99);
        
        // Convertir objetos individuales a un array
        JSONArray array = new JSONArray();
        array.put(objeto1);
        array.put(objeto2);
        
        System.out.println("Array JSON:\n" + array.toString(2));
        
        // Ejemplo 2: Extraer objetos desde array
        JSONObject primerElemento = array.getJSONObject(0);
        System.out.println("\nPrimer elemento:\n" + primerElemento.toString(2));
        
        // Ejemplo 3: Convertir array completo a objeto con clave
        JSONObject objetoContenedor = new JSONObject();
        objetoContenedor.put("productos", array);
        System.out.println("\nObjeto contenedor:\n" + objetoContenedor.toString(2));
        
        // Ejemplo 4: Recuperar array desde objeto contenedor
        JSONArray arrayRecuperado = objetoContenedor.getJSONArray("productos");
        System.out.println("\nArray recuperado:\n" + arrayRecuperado.toString(2));
    }
}