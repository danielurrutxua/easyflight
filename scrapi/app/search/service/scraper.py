from __init__ import app
from .sources.skyscanner import scrape as scrape_skyscanner
from .sources.kayak import scrape as scrape_kayak
from .sources.euskadi import scrape as scrape_euskadi

   
def execute(url):
    if 'skyscanner' in url:
        app.logger.debug("Scrape skyscanner request")
        return scrape_skyscanner(url)     
    if 'kayak' in url:
        app.logger.debug("Scrape kayak request")
        return scrape_kayak(url)
    if 'nation' in url:
        return scrape_euskadi(url)

            



    