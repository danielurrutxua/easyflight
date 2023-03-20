import json
from mitmproxy import http
import logging

class CaptureAddon:

    def response(self, flow: http.HTTPFlow) -> None:
        if flow.request.url.startswith("https://www.skyscanner.es/g/conductor/v1/"):
            logging.info("AAAAAA")
            logging.info(flow)
            response_text = flow.response.get_text()
            response_json = json.loads(response_text)
            with open('responses.json', 'w') as f:
                json.dump(response_json, f)

addons = [CaptureAddon()]               