# Use an official Python runtime as a parent image
FROM ultrafunk/undetected-chromedriver

# Set the working directory to /app
WORKDIR /app

# Copy the requirements.txt file into the container at /app
COPY ../requirements.txt /app
# Copy the requirements.txt file into the container at /app
COPY ./app /app

# Instalar dependencias
RUN apt-get update && apt-get install -y openssl wget gnupg2 unzip

RUN python -m pip install --upgrade pip
# Install any needed packages specified in requirements.txt
RUN pip install --trusted-host pypi.python.org -r requirements.txt

# Make port 5000 available to the world outside this container
EXPOSE 5000

# Define environment variable
ENV FLASK_APP=run.py

# Start the Flask app
CMD mitmdump -q & python run.py
