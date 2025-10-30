import { Card, Button, Badge } from 'react-bootstrap';

export default function TarjetaLibro({ libro, onAdd, onMarkRead, isRead }) {
    const { titulo, autor, precio, categoria, urlImagen } = libro;

    return (
        <Card className="h-100 shadow-sm">
        {urlImagen && (
            <Card.Img
            variant="top"
            src={urlImagen}
            alt={`imagen de ${titulo}`}
            style={{ objectFit: 'cover', height: 180 }}
            />
        )}
        <Card.Body className="d-flex flex-column">
            <div className="d-flex justify-content-between align-items-start mb-2">
            <Card.Title className="mb-0" style={{ fontSize: '1rem' }}>
                {titulo}
            </Card.Title>
            {categoria && <Badge bg="secondary">{categoria}</Badge>}
            </div>

            <Card.Text className="text-muted mb-3">
            {autor}
            </Card.Text>

            <Card.Text className="text-muted mb-3">
            ${Number(precio).toLocaleString('es-cl')}
            </Card.Text>

            <Button
            variant="primary"
            onClick={onAdd}
            className="mt-auto mb-2"
            aria-label={`agregar ${titulo} al carrito`}
            >
            agregar
            </Button>
            <Button
            variant={isRead ? "success" : "outline-secondary"}
            onClick={onMarkRead}
            >
            {isRead ? "leido" : "marcar como leido"}
            </Button>
        </Card.Body>
        </Card>
    );
}