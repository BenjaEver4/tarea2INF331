package cl.usm.biblioteca.model;

public class Libro {

    private final String isbn;
    private final String titulo;
    private final String autor;
    private final int anioPublicacion;
    private boolean disponible;

    public Libro(String isbn, String titulo, String autor, int anioPublicacion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.disponible = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void marcarComoPrestado() {
        this.disponible = false;
    }

    public void marcarComoDisponible() {
        this.disponible = true;
    }
}