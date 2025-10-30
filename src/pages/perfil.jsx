import { useState, useEffect } from 'react';
import { Container, Card, Button, Alert, Form } from 'react-bootstrap';
import axios from 'axios';
import { usarAutenticacion } from '../context/contextoAplicacion';

const API_BASE = 'https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW';

export default function Perfil() {
    const { usuario, token } = usarAutenticacion();
    const [datosUsuario, setDatosUsuario] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [imagenPerfil, setImagenPerfil] = useState(localStorage.getItem('imagenPerfil') || null);
    const [permisoCamara, setPermisoCamara] = useState(false);

    useEffect(() => {
        if (token) {
            axios.get(`${API_BASE}/auth/me`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            .then(res => setDatosUsuario(res.data))
            .catch(err => setError('Error cargando perfil'))
            .finally(() => setLoading(false));
        }
    }, [token]);

    const solicitarPermiso = () => {
        // simular permiso
        alert('Permiso para camara concedido');
        setPermisoCamara(true);
    };

    const seleccionarImagen = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                const base64 = reader.result;
                setImagenPerfil(base64);
                localStorage.setItem('imagenPerfil', base64);
            };
            reader.readAsDataURL(file);
        }
    };

    if (loading) return <Container><p>Cargando...</p></Container>;
    if (error) return <Container><Alert variant="danger">{error}</Alert></Container>;

    return (
        <Container className="mt-5">
            <h2>Perfil de Usuario</h2>
            {datosUsuario && (
                <Card>
                    <Card.Body>
                        <Card.Title>{datosUsuario.name}</Card.Title>
                        <Card.Text>Email: {datosUsuario.email}</Card.Text>
                        {imagenPerfil && <img src={imagenPerfil} alt="Perfil" style={{width: 100, height: 100, borderRadius: '50%'}} />}
                        <Form.Group className="mb-3">
                            <Form.Label>Seleccionar imagen de perfil</Form.Label>
                            <Form.Control type="file" accept="image/*" onChange={seleccionarImagen} />
                        </Form.Group>
                        <Button onClick={solicitarPermiso} disabled={permisoCamara}>
                            {permisoCamara ? 'Permiso concedido' : 'Solicitar permiso camara'}
                        </Button>
                    </Card.Body>
                </Card>
            )}
        </Container>
    );
}