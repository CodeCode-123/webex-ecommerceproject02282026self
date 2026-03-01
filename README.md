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

