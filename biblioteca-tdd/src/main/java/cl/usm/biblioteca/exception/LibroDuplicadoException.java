package cl.usm.biblioteca.exception;

public class LibroDuplicadoException extends RuntimeException {

    public LibroDuplicadoException(String mensaje) {
        super(mensaje);
    }
}