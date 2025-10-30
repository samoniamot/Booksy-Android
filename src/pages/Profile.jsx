import { useState, useEffect } from 'react';
import { Container, Button, Alert, Form } from 'react-bootstrap';
import { useAuth } from '../context/AppContext';
import axios from 'axios';
import { motion } from 'framer-motion';

const API_BASE = 'https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW';

export default function Profile() {
    const { user, token, logout } = useAuth();
    const [profile, setProfile] = useState(user);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [location, setLocation] = useState('');

    useEffect(() => {
        if (token) {
            fetchProfile();
        }
    }, [token]);

    const fetchProfile = async () => {
        setLoading(true);
        try {
            const res = await axios.get(`${API_BASE}/auth/me`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setProfile(res.data);
        } catch (err) {
            setError('error cargando perfil');
        } finally {
            setLoading(false);
        }
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                const base64 = reader.result;
                localStorage.setItem('profileImage', base64);
                setProfile({ ...profile, avatarUrl: base64 });
            };
            reader.readAsDataURL(file);
        }
    };

    const getLocation = () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (pos) => {
                    setLocation(`lat: ${pos.coords.latitude}, lng: ${pos.coords.longitude}`);
                },
                (err) => {
                    setError('error obteniendo ubicacion');
                }
            );
        } else {
            setError('geolocalizacion no soportada');
        }
    };

    const sendNotification = () => {
        if ('Notification' in window) {
            Notification.requestPermission().then(perm => {
                if (perm === 'granted') {
                    new Notification('Hola desde Booksy!', { body: 'Tu perfil esta listo' });
                }
            });
        }
    };

    if (!user) return <Container><p>no logueado</p></Container>;

    return (
        <Container className="mt-5">
            <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ duration: 0.5 }}>
                <h2>Perfil de {profile?.name}</h2>
                {error && <Alert variant="danger">{error}</Alert>}
                {loading && <p>cargando...</p>}
                <div>
                    <img src={profile?.avatarUrl || localStorage.getItem('profileImage')} alt="avatar" style={{ width: '100px', height: '100px' }} />
                    <Form.Control type="file" accept="image/*" onChange={handleImageChange} />
                </div>
                <p>Email: {profile?.email}</p>
                <Button onClick={getLocation}>Obtener Ubicacion</Button>
                <p>{location}</p>
                <Button onClick={sendNotification}>Enviar Notificacion</Button>
                <Button onClick={logout}>Logout</Button>
            </motion.div>
        </Container>
    );
}