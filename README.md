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

