import logging
import json
import os

def save_skyscanner_response(flow):
    logging.info("AAAAAA")
    logging.info(flow)
    response_text = flow.response.get_text()
    response_json = json.loads(response_text)
    file_name = get_skyscanner_json_name(response_json)
    file_path = os.path.join('/mitmt/responses/', file_name)
    with open(file_path, 'w') as f:
        json.dump(response_json, f)

def get_skyscanner_json_name(json):
    file_name = "skysanner"
    legs = json["query"]["legs"]
    for leg in legs:
        origin = get_place_name_from_id(leg["origin"], json["places"])
        destination = get_place_name_from_id(leg["destination"], json["places"])
        date = leg["date"]

        # Agregar la información de cada variable al nombre de archivo
        file_name += f"_{origin}-{destination}-{date}"
    return file_name

def get_place_name_from_id(id, places):
    for place in places:
        if place["id"] == id:
            return place["alt_id"]