import requests
import numpy as np


items = []

def getItems(search):
    items = []
    url = ("https://www.dollargeneral.com/bin/omni/pickup/productSearch?_="
           "1710261242248&pageSize=12&pageStartIndex=0&storeNumber=11817&sort=0&inStock=false&dgPickup=false&brand=&inStoreOnly=false&dgShipToHome"
           "=false&dgDelivery=false&mode=0&"
           "searchTerm=" + search +
           "&category=&deviceId=0523bcea-6751-430c-b30f-136cd2513597&isMobileDevice=false&clientOriginStoreNumber=")

    response = requests.get(url)

    if response.status_code == 200:
        data = response.json()
        #print(data)
        for p in data["categoriesResult"]["Items"]:
            items.append(p["description"])
            items.append(format(p["finalPrice"], '.2f'))
            items.append(p["image"])
            items.append("Dollar General")
    else:
        print('An error occurred:', response.text)
    return np.array(items)