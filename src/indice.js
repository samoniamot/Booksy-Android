import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';
import Aplicacion from './Aplicacion';
import { ProveedorAplicacion } from './context/contextoAplicacion';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <ProveedorAplicacion>
            <Aplicacion />
        </ProveedorAplicacion>
    </React.StrictMode>
);
