# Flujo de Calor y Torneo de Fútbol

Aplicación Android con dos módulos: una **guía educativa sobre flujo de calor** con calculadora de ejercicios, y un **registro de torneo de fútbol** para llevar equipos, partidos y goles.

## Módulo 1: Flujo de calor

Material de estudio sobre transferencia de energía térmica con una herramienta de cálculo.

- **Teoría** en ocho temas: transferencia de energía térmica, dirección del flujo de calor, causas, unidades de medida, mecanismos de transferencia, conducción, convección y radiación.
- **Tabla de conductividades térmicas** de distintos materiales, en W/m·K y Btu/h·ft·°F.
- **Calculadora de ejercicios** para conducción a través de paredes planas en serie:
  - Tres capas, cada una con su área, espesor y material (aluminio, cobre, zinc, bronce o vidrio).
  - Resistencia térmica de cada capa: `R = espesor / (área × conductividad)`.
  - Flujo de calor total: `q = (T inicial − T final) / ΣR`.

## Módulo 2: Torneo de fútbol

Registro de un torneo escolar con base de datos local.

- Registro de **equipos**.
- Registro de **partidos** con marcador; el ganador se calcula automáticamente a partir del resultado.
- Registro de **goles** por jugador.
- Consulta de **resultados**.

Los datos se guardan en una base de datos SQLite (`cecytem_tournament.db`) con las tablas `teams`, `games` y `goals`.

## Tecnologías

- Java
- Android SDK (Material Components, AppCompat, ConstraintLayout)
- SQLite (`SQLiteOpenHelper`)
- Gradle (Kotlin DSL)

## Requisitos

- Android Studio (versión reciente)
- Android 11 o superior (`minSdk 30`, `compileSdk 34`)

## Cómo ejecutarlo

1. Clona el repositorio:
   ```bash
   git clone https://github.com/iantrejogarcia-del/flujo-de-calor2.git
   ```
2. Abre el proyecto en Android Studio y espera a que termine la sincronización de Gradle.
3. Conecta un dispositivo Android o inicia un emulador.
4. Presiona **Run**.

## Estructura del proyecto

```
app/src/main/java/com/example/sapo_proyect/
├── Inicio_Activity.java     # Pantalla de inicio con los dos módulos
├── MainActivity.java        # Menú del torneo
├── bd/DatabaseHelper.java   # Base de datos SQLite
├── flujocalor/              # Teoría, tabla de conductividades y ejercicios
└── futbol/                  # Registro de equipos, partidos, goles y resultados
```

## Posibles mejoras

- Validar los datos del torneo (por ejemplo, evitar equipos repetidos).
- Mostrar una tabla de posiciones automática.
- Permitir más de tres capas en la calculadora.
- Agregar más materiales a la lista.

## Autor

**Ian Trejo García** — Estudiante de Ingeniería en Sistemas Computacionales
GitHub: [iantrejogarcia-del](https://github.com/iantrejogarcia-del)
