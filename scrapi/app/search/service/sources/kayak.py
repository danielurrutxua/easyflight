import time
from .utils.chromedriver_generator import create_chromedriver
from .utils.kayak_parser import get_json_from_kayak_response
from seleniumwire.utils import decode
from __init__ import app
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def scrape(url):
    return KayakScraper().get_results(url)

class KayakScraper:
    def __init__(self):
        self.json_data = {}
        
    def save_kayak_response(self, request):
        body = decode(request.response.body, request.response.headers.get('Content-Encoding', 'identity'))
        formatted_body = get_json_from_kayak_response(body)
        if formatted_body:
            app.logger.debug(formatted_body.keys())
            for key, value in formatted_body.items():
                self.json_data[key] = value
            app.logger.debug(len(self.json_data))
         
    def get_results(self, url):
        driver = self.launch_driver(url)
        app.logger.debug('Kayak page loaded successfully')
        self.show_more_results(driver)

        for request in filter(lambda x: 'FlightSearchPoll' in x.url, driver.requests):
            app.logger.debug('Kayak response found {}'.format(request.url))
            if request.response:
                self.save_kayak_response(request)

        driver.quit()
        return self.json_data
    
    def launch_driver(self, url):
        driver = create_chromedriver()
        #driver.request_interceptor = self.interceptor
        driver.get(url)
        return driver
    
    def show_more_results(self, driver):
        error = False
        try:
            WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CLASS_NAME, 'dDYU-close'))).click()
        except:
            app.logger.debug('No se pudo hacer clic en el botón "dDYU-close"')
            error = True
    
        if not error:
            time.sleep(5)
            error = self.load_page(driver, 1)
        
        if not error:
            time.sleep(5)
            error = self.load_page(driver, 2)
            time.sleep(5)

    def load_page(self, driver, index):
        try:
            WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CLASS_NAME, 'show-more-button'))).click()
            app.logger.debug('Showing more results: {}'.format(index))
            return False
        except:
            app.logger.debug('No se pudo hacer clic en el botón "show-more-button" {}'.format(index))
            return True
        

        

