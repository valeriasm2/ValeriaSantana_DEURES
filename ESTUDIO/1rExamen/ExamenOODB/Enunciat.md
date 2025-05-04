# Examen OO, DDBB

Modifica l'arxiu:

```bash
./src/main/java/com/examen/FileToDatabase.java
```

Per generar una base de dades *'data/personatges.sqlite' fent servir la informació de l'arxiu *'data/personatges.json'.

La base de dades ha de tenir les taules:

```text
taula cantants:
- id enter clau primaria autoincrement
- nom_complet text no null
- any_naixement enter
- discs_aurats text
- pais_naixement text
- grup_principal text
```

```text
taula esportistes:
- id enter clau primaria autoincrement
- nom_complet text no null
- any_naixement enter
- copes_mundials enter
- pais_naixement text
- equip_principal text
```

```text
taula cientifics:
- id enter clau primaria autoincrement
- nom_complet text no null
- any_naixement enter
- nobels enter
- pais_naixement text
- universitat_principal text
```

Modifica l'arxiu:

```bash
./src/main/java/com/examen/DatabaseToFile.java
```

Per tal de generar un arxiu *'data/animals.json'* fent servir les dades de *'data/animals.sqlite'*.

Aquest és un exemple de l'estructura i camps que ha de tenir *data/animals.json*:

```json
{
  "mamifers": [
    {
        "habitat": "Sabanes i zones semidesèrtiques d'Àfrica i una petita població a l'Índia",
        "pes_mitja_kg": 190,
        "ordre": "Carnivora",
        "nom_comu": "Lleó",
        "alimentacio": "Carnívor",
        "esperanca_vida_anys": [
            10,
            14
        ],
        "nom_cientific": "Panthera leo",
        "caracteristica_distintiva": "Gran crinera en mascles adults, caça en grup"
    }
  ],
  "aus": [
    {
        "habitat": "Muntanyes, penya-segats i zones obertes de l'hemisferi nord",
        "ordre": "Accipitriformes",
        "migratoria": "No (majoritàriament resident)",
        "nom_comu": "Àguila daurada",
        "alimentacio": "Carnívor / Rapinyaire (mamífers mitjans, aus, rèptils)",
        "nom_cientific": "Aquila chrysaetos",
        "caracteristica_distintiva": "Gran envergadura, vol potent, vista molt aguda",
        "envergadura_mitja_cm": 200
    }
  ]
}
```

## Instruccions

- Omple l'arxiu **"DadesAmumne.md"** amb les teves dades

- Entrega un arxiu **".zip"** anomenat: "NomCognom.zip" que contingui tot el projecte maven i els arxius **.sh** per executar-lo.

- Entrega la gravació de la pantalla en un arxiu fora del **".zip"**

- Entrega els arxius anteriors en un **Pen Drive**

## Important

- Has d'estar connectat a la WiFi d'exàmen tota la duració de l'exàmen.

- No pots fer servir Internet, eines de comunicació, ni assistents d'IA

- Qualsevol sospita de trampa resultarà en un 0

# Puntuació

Cada taula canviada de format val 2 punts.