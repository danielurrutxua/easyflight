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

print(driver.page_source)
# Cerrar el navegador
driver.quit()