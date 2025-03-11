# Base de dades MySQL i Docker

Cal instal·lar la base de dates 'dbGovernment.sql' en un servidor MySQL.

## Instal·lar base de dades MySQL

Per posar 'dbGovernment.sql' en un servidor MySQL amb Docker, amb la comanda 'docker' disponible i el servei 'Docker' funcionant:

A Linux i macOS:
```bash
docker pull mysql
docker run --name mysqlServer -p 3308:3306 -e MYSQL_ROOT_PASSWORD=pwd -d mysql
docker exec -i mysqlServer mysql -uroot -ppwd < dbGovernment.sql
```

A Windows:
```bash
docker pull mysql
docker run --name mysqlServer -p 3308:3306 -e MYSQL_ROOT_PASSWORD=pwd -d mysql
type dbGovernment.sql | docker exec -i mysqlServer mysql -uroot -ppwd
```

Amb la configuració anterior, s'accedeix al servidor MySQL del contenedor Docker, a través de:

* Usuari 'root'
* Constrasenya 'pwd'
* Port '3308'

Per llistar les bases de dades disponibles:
```bash
docker exec -it mysqlServer mysql -uroot -ppwd -e "SHOW DATABASES;"
```

Així, per veure les taules de la base de dades anterior des de la linia de comandes, des de 'docker exec' que executa comandes dins del terminal d'aquest contenedor Docker:

```bash
docker exec -it mysqlServer mysql -uroot -ppwd -e "USE government; SHOW TABLES;"
```

Per veure els continguts de la taula 'city':

```bash
docker exec -it mysqlServer mysql -uroot -ppwd -e "USE government; SELECT * FROM Citizens LIMIT 0,10;"
```

En canvi, per veure les taules de la base de dades anterior des de la linia de comandes, des de 'mysql' al port 3308:

```bash
mysql -h 127.0.0.1 -P 3308 -uroot -ppwd
```

Després des d'aquesta consola MySQL:
```SQL
USE government;
SHOW TABLES;
EXIT;
```

Per veure els continguts de la taula 'city':

```bash
mysql -h 127.0.0.1 -P 3308 -uroot -ppwd
```

Després des d'aquesta consola MySQL:
```SQL
USE government;
SELECT * FROM Citizens LIMIT 0,10;
EXIT;
```

