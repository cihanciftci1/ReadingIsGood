# ReadingIsGood

ReadingIsGood is an online book selling application. Developed with below techs : 
- JDK 17
- Spring Boot
- MySQL Database
- Spring JPA
- Spring Security - JWT
- Swagger
- Lombok

## Setup
- Install Java 17 and set JAVA_HOME as enviroment variable.
- Install Docker and start the application
### To Run
- Run "mvn clean install" in terminal
- Run "docker-compose up" in terminal
### To Stop
- Press CTRL+C in terminal
- Run "docker-compose down" in terminal

## Entities and Fields

- Customer : username, password, email, created date and updated date
- Book : title, price, stock, created date and updated date
- Orders : customer id, book count, total amount, created date and updated date
- BookOrder : customer id, order id, book id, price(book), quantity(book) and title. (This entity combines all three entites above )
- Roles : ADMIN and USER (For authentication)
- UserRoles : user id(customerID), role id (Relation for users and roles)


## Controllers

### About Controllers
- Postman Collection is added to root directory. You can send request once you run the application and see request/response bodies.

- All endpoints are secured. So first you need to register and then you can login with your username and password. You will receieve a token with "Bearer" prefix. When you send a request to other APIs you must use that token with "Authorization" key in the Headers. 

- First I designed like only admins can create book and update book stock. But it was kinda hard to test every single time because you need to give ADMIN role from db. So currently every API needs only user role. And user role is default for generated token. If you want to add an admin you need add with user id(customer) and role id from user_roles table in db.
### Customer Controller

#### Register

```http
  POST /customer/auth/register
```


#### Login

```http
  GET /customer/auth/login
```


#### Get Customer Orders

```http
  GET /customer/orders/get
```

### Book Controller

#### Create Book

```http
  POST /book/create
```


#### Update Book Stock

```http
  POST /book/updateBookStock
```

### Order Controller

#### Create Order

```http
  POST /order/create
```


#### Get Order By Id

```http
  GET /order/get/{orderId}
```


#### Get Orders By Date

```http
  GET /order/getByDate
```
### Statistics Controller

#### Get Customer Monthly Statistics

```http
  GET /statistics/customers/{customerId}
```


You can check also APIs in the link below after running the application :   
http://localhost:8080/swagger-ui/index.html  


### Total Unit Test Coverage
