import { Row, Col } from 'react-bootstrap';
import { motion } from 'framer-motion';
import TarjetaLibro from './tarjetaLibro';

export default function CuadriculaLibros({ items, onAdd, onMarkRead, isRead, onEdit, onDelete }) {
    // esta funcion hace el mapeo de libros, no la compliques
    return (
        <Row xs={1} sm={2} lg={3} className="g-3">
        {/* aqui se recorren los libros y se crea una tarjeta para cada uno */}
        {items.map((p, index) => (
            <Col key={p.id}>
            <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.5, delay: index * 0.1 }}
            >
            {/* usamos el bookcard que ya hicimos */}
            <TarjetaLibro libro={p} onAdd={() => onAdd(p)} onMarkRead={() => onMarkRead(p.id)} isRead={isRead(p.id)} onEdit={onEdit} onDelete={onDelete} />
            </motion.div>
            </Col>
        ))}
        </Row>
    );
}