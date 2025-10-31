import { ButtonGroup, Button, Badge } from 'react-bootstrap';

export default function Filtros({ current, onChange, options, total }) {
    const allActive = current === 'all';
    return (
        <div className="d-flex align-items-center justify-content-between mb-3">
        <ButtonGroup>
            {/* el boton de 'todos' */}
            <Button
            variant={allActive ? 'primary' : 'outline-primary'}
            onClick={() => onChange('all')}
            >
            todos <Badge bg={allActive ? 'light' : 'primary'} className="ms-1">{total}</Badge>
            </Button>
            
            {/* las otras categorias */}
            {options.map(opt => {
            const isActive = current === opt;
            return (
                <Button
                key={opt}
                variant={isActive ? 'primary' : 'outline-primary'}
                onClick={() => onChange(opt)}
                >
                {opt}
                </Button>
            );
            })}
        </ButtonGroup>
        </div>
    );
}