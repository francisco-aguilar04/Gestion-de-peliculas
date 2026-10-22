# \# Gestión de Películas - API REST con Spring Boot

# 

# Gestión de Películas es una API REST que permite gestionar un catálogo de películas, sus directores y el reparto de actores. Incluye validación de entradas, una anotación de validación personalizada, gestión de errores mediante `ProblemDetail` y documentación interactiva con OpenAPI 3 y Swagger UI.

# 

# Proyecto individual de \*\*Acceso a Datos\*\* y \*\*Programación de Servicios y Procesos\*\* (curso 2026-27).

# 

# \---

# 

# \# Índice

# 

# 1\. \[Funcionalidades del Sistema](#funcionalidades-del-sistema)

# 2\. \[Tecnologías Utilizadas](#tecnologías-utilizadas)

# 3\. \[Modelo de Datos](#modelo-de-datos)

# 4\. \[Estructura del Proyecto](#estructura-del-proyecto)

# 5\. \[Configuración y Persistencia](#configuración-y-persistencia)

# 6\. \[Puesta en Marcha](#puesta-en-marcha)

# &#x20;  - \[Requisitos Previos](#requisitos-previos)

# &#x20;  - \[Instrucciones de Ejecución](#instrucciones-de-ejecución)

# 7\. \[Documentación de la API (Swagger)](#documentación-de-la-api-swagger)

# 8\. \[Endpoints](#endpoints)

# 9\. \[Validaciones](#validaciones)

# 10\. \[Gestión de Errores](#gestión-de-errores)

# 11\. \[Probar la API](#probar-la-api)

# 

# \---

# 

# \## Funcionalidades del Sistema

# 

# \- \*\*Directores\*\*

# &#x20; - CRUD completo: crear, listar, consultar por id, actualizar y borrar.

# 

# \- \*\*Películas\*\*

# &#x20; - CRUD completo: crear, listar, consultar por id, actualizar y borrar.

# &#x20; - Al crear una película es obligatorio indicar el id de un director existente.

# &#x20; - El título es único: no se pueden crear dos películas con el mismo título.

# &#x20; - Un director debe ser mayor de edad para lanzar una película (validación personalizada).

# 

# \- \*\*Actores\*\*

# &#x20; - CRUD básico: crear, listar y consultar por id.

# 

# \- \*\*Reparto (relación Muchos a Muchos)\*\*

# &#x20; - Asignación de un actor a una película.

# &#x20; - Consulta de una película con su director y la lista completa de su reparto.

# &#x20; - Un mismo actor no puede asignarse dos veces a la misma película.

# 

# \---

# 

# \## Tecnologías Utilizadas

# 

# | Categoría | Tecnología |

# |------------|------------|

# | Lenguaje | Java 21 |

# | Framework Back-End | Spring Boot 4.1.1 |

# | Persistencia / ORM | Spring Data JPA + Hibernate |

# | Base de Datos | H2 (modo archivo) |

# | Validación | Jakarta Bean Validation (`spring-boot-starter-validation`) |

# | Documentación | springdoc-openapi 3.1.1 (OpenAPI 3 + Swagger UI) |

# | Utilidades | Lombok |

# | Pruebas de la API | Postman y archivo `.http` |

# | Gestor de Dependencias | Maven (Wrapper incluido) |

# 

# \---

# 

# \## Modelo de Datos

# 

# El modelo está formado por tres entidades. Un director dirige muchas películas (relación 1:N) y una película tiene muchos actores, igual que un actor participa en muchas películas (relación M:M).

# 

# ```mermaid

# erDiagram

# &#x20;   DIRECTOR ||--o{ PELICULA : dirige

# &#x20;   PELICULA }o--o{ ACTOR : "tiene en su reparto"

# 

# &#x20;   DIRECTOR {

# &#x20;       Long id PK

# &#x20;       String name

# &#x20;       Integer anioNacimiento

# &#x20;   }

# &#x20;   PELICULA {

# &#x20;       Long id PK

# &#x20;       String titulo "único"

# &#x20;       String genero

# &#x20;       LocalDate fechaEstreno

# &#x20;       Long director\_id FK

# &#x20;   }

# &#x20;   ACTOR {

# &#x20;       Long id PK

# &#x20;       String name

# &#x20;   }

# ```

# 

# | Entidad | Campo | Tipo | Observaciones |

# |---------|-------|------|---------------|

# | \*\*Director\*\* | `id` | Long | Clave primaria autogenerada |

# | | `name` | String | Nombre del director |

# | | `anioNacimiento` | Integer | Año de nacimiento |

# | \*\*Pelicula\*\* | `id` | Long | Clave primaria autogenerada |

# | | `titulo` | String | Único |

# | | `genero` | String | |

# | | `fechaEstreno` | LocalDate | Formato `yyyy-MM-dd` |

# | | `director` | Director | Relación `@ManyToOne` |

# | | `actores` | Set\\<Actor\\> | Relación `@ManyToMany` |

# | \*\*Actor\*\* | `id` | Long | Clave primaria autogenerada |

# | | `name` | String | Nombre del actor o actriz |

# 

# \### DTOs

# 

# Las entidades no se exponen directamente. Cada recurso tiene un DTO de entrada (`Edit...Dto`) y otro de salida (`Get...Detail`). La respuesta de una película usa \*\*DTOs anidados\*\*: contiene un `GetDirectorSimple` y una lista de `GetActorDetail`.

# 

# \---

# 

# \## Estructura del Proyecto

# 

# El proyecto Maven está dentro de la carpeta `Gestion de peliculas`:

# 

# ```text

# Gestion de peliculas/

# ├── pom.xml

# ├── mvnw

# ├── mvnw.cmd

# └── src/

# &#x20;   └── main/

# &#x20;       ├── java/com/salesianos/dam/gestiondepeliculas/

# &#x20;       │   ├── controller/        #

