import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NavBar from '../components/NavBar';
import Home from '../pages/Home';
import Books from '../pages/Books';
import Contact from '../pages/Contact';

function AppRoutes(){
    return (
        <BrowserRouter>
        <NavBar />
        {/* aqui se definen las rutas de la pagina */}
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/libros" element={<Books />} />
            <Route path="/contacto" element={<Contact />} />
        </Routes>
        </BrowserRouter>
    );
}

export default AppRoutes;