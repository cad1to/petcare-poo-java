# PetCare – Ejemplo de ejecución


> **Nota:** Salida de consola del **escenario de demostración obligatorio**, paso a paso.
>Lo que escribe el usuario aparece al final de cada línea de petición (por ejemplo `Elige una opción: 11`).

## Contenido

1. [Qué cubre cada paso](#1-qué-cubre-cada-paso)
2. [Carga del escenario (opción 11)](#2-carga-del-escenario-opción-11)
3. [Atender la tercera cita (opción 4)](#3-atender-la-tercera-cita-opción-4)
4. [Comprobantes (opción 5)](#4-comprobantes-opción-5)
5. [Polimorfismo: mascotas y su sonido (opción 7)](#5-polimorfismo-mascotas-y-su-sonido-opción-7)
6. [Total cobrado (opción 8)](#6-total-cobrado-opción-8)
7. [Historial clínico (opción 6)](#7-historial-clínico-opción-6)
8. [Validación de entradas y reglas de negocio](#8-validación-de-entradas-y-reglas-de-negocio)
9. [Dar de baja una mascota (opción 9)](#9-dar-de-baja-una-mascota-opción-9)
10. [Salir (opción 0)](#10-salir-opción-0)

---

## 1. Qué cubre cada paso

| Requisito del escenario | Dónde se ve |
| --- | --- |
| Registrar dos dueños y tres mascotas (perro, gato, ave) | Sección 2 |
| Registrar dos veterinarios | Sección 2 |
| Agendar tres citas | Sección 2 |
| Cita que viola una regla de negocio y su mensaje de error | Sección 2 y sección 8 |
| Atender dos citas (cirugía y vacunación) | Sección 2 |
| Costo de consulta general | Sección 3 |
| Listar mascotas con `hacerSonido()` (polimorfismo) | Sección 5 |
| Total cobrado | Sección 6 |
| Consultar un historial clínico | Sección 7 |
| Entradas inválidas sin cerrar el programa | Sección 8 |
| Dar de baja una mascota | Sección 9 |

---

## 2. Carga del escenario (opción 11)

La opción 11 registra dos dueños, tres mascotas y dos veterinarios, agenda tres citas,
intenta agendar una cuarta que viola una regla y atiende dos citas.

```text
===== CLÍNICA PETCARE =====
1. Registrar dueño y mascota
2. Registrar veterinario
3. Agendar una cita
4. Atender o cancelar una cita
5. Generar comprobante de una cita atendida
6. Consultar historial clínico de una mascota
7. Listar mascotas y su sonido
8. Ver total cobrado
9. Dar de baja una mascota
11. Cargar datos de demostración
0. Salir
Elige una opción: 11
Dueños, mascotas y veterinarios registrados.
Citas agendadas:
Cita [PROGRAMADA] 2026-11-05T10:00 - Rocky con Marta en Consultorio 1
Cita [PROGRAMADA] 2026-11-05T11:00 - Misu con Jorge en Consultorio 2
Cita [PROGRAMADA] 2026-11-05T12:00 - Kiwi con Marta en Consultorio 3
Regla de negocio violada: El veterinario ya tiene una cita a esa hora
Atendidas las citas 1 (cirugía) y 2 (vacunación).
```

**Costos calculados:**

| Cita | Servicio | Cálculo | Costo |
| --- | --- | --- | --- |
| 1 – Rocky | Cirugía (complejidad 2, 1.5 h) | 800 × 1.5 × 2 | $2400.0 |
| 2 – Misu | Vacunación (Rabia, vacuna $200) | 150 + 200 | $350.0 |

---

## 3. Atender la tercera cita (opción 4)

Se atiende la cita 3 con **consulta general** para mostrar los tres tipos de costo.

```text
===== CLÍNICA PETCARE =====
[... menú ...]
Elige una opción: 4
1. Cita [ATENDIDA] 2026-11-05T10:00 - Rocky con Marta en Consultorio 1
2. Cita [ATENDIDA] 2026-11-05T11:00 - Misu con Jorge en Consultorio 2
3. Cita [PROGRAMADA] 2026-11-05T12:00 - Kiwi con Marta en Consultorio 3
Elige el número: 3
1. Atender  2. Cancelar: 1
Servicio: 1. Consulta general  2. Vacunación  3. Cirugía
Servicio: 1
Cita atendida.
```

| Cita | Servicio | Cálculo | Costo |
| --- | --- | --- | --- |
| 3 – Kiwi | Consulta general | Tarifa base | $350.0 |

---

## 4. Comprobantes (opción 5)

Se genera un comprobante por cada cita atendida. El folio sube solo, porque el contador
`comprobantesGenerados` es un miembro `static` de `Comprobante`.

```text
Elige una opción: 5
1. Cita [ATENDIDA] 2026-11-05T10:00 - Rocky con Marta en Consultorio 1
2. Cita [ATENDIDA] 2026-11-05T11:00 - Misu con Jorge en Consultorio 2
3. Cita [ATENDIDA] 2026-11-05T12:00 - Kiwi con Marta en Consultorio 3
Elige el número: 1
=== Comprobante #1 ===
Mascota: Rocky
Dueño: Ana
Veterinario: Marta
Consultorio: 1
Servicio: Cirugía
Total: $2400.0
```

```text
Elige una opción: 5
[... lista de citas ...]
Elige el número: 2
=== Comprobante #2 ===
Mascota: Misu
Dueño: Ana
Veterinario: Jorge
Consultorio: 2
Servicio: Vacunación
Total: $350.0
```

```text
Elige una opción: 5
[... lista de citas ...]
Elige el número: 3
=== Comprobante #3 ===
Mascota: Kiwi
Dueño: Luis
Veterinario: Marta
Consultorio: 3
Servicio: Consulta general
Total: $350.0
```

---

## 5. Polimorfismo: mascotas y su sonido (opción 7)

Una sola lista `List<Mascota>` contiene un perro, un gato y un ave. Cada una responde
a `hacerSonido()` a su manera, sin usar `instanceof`.

```text
Elige una opción: 7
Rocky dice: Guau
Misu dice: Miau
Kiwi dice: Pío
```

---

## 6. Total cobrado (opción 8)

El total se calcula recorriendo una `List<Facturable>`, sin preguntar el tipo de cada servicio.
Los cobros se pueden consultar por veterinario y por mascota.

```text
Elige una opción: 8
--- Por veterinario ---
Marta Ruiz: $2750.0
Jorge Salas: $350.0
--- Por mascota ---
Rocky: $2400.0
Misu: $350.0
Kiwi: $350.0
TOTAL COBRADO: $3100.0
```

---

## 7. Historial clínico (opción 6)

```text
Elige una opción: 6
1. Perro: Rocky (4 años, 18.5 kg) - Dueño: Ana López - Raza: Labrador, Energía: Alta
2. Gato: Misu (3 años, 4.2 kg) - Dueño: Ana López - Doméstico: sí
3. Ave: Kiwi (2 años, 0.9 kg) - Dueño: Luis Pérez - Alas: 35.0 cm
Elige el número: 1
Historial clínico de Rocky:
- 2026-11-05T10:00 | Marta | Cirugía

```

---

## 8. Validación de entradas y reglas de negocio

El programa muestra el error y vuelve al menú, sin cerrarse.

### Texto donde se espera un número y opción fuera del menú

```text
Elige una opción: hola
Ingresa un número entero válido.
Elige una opción: 99
Opción no válida.
```

### Dato vacío

```text
Elige una opción: 2
Nombre: 
El dato no puede estar vacío.
Nombre: Pedro
```

### Edad que no cumple la regla (no puede ser cero ni negativa)

```text
Elige una opción: 1
¿Dueño ya registrado? (s/n): s
1. Ana López (9211111111, ana@correo.com) - Dirección: Calle Reforma 10
2. Luis Pérez (9212222222, luis@correo.com) - Dirección: Av. Central 25
Elige el número: 2
Tipo de mascota: 1. Perro  2. Gato  3. Ave
Tipo: 2
Nombre de la mascota: Pelusa
Edad (años): 0
Peso (kg): 3.5
¿Es doméstico? (s/n): s
Error: La edad debe ser mayor que cero
```

### Una cita cancelada no puede marcarse como atendida

Primero se agenda una cita nueva y se cancela:

```text
Elige una opción: 3
1. Rocky (4 años, 18.5 kg) - Dueño: Ana López ...
2. Misu (3 años, 4.2 kg) - Dueño: Ana López ...
3. Kiwi (2 años, 0.9 kg) - Dueño: Luis Pérez ...
Elige el número: 2
1. Marta Ruiz (9213333333, marta@petcare.com) - Cédula: CED1001, Especialidad: Cirugía
2. Jorge Salas (9214444444, jorge@petcare.com) - Cédula: CED1002, Especialidad: Medicina general
Elige el número: 2
Número de consultorio (1-3): 1
Fecha y hora (aaaa-mm-dd hh:mm): 2026-11-06 09:00
Cita agendada: Cita [PROGRAMADA] 2026-11-06T09:00 - Misu con Jorge en Consultorio 1
```

```text
Elige una opción: 4
[... lista de citas ...]
4. Cita [PROGRAMADA] 2026-11-06T09:00 - Misu con Jorge en Consultorio 1
Elige el número: 4
1. Atender  2. Cancelar: 2
Cita cancelada.
```

Después se intenta atenderla:

```text
Elige una opción: 4
[... lista de citas ...]
4. Cita [CANCELADA] 2026-11-06T09:00 - Misu con Jorge en Consultorio 1
Elige el número: 4
1. Atender  2. Cancelar: 1
Servicio: 1. Consulta general  2. Vacunación  3. Cirugía
Servicio: 1
Error: Una cita cancelada no puede marcarse como atendida
```

### Un veterinario no puede tener dos citas a la misma hora

Con la cita 3 ya atendida, el choque se ve agendando dos citas nuevas a la misma hora:

```text
Elige una opción: 3
[... elige Rocky, veterinario 1, consultorio 1, 2026-11-07 10:00 ...]
Cita agendada: Cita [PROGRAMADA] 2026-11-07T10:00 - Rocky con Marta en Consultorio 1

Elige una opción: 3
[... elige Kiwi, veterinario 1, consultorio 2, 2026-11-07 10:00 ...]
Error: El veterinario ya tiene una cita a esa hora
```

### Un consultorio no puede usarse en dos citas al mismo tiempo

```text
Elige una opción: 3
[... elige Kiwi, veterinario 2, consultorio 1, 2026-11-07 10:00 ...]
Error: El consultorio ya está ocupado a esa hora
```

### Fecha con formato incorrecto

```text
Fecha y hora (aaaa-mm-dd hh:mm): mañana
Formato no válido. Ejemplo: 2026-11-05 10:00
Fecha y hora (aaaa-mm-dd hh:mm): 2026-11-07 10:00
```

---

## 9. Dar de baja una mascota (opción 9)

Al dar de baja a Kiwi se eliminan su registro, su historial y sus citas.
Como el programa ya no tiene referencias a esos objetos, el recolector de basura de Java
libera su memoria. El dueño sigue existiendo.

```text
Elige una opción: 9
1. Perro: Rocky (4 años, 18.5 kg) - Dueño: Ana López - Raza: Labrador, Energía: Alta
2. Gato: Misu (3 años, 4.2 kg) - Dueño: Ana López - Doméstico: sí
3. Ave: Kiwi (2 años, 0.9 kg) - Dueño: Luis Pérez - Alas: 35.0 cm
Elige el número: 3
¿Confirmas dar de baja a Kiwi y su historial? (s/n): s
Mascota dada de baja.
```

Comprobación con la opción 7:

```text
Elige una opción: 7
Rocky dice: Guau
Misu dice: Miau
```

---

## 10. Salir (opción 0)

```text
Elige una opción: 0
Hasta luego.

Process finished with exit code 0
```