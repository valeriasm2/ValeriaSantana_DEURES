package com.exercicis;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

/**
    Introducció
    -----------

    En aquests exàmen es farà un gestor de dades per una notaria.

    Hi haurà diferents tipus de dades, 'clients' i 'operacions'.

    Exemples de com han de ser les dades:

    clients = {
        "client_100": {
            "nom": "Joan Garcia", 
            "edat": 45, 
            "factors": ["autònom", "risc mitjà"], 
            "descompte": 15
        },
        "client_401": {"nom": "Marta Pérez", "edat": 38, "factors": ["empresa", "risc baix"], "descompte": 10},
        "client_202": {"nom": "Pere López",  "edat": 52, "factors": ["autònom", "risc alt"],  "descompte": 5}
    }

    operacions = [
        {
            "id": "operacio_100", 
            "tipus": "Declaració d'impostos", 
            "clients": ["client_100", "client_202"], 
            "data": "2024-10-05", 
            "observacions": "Presentació conjunta", 
            "preu": 150.0
        },
        {"id": "operacio_304", "tipus": "Gestió laboral",    "clients": ["client_202"], "data": "2024-10-04", "observacions": "Contractació de personal",   "preu": 200.0},
        {"id": "operacio_406", "tipus": "Assessoria fiscal", "clients": ["client_401"], "data": "2024-10-03", "observacions": "Revisió d'informes", "preu": 120.0}
    ]
*/

public class Exercici0 {

    // Variables globals (es poden fer servir a totes les funcions)
    public static HashMap<String, HashMap<String, Object>> clients = new HashMap<>();
    public static ArrayList<HashMap<String, Object>> operacions = new ArrayList<>();

    // Neteja la consola tenint en compte el sistema operatiu
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Valida si un nom és vàlid.
     * Un nom és vàlid si no està buit i només conté lletres o espais
     * com a mínim a de tenir dues lletres
     *
     * @param nom El nom a validar.
     * @return True si el nom és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarNom"
     */
    public static boolean validarNom(String nom) {
        // Comprobar que el nombre no esté vacío
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("El nom no pot estar buit.");
            return false;  // Si el nombre es nulo o está vacío, no es válido
        }
    
        // Convertir el nombre a minúsculas para facilitar la comprobación
        nom = nom.trim().toLowerCase(); 
        
        // Comprobar que el nombre no contenga números ni caracteres especiales
        if (!nom.matches("[a-zàáèéìíòóùúäëïöüç ]+")) {
            System.out.println("El nom no pot contenir números ni caràcters especials.");
            return false;  // Si el nombre contiene caracteres no permitidos, no es válido
        }
        
        // Contar cuántas letras tiene el nombre
        int countLetters = 0;
        for (char c : nom.toCharArray()) {
            if (Character.isLetter(c)) {
                countLetters++;  // Incrementar el contador si el carácter es una letra
            }
        }
    
        // Comprobar que tenga al menos dos letras
        if (countLetters < 2) {
            System.out.println("El nom ha de tenir com a mínim dues lletres.");
            return false;  // Si el nombre tiene menos de dos letras, no es válido
        }
    
