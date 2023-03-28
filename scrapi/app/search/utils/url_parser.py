from urllib.parse import urlparse, parse_qs

def parse_url_params_to_json_file_name(url):
    if("skyscanner" in url):
        return parse_skyscanner_url
    if("kayak" in url):
        return parse_kayak_url



def parse_skyscanner_url(url):
    #"https://www.skyscanner.es/transporte/vuelos/ber/fnc/230317/230405/?adults=1&adultsv2=1&cabinclass=economy&children=0&childrenv2=&inboundaltsenabled=false&infants=0&originentityid=27547053&outboundaltsenabled=false&preferdirects=false&ref=home&rtn=1"
    # Obtener los componentes de la URL
    parsed_url = urlparse(url)
    
    # Extraer el origen y el destino de la URL
    origen = parsed_url.path.split("/")[-3]
    destino = parsed_url.path.split("/")[-2]
    
    # Extraer la fecha de origen y la fecha de destino (si existe)
    fecha_origen = parsed_url.path.split("/")[-1]
    query_params = parse_qs(parsed_url.query)
    fecha_destino = None
    for param in query_params:
        if param != fecha_origen:
            fecha_destino = query_params[param][0]
            break
    
    # Construir el string con la información extraída
    if fecha_destino:
        info = f"skycanner_{origen}-{destino}-{fecha_origen}_{destino}-{origen}-{fecha_destino}.json"
    else:
        info = f"skyscanner_{origen}-{destino}-{fecha_origen}.json"
    
    # Devolver el string resultante
    return info


def parse_kayak_url(url):
    #"https://www.kayak.es/flights/KUN-RMI/2023-04-22/2023-04-29?sort=bestflight_a"
    # Obtener los componentes de la URL
    parsed_url = urlparse(url)
    
    # Extraer el origen y el destino de la URL
    origen = parsed_url.path.split("/")[-3]
    destino = parsed_url.path.split("/")[-2]
    
    # Extraer la fecha de origen y la fecha de destino (si existe)
    fecha_origen = parsed_url.path.split("/")[-1]
    query_params = parse_qs(parsed_url.query)
    fecha_destino = None
    for param in query_params:
        if param != fecha_origen:
            fecha_destino = query_params[param][0]
            break
    
    # Construir el string con la información extraída
    if fecha_destino:
        info = f"skycanner_{origen}-{destino}-{fecha_origen}-{fecha_destino}.json"
    else:
        info = f"skyscanner_{origen}-{destino}-{fecha_origen}.json"
    
    # Devolver el string resultante
    return info

