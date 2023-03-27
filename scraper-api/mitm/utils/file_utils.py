import json
import os

def save_file(file_name, content):
    file_path = os.path.join('mitm', 'responses', file_name)
    with open(file_path, 'w') as f:
        json.dump(content, f)

def save_kayak_file(file_name, content):
    file_path = os.path.join('mitm', 'responses', file_name)
    if not os.path.isfile(file_path):
        # Si no existe, crea el archivo y guarda el contenido de flight_results_list
        with open(file_path, 'w') as file:
            json.dump(content, file)
    else:
        # Si existe, carga el contenido del archivo en una variable
        with open(file_path, 'r') as file:
            json_data = json.load(file)

        # Agrega los atributos del nuevo objeto JSON al diccionario
        for key, value in content.items():
            json_data[key] = value

        # Guarda el objeto JSON actualizado en el archivo
        with open(file_path, 'w') as file:
            json.dump(json_data, file)
