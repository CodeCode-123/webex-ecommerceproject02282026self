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
Url: http://localhost:8185/api/category/,
4. Name: GetCategoryById,
Method: GET,
Url: http://localhost:8185/api/category/1
5. Name: GetCategoryByName, 
Method: GET,
Url: http://localhost:8185/api/category/search/P
6. Name: UpdateCategory, 
Method: PUT,
Url: http://localhost:8185/api/category/edit,
RequestBody:
{
    "categoryId": 2,
    "categoryName": "Burger",
    "categoryDesc": "Double Cheese Burger"
}
7. Name: DeleteCategory,
Method: DELETE,
Url: http://localhost:8185/api/category/delete/2

