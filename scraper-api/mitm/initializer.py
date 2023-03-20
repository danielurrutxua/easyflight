from mitmproxy.tools.dump import DumpMaster
from .addon import CaptureAddon
from mitmproxy import options, proxy
from mitmproxy.tools.main import mitmdump
from mitmproxy.tools import dump
import subprocess

"""
def start_mitmproxy(url_to_capture):
    addon = CaptureAddon(url_to_capture)
    opts = options.Options(listen_host='0.0.0.0', listen_port=8080)
    pconf = proxy.config.ProxyConfig(opts)
    
    m = DumpMaster(opts)
    m.server = proxy.server.ProxyServer(pconf)
    m.addons.add(addon)

    try:
        m.run()
    except KeyboardInterrupt:
        m.shutdown()

def start_mitmproxy():
    mitmdump(args=["-s", "mitm_capture.py"])



def start_mitmproxy():
    subprocess.Popen(["mitmdump", "-s", "mitm_capture.py"])

    
sudo lsof -i -P -n | grep LISTEN
fuser -k 8080/tcp
"""

def start_mitmproxy():
    opts = options.Options(listen_port=8080)
    DumpMaster(opts).run()