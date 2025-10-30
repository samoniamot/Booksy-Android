import { Row, Col } from 'react-bootstrap';
import TarjetaProducto from './tarjetaProducto';

export default function CuadriculaProductos({ items, onAdd }) {
    return (
        <Row xs={1} sm={2} lg={3} className="g-3">
        {items.map(p => (
            <Col key={p.id}>
            <TarjetaProducto producto={p} onAdd={() => onAdd(p)} />
            </Col>
        ))}
        </Row>
    );
}
