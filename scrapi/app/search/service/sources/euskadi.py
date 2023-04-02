import time
import json
from .utils.chromedriver_generator import create_chromedriver
from seleniumwire.utils import decode
from __init__ import app
  
def scrape(url):
    driver = create_chromedriver()
    driver.get(url)
    app.logger.debug('Euskadi page loaded successfully')
    driver.requests.reverse()
    for request in driver.requests:
        if 'https://api.euskadi.eus/directory/entities' in request.url:
            app.logger.debug('Euskadi api request found')
            body = request.response.body.decode('utf-8')
            json_data = json.loads(body)
            
            return body