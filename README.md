### Application Developed - Payroll API

The application was created as a task for Wave

### Task asked
 * Upload File (CSV) API. It must receive the file, extract the id from the filename, then read the file extracting the report and saving into database for archive purpose.
 * Return API as json format with all report submitted.

### Extra Task (Added to demonstrate my knowledge in System Design)
 * Create user/login feature
 * Create Jwt Token security
 * Api to retrieve Report by Id

### Technology Stack

- Java 11
- Spring Boot Framework
- Spring Security
- JPA / Hibernate
- JWT - Token
- JUnit 5 - Testing
- H2 Database - In file database
- Swagger - API Exposed testing
- Maven

### Structure Explanation

- src -> Main folder for Java sources
- pom.xml -> Maven file to build and download dependencies
- testdb.mv.db -> Auto-generated DB file (Example database - It will get generated if it doesn't exist)

---
#### Inside SRC Folder

- ***main / java:***
    - ***controllers:***
      - ***AuthController:***
        > Controller to Receive request for Login / Logout.
        ```Json
        {"username": "admin", "email": "jbpadilha@gmail.com", "role": "ADMIN"}
        ```
      - ***PayrollController:***
        - ***"/data/upload"*** --> Method to Receive the CSV file.
        - ***"/report/{id}"*** --> Returns a Payroll Report based on Id.
        - ***"/data/all"*** --> Returns all Payroll reports available in the Database.
    - 
    - ***dto:*** -> Contains EmployeeDto to help transfer data from the CSV file to the entity.

    - ***helpers:*** -> Contains EmployeeDto to help transfer data from the CSV file to the entity.
      - ***FileHelper*** --> Contains the method to help read the csv file uploaded and extract the Id.
      - ***JobGroupEnum*** --> Enum to help calculate the employee payment.
      - ***JsonNumberFormat*** --> A class to help format the amout paid field in Json return value.
    
    - ***model:*** -> Contains all java Class to help the funcitonalities.
    
    - ***EmployeeReport***:
      - Contains all fields required to hold Employee Report Entity.
    - ***PayrollReport***:
        - Contains all fields required to hold Payroll Report Entity.
    - ***PayPeriod***:
      - Contains the Object to hold the period of work.
    - ***LoginFormRequest:***
      - Contains a simple form request to help login process.
    - ***MessageResponse:***
      - Holds a simple response message entity to return it to the user.
    - ***Roles:***
      - Enum Java to hold the user roles
    - ***User:***
      - Holds the user Entity.
    - ***UserInfoResponse:***
      - Holds a simple User response returned when the user is logged.
    - ***repo:*** -> Holds the Repository for User / Payroll
      - ***PayrollRepository:***
      - ***EmployeeReportRepository:***       
      - ***UserRepository:***
        > It holds all methods provided by JPA with an additional
        methods ***findByUsername*** to provide a better usage and help to find
        the proper user.
    - ***security:*** -> Holds all Spring Security Java classes.
      - ***AuthEntryPointJwt:*** -> By default Spring security returns all pages as 401. This class provides
        a better message for the error.
      - ***PayrollSecurity:*** -> Spring Security configuration to allow some request without
      require any authentication
      - ***JwTokenFilter:*** -> A filter for all API calls which requires authentication. It will verify
      if the user holds a proper token.
      - ***JwtUtils:*** -> It has some util functions to generate the token.
    - ***service:*** -> Holds all business classes for the service.
      - ***PayrollService:*** -> Contains CRUD methods for Payroll report / Employee Report.
      - ***UserDetailsImpl:*** -> Service to generate and build a UserDetail from Spring Security
      and return it when it is necessary.
      - ***UserService:*** -> Holds all user CRUD operations.

---
#### Miscellaneous

- ***application.properties*** -> File that holds Spring configuration for the database H2


#### Running the Project

- ***Run Command*** ->  mvn spring-boot:run

#### Swagger URL

- ***URL*** -> http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/payroll-controller/getPayrollReport
