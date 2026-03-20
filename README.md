
# Project Title

Tasks Management System


##  Introduction
This project has been develped using Java for the Back End and Angular for the Front End. The Backend has an number of Endpoints which are used to process requests coming from the user at the front end and save data to the database depending on the user request. User inputs are verified and any data not formatted according the accepted data format of the application is rejected and user has to enter the correct data. The Front End part has been developed using Angular provide user with a friendly User Interface that is easy to use and intuitive.
##  Introduction
This project has been develped using Java for the Back End and Angular for the Front End. The Backend has an number of Endpoints which are used to process requests coming from the user at the front end and save data to the database depending on the user request. User inputs are verified and any data not formatted according the accepted data format of the application is rejected and user has to enter the correct data. The Front End part has been developed using Angular provide user with a friendly User Interface that is easy to use and intuitive.
## API Reference

#### Create a task
```http
  POST "/createTask"
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `  None       |      |     |
This endpoint takes an object of type CaseTask, 
creates an record in the database and returns the task object.


#### Retrieve a task
```http
  GET "/tasksbyid/{idIn}"
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `idIn     | `Integer` | Identifies a specific task |
This endpoint is used to retrieve a specific record from the database


#### Retrieve all tasks
```http
  GET "/allcasetasks"
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `None     |          |                                   |
This endpoint is used to retrieve all case tasks from the 
database and returns a list of all tasks.


#### Update the status of a task
```http
  PUT "/updatestatus"
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `None     |          |                                   |
This endpoint takes an object of type UpdateStatusDTO, 
updates the status of a task,  saves changes to the database and 
returns the task object.

#### Delete a task
```http
  DELETE "/deletetask/{idIn}"
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `idIn     | `Integer` | Identifies a specific task |
This endpoint is used to delete a specific task from the 
database and returns nothing.


## Demo

Insert gif or link to demo


## Database connection
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.hibernate.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/casetasksdb
spring.datasource.username=postgres
spring.datasource.password=postgres