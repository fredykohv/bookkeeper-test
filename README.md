# A tool for a bookkeeper
This REST API application helps managing books and storing them in a mysql database.

## How to use it?
* First of all, in order to even get this to build and work you need to install [MySQL Workbench](https://dev.mysql.com/downloads/installer/) and [Postman](https://www.postman.com/downloads/)  
* Remember your root password when setting up MySQL
* Set MySQL up for a localhost with a port 3306
* Create a new schema called 'books'
* Clone this repository and change some variables in /rest/src/main/resources/application.properties
* * Set the username to your MySQL username (By default it is ```root```) ```spring.datasource.username=root```
* * Set the password to your MySQL password ```spring.datasource.password=PASSWORD```

* Now it should be compiled and see a new table called "book" in your MySQL database after executing the code and refresh!  
 ![Result](https://user-images.githubusercontent.com/56818310/216029748-b5ea780e-6fd1-4640-bd53-9b7fef7572bd.png)
* Using Postman you have to create a GET request to localhost:8080 (default) using this request URL ```localhost:8080/books``` to return all of the books and it should return this ![image](https://user-images.githubusercontent.com/56818310/216030463-cab3b7c5-c200-4861-b6ee-9abd1167e825.png)
 
## Insert a new book
* To add a book create a POST request with a request URL of ```localhost:8080/save``` and with a body of (raw and JSON)  
```json
{
    "title": "Art3",
    "author": "Boro",
    "release_year": 2023
}
```
![image](https://user-images.githubusercontent.com/56818310/216031610-e307698c-92ab-4cbd-8a0b-31ff36f9b254.png)
For a successful update it return "Book added!"

* To validate this, create a new GET request to return all of the books.
![image](https://user-images.githubusercontent.com/56818310/216031950-53fc213e-c67b-4660-9b68-433c9af241ee.png)

## Update a book
* To update a book, we need to know the book ID. We get the ID from requesting the full list of the books and looking at the "id" row.
* If the desired book ID is 1, we create a new PUT request with a request URL of ```localhost:8080/books/update/1``` 1 being the ID. We also need to create a JSON body for the updated information.
```json
{
    "title": "Art4",
    "author": "Boro2",
    "release_year": 2024,
    "availability": false
}
```
![image](https://user-images.githubusercontent.com/56818310/216032997-808c3c4c-73a2-4888-a40c-5febbd67b76e.png)
For a successful update it returns "Book updated!"

## Get books by the title
* To get a books by the title, we need to create a new GET request with a request URL of ```localhost:8080/books/title/Art4``` "Art4" being the title. 
![image](https://user-images.githubusercontent.com/56818310/216033799-2c560de5-9c29-460a-bdc6-9d0671d6b895.png)

## Get books by the author
* To get books by the author, we need to create a new GET request with a request URL of ```localhost:8080/books/author/Boro2``` "Boro2" being the title.
![image](https://user-images.githubusercontent.com/56818310/216034188-7133794e-0290-4827-9cf6-e0d87b3c7cad.png)

## Get books by the release year
* To get books by the release year, we need to create a new GET request with a request URL of ```localhost:8080/books/year/2024``` "2024" being the year.
![image](https://user-images.githubusercontent.com/56818310/216034738-ac05574c-d617-479e-89b6-ee89209966af.png)

## Loan a book
* To loan a book, we need to create a new PUT request with a request URL of ```localhost:8080/books/loan/Art4``` "Art4" being the title of the book.
* If the amount of books by the title is less than 5, it will loan out the book for 7 days, else 28 days.
* The book has to be available as well. 
* It returns with a message if it was able to loan or not and how long for.
![image](https://user-images.githubusercontent.com/56818310/216035500-dc800b01-9661-4f0e-b4e6-57a008c529f1.png)

* To validate this, we can request books by the title and check its availability and how long it has been loaned for.
![image](https://user-images.githubusercontent.com/56818310/216035904-ea741799-223e-4581-8520-fd6132e6aada.png)

## Return a book
* To return a book, we need to create a new PUT request with a request URL of ```localhost:8080/books/return/Art4``` "Art4" being the title of the book what is being returned.
* It returns with a message if it was able to return it or not.
![image](https://user-images.githubusercontent.com/56818310/216036524-cfb631a6-2bb3-4d37-aa23-a7af5698fcdf.png)

* To validate this, we can request books by thee title and check its availability again and how long it has been loaned for.
![image](https://user-images.githubusercontent.com/56818310/216036736-8bdd8dff-d7d9-490a-8043-8e3f4dab7d89.png)

## Delete a book
* To delete a book, we need to know its ID and create a new DELETE request with a request URL of ```localhost:8080/books/delete/1``` 1 being the ID of the book.
![image](https://user-images.githubusercontent.com/56818310/216037106-b397d4ad-a5b2-4c27-be9d-b0563bfb02de.png)

* To validate this, we can request for the full list of books and check if a book with an ID of "1" exists
![image](https://user-images.githubusercontent.com/56818310/216037312-4122bb82-f06d-4af7-9ec4-837ddf1a4875.png)

### After this little tutorial, there are no books left.

# Testing
* Tests are included in ```/rest/src/test/java/com/fredy/app/rest/BookControllerTests.java```
* These tests has to be ran in the order the @Test functions are created.


