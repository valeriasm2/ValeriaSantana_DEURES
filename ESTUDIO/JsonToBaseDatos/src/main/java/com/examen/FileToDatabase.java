package com.examen;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileToDatabase {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java FileToDatabase <animales_marinos.json> <animales_marinos.db>");
            return;
        }

        String jsonFile = args[0];
        String dbFile = args[1];

        try {
            // 1. Leer archivo JSON
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFile)));
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonContent);

            // 2. Crear conexión a la base de datos
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);

            // 3. Crear tablas
            createTables(conn);

            // 4. Insertar datos
            if (jsonObject.containsKey("mamiferos_marinos")) {
                insertMamiferos(conn, (JSONArray) jsonObject.get("mamiferos_marinos"));
            }
            if (jsonObject.containsKey("tiburones")) {
                insertTiburones(conn, (JSONArray) jsonObject.get("tiburones"));
            }

            System.out.println("Proceso completado exitosamente!");
            conn.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        
        // Tabla para mamíferos marinos
        stmt.execute("CREATE TABLE IF NOT EXISTS mamiferos_marinos (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "nombre_cientifico TEXT NOT NULL, " +
                   "nombre_comun TEXT, " +
                   "longitud_promedio REAL, " +
                   "peso_promedio_kg REAL, " +
                   "habitat TEXT, " +
                   "dieta TEXT)");
        
        // Tabla para tiburones
        stmt.execute("CREATE TABLE IF NOT EXISTS tiburones (" +
                   "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   "nombre_cientifico TEXT NOT NULL, " +
                   "nombre_comun TEXT, " +
                   "longitud_promedio REAL, " +
                   "habitats TEXT, " +
                   "especie_protegida INTEGER)");
        
        stmt.close();
    }

    private static void insertMamiferos(Connection conn, JSONArray mamiferos) throws SQLException {
        String sql = "INSERT INTO mamiferos_marinos " +
                     "(nombre_cientifico, nombre_comun, longitud_promedio, peso_promedio_kg, habitat, dieta) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        for (Object obj : mamiferos) {
            JSONObject mamifero = (JSONObject) obj;
            pstmt.setString(1, (String) mamifero.get("nombre_cientifico"));
            pstmt.setString(2, (String) mamifero.get("nombre_comun"));
            pstmt.setDouble(3, (double) mamifero.get("longitud_promedio"));
            pstmt.setDouble(4, (double) mamifero.get("peso_promedio_kg"));
            pstmt.setString(5, (String) mamifero.get("habitat"));
            pstmt.setString(6, mamifero.get("dieta").toString());
            pstmt.executeUpdate();
        }
        
        pstmt.close();
    }

    private static void insertTiburones(Connection conn, JSONArray tiburones) throws SQLException {
        String sql = "INSERT INTO tiburones " +
                     "(nombre_cientifico, nombre_comun, longitud_promedio, habitats, especie_protegida) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        for (Object obj : tiburones) {
            JSONObject tiburon = (JSONObject) obj;
            pstmt.setString(1, (String) tiburon.get("nombre_cientifico"));
            pstmt.setString(2, (String) tiburon.get("nombre_comun"));
            pstmt.setDouble(3, (double) tiburon.get("longitud_promedio"));
            pstmt.setString(4, tiburon.get("habitats").toString());
            pstmt.setInt(5, (boolean) tiburon.get("especie_protegida") ? 1 : 0);
            pstmt.executeUpdate();
        }
        
        pstmt.close();
    }
}