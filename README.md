# webex-ecommerceproject02282026self
## Postman test cases:
### Category:
1. Name: CreateCategory,
Method: POST,
Url: http://localhost:8185/api/category/create,
RequestBody: 
{
    "categoryId": 0,
    "categoryName": "Pizza",
    "categoryDesc": "Cheese Pizza"
} <br>
2. Name: CreateCategory,
Method: POST,
Url: http://localhost:8185/api/category/create,
RequestBody:
{
    "categoryId": 0,
    "categoryName": "Burger",
    "categoryDesc": "Cheese Burger"
} <br>
3. Name: GetAllCategories,
Method: GET,
Url: http://localhost:8185/api/category/<br>
4. Name: GetCategoryById,
Method: GET,
Url: http://localhost:8185/api/category/1<br>
5. Name: GetCategoryByName, 
Method: GET,
Url: http://localhost:8185/api/category/search/P<br>
6. Name: UpdateCategory, 
Method: PUT,
Url: http://localhost:8185/api/category/edit,
RequestBody:
{
    "categoryId": 2,
    "categoryName": "Burger",
    "categoryDesc": "Double Cheese Burger"
}<br>
7. Name: DeleteCategoryById,
Method: DELETE,
Url: http://localhost:8185/api/category/delete/2
<br>

### Item:
1. Name: CreateItem,
Method: POST,
Url: http://localhost:8185/api/item/create,
RequestBody: 
{
    "itemId": 0,
    "itemName": "Cheese Pizza",
    "itemPrice": 10
} <br>
2. Name: CreateItem,
Method: POST,
Url: http://localhost:8185/api/item/create,
RequestBody:
{
    "itemId": 0,
    "itemName": "Cheese Burger",
    "itemPrice": 6
} <br>
3. Name: GetAllItems,
Method: GET,
Url: http://localhost:8185/api/item/<br>
4. Name: GetItemById,
Method: GET,
Url: http://localhost:8185/api/item/1<br>
5. Name: UpdateItem, 
Method: PUT,
Url: http://localhost:8185/api/item/edit,
RequestBody:
{
    "itemId": 2,
    "itemName": "Double Cheese Burger",
    "itemPrice": 7
}<br>
6. Name: DeleteItem,
Method: DELETE,
Url: http://localhost:8185/api/item/delete/2
<br>

### Users:
1. Name: CreateUsers,
Method: POST,
Url: http://localhost:8185/api/users/create,
RequestBody: 
{
    "id": 0,
    "country": "United Kingdom",
    "emailId": "john.doe@abc.com",
    "firstName": "John",
    "gender": "male",
    "languages": ["C", "C#", "Java"],
    "lastName": "Doe",
    "password": "password"
} <br>
2. Name: GetAllUsers,
Method: GET,
Url: http://localhost:8185/api/users/ <br>
3. Name: GetUsersById,
Method: GET,
Url: http://localhost:8185/api/users/1 <br>
4. Name: UpdateUsers, 
Method: PUT,
Url: http://localhost:8185/api/users/edit,
RequestBody:
{
    "id": 1,
    "country": "United Kingdom",
    "emailId": "john.doe@abc.com",
    "firstName": "John",
    "gender": "male",
    "languages": ["C", "C#", "Java", "Python", "JavaScript"],
    "lastName": "Doe",
    "password": "password"
}<br>
5. Name: DeleteUsers,
Method: DELETE,
Url: http://localhost:8185/api/users/delete/1<br>

### Orders:
1. Name: CreateOrders, (make sure there is a user with id=1 in the database),
Method: POST,
Url: http://localhost:8185/api/orders/create,
RequestBody: 
{
    "oderId": 0,
    "orderDate": "03-01-2026",
    "totalAmount": 20,
    "userId": 1
} <br>
2. Name: GetAllOrders,
Method: GET,
Url: http://localhost:8185/api/orders/ <br>
3. Name: GetOrdersById,
Method: GET,
Url: http://localhost:8185/api/orders/1 <br>
4. Name: UpdateOrders, 
Method: PUT,
Url: http://localhost:8185/api/orders/edit,
RequestBody:
{
    "orderDate": "03-01-2026",
    "orderId": 1,
    "totalAmount": 30.0,
    "users": null
}<br>
5. Name: DeleteOrdersById,
Method: DELETE,
Url: http://localhost:8185/api/orders/delete/1<br>

### OrderDetails:
1. Name: CreateOrderDetails,
Method: POST,
Url: http://localhost:8185/api/orderdetails/create,
RequestBody: 
{
    "itemOrderId": 0,
    "productName": "Cheese Pizza",
    "categoryName": "Pizza",
    "price": 10,
    "qty": 2,
    "itemValue": 10
} <br>
2. Name: GetAllOrderDetails,
Method: GET,
Url: http://localhost:8185/api/orderdetails/ <br>
3. Name: GetOrderDetailsById,
Method: GET,
Url: http://localhost:8185/api/orderdetails/1 <br>
4. Name: UpdateOrderDetails, 
Method: PUT,
Url: http://localhost:8185/api/orderdetails/edit,
RequestBody:
{
    "categoryName": "Pizza",
    "itemOrder": null,
    "itemOrderId": 1,
    "itemValue": 20.0,
    "price": 10.0,
    "productName": "Double Cheese Pizza",
    "qty": 2
}<br>
5. Name: DeleteOrderDetailsById,
Method: DELETE,
Url: http://localhost:8185/api/orderdetails/delete/1<br>