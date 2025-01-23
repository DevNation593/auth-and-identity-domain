# User Authentication Service

Microservicio REST para autenticación y gestión de usuarios con JWT.

## Características

- Registro y autenticación de usuarios
- Tokens JWT para gestión de sesiones
- Spring Security para autorización
- Base de datos PostgreSQL
- Docker ready

## Requisitos

- Java 17+
- PostgreSQL
- Maven 3.6+

## Variables de Entorno

```bash
DB_URL=jdbc:postgresql://localhost:5432/userdb
DB_USERNAME=postgres
DB_PASSWORD=password
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000
SERVER_PORT=8080
```

## Instalación

```bash
mvn clean install
```

## Ejecución

```bash
mvn spring-boot:run
```

## API Endpoints

### Autenticación
- POST `/api/auth/register` - Registro de usuarios
- POST `/api/auth/login` - Login de usuarios

### Usuarios
- GET `/api/users` - Listar usuarios
- GET `/api/users/{id}` - Obtener usuario por ID
- PUT `/api/users/{id}` - Actualizar usuario
- DELETE `/api/users/{id}` - Eliminar usuario

## Docker

```bash
docker-compose up -d
```