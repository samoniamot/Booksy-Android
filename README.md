booksy - App de libros

app para estudiantes y profesores que quieren manejar sus libros de estudio. es una biblioteca digital basicmanete

tiene estas funcionalidades:
- login y registro con validaciones basicas
- una pantalla con libros en grid con filtros
-pantalla de perfil donde puedes poner tu foto
-animaciones simples
-guarda cosas en la base de datos del telefono
-conecta con una api externa

lo que use para hacerla:
-kotlin porque es lo que vimos en clase en el ramo Fullstack
-jetpack compose para la interfaz
-"room" para guardar datos localmente
-retrofit para llamar a la api
-hilt para no tener que crear todo manualmente
-navigation compose para moverse entre las pantallas

la api que usa es: https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

las llamadas Son:
post /auth/login - para loguearte
post /auth/signup - para crear cuenta
get /auth/me - para ver tu perfil
get /books - para traer los libros

-La arquitectura es mvvm que es lo que nos ensenaron.  tiene base de datos local con room para guardar los libros que agregas. Los recursos nativos son la camara y la galeria para cambiar la foto de perfil

-las animaciones son cuando cambias de pantalla se desliza y los botones se hacen mas pequenos cuando los tocas

el flujo de la app es: entras -> te logueas -> ves los libros -> vas al perfil

maneja errores cuando la api no funciona y muestra loading mientras carga

creo que cumple con lo que pide el ep3

bugs que tiene: a veces las validaciones no funcionan bien, la api se cae, los permisos de camara no piden siempre

pero mas o menos funciona

para probar usa: email test@example.com y password test123