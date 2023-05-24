# Sistema de Búsqueda de Vuelos - Web Scraping

Este proyecto consiste en un sistema de búsqueda de vuelos que utiliza técnicas de web scraping para recolectar datos. Se compone de tres componentes principales:

- `android-client`: Una aplicación de Android que sirve como interfaz de usuario para realizar búsquedas y visualizar resultados.
- `server`: Un servidor basado en Spring Boot que maneja las solicitudes de la aplicación móvil y se comunica con el componente de web scraping.
- `scrapi`: Una API en Flask que realiza el web scraping y proporciona los datos a `server`.

## Comenzando

Para levantar todo el proyecto, se incluye un archivo Docker Compose que levanta las imágenes correspondientes desde DockerHub.

### Prerrequisitos

- Docker
- Docker Compose

### Instalación

1. Clona el repositorio:
```bash
  git clone https://github.com/danielurrutxua/easyflight.git
```
2. Navega al directorio del proyecto:
```bash
  cd easyflight
```
3. Levanta los contenedores con Docker Compose:
```bash
  docker-compose up
```


## Uso

### Android Client

La aplicación de Android sirve como interfaz de usuario para realizar búsquedas y visualizar los resultados. Para usarla, sigue estos pasos:

1. Asegúrate de tener instalado Android Studio. Puedes descargarlo desde [aquí](https://developer.android.com/studio).

2. Importa el proyecto `android-client` en Android Studio. Para hacerlo, abre Android Studio, selecciona `File` -> `New` -> `Import Project` y selecciona el directorio `android-client`.

3. Después de importar el proyecto, genera el archivo APK. Ve a `Build` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`. 

4. Una vez generado el archivo APK, puedes instalarlo en tu dispositivo Android. Conecta tu dispositivo a tu máquina y copia el APK a tu dispositivo. Luego, en tu dispositivo, navega hasta la ubicación del archivo APK usando un administrador de archivos, tócalo e instálalo. 

> **Nota:** Asegúrate de habilitar la opción "Instalar aplicaciones desconocidas" en la configuración de seguridad de tu dispositivo para poder instalar el APK.

5. Abre la aplicación e ingresa los detalles de tu búsqueda de vuelos.

### Server y Scrapi

El servidor y la API de web scraping se levantan automáticamente cuando ejecutas `docker-compose up` en la raíz del proyecto. No es necesario ninguna intervención manual para utilizar estos componentes.



## Desarrollo

Si deseas contribuir al proyecto o personalizarlo para tu uso, aquí están las instrucciones para levantar los distintos componentes de manera local:

### Android Client

Para desarrollar en el cliente de Android, necesitarás Android Studio. Importa el proyecto como se describe en la sección [Uso](#uso) y estarás listo para comenzar a desarrollar.

### Server

Para desarrollar en el servidor Spring Boot, sigue estos pasos:

1. Asegúrate de tener instalado un entorno de desarrollo adecuado para Java y Spring Boot, como [IntelliJ IDEA](https://www.jetbrains.com/idea/).
2. Importa el proyecto `server` en tu entorno de desarrollo.
3. Modifica el archivo `application.properties` para apuntar a una base de datos MySQL local. Asegúrate de tener una instancia de MySQL en ejecución localmente y proporciona los detalles de conexión en `application.properties`.

### Scrapi

Para desarrollar en la API de Flask, necesitarás un entorno de Python. Aquí están los pasos para preparar tu entorno:

1. Asegúrate de tener Python instalado en tu máquina. Puedes descargar Python desde [aquí](https://www.python.org/downloads/).
2. Recomiendo usar un entorno virtual para gestionar las dependencias. Puedes crear uno con el siguiente comando:
 ```bash
   python3 -m venv venv
 ```
 Y luego actívalo con:
```bash
   source venv/bin/activate  # En Unix o MacOS
   .\venv\Scripts\activate   # En Windows
```
 3. Instala las dependencias necesarias con pip, el gestor de paquetes de Python. Las dependencias se enumeran en `scrapi/requirements.txt`. Para instalarlas, navega hasta el directorio `scrapi` y ejecuta:
```bash
   pip install -r requirements.txt
```
4. Ahora deberías estar listo para comenzar a desarrollar en scrapi.
