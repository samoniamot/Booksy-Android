import { BrowserRouter, Routes, Route } from 'react-router-dom';
import BarraNavegacion from '../components/barraNavegacion';
import Inicio from '../pages/inicio';
import Libros from '../pages/libros';
import Contacto from '../pages/contacto';
import IniciarSesion from '../pages/iniciarSesion';
import Registrarse from '../pages/registrarse';
import Perfil from '../pages/perfil';

function RutasAplicacion(){
    return (
        <BrowserRouter>
        <BarraNavegacion />
        {/* aqui se definen las rutas de la pagina */}
        <Routes>
            <Route path="/" element={<Inicio />} />
            <Route path="/libros" element={<Libros />} />
            <Route path="/contacto" element={<Contacto />} />
            <Route path="/login" element={<IniciarSesion />} />
            <Route path="/signup" element={<Registrarse />} />
            <Route path="/profile" element={<Perfil />} />
        </Routes>
        </BrowserRouter>
    );
}

export default RutasAplicacion;