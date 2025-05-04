package com.examen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

// Construeix la base de dades amb
// ./run.sh com.examen.BuildDatabase

public class BuildDatabase {

    public static void main(String[] args) {

        AppData db = AppData.getInstance();
        db.connect("./data/cars.sqlite");

        System.out.println("\nIniciar les dades de la base de dades:");
        initData();

        db.close();
    }

    public static void initData() {

        AppData db = AppData.getInstance();

        db.update("DROP TABLE IF EXISTS cars");

        db.update("""
            CREATE TABLE IF NOT EXISTS cars (
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            manufacturer TEXT,
            year INTEGER,
            cylinder TEXT,
            image TEXT)
            """);

        try {
            String content = new String(Files.readAllBytes(Paths.get("data/cars.json")));
            JSONArray items = new JSONArray(content);
            for (int i = 0; i < items.length(); i++) {
                JSONObject p = items.getJSONObject(i);
                String updateSQL = String.format(
                    "INSERT INTO cars (id, name, manufacturer, year, cylinder, image) VALUES (%d, '%s', '%s', '%d', '%s', '%s')",
                    p.getInt("id"),
                    p.getString("name"),
                    p.optString("manufacturer", ""),
                    p.optInt("year", -1),
                    p.optString("cylinder", ""),
                    p.optString("image", "")
                );
                db.update(updateSQL);
                System.out.println("Add car: " + p.getString("name"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
