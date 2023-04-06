# Use la imagen oficial de MySQL
FROM mysql:8.0

# Copy the CSV file to the container
COPY airports.csv /var/lib/mysql-files/airports.csv

# Copiar archivos al contenedor
COPY init.sql /docker-entrypoint-initdb.d/
COPY airports.csv /docker-entrypoint-initdb.d/

# Exponer el puerto 3306 para conectarse a MySQL
EXPOSE 3306
