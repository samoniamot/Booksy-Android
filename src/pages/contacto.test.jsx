import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Contact from './Contact';

describe('contact component', () => {

    test('el componente se monta bien', () => {
        render(<Contact />);

        const titulo = screen.getByRole('heading', { name: /contacto/i });
        expect(titulo).toBeInTheDocument();

        expect(screen.getByLabelText(/nombre/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/correo/i)).toBeInTheDocument();
        expect(screen.getByLabelText(/mensaje/i)).toBeInTheDocument();

        expect(screen.getByRole('button', { name: /enviar/i })).toBeInTheDocument();
    });

    test('muestra errores si el formulario se envia vacio', async () => {
        render(<Contact />);

        const enviarBtn = screen.getByRole('button', { name: /enviar/i });
        await userEvent.click(enviarBtn);

        const alertBox = screen.getByRole('alert');
        expect(alertBox).toHaveTextContent(/el nombre es obligatorio/i);
        // ==== AQUI ESTA LA CORRECCION ====
        // le agregamos la tilde a 'valido'
        expect(alertBox).toHaveTextContent(/el correo es obligatorio y debe ser válido/i);
        expect(alertBox).toHaveTextContent(/el mensaje es obligatorio/i);

        expect(screen.queryByText(/mensaje enviado/i)).not.toBeInTheDocument();
    });

    test('envio valido muestra mensaje de exito', async () => {
        render(<Contact />);

        await userEvent.type(screen.getByLabelText(/nombre/i), 'alan turing');
        await userEvent.type(screen.getByLabelText(/correo/i), 'alan@example.com');
        await userEvent.type(screen.getByLabelText(/mensaje/i), 'hola mundo');

        await userEvent.click(screen.getByRole('button', { name: /enviar/i }));

        expect(screen.getByText(/mensaje enviado/i)).toBeInTheDocument();
    });
});