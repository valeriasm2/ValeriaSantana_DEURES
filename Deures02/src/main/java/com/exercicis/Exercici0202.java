package com.exercicis;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONArray;

public class Exercici0202 {

    public static Scanner scanner;
    public static Locale defaultLocale;

    // ./run.sh com.exercicis.Exercici0202
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        // Llamadas a los métodos de ejemplo
        JSONAstronautesToArrayList("./data/astronautes.json");

        showEsportistesOrdenatsPerMedalla("./data/esportistes.json", "or");
        showEsportistesOrdenatsPerMedalla("./data/esportistes.json", "plata");

        ArrayList<HashMap<String, Object>> dades = new ArrayList<>();

        ArrayList<String> caracteristiquesPacific = new ArrayList<>();
        caracteristiquesPacific.add("És l'oceà més gran del món");
        caracteristiquesPacific.add("Conté la fossa de les Marianes, la més profunda del món");
        caracteristiquesPacific.add("Conté una illa de plàstics contaminants.");

        ArrayList<String> caracteristiquesAtlantic = new ArrayList<>();
        caracteristiquesAtlantic.add("Separa Amèrica d'Europa i Àfrica");
        caracteristiquesAtlantic.add("Conté el famós Triangle de les Bermudes");

        ArrayList<String> caracteristiquesMediterrani = new ArrayList<>();
        caracteristiquesMediterrani.add("És un mar gairebé tancat");
        caracteristiquesMediterrani.add("Connecta amb l'oceà Atlàntic a través de l'estret de Gibraltar");

        dades.add(crearMassaAigua("Oceà Pacífic", "oceà", 168723000, 10924, caracteristiquesPacific));
        dades.add(crearMassaAigua("Oceà Atlàntic", "oceà", 85133000, 8486, caracteristiquesAtlantic));
        dades.add(crearMassaAigua("Oceà Índic", "oceà", 70560000, 7450, new ArrayList<>()));
        dades.add(crearMassaAigua("Oceà Àrtic", "oceà", 15558000, 5450, new ArrayList<>()));
        dades.add(crearMassaAigua("Mar Mediterrani", "mar", 2500000, 5121, caracteristiquesMediterrani));
        dades.add(crearMassaAigua("Mar Carib", "mar", 2754000, 7686, new ArrayList<>()));
        dades.add(crearMassaAigua("Mar de la Xina Meridional", "mar", 3500000, 5560, new ArrayList<>()));

