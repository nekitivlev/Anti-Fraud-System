# Anti-Fraud-System
My realization of a simplified antifraud system that is usually used in the finance sector.
Implementation of Hyperskill's project that includes JSON, REST API, Spring Boot Security, H2 database, LocalDateTime, and Project Lombok concepts.

## Architecture
- The `config` directory includes Security Configuration and Main Exception handler.
- The `controller` directory includes the REST controllers of the application.
- The `model` directory includes Entities of application and responses and requests for application.
- The `repository` directory includes entities's repositories. Responsible for managing the data storage.
- The `security` directory includes authentication logic. Responsible for validating the user's credentials and creating a user session.
- The `service` directory includes entities's services. Main business logic.
- The `util` directory includes helper classes.
## API Endpoints

| Endpoint                                            | Anonymous | MERCHANT | ADMINISTRATOR | SUPPORT |
|-----------------------------------------------------|-----------|----------|---------------|---------|
| POST /api/auth/user                                 | +         | +        | +             | +       |
| GET /api/auth/list                                  | -         | -        | +             | +       |
| DELETE /api/auth/user/{username}                    | -         | -        | +             | -       |
| PUT /api/auth/access                                | -         | -        | +             | -       |
| PUT /api/auth/role                                  | -         | -        | +             | -       |
| POST /api/antifraud/transaction                     | -         | +        | -             | -       |
| POST, DELETE, GET /api/antifraud/suspicious-ip/{ip} | -         | -        | -             | +       |
| POST, DELETE, GET /api/antifraud/stolencard/{number}| -         | -        | -             | +       |

_'+' means the user with given role can access given endpoint. '-' means the user without given role can't access given endpoint._

## Examples of usage
### Sign up
```
POST /api/auth/user
{
   "name": "<String value, not empty>",
   "username": "<String value, not empty>",
   "password": "<String value, not empty>"
}
```

Response:

```
{
    "id": 1,
    "name": "John Doe",
    "username": "JohnDoe",
    "role": "ADMINISTRATOR"
}
```
### Login
```
POST /api/auth/login
{
   "username": "<String value, not empty>",
   "password": "<String value, not empty>"
}
```

Response:

```
{
    "id": 1,
    "name": "John Doe",
    "username": "JohnDoe",
    "role": "ADMINISTRATOR"
}
```
### User deleting
```
DELETE /api/auth/user/JohnDoe
```

Response:

```
{
   "username": "JohnDoe",
   "status": "Deleted successfully!"
}
```

### User list getting

```
GET /api/auth/list
```

Response:

```
[
    {
        "id": <user1 id>,
        "name": "<user1 name>",
        "username": "<user1 username>",
        "role": "<user1 role>"
    },
     ...
    {
        "id": <userN id>,
        "name": "<userN name>",
        "username": "<userN username>",
        "role": "<userN role>"
    }
]
```
### User role updating

```
PUT /api/auth/role
{
   "username": "<String value, not empty>",
   "role": "<String value, not empty>"
}
```

Response:

```
{
   "id": <Long value, not empty>,
   "name": "<String value, not empty>",
   "username": "<String value, not empty>",
   "role": "<String value, not empty>"
}
```

### User access updating

```
PUT /api/auth/access
{
   "username": "<String value, not empty>",
   "operation": "<[LOCK, UNLOCK]>"  // determines whether the user will be activated or deactivated
}
```

Response:

```
{
    "status": "User <username> <[locked, unlocked]>!"
}
```

### Transaction posting
```
POST /api/antifraud/transaction
{
  "amount": <Long>,
  "ip": "<String value, not empty>",
  "number": "<String value, not empty>",
  "region": "<String value, not empty>",
  "date": "yyyy-MM-ddTHH:mm:ss"
}
```

Response:

```
{
   "result": "ALLOWED",
   "info": "none"
}
```

### Suspicious IP saving

```
POST /api/antifraud/suspicious-ip
{
  "ip": "<String value, not empty>"
}
```

Response:

```
{
   "id": "<Long value, not empty>",
   "ip": "<String value, not empty>"
}
```

### Suspicious IP deleting
```
DELETE /api/antifraud/suspicious-ip/{ip}
```

Response:

```
{
   "status": "IP <ip address> successfully removed!"
}
```

### Stolen card number saving
```
POST /api/antifraud/stolencard
{
  "number": "<String value, not empty>"
}
```

Response:

```
{
   "id": "<Long value, not empty>",
   "number": "<String value, not empty>"
}
```

### Stolen card number deleting
```
DELETE /api/antifraud/stolencard/{number}
```

Response:

```
{
   "status": "Card <number> successfully removed!"
}
```

### Transaction feedback adding
```
PUT /api/antifraud/transaction
{
   "transactionId": <Long>,
   "feedback": "<String>"
}
```

_Feedback can be 'ALLOWED', 'MANUAL_PROCESSING' or 'PROHIBITED'_.

Response:

```
{
  "transactionId": <Long>,
  "amount": <Long>,
  "ip": "<String value, not empty>",
  "number": "<String value, not empty>",
  "region": "<String value, not empty>",
  "date": "yyyy-MM-ddTHH:mm:ss",
  "result": "<String>",
  "feedback": "<String>"
}
```

### Transaction feedback getting
```
GET /api/antifraud/history/{number}
```

Response:

```
[
    {
      "transactionId": <Long>,
      "amount": <Long>,
      "ip": "<String value, not empty>",
      "number": number,
      "region": "<String value, not empty>",
      "date": "yyyy-MM-ddTHH:mm:ss",
      "result": "<String>",
      "feedback": "<String>"
    },
     ...
    {
      "transactionId": <Long>,
      "amount": <Long>,
      "ip": "<String value, not empty>",
      "number": number,
      "region": "<String value, not empty>",
      "date": "yyyy-MM-ddTHH:mm:ss",
      "result": "<String>",
      "feedback": "<String>"
    }
]
```
## Stack
- Java 11
- Gradle
- Spring boot
- H2 Database

## Usage
`./gradlew bootRun`
