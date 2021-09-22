# Wizkid Manager 2000

This is the java backend application that talks to the database and exposes APIs.

The docker image can be built for the java application using the command

```
mvn spring-boot:build-image -DskipTests
```



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



With these environment variables defined, and using the docker image through docker compose or kubernetes. The backend is fully functional.