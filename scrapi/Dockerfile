# Use an official Python runtime as a parent image
FROM ultrafunk/undetected-chromedriver

# Create the /app directory
RUN mkdir -p /app

# Copy the requirements.txt file into the container at /app
COPY ./requirements.txt /app

# Copy the requirements.txt file into the container at /app
COPY ./app /app

# Set the working directory to /app
WORKDIR /app

# Instalar dependencias
RUN apt-get update && apt-get install -y openssl wget gnupg2 unzip

RUN python -m pip install --upgrade pip
# Install any needed packages specified in requirements.txt
RUN pip install -r requirements.txt

# Make port 5000 available to the world outside this container
EXPOSE 5000

# Start the Flask app
ENTRYPOINT ["python", "run.py"]
