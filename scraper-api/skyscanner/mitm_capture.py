from mitmproxy import http
from mitmproxy.addons import 
import json
import queue

captured_responses = queue.Queue()
class MyAddon(http.HTTPAddon):
    def request(self, flow: http.HTTPFlow) -> None:
        # Crear una cola global para almacenar las respuestas capturadas
        
        if flow.request.pretty_url.startswith("https://www.skyscanner.es/g/conductor/v1"):
            try:
                response_data = json.loads(flow.response.text)
            except json.JSONDecodeError:
                return

            captured_data = {
                "url": flow.request.url,
                "status_code": flow.response.status_code,
                "headers": {k: v for k, v in flow.response.headers.items()},
                "response_data": response_data,
            }
        # Agregar la respuesta capturada a la cola
        captured_responses.put(captured_data)




