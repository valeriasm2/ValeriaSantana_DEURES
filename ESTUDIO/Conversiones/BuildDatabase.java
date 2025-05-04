import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Clase principal para construir la base de datos de animales a partir de un archivo JSON.
 */
public class BuildDatabase {

    /**
     * Método principal que inicia la construcción de la base de datos.
     * @param args Argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        // Obtener instancia de la clase de acceso a datos
        AppData db = AppData.getInstance();
        
        // Conectar a la base de datos SQLite
        db.connect("./data/animales.sqlite");

        System.out.println("\nIniciando la base de datos de animales:");
        
        // Inicializar datos en la base de datos
        initData();

        // Cerrar la conexión a la base de datos
        db.close();
    }

    /**
     * Método para inicializar los datos en la base de datos.
     * Crea las tablas e inserta los datos del archivo JSON.
     */
    public static void initData() {
        // Obtener instancia de la clase de acceso a datos
        AppData db = AppData.getInstance();

        // Eliminar tablas existentes (si las hay) para empezar desde cero
        db.update("DROP TABLE IF EXISTS mamiferos_marinos");
        db.update("DROP TABLE IF EXISTS tiburones");
        db.update("DROP TABLE IF EXISTS aves");
        db.update("DROP TABLE IF EXISTS reptiles");
        db.update("DROP TABLE IF EXISTS insectos");
        db.update("DROP TABLE IF EXISTS animales_domesticos");

        // Crear tabla para mamíferos marinos con sus campos correspondientes
        db.update("""
            CREATE TABLE IF NOT EXISTS mamiferos_marinos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_cientifico TEXT NOT NULL,
                nombre_comun TEXT NOT NULL,
                longitud_promedio REAL,
                peso_promedio_kg REAL,
                habitat TEXT,
                dieta TEXT,  -- Almacena un array JSON como texto
                esperanza_vida INTEGER,
                conservacion TEXT)
            """);

        // Crear tabla para tiburones
        db.update("""
            CREATE TABLE IF NOT EXISTS tiburones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_cientifico TEXT NOT NULL,
                nombre_comun TEXT NOT NULL,
                longitud_promedio REAL,
                habitats TEXT,  -- Almacena un array JSON como texto
                especie_protegida INTEGER,  -- 1 para true, 0 para false
                conservacion TEXT)
            """);

        // Crear tabla para aves
        db.update("""
            CREATE TABLE IF NOT EXISTS aves (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_cientifico TEXT NOT NULL,
                nombre_comun TEXT NOT NULL,
                envergadura_cm REAL,
                longitud_promedio REAL,
                habitats TEXT,  -- Almacena un array JSON como texto
                dieta TEXT,     -- Almacena un array JSON como texto
                migratoria INTEGER,  -- 1 para true, 0 para false
                conservacion TEXT)
            """);

        // Crear tabla para reptiles
        db.update("""
            CREATE TABLE IF NOT EXISTS reptiles (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_cientifico TEXT NOT NULL,
                nombre_comun TEXT NOT NULL,
                longitud_promedio REAL,
                peso_promedio_kg REAL,
                habitat TEXT,
                habitats TEXT,  -- Almacena un array JSON como texto
                conservacion TEXT)
            """);

        // Crear tabla para insectos
        db.update("""
            CREATE TABLE IF NOT EXISTS insectos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_cientifico TEXT NOT NULL,
                nombre_comun TEXT NOT NULL,
                envergadura_cm REAL,
                tamano_cm REAL,
                migratoria INTEGER,  -- 1 para true, 0 para false
                ruta_migratoria TEXT,  -- Almacena un array JSON como texto
                habitat TEXT,
                organizacion_social TEXT,
                conservacion TEXT)
            """);

        // Crear tabla para animales domésticos
        db.update("""
            CREATE TABLE IF NOT EXISTS animales_domesticos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_cientifico TEXT NOT NULL,
                nombre_comun TEXT NOT NULL,
                razas TEXT,  -- Almacena un array JSON como texto
                esperanza_vida INTEGER,
                dieta TEXT)  -- Almacena un array JSON como texto
            """);

        // Insertar datos desde el archivo JSON
        try {
            // Leer el contenido completo del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get("data/animales.json")));
            
            // Parsear el contenido a un objeto JSON
            JSONObject data = new JSONObject(content);

            // ===== INSERTAR MAMÍFEROS MARINOS =====
            JSONArray mamiferos = data.getJSONArray("mamiferos_marinos");
            for (int i = 0; i < mamiferos.length(); i++) {
                JSONObject animal = mamiferos.getJSONObject(i);
                
                // Construir la sentencia SQL de inserción
                String updateSQL = String.format(
                    "INSERT INTO mamiferos_marinos (nombre_cientifico, nombre_comun, longitud_promedio, " +
                    "peso_promedio_kg, habitat, dieta, esperanza_vida, conservacion) " +
                    "VALUES ('%s', '%s', %f, %f, '%s', '%s', %d, '%s')",
                    animal.getString("nombre_cientifico"),
                    animal.getString("nombre_comun"),
                    animal.getDouble("longitud_promedio"),
                    animal.getDouble("peso_promedio_kg"),
                    animal.getString("habitat"),
                    animal.getJSONArray("dieta").toString(),  // Convertir array a string JSON
                    animal.getInt("esperanza_vida"),
                    animal.getString("conservacion")
                );
                
                // Ejecutar la inserción
                db.update(updateSQL);
                System.out.println("Añadido mamífero marino: " + animal.getString("nombre_comun"));
            }

            // ===== INSERTAR TIBURONES =====
            JSONArray tiburones = data.getJSONArray("tiburones");
            for (int i = 0; i < tiburones.length(); i++) {
                JSONObject animal = tiburones.getJSONObject(i);
                
                String updateSQL = String.format(
                    "INSERT INTO tiburones (nombre_cientifico, nombre_comun, longitud_promedio, " +
                    "habitats, especie_protegida, conservacion) " +
                    "VALUES ('%s', '%s', %f, '%s', %d, '%s')",
                    animal.getString("nombre_cientifico"),
                    animal.getString("nombre_comun"),
                    animal.getDouble("longitud_promedio"),
                    animal.getJSONArray("habitats").toString(),  // Convertir array a string JSON
                    animal.getBoolean("especie_protegida") ? 1 : 0,  // Convertir boolean a 1/0
                    animal.getString("conservacion")
                );
                
                db.update(updateSQL);
                System.out.println("Añadido tiburón: " + animal.getString("nombre_comun"));
            }

            // ===== INSERTAR AVES =====
            JSONArray aves = data.getJSONArray("aves");
            for (int i = 0; i < aves.length(); i++) {
                JSONObject animal = aves.getJSONObject(i);
                
                // Manejo de campos opcionales con has() y optString()
                String updateSQL = String.format(
                    "INSERT INTO aves (nombre_cientifico, nombre_comun, envergadura_cm, " +
                    "longitud_promedio, habitats, dieta, migratoria, conservacion) " +
                    "VALUES ('%s', '%s', %s, %s, '%s', '%s', %s, '%s')",
                    animal.getString("nombre_cientifico"),
                    animal.getString("nombre_comun"),
                    // Campo opcional: envergadura_cm
                    animal.has("envergadura_cm") ? String.valueOf(animal.getDouble("envergadura_cm")) : "NULL",
                    // Campo opcional: longitud_promedio
                    animal.has("longitud_promedio") ? String.valueOf(animal.getDouble("longitud_promedio")) : "NULL",
                    // habitats puede venir como array o como campo simple habitat
                    animal.has("habitats") ? animal.getJSONArray("habitats").toString() 
                                          : "[\"" + animal.optString("habitat", "") + "\"]",
                    // Campo opcional: dieta
                    animal.has("dieta") ? animal.getJSONArray("dieta").toString() : "NULL",
                    // Campo opcional: migratoria (convertir boolean a 1/0)
                    animal.has("migratoria") ? (animal.getBoolean("migratoria") ? "1" : "0") : "NULL",
                    // Campo opcional: conservacion
                    animal.optString("conservacion", "")
                );
                
                db.update(updateSQL);
                System.out.println("Añadido ave: " + animal.getString("nombre_comun"));
            }

            // ===== INSERTAR REPTILES =====
            JSONArray reptiles = data.getJSONArray("reptiles");
            for (int i = 0; i < reptiles.length(); i++) {
                JSONObject animal = reptiles.getJSONObject(i);
                
                String updateSQL = String.format(
                    "INSERT INTO reptiles (nombre_cientifico, nombre_comun, longitud_promedio, " +
                    "peso_promedio_kg, habitat, habitats, conservacion) " +
                    "VALUES ('%s', '%s', %f, %s, '%s', '%s', '%s')",
                    animal.getString("nombre_cientifico"),
                    animal.getString("nombre_comun"),
                    animal.getDouble("longitud_promedio"),
                    // Campo opcional: peso_promedio_kg
                    animal.has("peso_promedio_kg") ? String.valueOf(animal.getDouble("peso_promedio_kg")) : "NULL",
                    // Campo opcional: habitat
                    animal.optString("habitat", ""),
                    // Campo opcional: habitats
                    animal.has("habitats") ? animal.getJSONArray("habitats").toString() : "NULL",
                    // Campo opcional: conservacion
                    animal.optString("conservacion", "")
                );
                
                db.update(updateSQL);
                System.out.println("Añadido reptil: " + animal.getString("nombre_comun"));
            }

            // ===== INSERTAR INSECTOS =====
            JSONArray insectos = data.getJSONArray("insectos");
            for (int i = 0; i < insectos.length(); i++) {
                JSONObject animal = insectos.getJSONObject(i);
                
                String updateSQL = String.format(
                    "INSERT INTO insectos (nombre_cientifico, nombre_comun, envergadura_cm, " +
                    "tamano_cm, migratoria, ruta_migratoria, habitat, organizacion_social, conservacion) " +
                    "VALUES ('%s', '%s', %s, %s, %s, '%s', '%s', '%s', '%s')",
                    animal.getString("nombre_cientifico"),
                    animal.getString("nombre_comun"),
                    // Campo opcional: envergadura_cm
                    animal.has("envergadura_cm") ? String.valueOf(animal.getDouble("envergadura_cm")) : "NULL",
                    // Campo opcional: tamano_cm
                    animal.has("tamano_cm") ? String.valueOf(animal.getDouble("tamano_cm")) : "NULL",
                    // Campo opcional: migratoria (convertir boolean a 1/0)
                    animal.has("migratoria") ? (animal.getBoolean("migratoria") ? "1" : "0") : "NULL",
                    // Campo opcional: ruta_migratoria
                    animal.has("ruta_migratoria") ? animal.getJSONArray("ruta_migratoria").toString() : "NULL",
                    // Campo opcional: habitat
                    animal.optString("habitat", ""),
                    // Campo opcional: organizacion_social
                    animal.optString("organizacion_social", ""),
                    // Campo opcional: conservacion
                    animal.optString("conservacion", "")
                );
                
                db.update(updateSQL);
                System.out.println("Añadido insecto: " + animal.getString("nombre_comun"));
            }

            // ===== INSERTAR ANIMALES DOMÉSTICOS =====
            JSONArray domesticos = data.getJSONArray("animales_domesticos");
            for (int i = 0; i < domesticos.length(); i++) {
                JSONObject animal = domesticos.getJSONObject(i);
                
                String updateSQL = String.format(
                    "INSERT INTO animales_domesticos (nombre_cientifico, nombre_comun, razas, " +
                    "esperanza_vida, dieta) " +
                    "VALUES ('%s', '%s', '%s', %d, '%s')",
                    animal.getString("nombre_cientifico"),
                    animal.getString("nombre_comun"),
                    animal.getJSONArray("razas").toString(),  // Convertir array a string JSON
                    animal.getInt("esperanza_vida"),
                    animal.getJSONArray("dieta").toString()   // Convertir array a string JSON
                );
                
                db.update(updateSQL);
                System.out.println("Añadido animal doméstico: " + animal.getString("nombre_comun"));
            }

        } catch (IOException e) {
            // Manejar errores de lectura del archivo JSON
            System.err.println("Error al leer el archivo JSON:");
            e.printStackTrace();
        }
    }
}