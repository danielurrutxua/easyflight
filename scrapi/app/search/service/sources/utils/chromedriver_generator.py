import seleniumwire.undetected_chromedriver as uc
from fake_useragent import UserAgent

def create_chromedriver():
    proxy_port = 8080  # Cambia este valor si mitmproxy está utilizando otro puerto
    # Configuración de opciones
    options = uc.ChromeOptions()
    ua = UserAgent()
    user_agent = ua.random
    options.add_argument(f'user-agent={user_agent}')
    options.headless = True 
    options.add_argument(f"--proxy-server=http://proxy:{proxy_port}")
    options.add_argument('ignore-certificate-errors')

    seleniumwire_options = {  
    'proxy': {
        'http': 'http://proxy:8080', # user:pass@ip:port
        'https': 'http://proxy:8080',
        'no_proxy': 'localhost,127.0.0.1'
    }
    }
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-setuid-sandbox')
    options.add_argument('--disable-dev-shm-usage')  
    driver = uc.Chrome(options=options, seleniumwire_options=seleniumwire_options)

    driver.scopes = [
    '.*kayak.*',
    '.*skyscanner.*'
]
    return driver