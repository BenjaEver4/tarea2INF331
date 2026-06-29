# Sistema de Biblioteca

Aplicación Java orientada a objetos para gestionar una biblioteca en memoria. El sistema permite registrar libros, buscarlos por ISBN o título, listar los disponibles, prestar libros y devolverlos.

El proyecto está preparado para trabajo con Maven y pruebas automatizadas con JUnit 5. La persistencia es completamente en memoria usando colecciones Java, sin base de datos ni frameworks adicionales.

## UML sencillo

```mermaid
classDiagram
    class App {
        +main(String[] args)
    }

    class Libro {
        -String isbn
        -String titulo
        -String autor
        -int anioPublicacion
        -boolean disponible
        +Libro(String isbn, String titulo, String autor, int anioPublicacion)
        +getIsbn() String
        +getTitulo() String
        +getAutor() String
        +getAnioPublicacion() int
        +isDisponible() boolean
        +marcarComoPrestado() void
        +marcarComoDisponible() void
    }

    class Biblioteca {
        -Map~String, Libro~ libros
        +registrarLibro(Libro libro) void
        +buscarPorIsbn(String isbn) Optional~Libro~
        +buscarPorTitulo(String titulo) List~Libro~
        +listarDisponibles() List~Libro~
        +prestarLibro(String isbn) void
        +devolverLibro(String isbn) void
    }

    class LibroNoEncontradoException
    class LibroNoDisponibleException
    class LibroYaDisponibleException
    class LibroDuplicadoException

    Biblioteca --> Libro
    Biblioteca --> LibroNoEncontradoException
    Biblioteca --> LibroNoDisponibleException
    Biblioteca --> LibroYaDisponibleException
    Biblioteca --> LibroDuplicadoException
```

## Cómo ejecutar

### Windows

Desde la carpeta raíz del repositorio:

```
cd .\biblioteca-tdd\
.\mvnw.cmd test
```

### Linux / Mac

```bash
./mvnw test
```

> En Windows, el comando debe ejecutarse dentro de la carpeta `biblioteca-tdd`.

## Evidencia de ejecución

La suite de pruebas se ejecutó correctamente con Maven Wrapper en Windows y terminó con resultado exitoso.

```text
Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Esta salida confirma que el proyecto compila y que la suite JUnit 5 pasa completa.

## Tecnologías

- Java 21
- Maven
- JUnit 5
- Maven Wrapper
- Colecciones Java en memoria

## TDD

El proyecto sigue el enfoque TDD:

1. Red: escribir una prueba que falle.
2. Green: implementar lo mínimo para que pase.
3. Refactor: mejorar el diseño sin romper el comportamiento.

En este caso, el dominio de biblioteca está separado en modelo, servicio y excepciones para facilitar pruebas unitarias claras sobre cada regla de negocio. La suite fue diseñada para validar primero el comportamiento observable del servicio y luego comprobar el estado del modelo tras cada operación.

## Casos de prueba

La suite incluye 22 pruebas unitarias:

1. `registrarLibroValidoDebeGuardarlo`
2. `registrarLibroValidoDebeQuedarDisponible`
3. `registrarLibroNullDebeLanzarIllegalArgumentException`
4. `registrarLibroConIsbnNullDebeLanzarIllegalArgumentException`
5. `registrarLibroConIsbnVacioDebeLanzarIllegalArgumentException`
6. `registrarLibroConTituloNullDebeLanzarIllegalArgumentException`
7. `registrarLibroConTituloVacioDebeLanzarIllegalArgumentException`
8. `registrarLibroDuplicadoDebeLanzarLibroDuplicadoException`
9. `buscarPorIsbnExistenteDebeRetornarLibro`
10. `buscarPorIsbnInexistenteDebeRetornarOptionalVacio`
11. `buscarPorTituloConCoincidenciaParcialDebeRetornarLibros`
12. `buscarPorTituloDebeIgnorarMayusculasYMinusculas`
13. `buscarPorTituloInexistenteDebeRetornarListaVacia`
14. `listarDisponiblesDebeRetornarSoloLibrosDisponibles`
15. `prestarLibroDisponibleDebeMarcarloComoNoDisponible`
16. `prestarLibroInexistenteDebeLanzarLibroNoEncontradoException`
17. `prestarLibroYaPrestadoDebeLanzarLibroNoDisponibleException`
18. `prestarLibroConIsbnNullDebeLanzarLibroNoEncontradoException`
19. `prestarLibroConIsbnVacioDebeLanzarLibroNoEncontradoException`
20. `devolverLibroPrestadoDebeMarcarloComoDisponible`
21. `devolverLibroInexistenteDebeLanzarLibroNoEncontradoException`
22. `devolverLibroYaDisponibleDebeLanzarLibroYaDisponibleException`

Se midió cobertura funcional de reglas de negocio, ya que el objetivo principal de la tarea es verificar mediante JUnit que cada operación del sistema cumpla su comportamiento esperado y sus casos de error.

## Cobertura

La cobertura funcional actual se concentra en el comportamiento del servicio de biblioteca:

- registro correcto e incorrecto de libros
- búsqueda por ISBN
- búsqueda parcial e insensible a mayúsculas/minúsculas por título
- listado de libros disponibles
- préstamo y devolución de libros
- excepciones de dominio asociadas a cada flujo

La suite automatizada verifica el estado del sistema después de cada operación y valida que las reglas de negocio se cumplan sin usar base de datos ni mocks.

## Estructura del proyecto

```text
biblioteca-tdd/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   └── java/
│   │       └── cl/usm/biblioteca/
│   │           ├── App.java
│   │           ├── exception/
│   │           │   ├── LibroDuplicadoException.java
│   │           │   ├── LibroNoDisponibleException.java
│   │           │   ├── LibroNoEncontradoException.java
│   │           │   └── LibroYaDisponibleException.java
│   │           ├── model/
│   │           │   └── Libro.java
│   │           └── service/
│   │               └── Biblioteca.java
│   └── test/
│       └── java/
│           └── cl/usm/biblioteca/
│               └── service/
│                   └── BibliotecaTest.java
└── target/
```

## Notas del diseño

- `Libro` modela el estado del libro y su disponibilidad.
- `Biblioteca` concentra la lógica de negocio y la persistencia en memoria.
- Las excepciones personalizadas representan errores de dominio específicos.
- `Optional` se usa para búsquedas seguras por ISBN.
- `Map` se usa para acceso rápido por clave única.
