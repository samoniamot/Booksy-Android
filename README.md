booksy - app de gestion libros

app para estudiantes y profesores, que quieren adminsitrar sus libros de estudio. es una biblioteca digital basicmanete

tiene estas funcionalidades:
- login y registro con validaciones basicas
- una pantalla con libros en un grid con filtros
-pantalla de perfil para usbir imagen
-animaciones basicas de maeria vista en clases
-guardar datos en el telefono
-se conecta con una api 

lo que usamos para hacerla:
-kotlin porque es lo que vimos en clase en fullstack
-jetpack compose para la interfaz
-"room" para guardar datos localmente
-retrofit para llamar a la api
-navigation compose para moverse entre las pantallas

la api que usa es: https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

las llamadas Son:
post /auth/login - para loguearte
post /auth/signup - para crear cuenta
get /auth/me - para ver tu perfil
get /books - para traer los libros

-La arquitectura es mvvm que es lo que nos ensenaron.  tiene base de datos local con room para guardar los libros que agregas. Los recursos nativos son la camara y la galeria para cambiar la foto de perfil

-las animaciones son cuando se hace cambio de pantalla se desliza y los botones se hacen mas pequenos cuando los tocas como efecto hover

el flujo de la app es: entras > te logueas >ves los libros> pagina de perfil

hay manejo basico errores cuando la api no funciona y muestra loading mientras carga


se puede probar: email test@example.com y contrasena test123