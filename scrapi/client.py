import requests

url = "http://127.0.0.1:5000/scrape"
headers = {"Authorization": "v7RWO4ybCKkTlv1UfvOuOYrWJo9XybLF5AZXXmHk39OrTuxdC45SQYpYExViHtDgyFwQMPlefsHw8cT75hy5ZZoRJ6xXBaS5KTqvMLd1CMBLXeccPMCnsj7UMf1HqZ4P"}
#arams = {"url": f"https://www.skyscanner.es/transporte/vuelos/kan/dfwa/230413/230430/?adultsv2=1&cabinclass=economy&childrenv2=&inboundaltsenabled=false&is_banana_refferal=true&outboundaltsenabled=false&preferdirects=false&qp_prevScreen=HOMEPAGE&ref=home&rtn=1"}
params = {"url": f"https://www.kayak.es/flights/HAM-MAD/2023-04-22/2023-04-29?sort=bestflight_a"}
response = requests.get(url, headers=headers, params=params)

print(response.status_code)
print(response.text)
