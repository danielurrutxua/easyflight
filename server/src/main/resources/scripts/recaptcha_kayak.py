from selenium import webdriver
import undetected_chromedriver as uc
import time
import sys
import pyautogui
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains

# Inicializar el controlador del navegador (en este caso, Chrome)
driver = uc.Chrome()
# Navegar a una página web
url = sys.argv[1]
driver.get(url)

# Esperar a que se muestre la ventana de cookies y hacer clic en el botón de cierre
try:
    # Esperar a que aparezca el botón de cerrar cookies
    cookies_close_button = WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CLASS_NAME, "dDYU-close")))

    # Mover el mouse al botón de cerrar cookies
    button_location = cookies_close_button.location
    x = button_location['x']
    y = button_location['y']

    # Mover el cursor del ratón al botón
    pyautogui.moveTo(x, y, duration=1, tween=pyautogui.easeInOutQuad)

    cookies_close_button.click()

except:
    print("No se encontró la ventana de cookies")

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

# Esperar a que se complete la verificación de reCAPTCHA
WebDriverWait(driver, 30).until(EC.invisibility_of_element_located((By.CLASS_NAME, "recaptcha-checkbox-checkmark")))

# Cambiar de vuelta al contenido principal de la página
driver.switch_to.default_content()
# Cerrar el navegador
driver.quit()