        return true;  // Si pasa todas las comprobaciones, el nombre es válido
    }  
    
    /**
     * Valida que l'edat sigui un valor vàlid.
     * Comprova que l'edat sigui un enter i que estigui dins del rang acceptable 
     * (entre 18 i 100, ambdós inclosos).
     *
     * @param edat L'edat que s'ha de validar.
     * @return True si l'edat es troba entre 18 i 100 (inclosos), false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarEdat"
     */
    public static boolean validarEdat(int edat) {
        // Comprobar que la edad esté en el rango válido (18 a 100)
        if (edat < 18 || edat > 100) {
            System.out.println("L'edat ha de ser un valor entre 18 i 100.");
            return false;  // Si la edad no está entre 18 y 100, no es válida
        }
        return true;  // Si la edad está dentro del rango, se considera válida
    }
    
    /**
     * Valida que els factors proporcionats siguin vàlids.
     * Comprova que:
     * - Els factors siguin una llista amb exactament dos elements.
     * - El primer element sigui "autònom" o "empresa".
     * - El segon element sigui "risc alt", "risc mitjà" o "risc baix".
     * - Un "autònom" no pot ser de "risc baix".
     * 
     * Exemples:
     * validarFactors(new String[]{"autònom", "risc alt"})      // retorna true
     * validarFactors(new String[]{"empresa", "risc moderat"})  // retorna false
     *
     * @param factors Llista d'elements a validar.
     * @return True si els factors compleixen les condicions, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarFactors"
     */
    public static boolean validarFactors(String[] factors) {
        // Comprobar que los factores no sean nulos
        if (factors == null || factors.length != 2) {
            return false;  // Si no es un arreglo válido, devolver false
        }
    
        String factor1 = factors[0];
        String factor2 = factors[1];
    
        // Comprobar si alguno de los factores es nulo
        if (factor1 == null || factor2 == null) {
            return false;  // Si algún factor es nulo, devolver false
        }
    
        // El primer factor ha de ser "autònom" o "empresa"
        if (!factor1.equals("autònom") && !factor1.equals("empresa")) {
            return false;
        }
    
        // El segon factor ha de ser "risc alt", "risc mitjà" o "risc baix"
        if (!factor2.equals("risc alt") && !factor2.equals("risc mitjà") && !factor2.equals("risc baix")) {
            return false;
        }
    
        // Si el primer factor es "autònom", no puede tener "risc baix"
        if (factor1.equals("autònom") && factor2.equals("risc baix")) {
            return false;
        }
    
        return true;  // Si todo es válido, retornar true
    }
    
    /**
     * Valida que el descompte sigui un valor vàlid.
     * Comprova que:
     * - El descompte sigui un número vàlid (enter o decimal).
     * - El descompte es trobi dins del rang acceptable (entre 0 i 20, ambdós inclosos).
     *
     * Exemples:
     *  validarDescompte(15) retorna true
     *  validarDescompte(25) retorna false
     * 
     * @param descompte El valor del descompte a validar.
     * @return True si el descompte és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarDescompte"
     */
    public static boolean validarDescompte(double descompte) {
        // Validar que el descuento esté entre 0 y 20 (ambos incluidos)
        if (descompte >= 0 && descompte <= 20) {
            return true;  // Si el descuento está en el rango permitido, es válido
        } else {
            // Si el descuento no está en el rango permitido, mostrar un mensaje de error
            System.out.println("El descompte ha de ser un valor entre 0 i 20.");
            return false;  // Si el descuento está fuera del rango, no es válido
        }
    }
    
    /**
     * Valida que el tipus d'operació sigui vàlid.
     * Comprova que:
     * - El tipus d'operació proporcionat coincideixi amb algun dels tipus vàlids.
     * 
     * Els tipus vàlids inclouen:
     * "Declaració d'impostos", "Gestió laboral", "Assessoria fiscal",
     * "Constitució de societat", "Modificació d'escriptures",
     * "Testament", "Gestió d'herències", "Acta notarial",
     * "Contracte de compravenda", "Contracte de lloguer".
     *
     * Exemples:
     *  validarTipusOperacio("Declaració d'impostos") retorna true
     *  validarTipusOperacio("Operació desconeguda") retorna false
     * 
     * @param tipus El tipus d'operació a validar.
     * @return True si el tipus d'operació és vàlid, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarTipusOperacio"
     */
    public static boolean validarTipusOperacio(String tipus) {
        // Comprobar si el tipo es null
        if (tipus == null) {
            return false;  // Si el tipo es null, no es válido
        }
    
        // Comprobar si el tipo corresponde a uno de los valores válidos
        if (tipus.equals("Declaració d'impostos") || 
            tipus.equals("Gestió laboral") || 
            tipus.equals("Assessoria fiscal") || 
            tipus.equals("Constitució de societat") || 
            tipus.equals("Modificació d'escriptures") || 
            tipus.equals("Testament") || 
            tipus.equals("Gestió d'herències") || 
            tipus.equals("Acta notarial") || 
            tipus.equals("Contracte de compravenda") || 
            tipus.equals("Contracte de lloguer")) {
            return true;  // Si el tipo de operación es válido, devuelve true
        } else {
            return false;  // Si el tipo de operación no es válido, devuelve false
        }
    }
    
    /**
     * Valida que la llista de clients sigui vàlida.
     * Comprova que:
     * - La llista de clients sigui efectivament una llista.
     * - Una llista buida és vàlida.
     * - Tots els elements de la llista de clients siguin únics.
     * - Tots els clients de la llista es trobin dins de la llista global de clients vàlids.
     *
     * Exemples:
     *  validarClients(new ArrayList<>(List.of("client1", "client2")), 
     *                 new ArrayList<>(List.of("client1", "client2", "client3"))) retorna true
     *  validarClients(new ArrayList<>(List.of("client1", "client1")), 
     *                 new ArrayList<>(List.of("client1", "client2", "client3"))) retorna false
     *  validarClients(new ArrayList<>(), 
     *                 new ArrayList<>(List.of("client1", "client2", "client3"))) retorna true
     * 
     * @param clientsLlista La llista de clients a validar.
     * @param clientsGlobals La llista global de clients vàlids.
     * @return True si la llista de clients compleix totes les condicions, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarClients"
     */
    public static boolean validarClients(ArrayList<String> clientsLlista, ArrayList<String> clientsGlobals) {
        // Comprobar si la lista de clientes proporcionada es nula
        if (clientsLlista == null) {
            return false;  // La lista de clientes no es válida si es nula
        }
    
        // Comprobar si la lista global de clientes es nula
        if (clientsGlobals == null) {
            return false;  // La lista global no es válida si es nula
        }
    
        // Si la lista de clientes está vacía, la validación es correcta
        if (clientsLlista.isEmpty()) {
            return true;
        }
    
        // Comprobar que no haya clientes duplicados en la lista de clientes
        for (int i = 0; i < clientsLlista.size(); i++) {
            for (int j = i + 1; j < clientsLlista.size(); j++) {
                if (clientsLlista.get(i).equals(clientsLlista.get(j))) {
                    return false; // Si hay clientes duplicados, devolver false
                }
            }
        }
    
        // Comprobar que todos los clientes de la lista estén en la lista global
        for (String client : clientsLlista) {
            if (!clientsGlobals.contains(client)) {
                return false; // Si algún cliente no está en la lista global, devolver false
            }
        }
    
        // Si todas las comprobaciones son satisfactorias, devolver true
        return true;
    }
    
    /**
     * Comprova si una cadena conté només dígits.
     * 
     * @param str La cadena a comprovar.
     * @return True si la cadena conté només dígits, false altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testIsAllDigits"
     */
    public static boolean isAllDigits(String str) {
        // Comprobar si la cadena está vacía
        if (str.isEmpty()) {
            return false;
        }
    
        // Iterar por cada carácter de la cadena
        for (char c : str.toCharArray()) {
            // Comprobar si el carácter no es un dígito
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        // Si todos los caracteres son dígitos, devolver true
        return true;
    } 

    /**
     * Valida que la data sigui en un format vàlid (AAAA-MM-DD) i que representi una data possible.
     * Comprova que:
     * - La longitud de la cadena sigui exactament 10 caràcters.
     * - La cadena es pugui dividir en tres parts: any, mes i dia.
     * - Any, mes i dia estiguin formats per dígits.
     * - Any estigui en el rang [1000, 9999].
     * - Mes estigui en el rang [1, 12].
     * - Dia estigui en el rang [1, 31].
     * - Es compleixin les limitacions de dies segons el mes.
     *
     * Exemples:
     *  validarData("2023-04-15") retorna true
     *  validarData("2023-02-30") retorna false
     *  validarData("2023-13-01") retorna false
     *
     * @param data La cadena que representa una data en format 'AAAA-MM-DD'.
     * @return True si la data és vàlida, false altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarData"
     */
     public static boolean validarData(String data) {
        // Comprobar que la fecha no sea nula y que tenga una longitud exacta de 10 caracteres
        if (data == null || data.length() != 10) {
            return false;
        }
    
        // Verificar que los guiones estén en las posiciones correctas (índices 4 y 7)
        if (data.charAt(4) != '-' || data.charAt(7) != '-') {
            return false;
        }
    
        // Extraer el año, mes y día de la cadena
        String anyStr = data.substring(0, 4);
        String mesStr = data.substring(5, 7);
        String diaStr = data.substring(8, 10);
    
        // Comprobar que cada parte de la fecha (año, mes, día) contenga solo dígitos
        if (!isAllDigits(anyStr) || !isAllDigits(mesStr) || !isAllDigits(diaStr)) {
            return false;
        }
    
        // Convertir las partes de la fecha a enteros
        int any = Integer.parseInt(anyStr);
        int mes = Integer.parseInt(mesStr);
        int dia = Integer.parseInt(diaStr);
    
        // Comprobar que el año esté en el rango válido (entre 1000 y 9999)
        if (any < 1000 || any > 9999) {
            return false;
        }
    
        if (mes < 1 || mes > 12) { // Comprobar que el mes esté dentro del rango válido (1-12)
            return false;
        }
    
        if (dia < 1 || dia > 31) { // Comprobar que el día esté en el rango válido (1-31)
            return false;
        }
    
        if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) { // Verificar que los meses con 30 días no tengan más de 30 días
            return false;
        }
    
        if (mes == 2 && dia > 29) { // Verificar que febrero no tenga más de 29 días (no se considera si el año es bisiesto)
            return false;
        }
    
        return true;
    }
    
    /**
     * Valida que el preu sigui un número vàlid i superior a 100.
     * Comprova que:
     * - El preu sigui un número vàlid (decimal o enter).
     * - El valor del preu sigui estrictament superior a 100.
     *
     * Exemples:
     *  validarPreu(150.0) retorna true
     *  validarPreu(99.99) retorna false
     *  validarPreu(100.0) retorna false
     * 
     * @param preu El valor del preu a validar.
     * @return True si el preu és un número i és superior a 100, false altrament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testValidarPreu"
     */
    public static boolean validarPreu(double preu) {
        // Comprobar que el precio sea un número válido y mayor que 100
        return preu > 100;
    }

    /**
     * Genera una clau única per a un client.
     * La clau és en el format "client_XYZ", on XYZ és un número aleatori entre 100 i 999.
     * Comprova que la clau generada no existeixi ja en el diccionari de clients.
     *
     * @return Una clau única per al client.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGeneraClauClient"
     */
    public static String generaClauClient() {
        Random random = new Random();
    
        String clau;
        do {
            int numeroAleatori = 100 + random.nextInt(900); // Generar número aleatorio entre 100 y 999
            clau = "client_" + numeroAleatori;  // Crear la clave en formato "client_XYZ"
        } while (clients.containsKey(clau)); // Comprobar si la clave ya existe
    
        return clau; // Retornar la clave generada
    }
    
    /**
     * Afegeix un nou client al diccionari de clients.
     * - Genera una nova clau amb "generaClauClient"
     * - Afegeix una entrada al diccionari de clients, 
     *   on la clau és la nova clau generada i el valor és un HashMap 
     *   amb el nom, edat, factors i descompte del nou client.
     *
     * Exemples:
     *  afegirClient(clients, "Maria", 30, new ArrayList<>(List.of("empresa", "risc baix")), 10) retorna "client_0"
     *
     * @param nom El nom del nou client.
     * @param edat L'edat del nou client.
     * @param factors La llista de factors associats al client.
     * @param descompte El descompte associat al nou client.
     * @return La clau del nou client afegit.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirClient"
     */
    public static String afegirClient(String nom, int edat, ArrayList<String> factors, double descompte) {
        // Genera una clave única para el cliente
        String clau = generaClauClient();

        // Crea un HashMap con la información del cliente
        HashMap<String, Object> clientData = new HashMap<>();
        clientData.put("nom", nom);             // Nombre del cliente
        clientData.put("edat", edat);           // Edad del cliente
        clientData.put("factors", factors);     // Lista de factores asociados al cliente
        clientData.put("descompte", descompte); // Descuento del cliente

        // Añade la información del cliente al diccionario de clientes con la clave generada
        clients.put(clau, clientData);

        // Retorna la clave generada para el cliente añadido
        return clau;
    }

    /**
     * Modifica un camp específic d'un client al diccionari de clients.
     * - Comprova si la clau del client existeix al diccionari de clients.
     * - Si existeix, comprova si el camp que es vol modificar és vàlid (existeix dins del diccionari del client).
     * - Si el camp existeix, actualitza el valor del camp amb el nou valor.
     * - Si el camp no existeix, retorna un missatge d'error indicant que el camp no existeix.
     * - Si la clau del client no existeix, retorna un missatge d'error indicant que el client no es troba.
     * 
     * Retorn:
     * - Retorna "Client 'client_XYZ' no existeix." si el client no existeix
     * - Retorna "El camp 'campErroni' si el camp no existeix en aquest client
     * - "OK" si s'ha modificat el camp per aquest client
     * 
     * @param clauClient La clau del client que s'ha de modificar.
     * @param camp El camp que s'ha de modificar.
     * @param nouValor El nou valor que s'ha d'assignar al camp.
     * @return Un missatge d'error si el client o el camp no existeixen; "OK" altrament.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarClient"
     */
    public static String modificarClient(String clauClient, String camp, Object nouValor) {
        // Verificar si el cliente existe en el diccionario
        if (!clients.containsKey(clauClient)) {
            return "Client '" + clauClient + "' no existeix.";  // Si no existe, devolver mensaje
        }
    
        // Obtener los datos del cliente (HashMap)
        HashMap<String, Object> clientData = (HashMap<String, Object>) clients.get(clauClient);
    
        // Verificar si el campo que se quiere modificar existe
        if (!clientData.containsKey(camp)) {
            return "El camp " + camp + " no existeix.";  // Si no existe, devolver mensaje
        }
    
        // Actualizar el valor del campo con el nuevo valor
        clientData.put(camp, nouValor);
    
        // Retornar OK si la modificación se realizó con éxito
        return "OK";
    }
    
    /**
     * Esborra un client del diccionari de clients.
     * Comprova:
     * - Si la clau del client existeix dins del diccionari de clients.
     * - Si la clau del client existeix, elimina el client del diccionari.
     * - Si la clau del client no existeix, retorna un missatge d'error.
     *
     * Retorn:
     * - Si el client no existeix, retorna un missatge d'error: "Client amb clau {clauClient} no existeix."
     * - Si el client existeix, l'elimina i retorna "OK".
     *
     * @param clauClient La clau del client que s'ha d'esborrar.
     * @return Un missatge d'error si el client no existeix o "OK" si s'ha esborrat correctament.
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarClient"
     */
    public static String esborrarClient(String clauClient) {
        // Verificar si el cliente existe en el diccionario
        if (!clients.containsKey(clauClient)) {
            return "Client '" + clauClient + "' no existeix.";  // Si no existe, devolver mensaje
        }

        // Eliminar el cliente
        clients.remove(clauClient);

        // Comprobar si el cliente fue eliminado correctamente
        if (!clients.containsKey(clauClient)) {
            // El cliente se eliminó correctamente
            return "OK";
        } else {
            // El cliente no se eliminó correctamente
            return "Error en eliminar el client.";
        }
    }

    /**
     * Llista clients que compleixen determinades condicions.
     * Comprova si els clients que coincideixen amb les claus 
     * especificades compleixen les condicions proporcionades.
     * 
     * @param claus La llista de claus de clients a considerar per la cerca.
     * @param condicions Les condicions que els clients han de complir.
     * @return Una llista de diccionaris, on cada diccionari conté 
     *         la clau del client i les dades del client.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarClients"
     */
    public static ArrayList<HashMap<String, HashMap<String, Object>>> llistarClients(
        ArrayList<String> claus,
        HashMap<String, Object> condicions) {
    
        // Inicialitzem una llista per emmagatzemar els clients que compleixen les condicions.
        ArrayList<HashMap<String, HashMap<String, Object>>> resultat = new ArrayList<>();

        // Recorrer totes les claus de clients per comprovar qui compleix les condicions
        for (String clau : clients.keySet()) {

            // Si la clau del client no es troba a la llista de claus donada, continuar al següent client
            if (!claus.contains(clau)) {
                continue;  // saltar a la següent iteració
            }

            // Obtenir les dades del client actual
            HashMap<String, Object> datos = clients.get(clau);

            // Variable per controlar si el client compleix les condicions
            boolean coincidencia = true;

            // Comprovem si el client compleix totes les condicions
            for (String key : condicions.keySet()) {
                // Obtenim el valor esperat per la condició
                Object valorEsperado = condicions.get(key);

                // Si el client no té la clau o el valor no coincideix amb el valor esperat, no compleix
                if (!datos.containsKey(key) || !datos.get(key).equals(valorEsperado)) {
                    coincidencia = false;  // marquem que el client no compleix les condicions
                    break;  // sortim del bucle de condicions
                }
            }

            // Si el client compleix totes les condicions, afegim les dades a la llista de resultats
            if (coincidencia) {
                // Creem un diccionari per emmagatzemar les dades del client
                HashMap<String, HashMap<String, Object>> clientValido = new HashMap<>();
                clientValido.put(clau, datos);  // afegim la clau i les dades del client

                // Afegim el client a la llista de resultats
                resultat.add(clientValido);
            }
        }

        // Retornem la llista de clients que compleixen les condicions
        return resultat;
    }

    /**
     * Genera una clau única per a una operació.
     * La clau és en el format "operacio_XYZ", on XYZ és un número aleatori entre 100 i 999.
     * Comprova que la clau generada no existeixi ja en la llista d'operacions.
     *
     * @return Una clau única per a l'operació.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGeneraClauOperacio"
     */
    public static String generaClauOperacio() {
        Random random = new Random(); // Instanciamos el generador de números aleatorios
        String clave; // La variable donde almacenaremos la clave generada
    
        boolean existeix; // Variable para comprobar si la clave ya existe en la lista de operaciones
        do {
            // Genera un número aleatorio entre 100 y 999
            int numeroAleatorio = 100 + random.nextInt(900);
            clave = "operacio_" + numeroAleatorio; // Formamos la clave concatenando el prefijo con el número aleatorio generado
    
            // Comprobamos si la clave ya existe en la lista de operaciones
            existeix = false;

            for (HashMap<String, Object> operacion : operacions) {
                // Recorremos la lista de operaciones y comprobamos si alguna tiene la clave generada
                if (clave.equals(operacion.get("id"))) {
                    existeix = true; // Si la clave existe, cambiamos el valor de 'existeix' a true
                    break; // Si encontramos una coincidencia, salimos del bucle
                }
            }
        } while (existeix); // Si la clave ya existe, el bucle se repite para generar una nueva
    
        // Una vez obtenida una clave única, la retornamos
        return clave;
    }

    /**
     * Afegeix una nova operació a la llista d'operacions.
     * - Genera un nova clau amb "generaClauOperacio"
     * - Crea un HashMap que representa la nova operació amb els camps següents:
     *   - "id": clau de l'operació.
     *   - "tipus": el tipus d'operació.
     *   - "clients": llista de clients implicats.
     *   - "data": la data de l'operació.
     *   - "observacions": observacions de l'operació.
     *   - "preu": preu de l'operació.
     * - Afegeix aquest HashMap a la llista d'operacions.
     * 
     * @param tipus El tipus d'operació.
     * @param clientsImplicats La llista de clients implicats.
     * @param data La data de l'operació.
     * @param observacions Observacions addicionals sobre l'operació.
     * @param preu El preu associat a l'operació.
     * @return L'identificador de la nova operació.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirOperacio"
     */
    public static String afegirOperacio(
        String tipus,
        ArrayList<String> clientsImplicats,
        String data,
        String observacions,
        double preu) {

        // Genera una clave única para la operación
        String clauOperacio = generaClauOperacio();

        // Depuración: Imprime la clave generada
        System.out.println("Generada la clave de operación: " + clauOperacio);

        // Creamos un HashMap para representar la nueva operación
        HashMap<String, Object> novaOperacio = new HashMap<>();
        novaOperacio.put("id", clauOperacio);
        novaOperacio.put("tipus", tipus);
        novaOperacio.put("clients", clientsImplicats);
        novaOperacio.put("data", data);
        novaOperacio.put("observacions", observacions);
        novaOperacio.put("preu", preu);

        // Añadimos la nueva operación a la lista de operaciones
        operacions.add(novaOperacio);

        // Depuración: Imprime el tamaño de la lista de operaciones
        System.out.println("Número de operaciones después de añadir: " + operacions.size());

        return clauOperacio;
    }

    /**
     * Modifica un camp específic d'una operació dins de la llista d'operacions.
     * 
     * @param idOperacio L'identificador de l'operació que s'ha de modificar.
     * @param camp El camp específic dins del HashMap de l'operació que s'ha de modificar.
     * @param nouValor El nou valor que es vol assignar al camp especificat.
     * @return Un missatge d'error si l'operació o el camp no existeix, "OK" 
     *         si la modificació s'ha realitzat correctament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarOperacio"
     */
    public static String modificarOperacio(String idOperacio, String camp, Object nouValor) {
        // Iteramos sobre cada operación de la lista
        for (HashMap<String, Object> operacio : operacions) {
            // Comprobamos si la operación coincide con la clave especificada
            if (operacio.get("id").equals(idOperacio)) {
                // Comprobamos si el campo existe dentro de la operación
                if (operacio.containsKey(camp)) {
                    // Modificamos el valor del campo
                    operacio.put(camp, nouValor);
                    return "OK"; 
                } else {
                    return "El camp " + camp + " no existeix en l'operació.";
                }
            }
        }
        return "Operació amb id " + idOperacio + " no existeix.";
    }

    /**
     * Esborra una operació de la llista d'operacions basada en l'identificador de l'operació.
     * 
     * @param idOperacio L'identificador de l'operació que es vol esborrar.
     * @return Un missatge d'error si l'operació amb 'idOperacio' no existeix, "OK" si s'esborra correctament.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarOperacio"
     */
    public static String esborrarOperacio(String idOperacio) {
        // Iteramos sobre la lista de operaciones utilizando un índice
        for (int i = 0; i < operacions.size(); i++) {
            // Obtenemos la operación en la posición actual
            HashMap<String, Object> operacio = operacions.get(i);
    
            // Comprobamos si el 'id' de la operación coincide con el proporcionado
            if (operacio.get("id").equals(idOperacio)) {
                // Si encontramos la operación, la eliminamos de la lista
                operacions.remove(i);
                return "OK"; // Retornamos OK si la operación se ha eliminado correctamente
            }
        }
    
        // Si no se encuentra la operación, retornamos un mensaje de error
        return "Operació amb id " + idOperacio + " no existeix.";
    }

    /**
     * Lista las operaciones que cumplen ciertos criterios basados
     * en identificadores y condiciones específicas.
     * 
     * @param ids Una lista de identificadores de operaciones que se desean considerar. 
     *            Si está vacía, se consideran todas las operaciones.
     * @param condicions Un HashMap con las condiciones que las operaciones deben cumplir.
     * @return Una lista con las operaciones que cumplen las condiciones.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarOperacions"
     */
    public static ArrayList<HashMap<String, Object>> llistarOperacions(
            ArrayList<String> ids,
            HashMap<String, Object> condicions) {

        // Lista para almacenar las operaciones que cumplen las condiciones
        ArrayList<HashMap<String, Object>> opCorrectas = new ArrayList<>();

        // Si la lista de ids no es vacía, filtramos solo las operaciones que tengan esos ids
        boolean considerarTodos = (ids == null || ids.isEmpty());

        // Iteramos sobre todas las operaciones
        for (HashMap<String, Object> operacio : operacions) {

            // Si no estamos considerando todos los ids y el id de la operación no está en la lista, la saltamos
            if (!considerarTodos && !ids.contains(operacio.get("id"))) {
                continue;
            }

            boolean coincideix = true;

            // Si hay condiciones, las verificamos
            if (condicions != null && !condicions.isEmpty()) {
                // Iteramos sobre las condiciones utilizando Map.Entry para acceder a la clave y al valor
                for (Map.Entry<String, Object> entry : condicions.entrySet()) {
                    // Si la operación no contiene la clave o el valor no coincide, la descartamos
                    if (!operacio.containsKey(entry.getKey()) || !operacio.get(entry.getKey()).equals(entry.getValue())) {
                        coincideix = false;
                        break;
                    }
                }
            }

            // Si la operación cumple todas las condiciones, la agregamos a la lista de resultados
            if (coincideix) {
                opCorrectas.add(operacio);
            }
        }

        // Devolvemos la lista con las operaciones que cumplen las condiciones
        return opCorrectas;
    }

    /**
     * Llista les operacions associades a un client específic basant-se en la seva clau.
     * 
     * @param clauClient La clau única del client que es vol filtrar.
     * @return Una llista amb les operacions associades al client especificat.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarOperacionsClient"
     */
    public static ArrayList<HashMap<String, Object>> llistarOperacionsClient(String clauClient) {
        // Lista para almacenar las operaciones asociadas al cliente
        ArrayList<HashMap<String, Object>> operacionsClient = new ArrayList<>();
        
        // Iterar sobre todas las operaciones
        for (HashMap<String, Object> operacio : operacions) {
            // Verificar si la clave del cliente de la operación no es nula antes de compararla
            if (operacio.get("client") != null && operacio.get("client").equals(clauClient)) {
                operacionsClient.add(operacio); // Si coincide, agregar la operación a la lista
            }

            ArrayList<String> clients = (ArrayList<String>) operacio.get("clients");// Obtener la lista de clientes de la operación
            // Verificar si la lista de clientes no es nula y contiene la clave del cliente
            if (clients != null && clients.contains(clauClient)) {
                operacionsClient.add(operacio); // Si la lista contiene la clave del cliente, agregar la operación a la lista
            }
        }
        return operacionsClient;
    }

    /**
     * Mètode que formata i alinea columnes de text 
     * segons les especificacions donades.
     * 
     * El mètode processa cada columna:
     * - Si el text és més llarg que l'amplada especificada, el trunca
     * - Afegeix els espais necessaris segons el tipus d'alineació:
     *   * "left": alinea el text a l'esquerra i omple amb espais a la dreta
     *   * "right": omple amb espais a l'esquerra i alinea el text a la dreta
     *   * "center": distribueix els espais entre esquerra i dreta per centrar el text
     * 
     * @param columnes ArrayList que conté arrays d'Objects, on cada array representa una columna amb:
     *                 - posició 0: String amb el text a mostrar
     *                 - posició 1: String amb el tipus d'alineació ("left", "right", "center")
     *                 - posició 2: int amb l'amplada total de la columna
     * 
     * @return String amb totes les columnes formatades concatenades
     * 
     * Per exemple:
     * Si input és: {{"Hola", "left", 6}, {"Mon", "right", 5}}
     * Output seria: "Hola    Mon"
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAlineaColumnes"
     */
    public static String alineaColumnes(ArrayList<Object[]> columnes) {
        String resultat = "";
        for (Object[] columna : columnes) {
            String text = (String) columna[0];
            String alineacio = (String) columna[1];
            int amplada = (int) columna[2];
            
            // Si el text és més llarg que l'amplada, trunca el text
            if (text.length() > amplada) {
                text = text.substring(0, amplada);
            }
    
            // Calcula la quantitat d'espais necessaris
            int espais = amplada - text.length();
    
            if (alineacio.equals("left")) {
                // Alinea a l'esquerra i afegeix espais a la dreta
                resultat += text + " ".repeat(espais);
            } else if (alineacio.equals("right")) {
                // Alinea a la dreta i afegeix espais a l'esquerra
                resultat += " ".repeat(espais) + text;
            } else if (alineacio.equals("center")) {
                // Alinea al centre, distribuint els espais de manera uniforme
                int espaisEsquerra = espais / 2;
                int espaisDreta = espais - espaisEsquerra; // Si els espais són imparells, afegeix 1 a la dreta
                resultat += " ".repeat(espaisEsquerra) + text + " ".repeat(espaisDreta);
            }
        }
        return resultat;
    }

    /**
     * Genera una representació en forma de taula de les operacions 
     * associades a un client específic.
     * 
     * Cada linia del resultat es guarda en un String de l'ArrayList.
     * 
     * Fes servir: Locale.setDefault(Locale.US)
     * 
     * Format esperat de sortida:
     * ```
Marta Puig i Puig, 45               [empresa, risc alt]
-------------------------------------------------------
Tipus                         Data                 Preu
Constitució de societat       2024-01-15        1250.50
Testament                     2024-02-28         750.75
Acta notarial                 2024-03-10         500.25
-------------------------------------------------------
                                          Suma: 2501.50
Descompte: 10%                            Preu: 2126.28
Impostos:  21% (85.05)                   Total: 2572.80

*******************************************************

Pere Vila, 25                    [estudiant, risc baix]
-------------------------------------------------------
Tipus                         Data                 Preu
Certificat                    2024-01-10          25.50
Fotocòpia                     2024-01-15          15.25
Segell                        2024-01-20          35.50
-------------------------------------------------------
                                            Suma: 76.25
Descompte: 10%                              Preu: 68.63
Impostos:  21% (14.41)                     Total: 83.04
     * ```
     * On:
     * - La primera línia mostra el nom, edat i factors del client
     * - Els tipus d'operació s'alineen a l'esquerra
     * - Les dates tenen format YYYY-MM-DD
     * - Els preus mostren sempre 2 decimals
     * - El descompte és un percentatge enter
     * - Els impostos són sempre el 21% del preu amb descompte
     *
     * @param clauClient La clau única del client per generar la taula d'operacions.
     * @param ordre El camp pel qual s'ordenaran les operacions (exemple: "data", "preu").
     * @return Una llista de cadenes de text que representen les línies de la taula.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient0"
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient1"
     * @test ./runTest.sh "com.exercicis.TestExercici0#testTaulaOperacionsClient2"
     */
    public static ArrayList<String> taulaOperacionsClient(String clauClient, String ordre) {
    return"";
    }

    /**
     * Genera el menú principal de l'aplicació de Gestió de Notaria.
     * 
     * Retorna una llista de cadenes de text que representen 
     * les opcions disponibles en el menú principal de l'aplicació.
     *
     * @return Una llista de cadenes de text amb les opcions del menú principal.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testGetCadenesMenu"
     */
    public static ArrayList<String> getCadenesMenu() {
        String menuText = """
    === Menú de Gestió de Notaria ===
    1. Afegir client
    2. Modificar client
    3. Esborrar client
    4. Llistar clients
    5. Afegir operació
    6. Modificar operació
    7. Esborrar operació
    8. Llistar operacions
    0. Sortir
                """;
        String[] lines = menuText.split("\\R");
        return new ArrayList<>(Arrays.asList(lines));
    }
    
    /**
     * Genera el menú amb la llista de clients.
     * 
     * Retorna una llista de cadenes de text que representen 
     * cada un dels clients de la llista.
     * - El primer text de la llista és així: "=== Llistar Clients ==="
     * - En cas de no haver-hi cap client afegeix a la llista de retorn "No hi ha clients per mostrar."
     *
     * @return Una llista de cadenes de text amb els clients.
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlistarClientsMenu"
     */
    public static ArrayList<String> getLlistarClientsMenu() {
        ArrayList<String> linis = new ArrayList<>();
        linis.add("=== Llistar Clients ===");
    
        if (llistaClients.isEmpty()) {
            linis.add("No hi ha clients per mostrar.");
        } else {
            for (Client client : llistaClients) {
                linis.add(client.toString());
            }
        }
        return linis;
    }
    
    /**
     * Escriu per consola cada element d'una llista en una línia nova.
     * 
     * @param llista La llista de linies a mostrar
     *
     * @test ./runTest.sh "com.exercicis.TestExercici0#testDibuixarLlista"
     */
    public static void dibuixarLlista(ArrayList<String> lista) {
        for (String linia : lista) {
            System.out.println(linia);
        }
    }
    

    /**
     * Demana a l'usuari que seleccioni una opció i retorna l'opció transformada a una paraula clau si és un número.
     * 
     * Mostra el text: "Selecciona una opció (número o paraula clau): ".
     * - Si l'opció introduïda és un número vàlid, es transforma a les paraules clau corresponents segons el menú.
     * - Si l'opció són paraules clau vàlides, es retornen directament.
     *   Les paraules clau han d'ignorar les majúscules, minúscules i accents
     * - Si l'opció no és vàlida, mostra un missatge d'error i torna a preguntar fins que l'entrada sigui vàlida.
     *   "Opció no vàlida. Torna a intentar-ho."
     * 
     * Relació de números i paraules clau:
     *  1. "Afegir client"
     *  2. "Modificar client"
     *  3. "Esborrar client"
     *  4. "Llistar clients"
     *  5. "Afegir operació"
     *  6. "Modificar operació"
     *  7. "Esborrar operació"
     *  8. "Llistar operacions"
     *  0. "Sortir"
     * 
     * @return La cadena introduïda per l'usuari (número convertit a paraula clau o paraula clau validada).
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testObtenirOpcio"
     */
    public static String obtenirOpcio(Scanner scanner) {
        ArrayList<String> opcs = getCadenesMenu();
    
        while (true) {
            System.out.print("Selecciona una opció (número o paraula clau): ");
            String opcio = scanner.nextLine().toLowerCase().trim(); // Asegurarse de eliminar espacios extra
    
            // opción a número
            try {
                int numOpcio = Integer.parseInt(opcio);
                if (numOpcio >= 0 && numOpcio < opcs.size()) {
                    return opcs.get(numOpcio);  // Si es un número válido, devolver la opción correspondiente
                } else {
                    System.out.println("Opció no vàlida. Torna a intentar-ho.");
                }
            } catch (NumberFormatException e) {
                // Si no es un número, verificamos la opción de palabra clave
                boolean valid = false;
                for (String opc : opcs) {
                    // Compara sin importar mayúsculas, minúsculas ni acentos
                    if (normalizarString(opc).equals(normalizarString(opcio))) {
                        valid = true;
                        return opc;
                    }
                }
    
                if (!valid) {
                    System.out.println("Opció no vàlida. Torna a intentar-ho.");
                }
            }
        }
    }

    /**
     * Demana i valida el nom d'un client.
     * Mostra el missatge "Introdueix el nom del client: " i valida que el nom sigui correcte.
     * Si el nom no és vàlid, afegeix el missatge d'error a la llista i torna a demanar el nom.
     * Fes servir la funció "validarNom"
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return El nom validat del client
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirNom"
     */
    public static String llegirNom(Scanner scanner) {
        System.out.print("Introdueix el nom del client: ");
        String nomb = scanner.nextLine().trim();

        while (!validarNom(nom)) {
            System.out.println("Nom no vàlid. Torna a intentar-ho.");
            System.out.print("Introdueix el nom del client: ");
            nom = scanner.nextLine().trim();
        }
        return nomb;
    }

    /**
     * Demana i valida l'edat d'un client.
     * Mostra el missatge "Introdueix l'edat del client (18-100): " i valida que l'edat sigui correcta.
     * Si l'edat no és vàlida, afegeix el missatge d'error a la llista i torna a demanar l'edat.
     * Fes servir les funcions "isAllDigits" i "validarEdat"
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return L'edat validada del client
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirEdat"
     */
    public static int llegirEdat(Scanner scanner) {
        System.out.print("Introdueix l'edat del client (18-100): ");
        String edatStr = scanner.nextLine().trim();

        while (!isAllDigits(edatStr) || !validarEdat(edatStr)) {
            System.out.println("Edat no vàlida. Torna a intentar-ho.");
            System.out.print("Introdueix l'edat del client (18-100): ");
            edatStr = scanner.nextLine().trim();
        }

        return Integer.parseInt(edatStr);
    }

    
    /**
     * Demana i valida els factors d'un client.
     * Primer demana el tipus de client (autònom/empresa) i després el nivell de risc.
     * Per autònoms, només permet 'risc alt' o 'risc mitjà'.
     * Per empreses, permet 'risc alt', 'risc mitjà' o 'risc baix'.
     * 
     * Mostra els següents missatges:
     * - "Introdueix el primer factor ('autònom' o 'empresa'): "
     * - Per autònoms: "Introdueix el segon factor ('risc alt' o 'risc mitjà'): "
     * - Per empreses: "Introdueix el segon factor ('risc alt', 'risc baix' o 'risc mitjà'): "
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return ArrayList amb els dos factors validats
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirFactors"
     */
