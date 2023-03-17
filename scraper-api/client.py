import requests

url = "http://127.0.0.1:5000/skyscanner"
headers = {"Authorization": "v7RWO4ybCKkTlv1UfvOuOYrWJo9XybLF5AZXXmHk39OrTuxdC45SQYpYExViHtDgyFwQMPlefsHw8cT75hy5ZZoRJ6xXBaS5KTqvMLd1CMBLXeccPMCnsj7UMf1HqZ4P"}
params = {"url": f"https://www.skyscanner.es/transporte/vuelos/ber/fnc/230317/230405/?adults=1&adultsv2=1&cabinclass=economy&children=0&childrenv2=&inboundaltsenabled=false&infants=0&originentityid=27547053&outboundaltsenabled=false&preferdirects=false&ref=home&rtn=1"}

response = requests.get(url, headers=headers, params=params)

print(response.status_code)
print(response.text)
