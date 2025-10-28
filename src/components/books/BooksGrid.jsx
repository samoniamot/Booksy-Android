import { Row, Col } from 'react-bootstrap';
import BookCard from './BookCard';

export default function BookGrid({ items, onAdd }) {
    return (
        <Row xs={1} sm={2} lg={3} className="g-3">
        {/* aqui se recorren los libros y se crea una tarjeta para cada uno */}
        {items.map(p => (
            <Col key={p.id}>
            {/* usamos el bookcard que ya hicimos */}
            <BookCard book={p} onAdd={() => onAdd(p)} />
            </Col>
        ))}
        </Row>
    );
}