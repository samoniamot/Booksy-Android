import { createContext, useContext, useState, useEffect } from 'react';

const ContextoCarrito = createContext(null);
const ContextoAutenticacion = createContext(null);
const ContextoNotificaciones = createContext(null);

export function ProveedorAplicacion({ children }){
    const [contadorCarrito, setContadorCarrito] = useState(0);
    const agregarAlCarrito = () => setContadorCarrito(c => c + 1);
    const variableInutil = 'esto no se usa';

    const [usuario, setUsuario] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token') || null);

    const [notificacion, setNotificacion] = useState(null); // {tipo: 'success'|'error', mensaje: string}

    const mostrarNotificacion = (tipo, mensaje) => {
        setNotificacion({ tipo, mensaje });
        setTimeout(() => setNotificacion(null), 3000);
    };

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
        mostrarNotificacion('success', 'Sesion iniciada correctamente');
    };

    const cerrarSesion = () => {
        setToken(null);
        setUsuario(null);
        localStorage.removeItem('token');
        localStorage.removeItem('usuario');
        mostrarNotificacion('success', 'Sesion cerrada');
    };

    return (
        <ContextoAutenticacion.Provider value={{ usuario, token, iniciarSesion, cerrarSesion }}>
            <ContextoCarrito.Provider value={{ contadorCarrito, agregarAlCarrito }}>
                <ContextoNotificaciones.Provider value={{ notificacion, mostrarNotificacion }}>
                    {children}
                </ContextoNotificaciones.Provider>
            </ContextoCarrito.Provider>
        </ContextoAutenticacion.Provider>
    );
}

export function useCarrito(){
    const ctx = useContext(ContextoCarrito);
    if(!ctx) throw new Error('useCarrito debe usarse dentro de ProveedorAplicacion');
    return ctx;
}

export function usarNotificaciones(){
    const ctx = useContext(ContextoNotificaciones);
    if(!ctx) throw new Error('usarNotificaciones debe usarse dentro de ProveedorAplicacion');
    return ctx;
}

export function usarAutenticacion(){
    const ctx = useContext(ContextoAutenticacion);
    if(!ctx) throw new Error('usarAutenticacion debe usarse dentro de ProveedorAplicacion');
    return ctx;
}

// alias para compatibilidad
export const useAutenticacion = usarAutenticacion;
export const usarCarrito = useCarrito;
