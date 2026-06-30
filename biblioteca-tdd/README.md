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


Durante la ejecución se validaron los siguientes casos:

| Nº | Caso de prueba | Resultado |
|----|----------------|-----------|
| 1 | Registrar libro válido | ✅ Exitoso |
| 2 | Verificar disponibilidad inicial | ✅ Exitoso |
| 3 | Registrar libro nulo | ✅ Exitoso |
| 4 | Registrar libro con ISBN nulo | ✅ Exitoso |
| 5 | Registrar libro con ISBN vacío | ✅ Exitoso |
| 6 | Registrar libro con título nulo | ✅ Exitoso |
| 7 | Registrar libro con título vacío | ✅ Exitoso |
| 8 | Registrar libro duplicado | ✅ Exitoso |
| 9 | Buscar libro por ISBN existente | ✅ Exitoso |
| 10 | Buscar libro por ISBN inexistente | ✅ Exitoso |
| 11 | Buscar por coincidencia parcial de título | ✅ Exitoso |
| 12 | Buscar ignorando mayúsculas y minúsculas | ✅ Exitoso |
| 13 | Buscar título inexistente | ✅ Exitoso |
| 14 | Listar únicamente libros disponibles | ✅ Exitoso |
| 15 | Prestar libro disponible | ✅ Exitoso |
| 16 | Prestar libro inexistente | ✅ Exitoso |
| 17 | Prestar libro ya prestado | ✅ Exitoso |
| 18 | Prestar libro con ISBN nulo | ✅ Exitoso |
| 19 | Prestar libro con ISBN vacío | ✅ Exitoso |
| 20 | Devolver libro prestado | ✅ Exitoso |
| 21 | Devolver libro inexistente | ✅ Exitoso |
| 22 | Devolver libro ya disponible | ✅ Exitoso |

La ejecución finalizó correctamente:


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


## Reflexión sobre el uso de TDD

### Pregunta 1: ¿Cómo apliqué el ciclo Red → Green → Refactor?

Para aplicar TDD, primero definí los comportamientos esperados del sistema mediante pruebas unitarias. Por ejemplo, antes de validar completamente el registro de libros, se planteó el caso `registrarLibroDuplicadoDebeLanzarLibroDuplicadoException`.

En la etapa Red, la prueba representa un escenario que inicialmente no está cubierto. Luego, en Green, se implementa la lógica mínima en `Biblioteca` para detectar un ISBN repetido y lanzar la excepción correspondiente. Finalmente, en Refactor, se ordena la validación dentro del método privado `validarLibroParaRegistro`, manteniendo todas las pruebas exitosas.

Este mismo criterio se aplicó a las funcionalidades de búsqueda, préstamo y devolución.

### Pregunta 2: Ventajas y desventajas observadas al usar TDD

Una ventaja importante de TDD fue que permitió transformar cada regla del enunciado en un caso verificable. Por ejemplo, las reglas de no prestar un libro inexistente o no devolver un libro ya disponible quedaron respaldadas por pruebas específicas.

También ayudó a detectar errores temprano y a dar más confianza al modificar el código, ya que después de cada cambio se podía ejecutar la suite completa y comprobar que el comportamiento seguía correcto.

Como desventaja, TDD puede hacer que el desarrollo inicial sea más lento, porque antes de implementar la funcionalidad hay que pensar bien qué se espera probar. Además, al comienzo puede ser difícil decidir qué casos son realmente unitarios y cuáles son casos de integración.

### Pregunta 3: ¿Volvería a usar TDD?

Sí, volvería a usar TDD, especialmente en sistemas con reglas de negocio claras como este. El uso de pruebas permitió mejorar la calidad del software, porque cada funcionalidad importante quedó validada automáticamente.

Respecto al diseño, TDD ayudó a separar mejor las responsabilidades: `Libro` quedó como modelo de datos y estado, mientras que `Biblioteca` concentra la lógica de registro, búsqueda, préstamo y devolución.

También permitió detectar errores de forma temprana, por ejemplo en validaciones de ISBN, título, duplicados y disponibilidad. Aunque al principio puede tomar más tiempo escribir las pruebas, después entrega mayor confianza para refactorizar o agregar nuevas funcionalidades sin romper lo ya implementado.