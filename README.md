integrantes
tomas maino


microservicio propio (spring boot en render) - libros
base url: https://booksy-backend-jkte.onrender.com

GET /api/libros - obtener todos los libros
GET /api/libros/{id} - obtener un libro por id
POST /api/libros - crear libro nuevo
PUT /api/libros/{id} - actualizar libro
DELETE /api/libros/{id} - eliminar libro
Sí

microservicio backend
render: https://booksy-backend-jkte.onrender.com


apk firmado y keystore

el apk firmado esta en: app/build/outputs/apk/release/app-release.apk
el archivo de firma jks esta en: booksy-release.jks


pruebas unitarias

los tests estan en:
app/src/test/java/com/biblioteca/app/ui/viewmodel/LibrosViewModelTest.kt
app/src/test/java/com/biblioteca/app/data/repository/LibrosRepositoryTest.kt
app/src/test/java/com/biblioteca/app/data/model/LibroTest.kt

para ejecutar: ./gradlew test


repositorios

app android: https://github.com/samoniamot/Booksy-Android
backend spring boot: https://github.com/samoniamot/Booksy-Backend

# Booksy Backend

Backend de la aplicacion Booksy para la gestion de libros.

## Tecnologias usadas

- Spring Boot 3.2.0
- MongoDB
- Java 17

## Endpoints

- `GET /api/libros` - obtener todos los libros
- `GET /api/libros/{id}` - obtener un libro por id
- `GET /api/libros/buscar?titulo=texto` - buscar libros por titulo
- `POST /api/libros` - crear un libro nuevo
- `PUT /api/libros/{id}` - actualizar un libro
- `DELETE /api/libros/{id}` - eliminar un libro

## Variables de entorno

- `PORT` - puerto del servidor (default: 8080)
- `MONGODB_URI` - uri de conexion a mongodb

## Como ejecutar

```bash
mvn spring-boot:run
```



# Abrir Android Studio
open -a "Android Studio" "/Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android"

# Ejecutar tests
cd "/Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android" && ./gradlew test

# Build APK
cd "/Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android" && ./gradlew assembleRelease

# Probar API backend
curl https://booksy-backend-1.onrender.com/api/libros


# API de Swagger
https://booksy-backend-1.onrender.com/swagger-ui.html

# comandos por si a caso
# Correr backend local
cd "/Users/tomas/Coding/Tarea aplicaciones moviles/booksy-backend"
./mvnw spring-boot:run


# enlaces relevantes
MONGODB: mongosh "mongodb+srv://booksy.lweespa.mongodb.net/" --apiVersion 1 --username tomaino_db_user

CONTRASENA: BASE DE DATOS:

admin2025



repositorio codigo app Booksy:

https://github.com/samoniamot/Booksy-Android

repositorio backend app Booksy:

https://github.com/samoniamot/Booksy-Backend



backend en Render.com:

https://booksy-backend-1.onrender.com

api:
https://booksy-backend-1.onrender.com/swagger-ui.html

