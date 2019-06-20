# A Simple Bank API

This API is written using the Spring Boot Framework and Swagger 2.0.

You can view the swagger docs by running the project locally and 
navigating to: [http://127.0.0.1:8080/swagger-ui.html](http://127.0.0.1:8080/swagger-ui.html)

Available Routes:

| Path                 | Method | description                                                 |
|:---------------------|:-------|:------------------------------------------------------------|
| /balance/{accountId} | GET    | Get the Balance of an account by its Id                     |
| /deposit             | POST   | Deposit funds into account - takes JSON obj (PostAmtById)   |
| /withdraw            | POST   | Withdraws funds from account - takes JSON obj (PostAmtById) |
| /account             | POST   | Create an account - takes JSON obj (Account)                |
| /account/{accountId} | GET    | Get Account details by Id                                   |
| /account/{accountId} | PUT    | Update Account by Id - takes JSON obj (Account)             |
| /account/{accountId} | DELETE | Delete Account by Id                                        |

### Objects
#### PostAmtById

| Field     | Type              |
|:----------|:------------------|
| accountId | Long              |
| amount    | Number/BigDecimal |

#### Account

| Field                           | Type   |
|:--------------------------------|:-------|
| id (not passed in update calls) | Long   |
| username                        | String |
| firstName                       | String |
| lastName                        | String |
| email                           | String |
| phone                           | String |
| balance                         | Float  |


### Future Improvements
- The project can be improved by refactoring the business logic, making sure all types
are consistent, e.g. balance is a float, but when using the deposit/withdraw api, the amount field is a BigDecimal.
- Better error handling
- More features/apis
- Authentication

