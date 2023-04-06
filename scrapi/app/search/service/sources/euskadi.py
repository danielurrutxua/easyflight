import time
import json
from .utils.chromedriver_generator import create_chromedriver
from seleniumwire.utils import decode
from __init__ import app
import bs4 as BeautifulSoup

  
def scrape(url):
    driver = create_chromedriver()
    driver.get(url)
    
    page_source = driver.page_source

    # Pasar el HTML a Beautiful Soup
    soup = BeautifulSoup(page_source, "html.parser")

    # Crear un archivo CSV y agregar encabezados de columnas
    output_file = "airport.csv"
    with open(output_file, "w") as file:
        file.write("iata,name,country\n")

        # Analizar el HTML y extraer la información
        for element in soup.find_all("tbody"):
            for element1 in element.find_all("tr"):
                info = element1.find_all("td")
                if len(info) == 3:
                    iata = info[2].get_text()
                    name = info[0].get_text()
                    country = info[1].get_text()

                    # Agregar una nueva línea con los datos al archivo CSV
                    file.write(f"{iata},{name},{country}\n")

    print(f"Datos guardados en {output_file}.")

    # Cerrar el navegador y finalizar la instancia de ChromeDriver
    driver.quit()