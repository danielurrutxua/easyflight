# Flight Search System - Web Scraping

This project is a flight search system that uses web scraping techniques to collect data. It is composed of three main components:

- `android-client`: An Android application that serves as a user interface to perform searches and visualize results.
- `server`: A Spring Boot-based server that handles requests from the mobile application and communicates with the web scraping component.
- `scrapi`: A Flask API that performs web scraping and provides data to `server`.

## Getting Started

To start up the entire project, a Docker Compose file is included that brings up the corresponding images from DockerHub.

### Prerequisites

- Docker
- Docker Compose

### Installation

1. Clone the repository:
```bash
  git clone https://github.com/danielurrutxua/easyflight.git
```
2. Navigate to the project directory:
```bash
  cd easyflight
```
3. Bring up the containers with Docker Compose:
```bash
  docker-compose up
```
## Usage

### Android Client

The Android application serves as a user interface for conducting searches and visualizing the results. To use it, follow these steps:

1. Ensure you have Android Studio installed. You can download it from [here](https://developer.android.com/studio).

2. Import the `android-client` project into Android Studio. To do this, open Android Studio, select `File` -> `New` -> `Import Project` and select the `android-client` directory.

3. After importing the project, generate the APK file. Go to `Build` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`. 

4. Once the APK file is generated, you can install it on your Android device. Connect your device to your machine and copy the APK to your device. Then, on your device, navigate to the location of the APK file using a file manager, tap it, and install it. 

> **Note:** Be sure to enable the "Install unknown apps" option in your device's security settings in order to install the APK.

5. Open the application and enter the details of your flight search.

### Server and Scrapi

The server and the web scraping API automatically start up when you execute `docker-compose up` at the project root. No manual intervention is needed to utilize these components.


## Development

If you wish to contribute to the project or customize it for your use, here are the instructions for setting up the various components locally:

### Android Client

To develop on the Android client, you will need Android Studio. Import the project as described in the [Usage](#usage) section and you will be ready to start developing.

### Server

To develop on the Spring Boot server, follow these steps:

1. Ensure you have an appropriate development environment installed for Java and Spring Boot, such as [IntelliJ IDEA](https://www.jetbrains.com/idea/).
2. Import the `server` project into your development environment.
3. Modify the `application.properties` file to point to a local MySQL database. Make sure you have a MySQL instance running locally and provide the connection details in `application.properties`.

### Scrapi

To develop on the Flask API, you will need a Python environment. Here are the steps to prepare your environment:

1. Make sure you have Python installed on your machine. You can download Python from [here](https://www.python.org/downloads/).
2. I recommend using a virtual environment to manage dependencies. You can create one with the following command:
 ```bash
   python3 -m venv venv
 ```
Then activate it with:
```bash
   source venv/bin/activate  # En Unix o MacOS
   .\venv\Scripts\activate   # En Windows
```
 3. Install the necessary dependencies with pip, the Python package manager. The dependencies are listed in `scrapi/requirements.txt`. To install them, navigate to the `scrapi` directory and execute:
```bash
   pip install -r requirements.txt
```
4. You should now be ready to start developing in scrapi.
