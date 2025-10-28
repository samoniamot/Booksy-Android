import { Navbar, Container, Nav, Badge } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useCart } from '../context/AppContext';

function NavBar(){
    const { cartCount } = useCart();
    return (
        <Navbar bg="light" expand="md" className="mb-3 border-bottom">
        <Container>
            {/* logo o nombre de la app */}
            <Navbar.Brand as={Link} to="/">Booksy</Navbar.Brand>
            <Navbar.Toggle />
            <Navbar.Collapse>
            <Nav className="me-auto">
                <Nav.Link as={Link} to="/">inicio</Nav.Link>
                <Nav.Link as={Link} to="/libros">libros</Nav.Link>
                <Nav.Link as={Link} to="/contacto">contacto</Nav.Link>
            </Nav>
            <Nav>
                {/* esto es el carrito, despues lo hacemos bien */}
                <Nav.Link as={Link} to="/carrito">
                🛒 <Badge bg="primary" pill>{cartCount}</Badge>
                </Nav.Link>
            </Nav>
            </Navbar.Collapse>
        </Container>
        </Navbar>
    );
}

export default NavBar;