-- Crear un nuevo usuario y otorgarle privilegios de SELECT en la base de datos db_easyflight
CREATE USER 'java_user'@'server' IDENTIFIED BY 'fl1nt4c03';
GRANT SELECT ON db_easyflight.* TO 'java_user'@'server';
FLUSH PRIVILEGES;

-- Crea la tabla airports
CREATE TABLE IF NOT EXISTS airport (
    iata VARCHAR(3) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    country VARCHAR(50)
);

-- Importa los datos del archivo CSV en la tabla airports
LOAD DATA INFILE '/var/lib/mysql-files/airports.csv'
INTO TABLE airport
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(name, city, country, iata)