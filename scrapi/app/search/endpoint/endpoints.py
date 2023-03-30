from flask import Blueprint, request, jsonify, make_response
from flask_api import status, exceptions
from ..service.scraper import execute as execute_scraper

# Define la API Key
API_KEY = "v7RWO4ybCKkTlv1UfvOuOYrWJo9XybLF5AZXXmHk39OrTuxdC45SQYpYExViHtDgyFwQMPlefsHw8cT75hy5ZZoRJ6xXBaS5KTqvMLd1CMBLXeccPMCnsj7UMf1HqZ4P"

# Define el Blueprint
endpoints = Blueprint('endpoints', __name__)

# Define el endpoint 'kayak_request'
@endpoints.route('/scrape', methods=['GET'])
def scrape_request():
    # Comprueba si se proporcionó una clave de API válida
    if 'Authorization' not in request.headers or request.headers['Authorization'] != API_KEY:
        raise exceptions.AuthenticationFailed()

    # Comprueba si se proporcionó un URL válido
    if 'url' not in request.values:
        return jsonify({'error': 'URL no proporcionado'}), status.HTTP_400_BAD_REQUEST

    url = request.values['url']
    # Llama a la función getResults del archivo service.py
    result = execute_scraper(url)

    if result is None:
        return jsonify({'error': 'Could not find result json'}), status.HTTP_404_NOT_FOUND
    else:
        
        response = make_response(result)
        response.headers["Content-Type"] = "application/json"
        response.status_code = 200
        return response