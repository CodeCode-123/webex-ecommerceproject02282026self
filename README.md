# webex-ecommerceproject02282026self
## Postman test cases: 
If the method is not POST, and the URL does not start with http://localhost:8185/api/auth/, http://localhost:8185/api/category/, http://localhost:8185/api/item/, or http://localhost:8185/api/users/,
please use the token generated during the login to fill the blank of Bear Token in the Authorization field Auth Type. 
### Authentication:
1. Name: Signup, (please drop all the tables, especially the Users, if the entity constraints are different)
Method: POST,
URL: http://localhost:8185/api/auth/signup,
RequestBody: 
{
    "firstName": "test1",
    "lastName": "test1",
    "email": "test1@abc.com",
    "password": "1234"
} <br>
2. Name: Login, (response entity will provide the generated token for authorization)
Method: POST,
URL: http://localhost:8185/api/auth/login,
RequestBody:
{
    "email": "test1@abc.com",
    "password": "1234"
} <br>

### Category:
1. Name: CreateCategory,
Method: POST,
URL: http://localhost:8185/api/category/create,
RequestBody: 
{
    "categoryId": 0,
    "categoryName": "Pizza",
    "categoryDesc": "Cheese Pizza"
} <br>
2. Name: CreateCategory,
Method: POST,
URL: http://localhost:8185/api/category/create,
RequestBody:
{
    "categoryId": 0,
    "categoryName": "Burger",
    "categoryDesc": "Cheese Burger"
} <br>
3. Name: GetAllCategories, (please use the token generated in the Login)
Method: GET,
URL: http://localhost:8185/api/category/<br>
4. Name: GetCategoryById,
Method: GET,
URL: http://localhost:8185/api/category/1<br>
5. Name: GetCategoryByName, 
Method: GET,
URL: http://localhost:8185/api/category/search/P<br>
6. Name: UpdateCategory, 
Method: PUT,
URL: http://localhost:8185/api/category/edit,
RequestBody:
{
    "categoryId": 2,
    "categoryName": "Burger",
    "categoryDesc": "Double Cheese Burger"
}<br>
7. Name: UpdateCategoryById,
Method: PATCH,
URL: http://localhost:8185/api/category/edit/2,
RequestBody:
{
    "categoryDesc": "Double Cheese Burger"
}<br>
8. Name: DeleteCategoryById,
Method: DELETE,
URL: http://localhost:8185/api/category/delete/2<br>

### Item:
1. Name: CreateItem,
Method: POST,
URL: http://localhost:8185/api/item/create,
RequestBody: 
{
    "itemName": "Cheese Pizza",
    "itemPrice": 10,
    "category": {
        "categoryId": 1
    }
} <br>
2. Name: CreateItem,
Method: POST,
URL: http://localhost:8185/api/item/create,
RequestBody:
{
    "itemName": "Cheese Burger",
    "itemPrice": 6,
    "category": {
        "categoryId": 2
    }
} <br>
3. Name: GetAllItems,
Method: GET,
URL: http://localhost:8185/api/item/<br>
4. Name: GetItemById,
Method: GET,
URL: http://localhost:8185/api/item/1<br>
5. Name: UpdateItem, 
Method: PUT,
URL: http://localhost:8185/api/item/edit,
RequestBody:
{
    "category": {
        "categoryDesc": "Cheese Burger",
        "categoryId": 2,
        "categoryName": "Burger"
    },
    "imageData": null,
    "itemId": 2,
    "itemName": "Cheese Burger",
    "itemPrice": 8
}<br>
6. Name: UpdateItemById, (update itemName),
Method: PATCH,
URL: http://localhost:8185/api/item/edit/2,
RequestBody: 
{
    "itemName": "Double Cheese Burger"
}<br>
7. Name: UpdateItemById, (update category by categoryId),
Method: PATCH,
URL: http://localhost:8185/api/item/edit/1,
RequestBody: 
{
    "category": {
        "categoryId": 2
    }
}<br>
8. Name: DeleteItem,
Method: DELETE,
URL: http://localhost:8185/api/item/delete/2<br>

