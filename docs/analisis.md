# Análisis de clases y objetos: PetCare

## 1. Tabla de clases

| Clase candidata | Tipo | Atributos principales | Responsabilidades (métodos) | Justificación |
|---|---|---|---|---|
| Persona | Abstracta | | | |
| Dueno | Concreta | | | |
| Veterinario | Concreta | | | |
| Recepcionista | Concreta | | | |
| Mascota | Abstracta | | | |
| Perro | Concreta | | | |
| Gato | Concreta | | | |
| Ave | Concreta | | | |
| Facturable | Interfaz | | | |
| Servicio | Abstracta | | | |
| ConsultaGeneral | Concreta | | | |
| Vacunacion | Concreta | | | |
| Cirugia | Concreta | | | |
| Clinica | Concreta | | | |
| Consultorio | Concreta | | | |
| Cita | Concreta | | | |
| EstadoCita | Enum | | | |
| HistorialClinico | Concreta | | | |
| Comprobante | Concreta | | | |

## 2. Objetos de ejemplo (mínimo 3)

Formato: `nombreObjeto : Clase` con los valores de sus atributos.

- `obj1 : Clase` → atributo1 = valor, atributo2 = valor
- `obj2 : Clase` → ...
- `obj3 : Clase` → ...

## 3. Clases descartadas (mínimo 2)

| Clase candidata | Por qué se descartó (una línea) |
|---|---|
| Personal | La Clinica ya agrupa a los veterinarios, así que sería solo una lista sin responsabilidad propia. |
| | |

## 4. Justificación de relaciones (esto es el punto 1.3)

Una o dos frases por relación, pensando en el ciclo de vida: ¿qué pasa con una parte si la otra se elimina?

| Relación | Tipo | Justificación |
|---|---|---|
| Clinica ↔ Veterinario | | |
| Clinica ↔ Consultorio | | |
| Mascota ↔ HistorialClinico | | |
| Cita ↔ Mascota / Veterinario / Consultorio | | |
| Dueno ↔ Mascota | | |