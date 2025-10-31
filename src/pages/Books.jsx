import { useMemo, useState } from 'react';
import { Container } from 'react-bootstrap';
import { usarCarrito } from '../context/contextoAplicacion';
import { LIBROS, CATEGORIAS } from '../datos/libros.mock';
// nos aseguramos que estas rutas esten perfectas
import Filtros from '../components/books/filtros';
import CuadriculaLibros from '../components/books/cuadriculaLibros';

export default function Books() {
    const { agregarAlCarrito } = usarCarrito();
    const [filter, setFilter] = useState('all');
    const [readBooks, setReadBooks] = useState(JSON.parse(localStorage.getItem('readBooks') || '[]'));

    const list = useMemo(() => {
        return filter === 'all'
        ? LIBROS
        : LIBROS.filter(p => p.categoria === filter);
    }, [filter]);

    const markAsRead = (id) => {
        const newRead = [...readBooks, id];
        setReadBooks(newRead);
        localStorage.setItem('readBooks', JSON.stringify(newRead));
    };

    const isRead = (id) => readBooks.includes(id);

    return (
        <main>
        <Container>
            <h2 className="mb-2">catalogo de libros</h2>
            <p className="text-muted mb-3">explora nuestro catalogo de libros</p>

            <Filtros
            current={filter}
            onChange={setFilter}
            options={CATEGORIAS}
            total={LIBROS.length}
            />

            <CuadriculaLibros items={list} onAdd={agregarAlCarrito} onMarkRead={markAsRead} isRead={isRead} />
        </Container>
        </main>
    );
}