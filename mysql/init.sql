-- Crear un nuevo usuario y otorgarle privilegios de SELECT en la base de datos db_easyflight
CREATE USER 'java_user'@'192.168.0.10' IDENTIFIED BY 'fl1nt4c03';
GRANT SELECT, INSERT ON db_easyflight.* TO 'java_user'@'192.168.0.10';

FLUSH PRIVILEGES;

-- Crea la tabla airports
CREATE TABLE IF NOT EXISTS airport (
    iata VARCHAR(3) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS airline (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    logo_url VARCHAR(500) NOT NULL
);

-- Importa los datos del archivo CSV en la tabla airports
LOAD DATA INFILE '/var/lib/mysql-files/airports.csv'
INTO TABLE airport
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(name, country, iata)