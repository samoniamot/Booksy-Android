booksy - app de libros para ep3

hola, esta es mi app que hice para la evaluacion parcial 3 de desarrollo de aplicaciones moviles

es una app para estudiantes y profesores que quieren manejar sus libros de estudio

tiene estas cosas:
- login y registro con validaciones basicas
- una pantalla con libros en grid con filtros
- pantalla de perfil donde puedes poner tu foto
- animaciones simples
- guarda cosas en la base de datos del telefono
- conecta con una api externa

lo que use para hacerla:
kotlin porque es lo que vimos en clase
jetpack compose para la interfaz
room para guardar datos localmente
retrofit para llamar a la api
hilt para no tener que crear todo manualmente
navigation compose para moverme entre pantallas

para instalarla:
1. bajas el codigo del repo
2. lo abres en android studio
3. lo corres en un emulador o telefono

la api que usa es: https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

las llamadas que hace:
post /auth/login - para loguearte
post /auth/signup - para crear cuenta
get /auth/me - para ver tu perfil
get /books - para traer los libros

la arquitectura es mvvm que es lo que nos ensenaron

tiene base de datos local con room para guardar los libros que agregas

los recursos nativos son la camara y la galeria para cambiar la foto de perfil

las animaciones son cuando cambias de pantalla se desliza y los botones se hacen mas pequenos cuando los tocas

el flujo de la app es: entras -> te logueas -> ves los libros -> vas al perfil

maneja errores cuando la api no funciona y muestra loading mientras carga

creo que cumple con lo que pide el ep3

bugs que tiene: a veces las validaciones no funcionan bien, la api se cae, los permisos de camara no piden siempre

pero mas o menos funciona

para probar usa: email test@example.com y password test123

espero que le guste al profesor
