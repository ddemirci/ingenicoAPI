# ingenicoAPI
> Simple Java microservice that allows for two endpoints to be exposed. These are "Document" and "Profile".

## Table of Contents
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Usage](#usage)
* [Endpoints](#endpoints)
* [Tests](#tests)
* [Room for Improvement](#room-for-improvement)
* [Contact](#contact)

## Technologies Used
- Java - version 18
- Jersey - version 3.0.4
- JUnit - version 5.8.2
- Mockito-Core - version 4.5.1

## Features
List the ready features here:
- A profile can be created.
- A unique profile or all profiles can be listed.
- A profile can upload/retrieve/delete their own documents.

## Setup
1. Clone the project into your local machine.
2. Reload project for restoring Maven dependencies in pom.xml

3. $ mvn clean package
4. $ java -jar target\ingenico-api.jar

## Usage
The application is started in "http://localhost:8080"
For applicable requests and testing purpose, Postman collection json file can be found at [here](https://github.com/ddemirci/ingenicoAPI/blob/master/ingenicoAPI.postman_collection.json)

## Endpoints

### [/profile](https://github.com/ddemirci/ingenicoAPI/blob/master/src/main/java/com/ingenico/controller/ProfileController.java)
#### GET: /profile/{id}
To retrieve the requested profile by profileId. If the profile exists, return it with HTTP_200_OK code, unless returns HTTP_404_NotFound with a message.

#### GET: /profile
To retrieve all profiles. Returns the profile list with HTTP_200_OK code. The profile list might be an empty collection if there is no profile.

#### POST: /profile
To insert a profile. ProfileDto is requested as a body parameter. Unless there is an error, returns the inserted profile with HTTP_201_Created code. If there is, returns HTTP_404_NotFound with message. 


### [/document](https://github.com/ddemirci/ingenicoAPI/blob/master/src/main/java/com/ingenico/controller/DocumentController.java)

#### GET: /document/profile/{profileId}
To retrieve all documents related to the profile which has given profileId. If no such profile exists, return HTTP_404_NotFound with a message. If exists, returns document list wrapped with HTTP_200_OK code.
The document list might be an empty collection if there is no document of the profile.

#### POST: /document
To insert a document. DocumentDto is needed as an argument. If no such profile exists return HTTP_404_NotFound with message. Unless returns inserted document with HTTP_201_Created code or HTTP_404_NotFound that means an error has occurred.

#### PUT: /document/{id}
To update a document. Path parameter "id" and body parameter DocumentDto are requested.
If no such profile or document exists return HTTP_404_NotFound with a message.
If the profile does not own the document, an update will not be completed, HTTP_403_Forbidden is returned.
If all requirements are satisfied, the updated document is returned with the HTTP_200_OK code.

#### DELETE: /document/{id}/profile/{profileId}
To delete the requested document if exits and is applicable. Both "id" and "profileId" are requested as path parameters.
If no such profile or document exists return HTTP_404_NotFound with a message.
If the profile does not own the document, deletion will not be completed, the HTTP_403_Forbidden is returned.
If all requirements are satisfied and the document is deleted, returns HTTP_200_OK code, unless HTTP_404_NotFound.

## Tests
Unit tests were written for the controller and service layers. There are 30 test cases.
![TestCases](https://user-images.githubusercontent.com/17169727/171796235-b2617912-b42b-46f1-8330-07427f6c353d.PNG)

## Room for Improvement

- Number generators might implement an "Interface" and can be injected into the services.
- Service interfaces can be written and service methods are invoked via it.

## Contact
Created by [Doruk Demirci](mailto:dorukdemirci@yahoo.com) - do not hesitate to contact me!
