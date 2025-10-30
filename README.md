# 📚 Booksy SPA - Tienda de Libros

Aplicación web SPA desarrollada con **React 19** para la gestión y compra de libros. Incluye autenticación de usuarios, consumo de API, almacenamiento local, animaciones y recursos nativos simulados.

## ✨ Características Principales

- 🔐 **Autenticación**: Login y registro con validaciones y API
- 📖 **Catálogo de Libros**: Visualización de libros desde API y gestión personal
- 🛒 **Carrito de Compras**: Agregar libros al carrito con contador
- ⭐ **Marcar como Leído**: Sistema de libros leídos
- ➕ **CRUD Personal**: Agregar, editar y eliminar libros personales
- 🎨 **Animaciones**: Transiciones suaves con Framer Motion
- 📱 **Responsive**: Diseño adaptativo con Bootstrap
- 💾 **Almacenamiento Local**: Persistencia de datos de usuario
- 📷 **Imagen de Perfil**: Subida simulada de imagen (cámara/galería)

## 🚀 Instalación y Ejecución

1. Clonar el repositorio:
```bash
git clone <repo-url>
cd "Tarea aplicaciones moviles"
```

2. Instalar dependencias:
```bash
npm install
```

3. Ejecutar en desarrollo:
```bash
npm start
```

4. Construir para producción:
```bash
npm run build
```

## �️ Tecnologías Utilizadas

- **React 19** - Framework principal
- **React Router DOM** - Navegación SPA
- **Bootstrap 5** - Estilos y componentes UI
- **Framer Motion** - Animaciones
- **Axios** - Cliente HTTP para API
- **React Hook Form** - Manejo de formularios
- **Context API** - Gestión de estado global
- **LocalStorage** - Persistencia local

## 📡 API Endpoints

- **Base URL**: `https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW`
- **Autenticación**:
  - `POST /auth/login` - Iniciar sesión
  - `POST /auth/signup` - Registrarse
- **Libros**:
  - `GET /books` - Obtener catálogo de libros (requiere token)

## 🧩 Estructura del Proyecto

```
src/
├── app/
│   ├── routes.js          # Configuración de rutas
│   └── components/
│       ├── NavBar.jsx     # Barra de navegación
│       └── books/
│           ├── BookCard.jsx    # Tarjeta de libro
│           ├── BooksGrid.jsx   # Cuadrícula de libros
│           └── Filters.jsx     # Filtros por categoría
├── context/
│   └── contextoAplicacion.js   # Contextos globales
├── data/
│   └── libros.mock.js     # Datos mock de libros
├── pages/
│   ├── Libros.jsx         # Página de libros
│   ├── IniciarSesion.jsx  # Login
│   ├── Registrarse.jsx    # Registro
│   ├── Perfil.jsx         # Perfil de usuario
│   └── Home.jsx           # Página principal
└── components/
    └── Notificacion.jsx   # Componente de notificaciones
```

## 🎯 Funcionalidades MVP

- ✅ Diseño coherente y responsive
- ✅ Formularios validados (registro, login)
- ✅ Navegación entre páginas
- ✅ Gestión de estado (carrito, auth, notificaciones)
- ✅ Almacenamiento local (libros leídos, personales)
- ✅ Recursos nativos simulados (subida de imagen)
- ✅ Animaciones en la interfaz
- ✅ Consumo de API real

## � Pruebas

```bash
npm test
```

Ejecuta tests con Jest y React Testing Library.

## 📝 Notas de Desarrollo

- El proyecto incluye commits graduales para mostrar progreso incremental
- Manejo de errores con notificaciones toast
- Fallback a datos mock si la API falla
- Animaciones escalonadas en la carga de libros
