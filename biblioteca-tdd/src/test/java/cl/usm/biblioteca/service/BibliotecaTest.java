package cl.usm.biblioteca.service;

import cl.usm.biblioteca.exception.LibroDuplicadoException;
import cl.usm.biblioteca.exception.LibroNoDisponibleException;
import cl.usm.biblioteca.exception.LibroNoEncontradoException;
import cl.usm.biblioteca.exception.LibroYaDisponibleException;
import cl.usm.biblioteca.model.Libro;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BibliotecaTest {

    private Biblioteca biblioteca;

    @BeforeEach
    void setUp() {
        biblioteca = new Biblioteca();
    }

    @Test
    void registrarLibroValidoDebeGuardarlo() {
        Libro libro = libro("978-1", "Clean Code", "Robert C. Martin", 2008);

        biblioteca.registrarLibro(libro);

        Optional<Libro> encontrado = biblioteca.buscarPorIsbn("978-1");
        assertTrue(encontrado.isPresent());
        assertEquals("Clean Code", encontrado.get().getTitulo());
    }

    @Test
    void registrarLibroValidoDebeQuedarDisponible() {
        Libro libro = libro("978-2", "Effective Java", "Joshua Bloch", 2018);

        biblioteca.registrarLibro(libro);

        assertTrue(biblioteca.buscarPorIsbn("978-2").isPresent());
        assertTrue(biblioteca.buscarPorIsbn("978-2").get().isDisponible());
    }

    @Test
    void registrarLibroNullDebeLanzarIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> biblioteca.registrarLibro(null));
    }

    @Test
    void registrarLibroConIsbnNullDebeLanzarIllegalArgumentException() {
        Libro libro = libro(null, "Clean Code", "Robert C. Martin", 2008);

        assertThrows(IllegalArgumentException.class, () -> biblioteca.registrarLibro(libro));
    }

    @Test
    void registrarLibroConIsbnVacioDebeLanzarIllegalArgumentException() {
        Libro libro = libro("", "Clean Code", "Robert C. Martin", 2008);

        assertThrows(IllegalArgumentException.class, () -> biblioteca.registrarLibro(libro));
    }

    @Test
    void registrarLibroConTituloNullDebeLanzarIllegalArgumentException() {
        Libro libro = libro("978-3", null, "Robert C. Martin", 2008);

        assertThrows(IllegalArgumentException.class, () -> biblioteca.registrarLibro(libro));
    }

    @Test
    void registrarLibroConTituloVacioDebeLanzarIllegalArgumentException() {
        Libro libro = libro("978-4", "", "Robert C. Martin", 2008);

        assertThrows(IllegalArgumentException.class, () -> biblioteca.registrarLibro(libro));
    }

    @Test
    void registrarLibroDuplicadoDebeLanzarLibroDuplicadoException() {
        Libro libro = libro("978-5", "Clean Architecture", "Robert C. Martin", 2017);
        biblioteca.registrarLibro(libro);

        Libro duplicado = libro("978-5", "Clean Architecture", "Robert C. Martin", 2017);

        assertThrows(LibroDuplicadoException.class, () -> biblioteca.registrarLibro(duplicado));
    }

    @Test
    void buscarPorIsbnExistenteDebeRetornarLibro() {
        biblioteca.registrarLibro(libro("978-6", "Refactoring", "Martin Fowler", 2018));

        Optional<Libro> encontrado = biblioteca.buscarPorIsbn("978-6");

        assertTrue(encontrado.isPresent());
        assertEquals("Refactoring", encontrado.get().getTitulo());
    }

    @Test
    void buscarPorIsbnInexistenteDebeRetornarOptionalVacio() {
        Optional<Libro> encontrado = biblioteca.buscarPorIsbn("no-existe");

        assertFalse(encontrado.isPresent());
    }

    @Test
    void buscarPorTituloConCoincidenciaParcialDebeRetornarLibros() {
        biblioteca.registrarLibro(libro("978-7", "Clean Code", "Robert C. Martin", 2008));
        biblioteca.registrarLibro(libro("978-8", "Code Complete", "Steve McConnell", 2004));
        biblioteca.registrarLibro(libro("978-9", "Domain-Driven Design", "Eric Evans", 2003));

        List<Libro> encontrados = biblioteca.buscarPorTitulo("Code");

        assertEquals(2, encontrados.size());
    }

    @Test
    void buscarPorTituloDebeIgnorarMayusculasYMinusculas() {
        biblioteca.registrarLibro(libro("978-10", "Clean Code", "Robert C. Martin", 2008));

        List<Libro> encontrados = biblioteca.buscarPorTitulo("clean code");

        assertEquals(1, encontrados.size());
        assertEquals("Clean Code", encontrados.get(0).getTitulo());
    }

    @Test
    void buscarPorTituloInexistenteDebeRetornarListaVacia() {
        biblioteca.registrarLibro(libro("978-11", "Refactoring", "Martin Fowler", 2018));

        List<Libro> encontrados = biblioteca.buscarPorTitulo("inexistente");

        assertTrue(encontrados.isEmpty());
    }

    @Test
    void listarDisponiblesDebeRetornarSoloLibrosDisponibles() {
        biblioteca.registrarLibro(libro("978-12", "Libro 1", "Autor 1", 2020));
        biblioteca.registrarLibro(libro("978-13", "Libro 2", "Autor 2", 2021));
        biblioteca.prestarLibro("978-13");

        List<Libro> disponibles = biblioteca.listarDisponibles();

        assertEquals(1, disponibles.size());
        assertEquals("978-12", disponibles.get(0).getIsbn());
    }

    @Test
    void prestarLibroDisponibleDebeMarcarloComoNoDisponible() {
        biblioteca.registrarLibro(libro("978-14", "Libro prestable", "Autor", 2022));

        biblioteca.prestarLibro("978-14");

        assertFalse(biblioteca.buscarPorIsbn("978-14").get().isDisponible());
    }

    @Test
    void prestarLibroInexistenteDebeLanzarLibroNoEncontradoException() {
        assertThrows(LibroNoEncontradoException.class, () -> biblioteca.prestarLibro("999-99"));
    }

    @Test
    void prestarLibroYaPrestadoDebeLanzarLibroNoDisponibleException() {
        biblioteca.registrarLibro(libro("978-15", "Libro prestado", "Autor", 2022));
        biblioteca.prestarLibro("978-15");

        assertThrows(LibroNoDisponibleException.class, () -> biblioteca.prestarLibro("978-15"));
    }

    @Test
    void prestarLibroConIsbnNullDebeLanzarLibroNoEncontradoException() {
        assertThrows(LibroNoEncontradoException.class, () -> biblioteca.prestarLibro(null));
    }

    @Test
    void prestarLibroConIsbnVacioDebeLanzarLibroNoEncontradoException() {
        assertThrows(LibroNoEncontradoException.class, () -> biblioteca.prestarLibro(""));
    }

    @Test
    void devolverLibroPrestadoDebeMarcarloComoDisponible() {
        biblioteca.registrarLibro(libro("978-16", "Libro devuelto", "Autor", 2022));
        biblioteca.prestarLibro("978-16");

        biblioteca.devolverLibro("978-16");

        assertTrue(biblioteca.buscarPorIsbn("978-16").get().isDisponible());
    }

    @Test
    void devolverLibroInexistenteDebeLanzarLibroNoEncontradoException() {
        assertThrows(LibroNoEncontradoException.class, () -> biblioteca.devolverLibro("888-88"));
    }

    @Test
    void devolverLibroYaDisponibleDebeLanzarLibroYaDisponibleException() {
        biblioteca.registrarLibro(libro("978-17", "Libro disponible", "Autor", 2022));

        assertThrows(LibroYaDisponibleException.class, () -> biblioteca.devolverLibro("978-17"));
    }

    private Libro libro(String isbn, String titulo, String autor, int anioPublicacion) {
        return new Libro(isbn, titulo, autor, anioPublicacion);
    }
}