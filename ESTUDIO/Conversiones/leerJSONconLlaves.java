package ESTUDIO.PokemonProcessor;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.*;

public class leerJSONconLlaves {
    public static void main(String[] args) {
        try {
            // 1. Leer el archivo JSON
            String content = Files.readString(Paths.get("pokemon.json"), StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(content);
            
            // 2. Obtener el array de Pokémon
            JSONArray pokemonArray = jsonObject.getJSONArray("Pokemon");
            
            // 3. Recorrer todos los Pokémon
            System.out.println("=== LISTA COMPLETA DE POKÉMON ===");
            for (int i = 0; i < pokemonArray.length(); i++) {
                JSONObject pokemon = pokemonArray.getJSONObject(i);
                
                // 4. Obtener propiedades de cada Pokémon
                int id = pokemon.getInt("id");
                String nombre = pokemon.getString("name");
                String tipo = pokemon.getString("type");
                String imagen = pokemon.getString("image_path");
                
                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Tipo: " + tipo);
                System.out.println("Imagen: " + imagen);
                System.out.println("----------------------");
            }
            
            // 5. Acceder a un Pokémon específico (ejemplo: primero)
            if (pokemonArray.length() > 0) {
                JSONObject primerPokemon = pokemonArray.getJSONObject(0);
                System.out.println("\n=== PRIMER POKÉMON ===");
                System.out.println("Nombre: " + primerPokemon.getString("name"));
            }
            
        } catch (Exception e) {
            System.err.println("Error al procesar el JSON: " + e.getMessage());
        }
    }
}