### Users:
1. Name: CreateUsers,
Method: POST,
URL: http://localhost:8185/api/users/create,
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
URL: http://localhost:8185/api/users/ <br>
3. Name: GetUsersById,
Method: GET,
URL: http://localhost:8185/api/users/1 <br>
4. Name: Login,
Method: POST,
URL: http://localhost:8185/api/users/login?emailId=john.doe@abc.com&password=password <br>
5. Name: GetUserByEmailId,
Method: GET,
URL: http://localhost:8185/api/users/search/john.doe@abc.com <br>
6. Name: UpdateUsers, 
Method: PUT,
URL: http://localhost:8185/api/users/edit,
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
7. Name: UpdateUsersById,
Method: PATCH,
URL: http://localhost:8185/api/users/edit/1,
RequestBody: {
    "firstName": "John edit",
    "lastName": "Doe edit"
}<br>
8. Name: DeleteUsers,
Method: DELETE,
URL: http://localhost:8185/api/users/delete/1<br>

### OrderDetails:
1. Name: CreateOrderDetails,
Method: POST,
URL: http://localhost:8185/api/orderdetails/create,
RequestBody: 
{
    "item": {
        "itemId": 1
    },
    "qty": 1,
    "itemOrder":{
        "orderId": 0
    }
}<br>
2. Name: CreateOrderDetails, (created an itemOrderDetails with another item)
Method: POST,
URL: http://localhost:8185/api/orderdetails/create,
RequestBody:
{
    "item": {
        "itemId": 2
    },
    "qty": 1,
    "itemOrder":{
        "orderId": 0
    }
}<br>
3. Name: GetAllOrderDetails,
Method: GET,
URL: http://localhost:8185/api/orderdetails/ <br>
4. Name: GetOrderDetailsById,
Method: GET,
URL: http://localhost:8185/api/orderdetails/1 <br>
5. Name: UpdateOrderDetails, 
Method: PUT,
URL: http://localhost:8185/api/orderdetails/edit,
RequestBody:
{
    "item": {
        "category": {
            "categoryDesc": "Cheese Pizza",
            "categoryId": 1,
            "categoryName": "Pizza"
        },
        "imageData": null,
        "itemId": 1,
        "itemName": "Cheese Pizza",
        "itemPrice": 10
    },
    "itemOrderDetailsId": 1,
    "itemValue": 20.0,
    "qty": 2
}<br>
6. Name: UpdateOrderDetailsById, (only update qty),
Method: PATCH,
URL: http://localhost:8185/api/orderdetails/edit/1, 
RequestBody: 
{
    "qty": 3
}<br>
7. Name: UpdateOrderDetailsById, (only update qty),
Method: PATCH,
URL: http://localhost:8185/api/orderdetails/edit/2, 
RequestBody: 
{
    "qty": 5
}<br>
8. Name: DeleteOrderDetailsById,
Method: DELETE,
URL: http://localhost:8185/api/orderdetails/delete/1<br>

### Orders:
1. Name: PlaceOrders, (ensure that there is a user with id=1 and an item order details with itemOrderDetailsId=1 in the database, get the users' information and item order details' information during retrieving),
Method: POST,
URL: http://localhost:8185/api/orders/placeorder,
RequestBody: 
{
    "users":{
        "id": 1
    },
    "itemOrderDetailsList": [
        {
            "itemOrderDetailsId": 1
        }
    ]
}<br>
2. Name: PlaceOrders, (with multiple itemOrderDetails),
Method: POST,
URL: http://localhost:8185/api/orders/placeorder,
RequestBody: 
{
    "users":{
        "id": 1
    },
    "itemOrderDetailsList": [
        {
            "itemOrderDetailsId": 1
        },
        {
            "itemOrderDetailsId": 2
        }
    ]
}<br>
2. Name: GetAllOrders,
Method: GET,
URL: http://localhost:8185/api/orders/ <br>
3. Name: GetOrdersById,
Method: GET,
URL: http://localhost:8185/api/orders/1 <br>
4. Name: DeleteOrdersById,
Method: DELETE,
URL: http://localhost:8185/api/orders/delete/1<br>

