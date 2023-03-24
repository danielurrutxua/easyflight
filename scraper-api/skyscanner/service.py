import time
import os
import json
from .url_parser import parse_url_params_to_json_file_name
from .chromedriver_generator import create_chromedriver



def get_results(url):

    driver = create_chromedriver()

    #url = f"https://www.skyscanner.es/transporte/vuelos/ber/fnc/230317/230405/?adults=1&adultsv2=1&cabinclass=economy&children=0&childrenv2=&inboundaltsenabled=false&infants=0&originentityid=27547053&outboundaltsenabled=false&preferdirects=false&ref=home&rtn=1"
    url1 = f"https://www.kayak.es/flights/BCN-PMI/2023-04-24/2023-04-29?sort=bestflight_a"
    #"https://antcpt.com/score_detector/"
    driver.get(url1)
    

    try:
        # Esperar a que los resultados se carguen
        time.sleep(100)  # Aumenta este valor si es necesario

        response_json = search_captured_response(url)
        return response_json

    finally:
        # Cerrar navegador

#kill -9 122629
#lsof -i :8080

        driver.quit()


    # Cuando el hilo termina, se recuperan las respuestas capturadas

def search_captured_response(url):
    file_name = parse_url_params_to_json_file_name(url)
    # Construir la ruta del archivo JSON
    ruta_archivo = os.path.join("/mitmt", file_name)

    # Verificar si el archivo existe en la ruta especificada
    if os.path.exists(ruta_archivo):
        # Cargar el archivo JSON en Python
        with open(ruta_archivo, "r") as f:
            json_data = json.load(f)
        
        # Hacer algo con el archivo JSON cargado, por ejemplo:
        return json_data
    else:
        print("El archivo no existe en la ruta especificada")
        return None




    