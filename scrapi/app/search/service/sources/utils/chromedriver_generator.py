from selenium import webdriver
import seleniumwire.undetected_chromedriver as uc

def create_chromedriver():
    proxy_port = 8080  # Cambia este valor si mitmproxy está utilizando otro puerto
    # Configuración de opciones
    #options = uc.ChromeOptions()
    #options.add_argument(f"--proxy-server=http://localhost:{proxy_port}")
    #options.add_argument('ignore-certificate-errors')

    '''
    seleniumwire_options = {  
    'proxy': {
        'http': 'http://localhost:8080', # user:pass@ip:port
        'https': 'http://localhost:8080',
        'no_proxy': 'localhost,127.0.0.1'
    }
    }
    '''

    # Crear el driver con las opciones configuradas
    #driver = webdriver.Chrome(options=options, seleniumwire_options=seleniumwire_options)
    #options.arguments.extend(["--no-sandbox", "--disable-setuid-sandbox"])     # << this
    options = uc.ChromeOptions()
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-setuid-sandbox')
    options.add_argument('--disable-dev-shm-usage')  
    driver = uc.Chrome(options=options)
    '''
    driver.scopes = [
    '.*kayak.*',
    '.*skyscanner.*'
]
'''

    return driver