import time
import undetected_chromedriver as uc
from selenium import webdriver
from fake_useragent import UserAgent
import seleniumwire.undetected_chromedriver as webdriver
from mitm.initializer import start_mitmproxy
import signal
import threading


def get_results(url):

    #start_mitmproxy("https://mipagina.es/conductor/v1")
    #start_mitmproxy()

    driver = create_chromedriver()

    #url = f"https://www.skyscanner.es/transporte/vuelos/ber/fnc/230317/230405/?adults=1&adultsv2=1&cabinclass=economy&children=0&childrenv2=&inboundaltsenabled=false&infants=0&originentityid=27547053&outboundaltsenabled=false&preferdirects=false&ref=home&rtn=1"
    #url1 = f"https://twitter.com/"
    #"https://antcpt.com/score_detector/"
    driver.get(url)

    try:
        # Esperar a que los resultados se carguen
        time.sleep(100)  # Aumenta este valor si es necesario
    finally:
        # Cerrar navegador
        driver.quit()


    # Cuando el hilo termina, se recuperan las respuestas capturadas



"""
     # Devolver la respuesta JSON capturada, si la hay
    if not captured_responses.empty():
        captured_response = captured_responses.get()
        p.terminate()
        return captured_response
    else:
        print('No JSON response was captured')
        p.terminate()
        return None
"""
    
    

def create_chromedriver():
    proxy_port = 8080  # Cambia este valor si mitmproxy está utilizando otro puerto
    # Configuración de opciones
    options = uc.ChromeOptions()

    # Utilizar fake_useragent para generar un User-Agent aleatorio
    ua = UserAgent()
    user_agent = ua.random
    #options.add_argument(f'user-agent={user_agent}')
    options.add_argument(f"--proxy-server=http://localhost:{proxy_port}")
    options.add_argument('ignore-certificate-errors')

    seleniumwire_options = {  
    'proxy': {
        'http': 'http://localhost:8080', # user:pass@ip:port
        'https': 'http://localhost:8080',
        'no_proxy': 'localhost,127.0.0.1'
    }
    }

    # Crear el driver con las opciones configuradas
    driver = webdriver.Chrome(options=options, seleniumwire_options=seleniumwire_options)

    return driver


    