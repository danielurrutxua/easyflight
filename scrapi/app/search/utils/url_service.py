
def get_response_url(url_request):
    urls_dic = {'kayak': 'https://www.kayak.es/s/horizon/flights/results/FlightSearchPoll?p=0', 'skyscanner': 'https://www.skyscanner.es/g/conductor/v1/'}
    for name, url in urls_dic.items():
        if name in url_request:
            return name, url
    return None, None 
