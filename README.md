# Library Management System
The following was discovered as part of building this project:

## Prerequisites
- Java 17+
- Maven 3.9+

### cURL Commands to run from postman

Sample cURL Commands
1. Create a Book (POST)
curl -u admin:admin123 -X POST http://localhost:8081/api/books \
-H "Content-Type: application/json" \
-d '{
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "isbn": "9780134685991",
  "publicationYear": 2018,
  "genre": "SCIENCE",
  "availableCopies": 5,
  "totalCopies": 10
}'
2. Get All Books (GET)
curl -u admin:admin123 http://localhost:8081/api/books?page=0&size=10
3. Get Book by ID (GET)
curl -u admin:admin123 http://localhost:8081/api/books/1
4. Update a Book (PUT)
curl -u admin:admin123 -X PUT http://localhost:8081/api/books/1 \
-H "Content-Type: application/json" \
-d '{
  "title": "Effective Java - 3rd Edition",
  "author": "Joshua Bloch",
  "isbn": "9780134685991",
  "publicationYear": 2019,
  "genre": "SCIENCE",
  "availableCopies": 7,
  "totalCopies": 10
}'
5. Delete a Book (DELETE)
curl -u admin:admin123 -X DELETE http://localhost:8081/api/books/1
Validation Error Examples
Invalid ISBN
curl -u admin:admin123 -X POST http://localhost:8081/api/books \
-H "Content-Type: application/json" \
-d '{
  "title": "Bad ISBN",
  "author": "Test",
  "isbn": "123",
  "publicationYear": 2020,
  "genre": "FICTION",
  "availableCopies": 1,
  "totalCopies": 2
}'

Response:

{
  "status": 400,
  "error": "Validation failed",
  "details": ["isbn: Invalid ISBN format"]
}
availableCopies > totalCopies
{
  "status": 400,
  "error": "Available copies cannot exceed total copies"
}
H2 Console

URL: http://localhost:8081/h2-console

JDBC URL: jdbc:h2:mem:librarydb

Username: sa

Password: (empty)

Assumptions

ISBN is unique

H2 used for simplicity

Pagination enabled

In-memory authentication for demo

Bonus Features

Pagination

Swagger OpenAPI

Data validation

Production-grade exception handling

Spring Boot 4 migrates to Spring Framework 7, and current Swagger/OpenAPI tooling is not yet binary compatible.
To avoid unstable runtime behavior, I kept OpenAPI annotations and disabled Swagger UI until official support is released.
