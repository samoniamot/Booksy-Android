import { useState } from 'react';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useAutenticacion } from '../context/contextoAplicacion'; // asumir que agregaremos auth al context

const API_BASE = 'https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW';

export default function IniciarSesion() {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const { iniciarSesion } = useAutenticacion(); // agregar al context

    const onSubmit = async (data) => {
        setLoading(true);
        setError('');
        try {
            const res = await axios.post(`${API_BASE}/auth/login`, data);
            iniciarSesion(res.data.authToken, res.data.user);
            navigate('/libros');
        } catch (err) {
            setError('Error en login: ' + (err.response?.data?.message || err.message));
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="mt-5">
            <h2>Iniciar Sesion</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Form.Group className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        type="email"
                        {...register('email', { required: 'Email requerido' })}
                        isInvalid={!!errors.email}
                    />
                    <Form.Control.Feedback type="invalid">{errors.email?.message}</Form.Control.Feedback>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        {...register('password', { required: 'Password requerido' })}
                        isInvalid={!!errors.password}
                    />
                    <Form.Control.Feedback type="invalid">{errors.password?.message}</Form.Control.Feedback>
                </Form.Group>
                <Button type="submit" disabled={loading}>
                    {loading ? 'Cargando...' : 'Iniciar Sesion'}
                </Button>
            </Form>
        </Container>
    );
}