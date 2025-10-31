import { Alert } from 'react-bootstrap';
import { usarNotificaciones } from '../context/contextoAplicacion';

export default function Notificacion() {
    const { notificacion } = usarNotificaciones();
    if (!notificacion) return null;

    return (
        <div style={{ position: 'fixed', top: 20, right: 20, zIndex: 9999 }}>
            <Alert variant={notificacion.tipo === 'success' ? 'success' : 'danger'}>
                {notificacion.mensaje}
            </Alert>
        </div>
    );
}