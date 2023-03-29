import time
from ..utils.chromedriver_generator import create_chromedriver
from ..utils.url_service import get_response_url
from ..utils.kayak_parser import get_json_from_kayak_response
from seleniumwire.utils import decode
from __init__ import app



def get_results(url):
    driver = create_chromedriver()
    app.logger.debug("Llega al service")
    #url = f"https://www.skyscanner.es/transporte/vuelos/ber/fnc/230317/230405/?adults=1&adultsv2=1&cabinclass=economy&children=0&childrenv2=&inboundaltsenabled=false&infants=0&originentityid=27547053&outboundaltsenabled=false&preferdirects=false&ref=home&rtn=1"
    #url1 = f"https://www.kayak.es/flights/BIO-SVG/2023-04-24/2023-04-29?sort=bestflight_a"
    url2 = f"https://twitter.com/"
    url1 = "https://antcpt.com/score_detector/"
    driver.get(url)
    app.logger.debug("Carga la pagina")

    source_name, url_response = get_response_url(url)
    time.sleep(5)
    if source_name == 'skyscanner':
        for request in driver.requests.reversed():
            if url_response in request.url :
                return decode(request.response.body, request.response.headers.get('Content-Encoding', 'identity'))
    if source_name == 'kayak':
         for request in driver.requests:
            if url_response in request.url:
                app.logger.debug('Kayak response found')
                json_data = {}
                for i in range(2):
                    app.logger.debug('Index {}'.format(i))
                    new_url = request.url[:-1] + str(i)
                    request.url = new_url
                    # Realiza la solicitud modificada
                    body = decode(request.response.body, request.response.headers.get('Content-Encoding', 'identity'))
                    app.logger.debug('Body captured {}'.format(new_url))
                    formatted_body = get_json_from_kayak_response(body)
                    app.logger.debug(formatted_body.keys())
                    app.logger.debug(len(formatted_body))
                    for key, value in formatted_body.items():
                        json_data[key] = value
                    app.logger.debug(len(json_data))
                    time.sleep(10)
                return json_data
    driver.quit()

    

    #try:
        # Esperar a que los resultados se carguen
        #time.sleep(100)  # Aumenta este valor si es necesario

        #response_json = search_captured_response(url)
        #return response_json

    #finally:
        # Cerrar navegador

#kill -9 122629
#lsof -i :8080

        #driver.quit()


    # Cuando el hilo termina, se recuperan las respuestas capturadas
'''
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
'''



    