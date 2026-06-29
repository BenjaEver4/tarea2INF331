package cl.usm.biblioteca.exception;

public class LibroNoEncontradoException extends RuntimeException {

    public LibroNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}