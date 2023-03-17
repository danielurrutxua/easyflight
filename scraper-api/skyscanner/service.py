import time
import undetected_chromedriver as uc
from selenium import webdriver
from fake_useragent import UserAgent
import random
from .mitm_capture import MyAddon, captured_responses
from mitmproxy.tools.main import mitmproxy
from mitmproxy import options, controller, proxy
import seleniumwire.undetected_chromedriver as webdriver

def run_mitmproxy():
    # Configurar mitmproxy
    addons = [MyAddon()]
    opts = options.Options()
    p = proxy.ProxyServer(opts)
    m = controller.Master(opts)
    m.server = p
    m.addons.add(*addons)
    m.run()
    return m

    # Ejecutar mitmproxy en un hilo diferente
    mitmproxy(opts)

def get_results(url):

    # Iniciar mitmproxy en un hilo diferente
    m = run_mitmproxy

    driver = create_chromedriver()

    #url = f"https://www.skyscanner.es/transporte/vuelos/ber/fnc/230317/230405/?adults=1&adultsv2=1&cabinclass=economy&children=0&childrenv2=&inboundaltsenabled=false&infants=0&originentityid=27547053&outboundaltsenabled=false&preferdirects=false&ref=home&rtn=1"
    #url = f"http://twitter.es/"
    #"https://antcpt.com/score_detector/"
    driver.get(url)

    try:
        # Esperar a que los resultados se carguen
        time.sleep(5)  # Aumenta este valor si es necesario
    finally:
        # Cerrar navegador
        driver.quit()

     # Devolver la respuesta JSON capturada, si la hay
    if not captured_responses.empty():
        captured_response = captured_responses.get()
        m.shutdown()
        return captured_response
    else:
        print('No JSON response was captured')
        m.shutdown()
        return None
    
    

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


if __name__ == "__main__":


    buscar_vuelos()

    