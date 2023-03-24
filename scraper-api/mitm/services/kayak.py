import logging

def save_kayak_response(flow):
    logging.info("KKKKKK")
    logging.info(flow)
    response_text = flow.response.get_text()
    response_json = json.loads(response_text)
    file_name = "kayak" + flow.request.url[-1]
    with open(file_name, 'w') as f:
        json.dump(response_json, f)
    
    if int(flow.request.url[-1]) == self.kayak_max_requests:
        