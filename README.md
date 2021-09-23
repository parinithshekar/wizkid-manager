# Wizkid Manager 2000

This is the java backend application that talks to the database and exposes APIs.





### Run as a docker image

The docker image can be built for the java application using the command

```
mvn spring-boot:build-image -DskipTests
```



### Run as a locally built application

Prerequisites:

- JDK 11
- Postgres DB running on port 5432
- [schema.sql](https://github.com/parinithshekar/wizkid-manager-db/blob/main/schema.sql) run to create initial schema for the application

To run without building a docker image, open this as a maven project in IDEA IntelliJ IDE, add a run configuration with `WizkidManager2000Application.java` as the main class, include the following environment variables and run the application



### Environment variables

The springboot app requires the following environment variables to function

```
# Connect to the database
POSTGRES_HOST=localhost
POSTGRES_DB=owow
POSTGRES_PORT=5432
POSTGRES_USERNAME=<username>
POSTGRES_PASSWORD=<password>

# Set the port for the backend application to run on
PORT=8080

# Configuration options for the creating and validation JWTs
JWT_SECRET=<a long enough secret>
JWT_ISSUER=owow.agency
JWT_PASSWORD_STRENGTH=10
JWT_EXPIRATION_HOURS=24
```



With these environment variables defined, the backend is fully functional.



## Routes



### Guest routes

- `/wizkid` supports
  - `GET` to fetch all wizkids
  - `POST` to create new wizkid
- `/wizkid/{id}` supports
  - `GET` to fetch one wizkid (hiding personal information)
  - `PUT` to edit details of one wizkid (except email and phone number). Doesn't allow changing details of fired wizkids
  - `DELETE` to delete a wizkid



### User routes

- `/login` supports
  - `POST` to login by providing credentials (email, password) and returns a JWT token in the `Set-Cookie` response header



##### 	Requires authentication

- `/wizkid/fire/{id}` supports
  - `PUT` to fire a wizkid
- `/wizkid/unfire/{id}` supports
  - `PUT` to unfire a wizkid
- `/wizkid/details` supports
  - `PUT` to update details of a wizkid (including personal information). Doesn't allow changing details of fired wizkids