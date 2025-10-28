import { useMemo, useState } from 'react';
import { Container } from 'react-bootstrap';
import { useCart } from '../context/AppContext';
import { BOOKS, CATEGORIES } from '../data/books.mock';
// nos aseguramos que estas rutas esten perfectas
import Filters from '../components/books/Filters';
import BookGrid from '../components/books/BookGrid';

export default function Books() {
    const { addToCart } = useCart();
    const [filter, setFilter] = useState('all');

    const list = useMemo(() => {
        return filter === 'all'
        ? BOOKS
        : BOOKS.filter(p => p.category === filter);
    }, [filter]);

    return (
        <main>
        <Container>
            <h2 className="mb-2">catalogo de libros</h2>
            <p className="text-muted mb-3">explora nuestro catalogo de libros</p>

            <Filters
            current={filter}
            onChange={setFilter}
            options={CATEGORIES}
            total={BOOKS.length}
            />

            <BookGrid items={list} onAdd={addToCart} />
        </Container>
        </main>
    );
}