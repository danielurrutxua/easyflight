from selenium import webdriver
from fake_useragent import UserAgent
import seleniumwire.undetected_chromedriver as webdriver

def create_chromedriver():
    proxy_port = 8080  # Cambia este valor si mitmproxy está utilizando otro puerto
    # Configuración de opciones
    options = webdriver.ChromeOptions()
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