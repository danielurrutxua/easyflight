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
        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(str(content))
    else:
        # Si existe, carga el contenido del archivo en una variable
        with open(file_path, 'r', encoding='utf-8') as file:
            json_data = json.load(file)

        # Agrega los atributos de flight_results_list al objeto JSON
        json_data.append(content)

        # Guarda el objeto JSON actualizado en el archivo
        with open(file_path, 'w', encoding='utf-8') as file:
            json.dumps(json_data, file)
