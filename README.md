booksy app de libros

esta es una app que hice para la evaluacion ep3 de desarrollo de aplicaciones moviles

el caso que eligi fue el de educacion, una plataforma para estudiantes y profesores para manejar libros

la app tiene:
- login y registro con validaciones
- pantalla de libros con grid y filtros
- pantalla de perfil con imagen
- animaciones basicas
- guarda datos localmente
- usa api externa

tecnologias que use:
kotlin
jetpack compose
room para base de datos
retrofit para api
hilt para dependencias
navigation compose

como instalar:
1. clona el repo
2. abre en android studio
3. corre en emulador

la app consume esta api: https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

endpoints que usa:
post /auth/login - para iniciar sesion
post /auth/signup - para registrarse
get /auth/me - para obtener perfil
get /books - para obtener libros

la arquitectura es mvvm con viewmodels y flows

tiene persistencia local con room para los libros

los recursos nativos son camara y galeria para la imagen de perfil

las animaciones son transiciones entre pantallas y botones que se achican cuando los presionas

el flujo es: login -> libros -> perfil

tiene manejo de errores y estados de carga

creo que cumple con todos los requisitos del ep3

errores que tiene: algunos bugs en las validaciones, la api a veces falla, los permisos no siempre piden bien

pero funciona mas o menos

usuario de prueba: test@example.com password: test123
