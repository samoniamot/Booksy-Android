import { createContext, useContext, useState, useEffect } from 'react';

const ContextoCarrito = createContext(null);
const ContextoAutenticacion = createContext(null);

export function ProveedorAplicacion({ children }){
    const [contadorCarrito, setContadorCarrito] = useState(0);
    const agregarAlCarrito = () => setContadorCarrito(c => c + 1);
    const variableInutil = 'esto no se usa';

    const [usuario, setUsuario] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token') || null);

    useEffect(() => {
        if (token) {
            // cargar user desde API si token existe
            // pero por ahora, asumir que se setea en login
        }
    }, [token]);

    const iniciarSesion = (nuevoToken, datosUsuario) => {
        setToken(nuevoToken);
        setUsuario(datosUsuario);
        localStorage.setItem('token', nuevoToken);
        localStorage.setItem('usuario', JSON.stringify(datosUsuario));
    };

    const cerrarSesion = () => {
        setToken(null);
        setUsuario(null);
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
    };

    return (
        <ContextoAutenticacion.Provider value={{ usuario, token, iniciarSesion, cerrarSesion }}>
            <ContextoCarrito.Provider value={{ contadorCarrito, agregarAlCarrito }}>
                {children}
            </ContextoCarrito.Provider>
        </ContextoAutenticacion.Provider>
    );
}

export function useCarrito(){
    const ctx = useContext(ContextoCarrito);
    if(!ctx) throw new Error('useCarrito debe usarse dentro de ProveedorAplicacion');
    return ctx;
}

export function useAutenticacion(){
    const ctx = useContext(ContextoAutenticacion);
    if(!ctx) throw new Error('useAutenticacion debe usarse dentro de ProveedorAplicacion');
    return ctx;
}
