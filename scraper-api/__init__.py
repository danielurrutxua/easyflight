from flask import Flask, request, jsonify
from flask_api import status, exceptions

app = Flask(__name__)

# Importa y registra los endpoints del subdirectorio "kayak"
from kayak.endpoints import endpoints as kayak_endpoints
app.register_blueprint(kayak_endpoints)

# Importa y registra los endpoints del subdirectorio "skyscanner"
from skyscanner.endpoints import endpoints as skyscanner_endpoints
app.register_blueprint(skyscanner_endpoints)

# Manejador de excepciones para autenticación fallida
@app.errorhandler(exceptions.AuthenticationFailed)
def handle_authentication_failed(error):
    return jsonify({'error': 'Clave de API inválida'}), status.HTTP_401_UNAUTHORIZED