Booksy spa - app de gestion de libros

Booksy spa es una app para estudiantes y profesores, que quieren adminsitrar sus libros de estudio. es una biblioteca digital basicmanete.

Tiene estas funcionalidades:
- login y registro con validaciones basicas (contraseña minimo 8 caracteres)
- una pantalla con libros en un grid de 2 columnas
- pantalla de perfil para subir imagen desde la galeria
- animaciones basicas con animatedvisibility
- guardar datos en el telefono con sharedpreferences
- se conecta con una api para traer datos

Lo que usamos para hacerla:
- kotlin porque es lo que vimos en clase en fullstack
- jetpack compose para la interfaz
- sharedpreferences para guardar el token y la foto de perfil
- retrofit para llamar a la api
- navigation compose para moverse entre las pantallas
- coil para cargar imagenes

la api swaggger es: https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

las llamadas Son:
post /auth/login - para loguearte
post /auth/signup - para crear cuenta
get /auth/me - para ver tu perfil
get /books - para traer los libros

-La arquitectura es mvvm, tiene los viewmodels para cada pantalla, y con stateflow para manejar el estado. 
los recursos nativos son la galeria para cambiar la foto de perfil usando "activityresultlauncher".

-las animaciones son con animatedvisibility carga cuadno aparecen los elemetnos en pantalla.

el flujo es:
entrar a la web >  registro o loguearse > ver los libros en un grid > pagina de perfil con foto.

Hay manejo basico errores, cuando la api no funciona muestra un loading visual mientras carga. tambien se guardan las cosas en sharedpreferences como el token y la imagen


para probar se debe crear una cuenta nueva o usar cualquier cuenta creada anteriormente, ahi se vera el icono de perfil en que se puede editar la imagen, y se veran los libros cargados.