import time
import json
from .utils.chromedriver_generator import create_chromedriver
from seleniumwire.utils import decode
from __init__ import app

  
def scrape(url):
    driver = create_chromedriver()
    driver.get(url)
    
    # Cerrar el navegador y finalizar la instancia de ChromeDriver
    driver.quit()