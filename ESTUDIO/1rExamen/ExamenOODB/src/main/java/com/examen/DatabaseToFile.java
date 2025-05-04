package com.examen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// Executa aquest programa amb:
// ./run.sh com.examen.DatabaseToFile

public class DatabaseToFile {

    public static void main(String[] args) {
        AppData db = AppData.getInstance();
        db.connect("./data/animals.sqlite");

        System.out.println("Exportant dades de la base de dades a JSON...");
        exportDataToJson(db);

        db.close();
        System.out.println("Exportació completada.");
    }

    public static void exportDataToJson(AppData db) {
        JSONObject root = new JSONObject();

        // Procesar mamífers
        JSONArray mamifersArray = processMamifers(db);
        root.put("mamifers", mamifersArray);

        // Procesar aus
        JSONArray ausArray = processAus(db);
        root.put("aus", ausArray);

        // Guardar a archivo
        saveToFile(root, "data/animals.json");
    }

    private static JSONArray processMamifers(AppData db) {
        JSONArray mamifers = new JSONArray();
        ArrayList<HashMap<String, Object>> results = db.query("SELECT * FROM mamifers");
        
        for (HashMap<String, Object> row : results) {
            JSONObject mamifer = new JSONObject();
            mamifer.put("habitat", row.get("habitat"));
            mamifer.put("pes_mitja_kg", row.get("pes_mitja_kg"));
            mamifer.put("ordre", row.get("ordre"));
            mamifer.put("nom_comu", row.get("nom_comu"));
            mamifer.put("alimentacio", row.get("alimentacio"));
            
            JSONArray esperancaVida = new JSONArray();
            esperancaVida.put(row.get("esperanca_vida_min"));
            esperancaVida.put(row.get("esperanca_vida_max"));
            mamifer.put("esperanca_vida_anys", esperancaVida);
            
            mamifer.put("nom_cientific", row.get("nom_cientific"));
            mamifer.put("caracteristica_distintiva", row.get("caracteristica_distintiva"));
            
            mamifers.put(mamifer);
        }
        
        return mamifers;
    }

    private static JSONArray processAus(AppData db) {
        JSONArray aus = new JSONArray();
        ArrayList<HashMap<String, Object>> results = db.query("SELECT * FROM aus");
        
        for (HashMap<String, Object> row : results) {
            JSONObject au = new JSONObject();
            au.put("habitat", row.get("habitat"));
            au.put("ordre", row.get("ordre"));
            au.put("migratoria", row.get("migratoria"));
            au.put("nom_comu", row.get("nom_comu"));
            au.put("alimentacio", row.get("alimentacio"));
            au.put("nom_cientific", row.get("nom_cientific"));
            au.put("caracteristica_distintiva", row.get("caracteristica_distintiva"));
            au.put("envergadura_mitja_cm", row.get("envergadura_mitja_cm"));
            
            aus.put(au);
        }
        
        return aus;
    }

    private static void saveToFile(JSONObject data, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(data.toString(4)); // 4 espacios de indentación
            System.out.println("Dades guardades correctament a " + filePath);
        } catch (IOException e) {
            System.err.println("Error al guardar l'arxiu JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}