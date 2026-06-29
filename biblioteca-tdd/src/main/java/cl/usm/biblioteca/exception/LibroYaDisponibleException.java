package cl.usm.biblioteca.exception;

public class LibroYaDisponibleException extends RuntimeException {

    public LibroYaDisponibleException(String mensaje) {
        super(mensaje);
    }
}