from __init__ import app
from .sources.skyscanner import scrape as scrape_skyscanner
from .sources.kayak import scrape as scrape_kayak

   
def execute(url):
    if 'skyscanner' in url:
        app.logger.debug("Scrape skyscanner request")
        return scrape_skyscanner(url)     
    if 'kayak' in url:
        app.logger.debug("Scrape kayak request")
        return scrape_kayak(url)

            



    