import { Card, Button, Badge } from 'react-bootstrap';

export default function TarjetaProducto({ producto, onAdd }) {
    const { nombre, precio, categoria, urlImagen } = producto;

    return (
        <Card className="h-100 shadow-sm card-hover">
        {urlImagen && (
            <Card.Img
            variant="top"
            src={urlImagen}
            alt={`Imagen de ${nombre}`}
            loading="lazy"
            style={{ objectFit: 'cover', height: 180 }}
            />
        )}
        <Card.Body className="d-flex flex-column">
            <div className="d-flex justify-content-between align-items-start mb-2">
            <Card.Title className="mb-0" style={{ fontSize: '1rem', lineHeight: 1.2 }}>
                {nombre}
            </Card.Title>
            {categoria && <Badge bg="secondary">{categoria}</Badge>}
            </div>

            <Card.Text className="text-muted mb-3">
            ${Number(precio).toLocaleString('es-CL')}
            </Card.Text>

            <Button
            variant="primary"
            onClick={onAdd}
            className="mt-auto"
            aria-label={`Agregar ${nombre} al carrito`}
            >
            Agregar
            </Button>
        </Card.Body>
        </Card>
    );
}
