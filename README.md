# Rest API for a class enrollment system for a university.

## Requirements:

The users of the system will consist of both school administrators and students. School administrators will create student identities, and students will be able to enroll themselves in classes before each term.


## Technologies

- **Backend**
    - Spring Boot
    - MongoDB
    - Maven
    - & Libraries [Lombok, Map Struct, Mockito, Junit 5 and Swagger Open Api]
- **DevOps**
    - Docker
    
## Pre-requisites
 
 Git ([https://git-scm.com/book/en/v2/Getting-Started-Installing-Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git))

 Docker:([https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/))
    

## Project Setup

**Clone this repository** (Click on the green button that says Clone)
1. Copy the URL
1. Go to the Terminal/Command Line and go to a directory/folder where you want to place the repository. You can navigate through folders with the commands `ls DIRECTORY` (shows all the folders in your current folder) and `cd DIRECTORY` (changes directory)
1. Once you're in a directory of your choice, type `git clone PASTE_THE_URL_HERE`
1. cd enrollment-system

### Build the application
```
mvn clean package
```

or

```
./mvnw clean package
```

### Build the application image in the docker

 To build the application image, you can use a simpler Docker command line:

```
docker build -t enrollment-system:1.0 .
```

Instead of building with the Docker command line, we could use Spring boot image, but it takes a longer time [Alternative option]

```
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=enrollment-system:1.0
```

### Start the containers

Run `docker-compose up`; this will take quite some time. (`Started EnrollmentSystemApplication in 5.602 seconds (JVM running for 6.33)` is the line that confirms the startup).

```
docker-compose build
docker-compose up
```
    
### Port Information

By default, this application runs on port **8080** inside the container.

### API Documentation

To check the API documentation, which is automatically generated using Open API (Swagger). 

 
Go to : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

 

### To stop running the application

- Mac: `ctrl + c`
- Windows: `ctrl + c`
- or in another tab in the terminal/command line, enter the following
    
```
docker-compose down 

```

With volumes.

```
docker-compose down -v

```

### To view the application logs
```
docker logs enrollment-system
```

### To connect the MongoDB Container
```
docker exec -it mongodb bash
```

### APIs:

#### Steps to use this application first time using postman or any rest client tools

##### Step 1: Add a new semester
	Method: Post
	Url: http://localhost:8080/semesters
	Body [Json] : 
		{
		  "name": "Spring2021",
		  "startDate": "2021-02-07",
		  "endDate": "2021-02-07"
		}

#### Step 2: Add a new class
	Method: Post
	Url: http://localhost:8080/classes
	Body [Json] : 
		{
		  "name": "classA",
		  "credit": 4
		}

#### Step 3: Add a new student
		Method: Post
		Url: http://localhost:8080/students
		Body [Json] : 
		{
		 "id": 1,
		  "firstName": "vadivel",
		  "lastName": "murugesan",
		  "nationality": "US",
		}
#### Step 4: Enroll student into a class for a particular semester
		Method: Post
		Url: http://localhost:8080/enrollments
		Body [Json] :
			{
			  "semester": "Spring2021",
			  "id": 1,
			  "class": "classA"
			}
		
#### Step 5: Get a student by id
		Method: Get
		Url: http://localhost:8080/students/1
			
### Other APIs		
#### API to modify students
		Method: PUT
		Url: http://localhost:8080/students
		Body [Json] : 
		{
		 "id": 1,
		  "firstName": "vadivel",
		  "lastName": "murugesan",
		  "nationality": "US",
		}

#### API to get the list of classes for a particular student for a semester or the full history of classes enrolled.
		Method: GET
		Urls:
			http://localhost:8080/fetchClasses?semester=Spring2021&id=1
			
			http://localhost:8080/fetchClasses?id=1

#### API to get the list of students enrolled in a class for a particular semester [pagination enabled]
		Method: GET
		Urls:
			http://localhost:8080/fetchStudents
			
			http://localhost:8080/fetchClasses?id=1
			
			http://localhost:8080/fetchClasses?semester=Spring2021
			
			http://localhost:8080/fetchClasses?pageNo=1&pageSize=100

#### API to drop a student from a class.
		Method: Delete
		Url: http://localhost:8080/enrollments?id=1&class=classA

Note: ***It is recommended to use [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) for detailed information.***

    