Booksy spa - app de gestion de libros

Booksy spa es una app para estudiantes y profesores, que quieren adminsitrar sus libros de estudio. es una biblioteca digital basicmanete.

funcionalidades:
-login y registro con validaciones basicas (contraseña minimo 8 caracteres)
-una pantalla con libros en un grid de 2 columnas
-pantalla de perfil para subir imagen desde la galeria
-animaciones basicas con animatedvisibility
-guardar datos en el telefono con sharedpreferences
-se conecta con una api para traer datos

Lo que usamos para hacerla:
- kotlin
- jetpack compose para la interfaz
- sharedpreferences para guardar el token y la foto de perfil
- retrofit para llamar a la api
- navigation compose para moverse entre las pantallas
- coil para cargar imagenes

la api del Swaggger es: https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

las llamadas Son:
post /auth/login - para loguearse
post /auth/signup - para crear cuenta
get /auth/me - para ver perfil
get /books - para traer los libros

-La arquitectura es mvvm, tiene los viewmodels para cada pantalla, y con stateflow para manejar el estado. 
los recursos nativos son la galeria para cambiar la foto de perfil usando "activityresultlauncher".

-las animaciones son con animatedvisibility carga cuadno aparecen los elemetnos en pantalla.

el flujo es:
entrar a la web >  registro o loguearse > ver los libros en un grid > pagina de perfil con foto.

Hay manejo basico errores, cuando la api no funciona muestra un loading visual mientras carga. tambien se guardan las cosas en sharedpreferences como el token y la imagen


para probar se debe crear una cuenta nueva o usar cualquier cuenta creada anteriormente, ahi se vera el icono de perfil en que se puede editar la imagen, y se veran los libros cargados.






datos tecnicos:
diseño:
/Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/java/com/biblioteca/app/ui/theme/Theme.kt



/Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/java/com/biblioteca/app/ui/navigation/AppNavegacion.kt tiene la logica de la navegacion.

en mainactivity, el Navhost es el anvegador de rutas de android, y define las rutas login registtro libros y perfil y qué pantalla mostrara en cada una.




usamos model view view model , jetpack compose para la build y retrofit para conexxiones http con la api
api:


/Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/java/com/biblioteca/app/data/api

https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW



-se crea el cliente HTTP con retrofit, ruta del arhicvo /Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/java/com/biblioteca/app/data/api/RetrofitClient.kt

luego tenemos  los ednpoints definidos., en que al ir a loginvewmodel se usan los base url de la api, para leerlo en kotlin, se hace post https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/auth/login. 
entonces recibo respuesta con hhtp

POST /auth/signup crear usuario no
POST /auth/login iniciar sesion
GET /auth/me obtener perfil


codigo ia para proabr api

curl -X POST "https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/auth/signup" \
  -H "Content-Type: application/json" \
  -d '{"email":"test'$(date +%s)'@test.com","password":"pass123456","name":"Tomas"}'



validaciones:
contrasena con 8 o mas caracteres
-animacion en mesaje de error
-si conexion falla muestra error de conexion
-si no encuentra 


Persitencia local:
en /Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/java/com/biblioteca/app/data/repository/PreferenciasRepository.kt todo se guarda bajo SharedPreferences bajo la variable biblioteca_prefs





MVVM = 
view:  solo renderiza la ui, la pantalal, es lo que se vea, entonces vemos todos con flowstate las val, 
    val email by viewModel.email.collectAsState()
    val error by viewModel.error.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
observa los cambios de estados de viewmodel


viewmodel:





persmios para camara:
/Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/java/com/biblioteca/app/ui/screens/PerfilScreen.kt aca se usa activityresultcontracts que es nativo de jetpack

este es un composable de jetpack
los permisos estan en /Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/AndroidManifest.xml

este es por ejemplo de camara 
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

entonces cuadno apreto cambiar imagen de perifl se llama a launcherGaleria en el arhicvo /Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/src/main/java/com/biblioteca/app/ui/screens/PerfilScreen.kt

las dependencias que uso para esto estan en /Users/tomas/Coding/Tarea aplicaciones moviles/biblioteca-android/app/build.gradle.kts
