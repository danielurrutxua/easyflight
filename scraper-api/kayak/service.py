import undetected_chromedriver as uc
import time
import pyautogui
import re
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from bs4 import BeautifulSoup


def getResults(url):
    
    # Inicializar el controlador del navegador (en este caso, Chrome)
    driver = uc.Chrome()
    # Navegar a una página web
    driver.get(url)

    if 'security' in driver.current_url:
        repeat = True
        # Esperar a que aparezca el botón de cerrar cookies
        cookies_close_button = WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CLASS_NAME, "dDYU-close")))

        # Mover el mouse al botón de cerrar cookies
        button_location = cookies_close_button.location
        x = button_location['x']
        y = button_location['y']

        # Mover el cursor del ratón al botón
        pyautogui.moveTo(x, y, duration=1, tween=pyautogui.easeInOutQuad)

        cookies_close_button.click()

        time.sleep(5)

        # Encontrar el elemento de iframe para reCAPTCHA
        iframe_elements = driver.find_elements(By.TAG_NAME, "iframe")
        iframe_element = None
        for iframe in iframe_elements:
            if iframe.get_attribute("title") == "reCAPTCHA":
                iframe_element = iframe
                break

        # Cambiar al iframe de reCAPTCHA
        driver.switch_to.frame(iframe_element) 

        # Esperar a que el botón "No soy un robot" sea visible
        recaptcha_checkbox = WebDriverWait(driver, 10).until(EC.visibility_of_element_located((By.CLASS_NAME, "recaptcha-checkbox-border")))

        # Mover el mouse al botón "No soy un robot" y hacer clic
        button_location = recaptcha_checkbox.location
        x = button_location['x']
        y = button_location['y']
        pyautogui.moveTo(x, y, duration=0.5, tween=pyautogui.easeInOutQuad)
        recaptcha_checkbox.click()

        if repeat:
            getResults(url)
        
        repeat = False
    else: 
        # Obtiene el contenido HTML de la página cargada en el navegador
        html_content = driver.page_source
        # Cerrar el navegador
        driver.quit()

        # Crea un objeto BeautifulSoup para analizar el contenido HTML
        soup = BeautifulSoup(html_content, 'html.parser')

        # Busca todos los elementos script
        scripts = soup.find_all('script')

        # Inicializa la variable que contendrá el script encontrado
        script_content = None

        # Busca el último elemento script que tenga el atributo "type" con valor "text/javascript"
        for script in reversed(scripts):
            if script.has_attr('type') and script['type'].lower() == 'text/javascript':
                script_content = script.text
                break

        pattern = re.compile('FlightResultsList\":(.*?),\"FlightFilterData"')
        match = pattern.search(script_content)

        if match:
            # Extrae la sección del JSON que necesitas
            json = match.group(1)
            return json
        else:
            print('No se encontró la sección del JSON que necesitas.')
            return None

