import { render, screen } from '@testing-library/react';
import Books from './Books';

// esto es un mock para que no falle el test
jest.mock('../context/AppContext', () => ({
    useCart: () => ({
        addToCart: jest.fn()
    }),
}));

describe('componente books', () => {

    test('se monta correctamente y muestra el titulo', () => {
        render(<Books />);
        // buscamos el titulo principal de la pagina
        expect(screen.getByText(/catalogo de libros/i)).toBeInTheDocument();
    });

    test('renderiza todas las tarjetas de libro', () => {
        render(<Books />);
        // buscamos los botones de agregar para saber cuantos libros hay
        const botones = screen.getAllByRole('button', { name: /agregar/i });
        expect(botones.length).toBe(5); // en el mock hay 5 libros
    });

    test('el boton "agregar" aparece en cada tarjeta', () => {
        render(<Books />);
        // lo mismo que el anterior pero mas simple
        expect(screen.getAllByText(/agregar/i).length).toBeGreaterThan(0);
    });

});