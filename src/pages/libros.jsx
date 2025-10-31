import { useMemo, useState, useEffect } from 'react';
import { Container, Button, Form, Row, Col } from 'react-bootstrap';
import { motion } from 'framer-motion';
import axios from 'axios';
import { usarCarrito, usarAutenticacion } from '../context/contextoAplicacion';
import { LIBROS, CATEGORIAS } from '../datos/libros.mock';
// nos aseguramos que estas rutas esten perfectas
import Filtros from '../components/books/filtros';
import CuadriculaLibros from '../components/books/cuadriculaLibros';

export default function Libros() {
    const { agregarAlCarrito } = usarCarrito();
    const { token } = usarAutenticacion();
    const [filter, setFilter] = useState('all');
    const [readBooks, setReadBooks] = useState(JSON.parse(localStorage.getItem('readBooks') || '[]'));
    const [userBooks, setUserBooks] = useState(JSON.parse(localStorage.getItem('userBooks') || '[]'));
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showAddForm, setShowAddForm] = useState(false);
    const [newBook, setNewBook] = useState({ titulo: '', autor: '', categoria: '', precio: '', imagen: '' });
    const [editingBook, setEditingBook] = useState(null);
    console.log('pagina de libros cargada');

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                setLoading(true);
                const config = token ? { headers: { Authorization: `Bearer ${token}` } } : {};
                const response = await axios.get('https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/books', config);
                setBooks(response.data);
            } catch (err) {
                setError('Error al cargar libros');
                console.error(err);
                // fallback to mock
                setBooks(LIBROS);
            } finally {
                setLoading(false);
            }
        };
        fetchBooks();
    }, [token]);

    const list = useMemo(() => {
        const allBooks = [...books, ...userBooks];
        return filter === 'all'
        ? allBooks
        : allBooks.filter(p => p.categoria === filter);
    }, [filter, books, userBooks]);

    const markAsRead = (id) => {
        const newRead = [...readBooks, id];
        setReadBooks(newRead);
        localStorage.setItem('readBooks', JSON.stringify(newRead));
    };

    const isRead = (id) => readBooks.includes(id);

    const addUserBook = (book) => {
        const newBooks = [...userBooks, { ...book, id: Date.now() }];
        setUserBooks(newBooks);
        localStorage.setItem('userBooks', JSON.stringify(newBooks));
    };

    const editUserBook = (id, book) => {
        setEditingBook(book);
        setNewBook(book);
        setShowAddForm(true);
    };

    const saveEditUserBook = (id, updatedBook) => {
        const newBooks = userBooks.map(b => b.id === id ? { ...b, ...updatedBook } : b);
        setUserBooks(newBooks);
        localStorage.setItem('userBooks', JSON.stringify(newBooks));
    };

    const deleteUserBook = (id) => {
        const newBooks = userBooks.filter(b => b.id !== id);
        setUserBooks(newBooks);
        localStorage.setItem('userBooks', JSON.stringify(newBooks));
    };

    return (
        <motion.main
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.5 }}
        >
        <Container>
            <h2 className="mb-2">catalogo de libros</h2>
            <p className="text-muted mb-3">explora nuestro catalogo de libros</p>

            <Button onClick={() => { setShowAddForm(!showAddForm); if (!showAddForm) setEditingBook(null); }} className="mb-3">{editingBook ? 'Editar Libro' : 'Agregar Libro Personal'}</Button>

            {showAddForm && (
                <Form onSubmit={(e) => { e.preventDefault(); if (editingBook) { saveEditUserBook(editingBook.id, newBook); setEditingBook(null); } else { addUserBook(newBook); } setNewBook({ titulo: '', autor: '', categoria: '', precio: '', imagen: '' }); setShowAddForm(false); }} className="mb-3">
                    <Row>
                        <Col><Form.Control placeholder="Título" value={newBook.titulo} onChange={(e) => setNewBook({...newBook, titulo: e.target.value})} required /></Col>
                        <Col><Form.Control placeholder="Autor" value={newBook.autor} onChange={(e) => setNewBook({...newBook, autor: e.target.value})} required /></Col>
                        <Col><Form.Select value={newBook.categoria} onChange={(e) => setNewBook({...newBook, categoria: e.target.value})} required>
                            <option value="">Categoría</option>
                            {CATEGORIAS.map(c => <option key={c} value={c}>{c}</option>)}
                        </Form.Select></Col>
                        <Col><Form.Control type="number" placeholder="Precio" value={newBook.precio} onChange={(e) => setNewBook({...newBook, precio: parseFloat(e.target.value)})} required /></Col>
                        <Col><Form.Control placeholder="Imagen URL" value={newBook.imagen} onChange={(e) => setNewBook({...newBook, imagen: e.target.value})} /></Col>
                        <Col><Button type="submit">Agregar</Button></Col>
                    </Row>
                </Form>
            )}

            {loading && <p>Cargando libros...</p>}
            {error && <p className="text-danger">{error}</p>}

            <Filtros
            current={filter}
            onChange={setFilter}
            options={CATEGORIAS}
            total={books.length}
            />

            <CuadriculaLibros items={list} onAdd={agregarAlCarrito} onMarkRead={markAsRead} isRead={isRead} onEdit={editUserBook} onDelete={deleteUserBook} />
        </Container>
        </motion.main>
    );
}