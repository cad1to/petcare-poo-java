# PetCare – Sistema de clínica veterinaria

Aplicación de consola en Java para la clínica veterinaria **PetCare**. Resuelve el
trabajo diario de la clínica: registrar dueños y mascotas (perros, gatos y aves),
registrar veterinarios, agendar citas sin choques de horario, atender o cancelar
citas, generar comprobantes de cobro y consultar el historial clínico de cada mascota.
Los datos viven en memoria mientras el programa corre.

## Contenido

1. [Tecnologías](#tecnologías)
2. [Modelo de la solución](#modelo-de-la-solución)
3. [Estructura del proyecto](#estructura-del-proyecto)
4. [Conceptos aplicados](#conceptos-aplicados)
5. [Instalación y ejecución](#instalación-y-ejecución)
6. [Ejemplo de uso](#ejemplo-de-uso)
7. [Reglas de negocio](#reglas-de-negocio)
8. [Reflexión](#reflexión)
9. [Autor](#autor)

## Tecnologías

- **Java 26** (el proyecto requiere Java 17 o superior)
- **Mermaid** para el diagrama de clases
- **IntelliJ IDEA 2026.2** como IDE
- **Git y GitHub** para el control de versiones

## Modelo de la solución

![Diagrama de clases de PetCare](docs/diagramas/diagramaclase_petcare.png)

El análisis de clases y objetos está en [docs/analisis.md](docs/analisis.md).

### Decisiones de diseño

**Herencia (dos jerarquías)**
- `Persona` (abstracta) es el padre de `Dueno`, `Veterinario` y `Recepcionista`.
  Nombre, apellido, teléfono y correo son comunes a todos, así que viven en el padre y
  cada hija agrega solo su dato propio (dirección, cédula y especialidad, turno).
  `describir()` es abstracto y cada hija lo implementa a su manera.
- `Mascota` (abstracta) es el padre de `Perro`, `Gato` y `Ave`. Nombre, edad, peso y
  dueño son comunes, y `hacerSonido()` es abstracto porque cada especie suena distinto.

**Interfaz**
- `Facturable` define `calcularCosto()`. La clase abstracta `Servicio` la implementa y sus
  tres hijas (`ConsultaGeneral`, `Vacunacion`, `Cirugia`) calculan el costo de forma
  distinta. Elegí una interfaz porque "poder cobrarse" es una capacidad, no una
  jerarquía, y así se puede sumar una `List<Facturable>` sin importar el tipo.

**Asociación**
- `Cita` referencia a `Mascota`, `Veterinario` y `Consultorio`. Los tres son objetos
  independientes: si la cita se cancela o se elimina, ellos siguen existiendo.
  Lo mismo pasa entre `Mascota` y `Dueno`.

**Agregación**
- `Clinica` agrupa `Veterinario`. Los veterinarios se crean fuera y la clínica los recibe
  ya hechos con `agregarVeterinario()`. Si la clínica cierra, el veterinario sigue
  existiendo y puede trabajar en otra.

**Composición**
- `Clinica` crea y posee sus `Consultorio` (se crean en su constructor y su constructor es
  de paquete, así que no existen fuera de ella). Si la clínica desaparece, los consultorios
  también.
- `Mascota` crea y posee su `HistorialClinico`. Si la mascota se da de baja, el historial
  desaparece con ella.

## Estructura del proyecto

```text
petcare-poo-java/
├── README.md
├── .gitignore
├── docs/
│   ├── analisis.md
│   ├── ejemplo-ejecucion.md
│   ├── diagramas/
│   └── capturas/
└── src/
    ├── app/
    │   └── Main.java
    └── modelo/
        ├── personas/
        ├── mascotas/
        ├── servicios/
        ├── clinica/
        └── facturacion/
```

| Paquete | Propósito |
| --- | --- |
| `app` | Punto de entrada y menú de consola, con validación de entradas. |
| `modelo.personas` | `Persona` y sus hijas: `Dueno`, `Veterinario`, `Recepcionista`. |
| `modelo.mascotas` | `Mascota`, sus hijas `Perro`, `Gato`, `Ave` y el `HistorialClinico`. |
| `modelo.servicios` | `Facturable`, `Servicio` y los servicios que se cobran. |
| `modelo.clinica` | `Clinica`, `Cita`, `Consultorio` y `EstadoCita`; aquí viven las reglas de agenda. |
| `modelo.facturacion` | `Comprobante`, con el folio automático. |

## Conceptos aplicados

Completa la columna de línea abriendo cada archivo en IntelliJ.

| Concepto | Archivo | Línea |
| --- | --- | --- |
| Clase abstracta | `Persona.java`, `Mascota.java`, `Servicio.java` | |
| Método abstracto | `Persona.describir()`, `Mascota.hacerSonido()` | |
| Herencia y `super(...)` | `Dueno.java`, `Veterinario.java`, `Perro.java`, `Gato.java`, `Ave.java` | |
| Interfaz | `Facturable.java` (implementada por `Servicio`) | |
| Polimorfismo con mascotas | `Main.listarMascotas()` | |
| Polimorfismo con servicios | `Clinica.sumarCostos()` | |
| Sobrecarga de métodos | `Clinica.buscarCitas(String)` y `buscarCitas(LocalDate)` | |
| Sobrecarga de constructores | `Clinica()` y `Clinica(String, int)` | |
| Sobreescritura (`@Override`) | `toString()`, `describir()`, `hacerSonido()`, `calcularCosto()` | |
| Asociación | `Cita.java` | |
| Agregación | `Clinica.agregarVeterinario()` | |
| Composición | `Clinica` (constructor, consultorios) y `Mascota` (historial) | |
| Miembro `static` | `Comprobante.comprobantesGenerados` | |
| Constantes | `ConsultaGeneral.TARIFA_BASE`, `Cirugia.TARIFA_HORA_QUIROFANO` | |
| Encapsulamiento y validación | setters de `Mascota`, `Persona` | |
| `protected` | `Persona.validarTexto()` | |
| Acceso de paquete | constructor de `Consultorio` y de `HistorialClinico` | |
| Eliminación de objetos y recolector de basura | `Clinica.darDeBajaMascota()` | |

### Niveles de acceso

- `private`: atributos de todas las clases, para que solo se modifiquen por sus setters validados.
- `protected`: `Persona.validarTexto()`, para que solo las clases hijas lo reutilicen.
- Acceso de paquete: constructores de `Consultorio` y `HistorialClinico`, para que solo
  `Clinica` y `Mascota` (sus contenedores) puedan crearlos.
- `public`: métodos que el resto del programa necesita, como `agendarCita()`.

## Instalación y ejecución

Requisitos: tener instalado **JDK 17 o superior** y **Git**.

```bash
git clone https://github.com/cad1to/petcare-poo-java.git
cd petcare-poo-java
javac -d out -sourcepath src src/app/Main.java
java -cp out app.Main
```

Si en Windows ves símbolos raros en lugar de acentos, ejecuta `chcp 65001` antes del
comando `java`.

Al iniciar, elige la opción **11** para cargar el escenario de demostración.

## Ejemplo de uso

El escenario completo, con todas sus salidas, está en
[docs/ejemplo-ejecucion.md](docs/ejemplo-ejecucion.md). Las capturas están en
[docs/capturas/](docs/capturas/).

Fragmento de la carga del escenario (opción 11):

```text
Dueños, mascotas y veterinarios registrados.
Citas agendadas:
Cita [PROGRAMADA] 2026-11-05T10:00 - Rocky con Marta en Consultorio 1
Cita [PROGRAMADA] 2026-11-05T11:00 - Misu con Jorge en Consultorio 2
Cita [PROGRAMADA] 2026-11-05T12:00 - Kiwi con Marta en Consultorio 3
Regla de negocio violada: El veterinario ya tiene una cita a esa hora
Atendidas las citas 1 (cirugía) y 2 (vacunación).
```

Polimorfismo (opción 7) y total cobrado (opción 8):

```text
Rocky dice: Guau
Misu dice: Miau
Kiwi dice: Pío

Marta Ruiz: $2750.0
Jorge Salas: $350.0
TOTAL COBRADO: $3100.0
```

## Reglas de negocio

| Regla | Dónde se valida |
| --- | --- |
| Peso y edad mayores que cero | `Mascota.setPeso()` y `Mascota.setEdad()` (`IllegalArgumentException`) |
| Un veterinario no tiene dos citas programadas a la misma hora | `Clinica.validarDisponibilidad()` |
| Una cita cancelada no puede marcarse como atendida | `Cita.atender()` (`IllegalStateException`) |
| Un consultorio no se usa en dos citas al mismo tiempo | `Clinica.validarDisponibilidad()` |
| Los costos nunca son negativos | `Servicio.validarMonto()` y el constructor de `Cirugia` |
| Cobros consultables por mascota y por veterinario | `Clinica.totalCobradoPorMascota()` y `totalCobradoPorVeterinario()` |

Las entradas del menú (texto donde va un número, datos vacíos, fechas mal escritas) se
validan en `Main` con `leerTexto()`, `leerEntero()`, `leerDecimal()` y `leerFechaHora()`,
que repiten la pregunta sin cerrar el programa. Los errores de reglas de negocio se
atrapan en un solo `try/catch` dentro del ciclo del menú.

## Reflexión

> **Esta sección la escribes tú.** Responde con tus palabras y con honestidad:
>
> - ¿Qué aprendiste? (por ejemplo, la diferencia entre agregación y composición, o por qué
    >   `Mascota` es abstracta)
> - ¿Qué fue lo más difícil?
> - ¿Qué mejorarías? (por ejemplo, guardar datos en archivos o evitar comprobantes duplicados)
> - ¿Cómo usaste la IA? Indica para qué la usaste, qué partes te explicó y qué entendiste de ellas.

## Autor

**Diego Torres Cartas** – Licenciatura en Ingeniería de Software, Universidad Veracruzana.
GitHub: [cad1to](https://github.com/cad1to)