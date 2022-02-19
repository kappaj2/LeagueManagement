# Getting Started

This application is a SpringBoot application running with Maven dependency management.

## System Requirements

To run this application you need the following installed:

* Java 15 or later
* Maven
* Optional run it inside an IDE of choice.

### Application information

Running the application as is will use the application.yaml file property that specifies the path to a datafile that
will be loaded. This can be updated in the property file or overridden via a command-line argument.<br>
Example:

```aidl
 mvn spring-boot:run -Dspring-boot.run.arguments=--application.file-name=//opt/testdata/testdata.txt 
```

Game scores are kept in an internal data structure and is not persisted to any datastore. Thus restarting the
application will clean out any previous scores and ratings.

### Resources included

A sample data file is available in the resources folder of the application.

A Postman collection is also included in the resources folder for testing these endpoints.

### Endpoints provided

Data endpoints are available to extract certain data from the application. These are expose via REST GET endpoints as
follows:<br>

* [Get a specific League teams detail](http://localhost:8080/api/team/Lions)
* [Get all the teams loaded - not ranked or sorted](http://localhost:8080/api/teams)
* [Get all the teams loaded, ranked and sorted](http://localhost:8080/api/teams/sorted)

Using the last endpoint will also print the required output as per task in the console.