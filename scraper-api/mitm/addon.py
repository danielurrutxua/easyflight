from mitmproxy import http
import logging
from mitm.services.skyscanner import save_skyscanner_response

class CaptureAddon:
    
    def request(self, flow:http.HTTPFlow) -> None:

        if flow.request.url.startswith("https://www.kayak.es/s/horizon/flights/results/FlightSearchPoll?p=0"):
            max_kayak_requests = 5
            # Iterar sobre cada valor y enviar la petición con ese valor
            for i in range(max_kayak_requests):
                # Cambia el valor del parametro 'p' en la url
                modified_url = flow.request.url.replace("p=0", f"p={i}")
                logging.info(modified_url)

                new_flow = flow.copy()
                new_flow.request.url = modified_url
    
    def response(self, flow:http.HTTPFlow) -> None:
        if flow.request.url.startswith("https://www.skyscanner.es/g/conductor/v1/"):
            save_skyscanner_response(flow)

        if flow.request.url.startswith("https://www.kayak.es/s/horizon/flights/results/FlightSearchPoll"):

addons = [CaptureAddon()]