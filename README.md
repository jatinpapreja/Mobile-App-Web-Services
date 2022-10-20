# Mobile-App-Web-Services

In this project, RESTful web services have been created using Spring Boot Framework. 


### Softwares Required:
  - Java
  - Spring Tool Suite (STS)
  - Postman

### Before Starting the Project:

All the properties mentioned below can be changed in "application.properties" file under "resources" folder.

  1. The project works on Tomcat server on port 8080.
  2. Context Path for APIs is set to "/mobile-app-ws".
  2. Either create your own Database (Ex:- MySQL)
                                            OR
     Let the default H2 Database connect on url: "jdbc:h2:mem:testdb".
  3. Default UserName and Password for Database connection has been set to "jatin".
     
### How to Start the Project:
1. Launch "Mobile App Web Services" folder in Spring Tool Suite.
2. It will take some time to build the project using Maven.
3. After the build has been completed, right click on "mobile-app-ws" folder and then click "Run As" and finally choose "Spring Boot App". 

The project will then finally start on your localhost. You can either use Postman or Swagger to send Requests.

### Request Format

<b>Swagger UI url:</b> http://localhost:8080/mobile-app-ws/swagger-ui/index.html

Swagger UI is a collection of HTML, JavaScript, and CSS assets that dynamically generate beautiful documentation from a Swagger-compliant API. All the Request Formats will be available here.

### Database Records

<b>H2 Console url:</b> http://localhost:8080/mobile-app-ws/h2-console

The H2 Console application lets you access a database using a browser. This can be a H2 database, or another database that supports the JDBC API. In our case, H2 Database is selected as default. After entering the credentials in H2 console url, you will be able to access the Database Records.
