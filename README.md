
# Coffee Store Service (Backend Implementation)

For new online coffee shop startup business, backend services needed where customers can purchase drinks with toppings and administrators may create, alter, or delete drinks and toppings and have access to the most popular toppings. Also, there will be a discount scheme for those that order more frequently than usual.

• API that will be used to order drinks with any of the topping combinations.

• Visitor journeys should be transparent; the current amount of the cart and the products should be communicated back to the caller of the API.

• When finalizing the order, the original amount and the discounted amount should be displayed in return.

Drinks examples:
• Black Coffee - 4€
• Latte - 5€
• Mocha - 6€
• Tea-3€

Toppings/sides examples:
• Milk - 2€
• Hazelnut syrup - 3€
• Chocolate sauce - 5€
• Lemon - 2€

Discount scheme
1. If the total cost of the cart is more than 12 euros, there should be a 25% discount.

2. If there are 3 or more drinks in the cart, the one with the lowest amount (including toppings) should be free.

3. If the cart is eligible for both promotions, the promotion with the lowest cart amount should be used and the other one should be ignored.


## API Reference
You can find PostmanCollection in the project.

#### User must be register to use the other endpoints.
Register:
```http
  POST /authenticate/register
```
Login:
```http
  POST /authenticate/login?email
```

Add product: 
```http
  POST v1/api/admin/products
```
Update Product:
```http
  POST v1/api/admin/products/{id}
```
Delete Product: 
```http
  DELETE v1/api/admin/products/{id}
```

Get Most used Toppings: 
```http
  GET v1/api/admin/products/most-used-toppings
```

Add Item to the basket:
```http
  POST v1/api/basket/items
```

Delete Item from the basket:
```http
  v1/api/basket/items/{id}/{quantity}
```
Get your basket:
```http
  GET v1/api/basket/items
```
Clear your basket:
```http
  DELETE v1/api/admin/products
```

Place Order:
```http
  POST v1/api/orders
```

Get orders:
```http
  GET v1/api/orders
```


#### Documentation 

```http
  localhost:8080/documentation 
```



## Tech Stack

**Technologies:** Java 17, Spring Boot,h2-database , Docker, Junit, Mockito, Integration Test.

