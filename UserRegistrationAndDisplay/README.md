# User registration and display

# Aim

Aim of this project was to get familiar with Springboot by creating a small project with this framework and spending two days working on it.

# Features

The base features are the following :

* Register a user
* Display a registered user

# Requirements

The exercise statement provided a few requirements :

* Users must be defined by multiple fields, including mandatory and optional fields.
* Input must be validated and correct error message / http status must be returned when necessary
* Input and outputs of each call must be logged at each call along with processing time.
* One non mandatory request parameter with a default value must be used at some point
* A path variable must be used at some point
* User registration is restricted to adult people living in France

# Bonuses

* Use a non relational DB
* Use Aspect Oriented Programming
* Provide documentation / UML / schemas to explain architecture

# Decisions taken

* Use english everywhere (despite the french-oriented nature of the project) 
* Use MongoDB
* Start with the SpringBoot gettingStarted project and modify it (
* Add Postman requests and responses as integration tests
* Another feature has been added to our service that is responsible for greeting users

# Left to do

* Automate Postman tests
* Bonuses

# 1- Users

Decision has been taken to give User object the following fields : 

* Name
* BirthDate
* Country
* Mail Address
* Job (Optional)
* Id

# 2- Input validation and error messages / http status

In order to validate inputs, the user class fields have been annotated with javax.validation.constraints keywords, such as @Size or @NotNull.

An example of error message used in this project is the one provided in case a user tries to register but doesn't meet the expectations (provides another country than France or isn't adult yet). A message explaining that one of those two conditions is not satisfied is displayed :
User must live in France and be older than 18 in order to register.

The 422 Http status is returned in this case.

In case registration is successful, we return a 201 Http status.

# 3- Logging input and outputs

The slf4j logger object has been used to log inputs and outputs of each call of the UserController methods in the console. Execution time is provided with the output log.

# 4- A non mandatory request parameter with default value

The greeting feature has been created in order to complete this request.
Sending a get request to the Uri /user/welcomeUser?name=ExampleName will return the following text : Welcome to this example application ExampleName.
This parameter is non mandatory and the default value has been set to "unknown user" which means that sending a get request to the Usi user/welcomeUser will result in the following text : Welcome to this example application unknown user.

# 5- Have a path variable

The get request sent to the /user uri will display the list of all registered users. Sending a get request to the following uri : /user/exampleName will display the list of all registered users that have their name field equal to "exampleName". If none is found, the list is empty.

# 6- Only adults living in France can create an account

This condition has been implemented as previously mentioned.

# Bonuses

No bonus has been intentionally implemented if any of them is implemented.
This document constitutes the functional documentation of the project.
Technical documentation has not been pushed any further than writing javadoc for exposed interfaces and public methods.
Postman examples of working and not working requests can be found in the resource folder of this project.

Note : The deleteRequestById request is using a Path parameter (a user id) that needs to be manually inputed as it is auto-generated when registering a new user.