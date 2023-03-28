import logging
import json
from utils.file_utils import save_file

def save_skyscanner_response(flow):
    logging.info("AAAAAA")
    logging.info(flow)
    response_text = flow.response.get_text()
    response_json = json.loads(response_text)
    file_name = get_skyscanner_json_name(response_json)
    save_file(file_name, response_json)

def get_skyscanner_json_name(json):
    file_name = "skysanner"
    legs = json["query"]["legs"]
    origin = get_place_name_from_id(legs[0]["origin"], json["places"])
    destination = get_place_name_from_id(legs[0]["destination"], json["places"])
    date = legs[0]["date"]
    # Agregar la información de cada variable al nombre de archivo
    file_name += f"_{origin}-{destination}-{date}"
    if legs[1]:
        return_date = legs[1]["date"]
        file_name += f"-{return_date}"

    return file_name

def get_place_name_from_id(id, places):
    for place in places:
        if place["id"] == id:
            return place["alt_id"]