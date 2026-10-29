# Análisis de clases y objetos: PetCare

## 1. Tabla de clases

|#| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
|---|---|---|---|---|---|
| 1 | `Persona` | Abstracta | nombre, apellido, telefono, correo | `getNombreCompleto()`, `describir()` (abstracto), `toString()` | Dueños, veterinarios y recepcionistas comparten los mismos datos de contacto. Se concentran aquí para no repetirlos. No se crea una "persona" suelta: siempre es alguno de los tres tipos. |
| 2 | `Dueno` | Concreta | dirección, (hereda los de Persona) | `describir()`, `toString()` | Es una persona con un dato propio (dirección) y es quien posee mascotas. Sí se crea directamente. |
| 3 | `Veterinario` | Concreta | cedulaProfesional, especialidad | `describir()` | Persona con cédula y especialidad. Atiende citas y se le consulta su total cobrado. Existe aunque la clínica cierre. |
| 4 | `Recepcionista` | Concreta | turno | `describir()` | Persona con turno de trabajo. Tercera clase de la jerarquía de personas. |
| 5 | `Mascota` | Abstracta | nombre, edad, peso, dueno, historial | `hacerSonido()` (abstracto), `setEdad(int)` y `setPeso(double)` (lanzan IllegalArgumentException si el valor es 0 o negativo), getters, `toString()` | Perros, gatos y aves comparten datos (nombre, edad, peso) y la obligación de "hacer sonido". No llega "una mascota" genérica: llega un perro, un gato o un ave. |
| 6 | `Perro` | Concreta | raza, nivelEnergia | `hacerSonido()` → "Guau" | Tipo de mascota con datos propios. Se crea directamente. |
| 7 | `Gato` | Concreta | esDomestico | `hacerSonido()` → "Miau" | Tipo de mascota con dato propio (si es doméstico). |
| 8 | `Ave` | Concreta | longitudAlas | `hacerSonido()` → "Pío" | Tipo de mascota exótica con dato propio (longitud de alas). |
| 9 | `HistorialClinico` | Concreta | citasAtendidas | `agregarCita(Cita)`, `listarCitas()` | Guarda las citas atendidas de UNA mascota. No tiene sentido sin su mascota: si la mascota se elimina, su historial también (composición). |
| 10 | `Consultorio` | Concreta | numero | `getNumero()`, `toString()` | Espacio físico numerado. Pertenece a la clínica y desaparece con ella (composición). La disponibilidad se revisa al agendar, no se guarda aquí. |
| 11 | `Clinica` | Concreta | nombre, listaVeterinarios, listaConsultorios, listaCitas, listaMascotas, listaDuenos, listaComprobantes | `agregarVeterinario(Veterinario)`, `agendarCita(...)`, `buscarCita(String)`, `buscarCita(LocalDate)`, `darDeBajaMascota(Mascota)`, `totalCobradoPorVeterinario(Veterinario)`, `totalCobradoPorMascota(Mascota)` | Es el "centro" que agrupa todo: contiene a su personal, sus consultorios y coordina las citas. |
| 12 | `Cita` | Concreta | fechaHora, estado, mascota, veterinario, consultorio, servicio | `atender()`, `cancelar()`, `generarComprobante()` | Relaciona a una mascota, un veterinario y un consultorio en una fecha. Cambia de estado (programada, atendida, cancelada). |
| 13 | `EstadoCita` | Enumeración | PROGRAMADA, ATENDIDA, CANCELADA | — | Una cita solo puede estar en uno de tres estados. Un enum evita errores de escribir el estado "a mano". |
| 14 | `Facturable` | Interfaz | — | `calcularCosto()` | Contrato: "yo sé cuánto cobro". Lo cumplen consulta, vacunación y cirugía, aunque cada una calcule distinto. Permite sumar costos sin preguntar el tipo. |
| 15 | `Servicio` | Abstracta | descripción | `calcularCosto()` (abstracto) | Base común de los tres servicios. Implementa `Facturable`. Agrupa lo compartido entre consulta, vacunación y cirugía. |
| 16 | `ConsultaGeneral` | Concreta | tarifaBase | `calcularCosto()` → tarifa base | Servicio más simple: cobra una tarifa fija. |
| 17 | `Vacunacion` | Concreta | tarifaBase, costoVacuna | `calcularCosto()` → tarifa + costo vacuna | Servicio que suma el costo de la vacuna aplicada. |
| 18 | `Cirugia` | Concreta | complejidad, horasQuirofano | `calcularCosto()` → según complejidad y horas | Servicio más caro: su costo depende de la dificultad y el tiempo de quirófano. |
| 19 | `Comprobante` | Concreta | folio, total, cita | `imprimir()` | Se genera al atender una cita. Lleva un contador static de folios (comprobantes generados). |

## 2. Objetos de ejemplo (mínimo 3)

| Objeto | Clase | Valores |
|---|---|---|
| `firulais` | `Perro` | nombre = "Firulais", edad = 4, peso = 12.5, raza = "Labrador", nivelEnergia = "alto" |
| `dra_lopez` | `Veterinario` | nombre = "Ana", apellido = "López", cédula = "VET-1024", especialidad = "Cirugía" |
| `cita001` | `Cita` | fecha = 2026-11-03, hora = 10:00, estado = PROGRAMADA, mascota = firulais, veterinario = dra_lopez |

## 3. Clases descartadas (mínimo 2)

| Clase candidata | Por qué se descartó (una línea) |
|---|---|
| Personal | La `Clinica` ya agrupa a los veterinarios, así que sería solo una lista sin responsabilidad propia. |
| Cobro | `Comprobante` ya guarda el total y la cita; una clase `Cobro` aparte solo duplicaría esos datos.  |

## 4. Justificación de relaciones (esto es el punto 1.3)

Una o dos frases por relación, pensando en el ciclo de vida: ¿qué pasa con una parte si la otra se elimina?

| Relación | Tipo | Justificación |
|---|---|---|
| Clinica ↔ Veterinario | Agregación | La clínica agrupa veterinarios que ya existen; si la clínica cierra, el veterinario sigue existiendo y puede trabajar en otra. No es composición porque la parte no depende del todo para existir. |
| Clinica ↔ Consultorio | Composición | Los consultorios pertenecen a la clínica y desaparecen con ella; no tienen sentido sin ella ni se comparten con otra. No es agregación porque, a diferencia del veterinario, un consultorio no existe de forma independiente. |
| Mascota ↔ HistorialClinico | Composición | El historial pertenece a una sola mascota y no tiene sentido sin ella; si la mascota se da de baja, su historial desaparece también. |
| Cita ↔ Mascota / Veterinario / Consultorio | Asociación | La cita relaciona objetos independientes entre sí; si la cita se cancela, la mascota, el veterinario y el consultorio siguen existiendo sin cambios. No es agregación ni composición porque la cita no agrupa ni posee a ninguno de ellos. |
| Dueno ↔ Mascota | Asociación | La mascota referencia a su dueño, pero ambos existen por separado: si se da de baja la mascota, el dueño sigue en el sistema y puede tener otras. No es agregación ni composición porque el dueño no agrupa ni posee a la mascota como parte suya. |