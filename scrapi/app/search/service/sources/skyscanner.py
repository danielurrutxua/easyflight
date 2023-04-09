import time
import json
from .utils.chromedriver_generator import create_chromedriver
from seleniumwire.utils import decode
from __init__ import app
  
def scrape(url):
    app.logger.debug('SkyScanner: {}'.format(url))
    driver = create_chromedriver()
    driver.get(url)
    app.logger.debug('Skyscanner page loaded successfully')
    time.sleep(20)
    driver.requests.reverse()
    final_body = None
    max_count_itineraries = 0
    for request in driver.requests:
        if 'https://www.skyscanner.es/g/conductor/v1/' in request.url:
            app.logger.debug('SkyScanner request intercepted')
             
            if request.response:
                body = decode(request.response.body, request.response.headers.get('Content-Encoding', 'identity'))
                count = count_itineraries(body)
                if count > max_count_itineraries:
                    max_count_itineraries = count
                    app.logger.debug("Updated body with {} itineraries".format(max_count_itineraries))
                    final_body = body
                driver.quit()
            else:
                app.logger.debug('SkyScanner response null')
    return final_body
    
def count_itineraries(body):
    json_body = json.loads(body)
    itineraries = json_body.get("itineraries")
    if itineraries is not None:
        return len(itineraries)
    else:
        return 0

    