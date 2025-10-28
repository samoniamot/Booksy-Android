import { Card, Button, Badge } from 'react-bootstrap';

export default function BookCard({ book, onAdd }) {
    const { title, author, price, category, imageUrl } = book;

    return (
        <Card className="h-100 shadow-sm">
        {imageUrl && (
            <Card.Img
            variant="top"
            src={imageUrl}
            alt={`imagen de ${title}`}
            style={{ objectFit: 'cover', height: 180 }}
            />
        )}
        <Card.Body className="d-flex flex-column">
            <div className="d-flex justify-content-between align-items-start mb-2">
            <Card.Title className="mb-0" style={{ fontSize: '1rem' }}>
                {title}
            </Card.Title>
            {category && <Badge bg="secondary">{category}</Badge>}
            </div>

            <Card.Text className="text-muted mb-3">
            {author}
            </Card.Text>

            <Card.Text className="text-muted mb-3">
            ${Number(price).toLocaleString('es-cl')}
            </Card.Text>

            <Button
            variant="primary"
            onClick={onAdd}
            className="mt-auto"
            aria-label={`agregar ${title} al carrito`}
            >
            agregar
            </Button>
        </Card.Body>
        </Card>
    );
}