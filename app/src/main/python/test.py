import requests
from bs4 import BeautifulSoup

import time
from selenium import webdriver
from selenium.webdriver import Edge
from selenium.webdriver.common.by import By

import threading
import numpy as np

products = []
prices = []
images = []
store = []

allItems = []


def addProduct(product, price, storeLocation):
    if product != "" or price != "":
        #item = [product, price, storeLocation]
        products.append(product)
        prices.append(price)
        store.append(storeLocation)
        #allItems.append(item)


def scrapeCvs(search):
    """
    Scrapes CVS and extracts the data
    :param search: what the user enters to search with
    :return:
    """
    url = 'https://www.cvs.com/search?searchTerm=' + search
    page = requests.get(url)

    # parses the website to get the code
    soup = BeautifulSoup(page.text, 'html.parser')

    # what scrapes the spacific data depending on the class
    CvsGetProducts = soup.find_all('div', class_='css-1dbjc4n r-eqz5dr r-15d164r r-156q2ks r-1udh08x')
    CvsGetPrice = soup.find_all('div', class_='css-901oao r-1xaesmv r-ubezar r-majxgm r-wk8lta')
    # CvsGetImage = soup.find_all('div', class_='css-1dbjc4n r-ywje51')

    # adding the values to lists where they are readable

    for p in range(0, len(CvsGetProducts)):
        addProduct(CvsGetProducts[p].text, CvsGetPrice[p].text, "CVS")


def generalScrape(url, storeName, wait):
    """
    A general scraper for most stores using selenium, gets it started before passing it to a new function for individual stores
    :param url: takes the url of the website that it needs to scrape
    :param storeName: inorder to classify what store its from it needs the store name to sort them correctly
    :param wait: how long the program should wait for the page to load
    :return:
    """
    options = webdriver.EdgeOptions()
    options.add_argument("--headless")
    options.page_load_strategy = "none"
    driver = Edge(options=options)
    driver.implicitly_wait(wait)
    driver.get(url)
    time.sleep(wait)
    match storeName:
        case "Dg":
            scrapeDg(driver)
        case "Hd":
            scrapeHd(driver)
        case "Wf":
            scrapeWf(driver)
    # DgGetImage = driver.find_elements(By.CSS_SELECTOR, "div[class*='product-image product-card__image-container")


def scrapeDg(driver):
    """
    extracts information from Dollar General
    :param driver: the page scraped
    :return:
    """
    load = 1
    try:
        driver.find_element(By.CSS_SELECTOR, "button[class*='idmaccount-modal__close-button'").click()
    except:
        load = 0

    time.sleep(load)
    DgGetProduct = driver.find_elements(By.CSS_SELECTOR, "p[class*='product-name product-card__title")
    DgGetPrice = driver.find_elements(By.CSS_SELECTOR, "div[class*='product-info__section-price'")

    for p in range(0, len(DgGetProduct)):
        addProduct(DgGetProduct[p].text, DgGetPrice[p].text, "Dollar General")


def scrapeHd(driver):
    """
    currently not working, extracts data from Home Depo
    :param driver: the scraped data
    :return:
    """
    HdGetProduct = driver.find_elements(By.CSS_SELECTOR, "h3[class*='product-header__title--bkfty product-header__title-product--bkfty'")
    HdGetPrice = driver.find_elements(By.CSS_SELECTOR, "div[class*='price-format__main-price'")
    HdGetDelivery = driver.find_elements(By.CSS_SELECTOR, "div[class*='store__second-line'")

    for p in range(0, len(HdGetProduct)):
        if HdGetDelivery[p].text.__contains__("Not eligible for pickup"):
            addProduct(HdGetProduct[p].text.replace('\n', ""), HdGetPrice[p].text.replace('\n', ""), "Home Depot")

def scrapeWf(driver):
    """
    extracts data from Whole Foods
    :param driver: the information scraped from the webpage
    :return:
    """
    WfGetProduct = driver.find_elements(By.CSS_SELECTOR, "div[class*='w-item--title'")
    WfGetPrice = driver.find_elements(By.CSS_SELECTOR, "div[class*='w-item--info'")

    for p in range(0, len(WfGetProduct)):
        if '$' in WfGetPrice[p].text:
            addProduct(WfGetProduct[p].text, trimString(WfGetPrice[p].text), "Whole Foods")


def trimString(trim):
    """
    trims the price from whole foods as well as sorts them by if they have a set price or not
    :param trim:
    :return: the trimed price string
    """
    remove = ""
    for char in trim:
        if char != '$':
            remove += char
        else:
            break
    return trim.removeprefix(remove)

def useSearch(search):
    search = 'cheese'
    DgUrl = 'https://www.dollargeneral.com/product-search?q=' + search + '&current-page=&productSearchedMode=recommended'
    HdUrl = "https://www.homedepot.com/s/" + search + "?NCNI-5"
    NsUrl = "https://www.wholefoodsmarket.com/shop/ELN/search?submit=&search=" + search
    time.sleep(1)
    #scrapeCvs(search)
    # generalScrape(DgUrl, "Dg")
    # generalScrape(HdUrl, "Hd")
    DollarGeneralThread = threading.Thread(target=generalScrape, args=(DgUrl, "Dg", 20))
    #HomeDepotThread = threading.Thread(target=generalScrape, args=(HdUrl, "Hd", 500))
    WholeFoodsThread = threading.Thread(target=generalScrape, args=(NsUrl, "Wf", 20))

    DollarGeneralThread.start()
    #HomeDepotThread.start()
    WholeFoodsThread.start()

    DollarGeneralThread.join()
    #HomeDepotThread.join()
    WholeFoodsThread.join()
    for p in range(0, len(allItems)):
        print(allItems[p])
    return np.array(products)
    #return np.array(allItems)