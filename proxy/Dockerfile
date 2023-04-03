# Use an official Python runtime as a parent image
FROM python:3.9-slim

# Set the working directory to /app
WORKDIR /app

# Copy the mitmproxy scripts folder into the container at /app
COPY mitmproxy-ca-cert.pem /app

RUN python -m pip install --upgrade pip
# Install mitmproxy
RUN pip install mitmproxy

# Add certificate
RUN mv mitmproxy-ca-cert.pem /usr/local/share/ca-certificates/mitmproxy.cert && update-ca-certificates

EXPOSE 8090

# Start mitmproxy with the main script in the mitm_scripts folder
CMD ["mitmdump", "-q"]
