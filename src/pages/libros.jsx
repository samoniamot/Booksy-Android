import { useMemo, useState, useEffect } from 'react';
import { Container } from 'react-bootstrap';
import { motion } from 'framer-motion';
import axios from 'axios';
import { usarCarrito, usarAutenticacion } from '../context/contextoAplicacion';
import { LIBROS, CATEGORIAS } from '../datos/libros.mock';
// nos aseguramos que estas rutas esten perfectas
import Filtros from '../components/books/filtros';
import CuadriculaLibros from '../components/books/cuadriculaLibros';

export default function Libros() {
    const { agregarAlCarrito } = usarCarrito();
    const { token } = usarAutenticacion();
    const [filter, setFilter] = useState('all');
    const [readBooks, setReadBooks] = useState(JSON.parse(localStorage.getItem('readBooks') || '[]'));
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    console.log('pagina de libros cargada');

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                setLoading(true);
                const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {};
                const response = await axios.get('https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/books', config);
                setBooks(response.data);
            } catch (err) {
                setError('Error al cargar libros');
                console.error(err);
                // fallback to mock
                setBooks(LIBROS);
            } finally {
                setLoading(false);
            }
        };
        fetchBooks();
    }, [token]);

    const list = useMemo(() => {
        return filter === 'all'
        ? books
        : books.filter(p => p.categoria === filter);
    }, [filter, books]);

    const markAsRead = (id) => {
        const newRead = [...readBooks, id];
        setReadBooks(newRead);
        localStorage.setItem('readBooks', JSON.stringify(newRead));
    };

    const isRead = (id) => readBooks.includes(id);

    return (
        <motion.main
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.5 }}
        >
        <Container>
            <h2 className="mb-2">catalogo de libros</h2>
            <p className="text-muted mb-3">explora nuestro catalogo de libros</p>

            {loading && <p>Cargando libros...</p>}
            {error && <p className="text-danger">{error}</p>}

            <Filtros
            current={filter}
            onChange={setFilter}
            options={CATEGORIAS}
            total={books.length}
            />

            <CuadriculaLibros items={list} onAdd={agregarAlCarrito} onMarkRead={markAsRead} isRead={isRead} />
        </Container>
        </motion.main>
    );
}