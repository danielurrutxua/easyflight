import time
from .utils.chromedriver_generator import create_chromedriver
from seleniumwire.utils import decode
from __init__ import app
  
def scrape(url):
    driver = create_chromedriver()
    driver.get(url)
    app.logger.debug('Skyscanner page loaded successfully')
    time.sleep(10)
    driver.requests.reverse()
    for request in driver.requests:
        if 'https://www.skyscanner.es/g/conductor/v1/' in request.url:
            body = decode(request.response.body, request.response.headers.get('Content-Encoding', 'identity'))
            driver.quit
            return body
    
        

    