        try {
            generarJSON(dades, "./data/aigua.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Locale.setDefault(defaultLocale);
        scanner.close();
    }

    public static void showJSONAstronautes(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            
            // Parsear el JSON como un objeto
            JSONObject jsonObject = new JSONObject(content);
    
            // Obtener el arreglo de astronautes desde la clave "astronautes"
            JSONArray astronautesArray = jsonObject.getJSONArray("astronautes");
    
            for (int i = 0; i < astronautesArray.length(); i++) {
                JSONObject astronauta = astronautesArray.getJSONObject(i);
                System.out.println("> Astronauta " + i + ":");
                System.out.println("   Nom: " + astronauta.getString("nom"));
                System.out.println("   Naixement: " + astronauta.getInt("any_naixement"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static ArrayList<HashMap<String, Object>> JSONAstronautesToArrayList(String filePath) {
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
        
            // Parsear el JSON como un objeto
            JSONObject jsonObject = new JSONObject(content);
        
            // Obtener el arreglo de astronautes desde la clave "astronautes"
            JSONArray astronautesArray = jsonObject.getJSONArray("astronautes");
        
            // Iterar sobre cada astronauta y agregar los datos al ArrayList
            for (int i = 0; i < astronautesArray.length(); i++) {
                JSONObject astronautaObject = astronautesArray.getJSONObject(i);
                HashMap<String, Object> astronautaMap = new HashMap<>();
                astronautaMap.put("nom", astronautaObject.getString("nom"));
                astronautaMap.put("any_naixement", astronautaObject.getInt("any_naixement"));
                rst.add(astronautaMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    // Método para convertir el JSON de planetes a un ArrayList
    public static ArrayList<HashMap<String, Object>> JSONPlanetesToArrayList(String filePath) {
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parsear el JSON como un objeto
            JSONObject jsonObject = new JSONObject(content);

            // Obtener el arreglo de planetas desde la clave "planetes"
            JSONArray planetesArray = jsonObject.getJSONArray("planetes");

            // Iterar sobre cada planeta y agregar los datos al ArrayList
            for (int i = 0; i < planetesArray.length(); i++) {
                JSONObject planetaObject = planetesArray.getJSONObject(i);
                HashMap<String, Object> planetaMap = new HashMap<>();
                planetaMap.put("nom", planetaObject.getString("nom"));
                planetaMap.put("diametre", planetaObject.getDouble("diametre"));
                planetaMap.put("distancia_sol", planetaObject.getDouble("distancia_sol"));
                rst.add(planetaMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    // Método para mostrar los planetas ordenados
    public static void mostrarPlanetesOrdenats(String filePath, String tipusOrdenacio) {
        ArrayList<HashMap<String, Object>> planetes = JSONPlanetesToArrayList(filePath);

        if (!tipusOrdenacio.equals("diametre") && !tipusOrdenacio.equals("distancia_sol")) {
            throw new IllegalArgumentException("Tipus d'ordenació invàlid: " + tipusOrdenacio + ". Tipus vàlids: 'diametre' o 'distancia_sol'.");
        }

        planetes.sort((planeta0, planeta1) -> {
            Double a = (Double) planeta0.get(tipusOrdenacio);
            Double b = (Double) planeta1.get(tipusOrdenacio);
            return a.compareTo(b);
        });

        String capitalizedOrdenacio = tipusOrdenacio.substring(0, 1).toUpperCase() + tipusOrdenacio.substring(1).toLowerCase();

        System.out.println("┌──────────────────────┬────────────┬────────────┐");
        System.out.println("│ Nom                  │ Diametre   │ Distància Sol │");
        System.out.println("├──────────────────────┼────────────┼────────────┤");

        for (HashMap<String, Object> planeta : planetes) {
            String nom = (String) planeta.get("nom");
            double diametre = (Double) planeta.get("diametre");
            double distanciaSol = (Double) planeta.get("distancia_sol");

            System.out.printf("│ %-20s │ %-10.2f │ %-12.2f │\n", nom, diametre, distanciaSol);
        }
        System.out.println("└──────────────────────┴────────────┴────────────┘");
    }

    public static ArrayList<HashMap<String, Object>> JSONEsportistesToArrayList(String filePath) {
        ArrayList<HashMap<String, Object>> rst = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                map.put("nom", jsonObject.getString("nom"));
                map.put("any_naixement", jsonObject.getInt("any_naixement"));
                map.put("pais", jsonObject.getString("pais"));

                JSONObject medalles = jsonObject.getJSONObject("medalles_olimpiques");
                HashMap<String, Object> mapMedalles = new HashMap<>();
                mapMedalles.put("or", medalles.getInt("or"));
                mapMedalles.put("plata", medalles.getInt("plata"));
                mapMedalles.put("bronze", medalles.getInt("bronze"));

                map.put("medalles", mapMedalles);
                rst.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    public static ArrayList<HashMap<String, Object>> ordenarEsportistesPerMedalla(String filePath, String tipusMedalla) {
        ArrayList<HashMap<String, Object>> esportistes = JSONEsportistesToArrayList(filePath);

        if (!tipusMedalla.equals("or") && !tipusMedalla.equals("plata") && !tipusMedalla.equals("bronze")) {
            throw new IllegalArgumentException("Tipus de medalla invàlid: " + tipusMedalla + ". Tipus vàlids: 'or', 'plata' o 'bronze'.");
        }

        esportistes.sort((esportista0, esportista1) -> {
            HashMap<?, ?> medalles0 = (HashMap<?, ?>) esportista0.get("medalles");
            HashMap<?, ?> medalles1 = (HashMap<?, ?>) esportista1.get("medalles");

            Integer a = (Integer) medalles0.get(tipusMedalla);
            Integer b = (Integer) medalles1.get(tipusMedalla);

            return b.compareTo(a);
        });

        return esportistes;
    }

    public static void showEsportistesOrdenatsPerMedalla(String filePath, String tipusMedalla) {
        ArrayList<HashMap<String, Object>> esportistes = ordenarEsportistesPerMedalla(filePath, tipusMedalla);

        String capitalizedMedalla = tipusMedalla.substring(0, 1).toUpperCase() + tipusMedalla.substring(1).toLowerCase();

        System.out.println("┌──────────────────────┬─────────────────┬────────────┬────────┐");
        System.out.println("│ Nom                  │ País            │ Naixement  │ " + capitalizedMedalla + "  │");
        System.out.println("├──────────────────────┼─────────────────┼────────────┼────────┤");

        for (HashMap<String, Object> esportista : esportistes) {
            String nom = (String) esportista.get("nom");
            String pais = (String) esportista.get("pais");
            int any_naixement = (Integer) esportista.get("any_naixement");
            HashMap<?, ?> medalles = (HashMap<?, ?>) esportista.get("medalles");
            int medallaCount = (Integer) medalles.get(tipusMedalla);

            System.out.printf("│ %-20s │ %-15s │ %-10d │ %-6d │\n", nom, pais, any_naixement, medallaCount);
        }
        System.out.println("└──────────────────────┴─────────────────┴────────────┴────────┘");
    }

    public static HashMap<String, Object> crearMassaAigua(String nom, String tipus, double superficie_km2, double profunditat_max_m, ArrayList<String> caracteristiques) {
        HashMap<String, Object> massaAigua = new HashMap<>();
        massaAigua.put("nom", nom);
        massaAigua.put("tipus", tipus);
        massaAigua.put("superficie_km2", superficie_km2);
        massaAigua.put("profunditat_max_m", profunditat_max_m);
        massaAigua.put("caracteristiques", caracteristiques);
        return massaAigua;
    }

    public static void generarJSON(ArrayList<HashMap<String, Object>> dades, String filePath) throws IOException {
        JSONArray jsonArray = new JSONArray(dades);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Arxiu de mar i oceans creat correctament.");
    }
}