public static ArrayList<String> llegirFactors(Scanner scanner) {
    ArrayList<String> factors = new ArrayList<>();
    
    // Llegir el primer factor (tipo de persona)
    System.out.print("Introdueix el tipus de persona ('autònom' o 'empresa'): ");
    String tipoPersona = scanner.nextLine().trim();
    while (!tipoPersona.equals("autònom") && !tipoPersona.equals("empresa")) {
        System.out.println("Tipus de persona no vàlid. Ha de ser 'autònom' o 'empresa'.");
        System.out.print("Introdueix el tipus de persona ('autònom' o 'empresa'): ");
        tipoPersona = scanner.nextLine().trim();
    }
    factors.add(tipoPersona);
    
    // Llegir el segon factor (nivell de risc)
    String promptNivelRiesgo = tipoPersona.equals("autònom")
            ? "Introdueix el nivell de risc ('risc alt' o 'risc mitjà'): "
            : "Introdueix el nivell de risc ('risc alt', 'risc baix' o 'risc mitjà'): ";
    
    System.out.print(promptNivelRiesgo);
    String nivelRiesgo = scanner.nextLine().trim();
    while (true) {
        if (tipoPersona.equals("autònom")) {
            if (nivelRiesgo.equals("risc alt") || nivelRiesgo.equals("risc mitjà")) break;
            System.out.println("Nivell de risc no vàlid. Per a autònoms només pot ser 'risc alt' o 'risc mitjà'.");
        } else {
            if (nivelRiesgo.equals("risc alt") || nivelRiesgo.equals("risc baix") || nivelRiesgo.equals("risc mitjà")) break;
            System.out.println("Nivell de risc no vàlid. Ha de ser 'risc alt', 'risc baix' o 'risc mitjà'.");
        }
        System.out.print(promptNivelRiesgo);
        nivelRiesgo = scanner.nextLine().trim();
    }
    factors.add(nivelRiesgo);
    
    return factors;
}

    
    /**
     * Demana i valida un descompe
     * Primer demana el descompte amb: 
     * "Introdueix el descompte (0-20): "
     * 
     * Mostra el següent missatge en cas d'error: 
     * "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     *
     * @param scanner Scanner per llegir l'entrada de l'usuari
     * @return ArrayList amb els dos factors validats
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testLlegirDescompte"
     */
    public static double llegirDescompte(Scanner scanner) {
        System.out.print("Introdueix el descompte (0-20): ");
        String descompteStr = scanner.nextLine().trim();

        while (!isAllDigits(descompteStr) || !validarDescompte(descompteStr)) {
            System.out.println("Descompte no vàlid. Ha de ser un número entre 0 i 20.");
            System.out.print("Introdueix el descompte (0-20): ");
            descompteStr = scanner.nextLine().trim();
        }
        return Double.parseDouble(descompteStr);
    }

    /**
     * Gestiona el procés d'afegir un nou client mitjançant interacció amb l'usuari.
     * Utilitza les següents funcions auxiliars per obtenir i validar les dades:
     * - llegirNom: per obtenir el nom del client
     * - llegirEdat: per obtenir l'edat (entre 18 i 100)
     * - llegirFactors: per obtenir el tipus (autònom/empresa) i nivell de risc
     * - llegirDescompte: per obtenir el descompte (entre 0 i 20)
     * 
     * La primera línia del retorn sempre és "=== Afegir Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn per les funcions auxiliars:
     * - "Nom no vàlid. Només s'accepten lletres i espais."
     * - "Edat no vàlida. Introdueix un número entre 18 i 100."
     * - "Factor no vàlid. Ha de ser 'autònom' o 'empresa'."
     * - "Factor no vàlid. Per a autònoms només pot ser 'risc alt' o 'risc mitjà'."
     * - "Factor no vàlid. Ha de ser 'risc alt', 'risc baix' o 'risc mitjà'."
     * - "Els factors no són vàlids."
     * - "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha afegit el client amb clau " + novaClau + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari
     * @return Una llista de cadenes de text que contenen els missatges d'estat del procés
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testAfegirClientMenu"
     */
    public static ArrayList<String> afegirClientMenu(Scanner scanner) {
        ArrayList<String> missatges = new ArrayList<>();
        missatges.add("=== Afegir Client ===");
    
        String nomClient = llegirNom(scanner);  // Cambié "nom" por "nomClient"
        int edatClient = llegirEdat(scanner);  // Cambié "edat" por "edatClient"
        ArrayList<String> factorsClient = llegirFactors(scanner);  // Cambié "factors" por "factorsClient"
        
        if (!validarFactors(factorsClient.toArray(new String[0]))) {  // Cambié "factors" por "factorsClient"
            missatges.add("Els factors no són vàlids.");
            return missatges;
        }
    
        double descompteClient = llegirDescompte(scanner);  // Cambié "descompte" por "descompteClient"
        
        String clauClient = afegirClient(nomClient, edatClient, factorsClient, descompteClient);  // Cambié "novaClau" por "clauClient"
        missatges.add("S'ha afegit el client amb clau " + clauClient + ".");
        return missatges;
    }
    
    
    /**
     * Gestiona el procés de modificació d'un client existent.
     * 
     * Primer demana i valida la clau del client:
     * - "Introdueix la clau del client a modificar (per exemple, 'client_100'): "
     * 
     * Si el client existeix:
     * - Mostra "Camps disponibles per modificar: nom, edat, factors, descompte"
     * - Demana "Introdueix el camp que vols modificar: "
     * 
     * Segons el camp escollit, utilitza les funcions auxiliars:
     * - llegirNom: si es modifica el nom
     * - llegirEdat: si es modifica l'edat
     * - llegirFactors: si es modifiquen els factors
     * - llegirDescompte: si es modifica el descompte
     * 
     * La primera línia del retorn sempre és "=== Modificar Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn:
     * - "Client amb clau " + clauClient + " no existeix."
     * - "El camp " + camp + " no és vàlid."
     * 
     * Més els missatges d'error de les funcions auxiliars:
     * - "Nom no vàlid. Només s'accepten lletres i espais."
     * - "Edat no vàlida. Introdueix un número entre 18 i 100."
     * - "Factor no vàlid. Ha de ser 'autònom' o 'empresa'."
     * - "Factor no vàlid. Per a autònoms només pot ser 'risc alt' o 'risc mitjà'."
     * - "Factor no vàlid. Ha de ser 'risc alt', 'risc baix' o 'risc mitjà'."
     * - "Els factors no són vàlids."
     * - "Descompte no vàlid. Ha de ser un número entre 0 i 20."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha modificat el client " + clauClient + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari
     * @return Una llista de cadenes de text que contenen els missatges d'estat del procés
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testModificarClientMenu"
     */
    public static ArrayList<String> modificarClientMenu(Scanner scanner) {
        ArrayList<String> missatges = new ArrayList<>();
        missatges.add("=== Modificar Client ===");
    
        // Llegir clau client
        System.out.print("Introdueix la clau del client a modificar (per exemple, 'client_100'): ");
        String clauClient = scanner.nextLine().trim();
        if (!clients.containsKey(clauClient)) {
            missatges.add("Client amb clau " + clauClient + " no existeix.");
            return missatges;
        }
    
        // Llegir camp a modificar
        missatges.add("Camps disponibles per modificar: nom, edat, factors, descompte");
        System.out.print("Introdueix el camp que vols modificar: ");
        String camp = scanner.nextLine().trim();
        if (!Arrays.asList("nom", "edat", "factors", "descompte").contains(camp)) {
            missatges.add("El camp " + camp + " no és vàlid.");
            return missatges;
        }
    
        // Llegir nou valor segons el camp
        Object nouValor = null;
        if (camp.equals("nom")) {
            nouValor = llegirNom(scanner);
        } else if (camp.equals("edat")) {
            nouValor = llegirEdat(scanner);
        } else if (camp.equals("factors")) {
            ArrayList<String> factorsClient = llegirFactors(scanner);
            if (!validarFactors(factorsClient.toArray(new String[0]))) {
                missatges.add("Els factors no són vàlids.");
                return missatges;
            }
            nouValor = factorsClient;
        } else if (camp.equals("descompte")) {
            nouValor = llegirDescompte(scanner);
        }
    
        if (nouValor == null) return missatges;
    
        String resultat = modificarClient(clauClient, camp, nouValor);
        if (!resultat.equals("OK")) {
            missatges.add(resultat);
        } else {
            missatges.add("S'ha modificat el client " + clauClient + ".");
        }
    
        return missatges;
    }
    
    /**
     * Gestiona el procés d'esborrar un client existent mitjançant interacció amb l'usuari.
     * 
     * Mostra per pantalla el següent missatge per demanar dades:
     * - "Introdueix la clau del client a esborrar (per exemple, 'client_100'): "
     * 
     * La primera línia del retorn sempre és "=== Esborrar Client ==="
     * 
     * Missatges d'error que s'afegeixen a la llista de retorn:
     * - "Client amb clau " + clauClient + " no existeix."
     * 
     * En cas d'èxit, s'afegeix a la llista:
     * - "S'ha esborrat el client " + clauClient + "."
     * 
     * @param scanner L'objecte Scanner per rebre l'entrada de l'usuari.
     * @return Una llista de cadenes de text que contenen els missatges d'estat del procés.
     * 
     * @test ./runTest.sh "com.exercicis.TestExercici0#testEsborrarClientMenu"
     */
    public static ArrayList<String> esborrarClientMenu(Scanner scanner) {
        ArrayList<String> missatges = new ArrayList<>();
        missatges.add("=== Esborrar Client ===");

        // Llegir clau client
        System.out.print("Introdueix la clau del client a esborrar (per exemple, 'client_100'): ");
        String clauClient = scanner.nextLine().trim();
        if (!clients.containsKey(clauClient)) {
            missatges.add("Client amb clau " + clauClient + " no existeix.");
            return missatges;
        }
        // Esborrar el client
        String resultat = esborrarClient(clauClient);
        if (!resultat.equals("OK")) {
            missatges.add(resultat);
        } else {
            missatges.add("S'ha esborrat el client " + clauClient + ".");
        }
        return missatges;
    }

    /**
     * Gestiona el menú principal de l'aplicació i l'execució de les operacions.
     *
     * Aquesta funció implementa un bucle que:
     * 1. Mostra el menú principal.
     * 2. Mostra els missatges d'error o avís
     * 3. Obté l'opció seleccionada per l'usuari.
     * 4. Executa l'acció corresponent utilitzant les funcions existents.
     * 5. Finalitza quan l'usuari selecciona "Sortir".
     *
     * Els textos mostrats són:
     * - "=== Menú de Gestió de Notaria ==="
     * - "Selecciona una opció (número o paraula clau): "
     * - "Opció no vàlida. Torna a intentar-ho."
     * - "Fins aviat!"
     *
     * @param scanner L'objecte Scanner per llegir l'entrada de l'usuari.
     */
    public static void gestionaClientsOperacions(Scanner scanner) {
        // Ejemplo de interacción con el usuario
        System.out.print("Introduce el nombre del cliente: ");
        String nombre = scanner.nextLine();
        if (!validarNom(nombre)) {
            System.out.println("Nombre no válido.");
            return; // Salir si el nombre no es válido
        }
    
        // Pidiendo una fecha
        System.out.print("Introduce una fecha (AAAA-MM-DD): ");
        String fecha = scanner.nextLine();
        if (!validarData(fecha)) {
            System.out.println("Fecha no válida.");
            return; // Salir si la fecha no es válida
        }
    
        // Pidiendo la edad
        System.out.print("Introduce la edad: ");
        int edad = scanner.nextInt();
        if (!validarEdat(edad)) {
            System.out.println("Edad no válida.");
            return; // Salir si la edad no es válida
        }
        
        // Agregar más validaciones aquí si es necesario
        System.out.println("Datos válidos. Procediendo con la operación.");
    }
    

    /**
     * 
     * @run ./run.sh "com.exercicis.Exercici0"
     * @test ./runTest.sh "com.exercicis.TestExercici0"
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        gestionaClientsOperacions(scanner);

        scanner.close();
    }
}
