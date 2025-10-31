import { useState } from 'react';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const API_BASE = 'https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW';

export default function Registrarse() {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        const errors = [];
        if (!data.name || data.name.trim().length < 2) {
            errors.push('Nombre debe tener al menos 2 caracteres');
        }
        if (!data.email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(data.email)) {
            errors.push('Email inválido');
        }
        if (!data.password || data.password.length < 6) {
            errors.push('Password debe tener al menos 6 caracteres');
        }
        if (errors.length > 0) {
            setError(errors.join(', '));
            return;
        }
        setLoading(true);
        setError('');
        try {
            await axios.post(`${API_BASE}/auth/signup`, data);
            navigate('/login');
        } catch (err) {
            setError('Error en registro: ' + (err.response?.data?.message || err.message));
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="mt-5">
            <h2>Registrarse</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Form.Group className="mb-3">
                    <Form.Label>Nombre</Form.Label>
                    <Form.Control
                        {...register('name', { required: 'Nombre requerido' })}
                        isInvalid={!!errors.name}
                    />
                    <Form.Control.Feedback type="invalid">{errors.name?.message}</Form.Control.Feedback>
                </Form.Group>
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
                    {loading ? 'Cargando...' : 'Registrar'}
                </Button>
            </Form>
        </Container>
    );
}