package com.examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

// Executa aquest programa amb:
// ./run.sh com.examen.FileToDatabase



public class FileToDatabase {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data/personatges.sqlite")) {
            
            // Crear tablas
            createTables(connection);
            
            // Leer archivo JSON
            String jsonContent = new String(Files.readAllBytes(Paths.get("data/personatges.json")));
            JSONObject data = new JSONObject(jsonContent);
            
            // Insertar datos
            insertCantants(connection, data.getJSONArray("cantants"));
            insertEsportistes(connection, data.getJSONArray("esportistes"));
            insertCientifics(connection, data.getJSONArray("cientifics"));
            
            System.out.println("Base de datos creada exitosamente!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            // Tabla cantants
            stmt.execute("CREATE TABLE IF NOT EXISTS cantants (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "nom_complet TEXT NOT NULL, " +
                         "any_naixement INTEGER, " +
                         "discs_aurats TEXT, " +
                         "pais_naixement TEXT, " +
                         "grup_principal TEXT)");
            
            // Tabla esportistes
            stmt.execute("CREATE TABLE IF NOT EXISTS esportistes (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "nom_complet TEXT NOT NULL, " +
                         "any_naixement INTEGER, " +
                         "copes_mundials INTEGER, " +
                         "pais_naixement TEXT, " +
                         "equip_principal TEXT)");
            
            // Tabla cientifics
            stmt.execute("CREATE TABLE IF NOT EXISTS cientifics (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "nom_complet TEXT NOT NULL, " +
                         "any_naixement INTEGER, " +
                         "nobels INTEGER, " +
                         "pais_naixement TEXT, " +
                         "universitat_principal TEXT)");
        }
    }

    private static void insertCantants(Connection connection, JSONArray cantants) throws Exception {
        String sql = "INSERT INTO cantants (nom_complet, any_naixement, discs_aurats, pais_naixement, grup_principal) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < cantants.length(); i++) {
                JSONObject cantant = cantants.getJSONObject(i);
                ps.setString(1, cantant.getString("nom_complet"));
                ps.setInt(2, cantant.getInt("any_naixement"));
                ps.setString(3, cantant.getString("discs_aurats"));
                ps.setString(4, cantant.getString("pais_naixement"));
                ps.setString(5, cantant.getString("grup_principal"));
                ps.executeUpdate();
            }
        }
    }

    private static void insertEsportistes(Connection connection, JSONArray esportistes) throws Exception {
        String sql = "INSERT INTO esportistes (nom_complet, any_naixement, copes_mundials, pais_naixement, equip_principal) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < esportistes.length(); i++) {
                JSONObject esportista = esportistes.getJSONObject(i);
                ps.setString(1, esportista.getString("nom_complet"));
                ps.setInt(2, esportista.getInt("any_naixement"));
                ps.setInt(3, esportista.getInt("copes_mundials"));
                ps.setString(4, esportista.getString("pais_naixement"));
                ps.setString(5, esportista.getString("equip_principal"));
                ps.executeUpdate();
            }
        }
    }

    private static void insertCientifics(Connection connection, JSONArray cientifics) throws Exception {
        String sql = "INSERT INTO cientifics (nom_complet, any_naixement, nobels, pais_naixement, universitat_principal) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < cientifics.length(); i++) {
                JSONObject cientific = cientifics.getJSONObject(i);
                ps.setString(1, cientific.getString("nom_complet"));
                ps.setInt(2, cientific.getInt("any_naixement"));
                ps.setInt(3, cientific.getInt("nobels"));
                ps.setString(4, cientific.getString("pais_naixement"));
                ps.setString(5, cientific.getString("universitat_principal"));
                ps.executeUpdate();
            }
        }
    }
}