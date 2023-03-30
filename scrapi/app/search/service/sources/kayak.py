import time
from .utils.chromedriver_generator import create_chromedriver
from .utils.kayak_parser import get_json_from_kayak_response, add_quotes
from seleniumwire.utils import decode
from __init__ import app

def scrape(url):
    return KayakScraper().get_results(url)

class KayakScraper:
    def __init__(self):
        self.first_kayak_request = None
        self.page_index = 1
        self.waiting_responses = True
        self.json_data = {}
        self.max_kayak_requests = 3
        
    def save_kayak_response(self, request):
        body = decode(request.response.body, request.response.headers.get('Content-Encoding', 'identity'))
        formatted_body = get_json_from_kayak_response(body)
        if formatted_body:
            app.logger.debug(formatted_body.keys())
            for key, value in formatted_body.items():
                self.json_data[key] = value
            app.logger.debug(len(self.json_data))

    def interceptor(self, request):
            if 'https://www.kayak.es/s/horizon/flights/results/FlightSearchPoll?p=3' in request.url:
                body = request.body.decode('utf-8')
                body = body.replace('pageNumber=1', 'pageNumber=2')
                body = body.replace('append=false', 'append=true')
                request.body = body.encode('utf-8')
                self.wait_search_completed = False
            


    def get_results(self, url):
        driver = self.launch_driver(url)
        app.logger.debug('Kayak page loaded successfully')
        self.wait_search_completed()
        for request in driver.requests:
            if 'https://www.kayak.es/s/horizon/flights/results/FlightSearchPoll' in request.url:
                app.logger.debug('Kayak response found {}'.format(request.url))
                app.logger.debug('Response status: {}'.format(request.response.status_code))
                self.save_kayak_response(request)
                if request.url[-1] == str(self.max_kayak_requests):
                    driver.quit()
                    return self.json_data
                
    def wait_search_completed(self):
        while(self.waiting_responses):
            app.logger.debug("WAITING")
            time.sleep(2)
    
    def launch_driver(self, url):
        driver = create_chromedriver()
        driver.request_interceptor = self.interceptor
        driver.get(url)
        return driver