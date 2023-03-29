import json
import re
import logging
from __init__ import app

def extract_dispatch_content(text):
    pattern = r'R9\.redux\.dispatch\((\{.+?\})\)'
    matches = re.findall(pattern, text, flags=re.DOTALL)
    return matches

def add_quotes(json_str):
    # Agregar comillas a las claves de primer nivel
    regex_keys = r'(?<={|,)\s*([a-zA-Z0-9]+)\s*:'
    result = re.sub(regex_keys, r' "\1":', json_str)

    # Reemplazamos las comillas simples por dobles
    pattern = r"(?<!\w)'(?!\w)|(?<=\w)'(?!\w)|(?<!\w)'(?=\w)"
    result = re.sub(pattern, '"', result)
    return result

def get_json_from_kayak_response(response_text):
    response_json = json.loads(response_text)

    # Accede a los campos del JSON y extrae el contenido de los scripts
    scripts = response_json.get('bufferedScripts')

    # Combina todas las cadenas de los scripts en una sola cadena
    combined_scripts = ' '.join(scripts)
    # Encuentra todas las ocurrencias de 'R9.redux.dispatch(...)'
    matches = extract_dispatch_content(combined_scripts)
    # Busca el contenido en el que el valor de 'type' es 'FlightResultsList'
    flight_results_list = None
    for match in matches:
        formatted_match = add_quotes(match)
        json_data = json.loads(formatted_match)
        logging.info(json_data.get('type'))
        if 'FlightResultsList' in json_data.get('type'):
            flight_results_list = json_data.get('state').get('results')
            return flight_results_list
        
    app.logger.debug("No se encontró un objeto con 'type' igual a 'FlightResultsList'")

 
        