booksy aplicacion de biblioteca digital

esta es una app de android para una biblioteca digital que hice con kotlin y jetpack compose.

lo que hace:
login y registro de usuarios
perfil con foto de camara o galeria
guarda cosas con sharedpreferences y datastore
usa api rest con retrofit
arquitectura mvvm
animaciones con compose
valida los formularios
material design 3

Tecnologias que usamos
kotlin el lenguaje
jetpack compose para la ui
navigation compose para ir de una pantalla a otra
retrofit para llamar a la api
coil para cargar imagenes
datastore para guardar datos
sharedpreferences para guardar cosas local
accompanist permissions para los permisos


api que usa:
la app llama a estos endpoints
post auth login para loguearse
post auth signup para registrarse
get auth me para ver el perfil
url base https x8ki letl twmt n7 xano io api Rfm 61dW

permisos que necesita:
la app pide estos permisos
camera para sacar fotos
read external storage para elegir fotos de la galeria

que hace cada pantalla:

login
valida email y contraseña
muestra errores con animacion
va a registro o perfil

registro
valida los campos nombre email contraseña
contraseña minimo 6 letras
animaciones de transicion

perfil
muestra datos del usuario
cambiar foto camara o galeria
cerrar sesion
guarda la foto local


laboratorios que hicicmos:
 10 navegacion con navigation compose
 11 formularios y validaciones
lab 12 animaciones y estados
lab 13 camara y galeria


notas
usamos material 3 para el diseño
mvvm para separar las cosas
stateflow para el estado
animaciones basicas con fadein slidein y spring
