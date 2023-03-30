from flask import Flask, jsonify
from flask_api import status, exceptions

app = Flask(__name__)

# Importa y registra los endpoints
from search.endpoint.endpoints import endpoints
app.register_blueprint(endpoints)

# Manejador de excepciones para autenticación fallida
@app.errorhandler(exceptions.AuthenticationFailed)
def handle_authentication_failed(error):
    return jsonify({'error': 'Invalid API key'}), status.HTTP_401_UNAUTHORIZED