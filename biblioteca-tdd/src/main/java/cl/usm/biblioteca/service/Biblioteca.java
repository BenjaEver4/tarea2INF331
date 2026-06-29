package cl.usm.biblioteca.service;

import cl.usm.biblioteca.exception.LibroDuplicadoException;
import cl.usm.biblioteca.exception.LibroNoDisponibleException;
import cl.usm.biblioteca.exception.LibroNoEncontradoException;
import cl.usm.biblioteca.exception.LibroYaDisponibleException;
import cl.usm.biblioteca.model.Libro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Biblioteca {

    private final Map<String, Libro> libros = new HashMap<String, Libro>();

    public void registrarLibro(Libro libro) {
        validarLibroParaRegistro(libro);

        if (libros.containsKey(libro.getIsbn())) {
            throw new LibroDuplicadoException("Ya existe un libro con el ISBN indicado.");
        }

        libro.marcarComoDisponible();
        libros.put(libro.getIsbn(), libro);
    }

    public Optional<Libro> buscarPorIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(libros.get(isbn));
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String tituloBuscado = titulo.toLowerCase();
        List<Libro> resultados = new ArrayList<Libro>();

        for (Libro libro : libros.values()) {
            if (libro.getTitulo() != null && libro.getTitulo().toLowerCase().contains(tituloBuscado)) {
                resultados.add(libro);
            }
        }

        return resultados;
    }

    public List<Libro> listarDisponibles() {
        List<Libro> disponibles = new ArrayList<Libro>();

        for (Libro libro : libros.values()) {
            if (libro.isDisponible()) {
                disponibles.add(libro);
            }
        }

        return disponibles;
    }

    public void prestarLibro(String isbn) {
        Libro libro = obtenerLibroExistente(isbn);

        if (!libro.isDisponible()) {
            throw new LibroNoDisponibleException("El libro ya está prestado.");
        }

        libro.marcarComoPrestado();
    }

    public void devolverLibro(String isbn) {
        Libro libro = obtenerLibroExistente(isbn);

        if (libro.isDisponible()) {
            throw new LibroYaDisponibleException("El libro ya estaba disponible.");
        }

        libro.marcarComoDisponible();
    }

    private void validarLibroParaRegistro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo.");
        }

        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede ser nulo o vacío.");
        }

        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío.");
        }
    }

    private Libro obtenerLibroExistente(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new LibroNoEncontradoException("No existe un libro con el ISBN indicado.");
        }

        Libro libro = libros.get(isbn);

        if (libro == null) {
            throw new LibroNoEncontradoException("No existe un libro con el ISBN indicado.");
        }

        return libro;
    }
}