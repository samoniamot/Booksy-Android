import { Row, Col } from 'react-bootstrap';
import TarjetaLibro from './tarjetaLibro';

export default function CuadriculaLibros({ items, onAdd, onMarkRead, isRead }) {
    // esta funcion hace el mapeo de libros, no la compliques
    return (
        <Row xs={1} sm={2} lg={3} className="g-3">
        {/* aqui se recorren los libros y se crea una tarjeta para cada uno */}
        {items.map(p => (
            <Col key={p.id}>
            {/* usamos el bookcard que ya hicimos */}
            <TarjetaLibro libro={p} onAdd={() => onAdd(p)} onMarkRead={() => onMarkRead(p.id)} isRead={isRead(p.id)} />
            </Col>
        ))}
        </Row>
    );
}