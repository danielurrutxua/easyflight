import logging
import json
import re
from utils.file_utils import save_kayak_file, save_file

def add_quotes(json_str):
    # Agregar comillas a las claves de primer nivel
    regex_keys = r'(?<={|,)\s*([a-zA-Z0-9]+)\s*:'
    result = re.sub(regex_keys, r' "\1":', json_str)

    # Reemplazamos las comillas simples por dobles
    pattern = r"(?<!\w)'(?!\w)|(?<=\w)'(?!\w)|(?<!\w)'(?=\w)"
    result = re.sub(pattern, '"', result)
    return result

def get_file_name(search_url):
    pattern = r'/flights/(\w+)-(\w+)/(\d{4}-\d{2}-\d{2})(?:/(\d{4}-\d{2}-\d{2}))?'
    match = re.match(pattern, search_url)
    
    if not match:
        raise ValueError("El string proporcionado no tiene el formato correcto")
    
    origen, destino, fecha_inicio, fecha_fin = match.groups()
    
    if fecha_fin:
        result = f"kayak_{origen}-{destino}-{fecha_inicio}-{fecha_fin}.json"
    else:
        result = f"kaya_{origen}-{destino}-{fecha_inicio}.json"
    
    return result


def extract_dispatch_content(text):
    pattern = r'R9\.redux\.dispatch\((\{.+?\})\)'
    matches = re.findall(pattern, text, flags=re.DOTALL)
    return matches

def save_kayak_response(flow):
    logging.info("KKKKKK")
    logging.info(flow)
    response_text = flow.response.get_text()
    response_json = json.loads(response_text)

    # Accede a los campos del JSON y extrae el contenido de los scripts
    scripts = response_json.get('bufferedScripts')

    # Combina todas las cadenas de los scripts en una sola cadena
    combined_scripts = ' '.join(scripts)
    # Encuentra todas las ocurrencias de 'R9.redux.dispatch(...)'
    matches = extract_dispatch_content(combined_scripts)
    # Busca el contenido en el que el valor de 'type' es 'FlightResultsList'
    flight_results_list = None
    file_name = None
    for match in matches:
        formatted_match = add_quotes(match)
        json_data = json.loads(formatted_match)
        logging.info(json_data.get('type'))
        if 'FlightResultsList' in json_data.get('type'):
            flight_results_list = json_data.get('state').get('results')
        if 'Poll' in json_data.get('type'):
            file_name = get_file_name(json_data.get('state').get('searchUrl'))
 

    if flight_results_list:
        save_kayak_file(file_name, flight_results_list)
    else:
        logging.info("No se encontró un objeto con 'type' igual a 'FlightResultsList'")
            