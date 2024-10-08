# API Contact manager
API REST with :
- Spring Boot
- MongoDB database

Docker image of the project : <a href="https://hub.docker.com/r/toniin/api-contact-manager" target="_blank">toniin/api-contact-manager</a>

## Clone the application

```bash
git clone https://github.com/Toniin/API_Contact_manager.git
```

## Put your environment variables (.env)

- **ALLOWED_ORIGINS**: List of url(s) application allowed to communicate with the API

- **DATABASE_URL**: Url/Ip adress of the mongodb database | name of the mongodb container

- **DATABASE_PORT**: Port of the mongodb database

- **MONGODB_DATABASE**: Name of the database where data is store

- **DATABASE_USERNAME**: Username to connect to database

- **DATABASE_PASSWORD**: Password to connect to database

- **JWT_SECRET_KEY**: Secret key to signing/verifying the JWT tokens
You can generate it with this command :
```sh
openssl rand -base64 32
```

- **JWT_EXPIRATION_TIME**: Time in milliseconds

## Build and run the app using maven

```bash
./mvnw clean package
java -jar target/API_Contact_manager-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
./mvnw spring-boot:run
```

The app will start running at <http://localhost:8080/api/contacts>.

## Explore Rest APIs

The app defines following CRUD APIs.

Users:

    POST /api/contacts/auth/register (Sign up)

    POST /api/contacts/auth/login (Sign in)


Contacts:

    POST /api/contacts/add (Create contact)

    GET /api/contacts (Get all contacts)
    
    GET /api/contacts/find/{phoneNumber} (Get specific contact)

    PUT /api/contacts/update/{phoneNumber} (Update contact name)

    DELETE /api/contacts/delete/{phoneNumber} (Delete contact)
