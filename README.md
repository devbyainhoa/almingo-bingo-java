# ALMINGO – Bingo digital en Java

## Descripción del proyecto

ALMINGO es una aplicación de bingo digital multijugador desarrollada en Java. Permite que varios participantes jueguen simultáneamente mediante dos programas independientes: **CENTRAL** y **JUGADOR**.

El proyecto incorpora preguntas relacionadas con la sostenibilidad para validar las solicitudes de línea y bingo, combinando entretenimiento y aprendizaje.

## Tecnologías utilizadas

- **Java:** lenguaje de programación utilizado para desarrollar la aplicación.
- **Java I/O:** gestión de archivos y lectura y escritura de información.
- **Git y GitHub:** control de versiones y almacenamiento del código fuente.
- **GitHub Desktop:** gestión y sincronización del repositorio.
- **Carpeta compartida en red:** mecanismo de comunicación entre los programas.

## Arquitectura de la aplicación

El proyecto está dividido en dos programas:

### CENTRAL

Es el programa encargado de organizar y controlar la partida.

Sus principales funciones son:

- Consultar los jugadores registrados.
- Comprobar que haya al menos dos participantes.
- Generar y asignar los cartones.
- Extraer números aleatorios sin repetir.
- Gestionar las solicitudes de línea y bingo.
- Enviar preguntas de sostenibilidad y validar las respuestas.
- Comunicar el resultado y finalizar la partida.

### JUGADOR

Es el programa utilizado por los participantes.

Sus principales funciones son:

- Registrar el nombre del jugador.
- Recibir y mostrar su cartón.
- Consultar los números extraídos.
- Marcar los números que aparecen en su cartón.
- Solicitar la validación de una línea o un bingo.
- Responder a las preguntas de sostenibilidad.
- Consultar el resultado de las solicitudes.

## Reglas del bingo

- Cada cartón tiene **3 filas y 5 columnas (15 casillas)**.
- Los números están comprendidos entre el 1 y el 49.
- Los números se extraen aleatoriamente y no se repiten.
- Cada jugador recibe un cartón diferente.
- Para cantar línea es necesario completar una fila de cinco números.
- Para cantar bingo es necesario completar las quince casillas.
- Las solicitudes de línea y bingo deben validarse mediante una pregunta de sostenibilidad.
- Si la respuesta es incorrecta, la solicitud se rechaza, pero se mantienen las casillas marcadas.
- Si se extraen los 49 números sin conseguir un bingo válido, la partida termina en empate.

## Comunicación entre los programas

La comunicación se realiza mediante una **carpeta compartida en red**, sin utilizar sockets ni RMI.

Los dos programas acceden a una serie de archivos de texto que permiten intercambiar y consultar la información necesaria para desarrollar la partida.

Los archivos utilizados son:

- `jugadores.txt`: almacena la información de los participantes y sus cartones.
- `estado.txt`: recoge el estado de la partida, los números extraídos y las solicitudes.
- `preguntas.txt`: contiene las preguntas de sostenibilidad.
- `preguntaActual.txt`: almacena la pregunta enviada al jugador.
- `respuesta.txt`: recoge las respuestas de los participantes.

## Estructura del proyecto

El código fuente está organizado en tres paquetes principales:

- `principal`: contiene las clases `Central` y `Jugador`.
- `logica`: contiene las clases `Carton` y `Sorteo`.
- `archivos`: contiene las clases encargadas de gestionar los archivos compartidos y la comunicación.

## Requisitos y ejecución

Para ejecutar el proyecto es necesario disponer de:

- Un entorno con Java instalado.
- Acceso a la carpeta compartida en red.
- Los archivos necesarios para el funcionamiento del juego.

### Pasos para iniciar una partida

1. Configurar la carpeta compartida y comprobar que los equipos tienen acceso a ella.
2. Ejecutar el programa `Central` en el ordenador organizador.
3. Ejecutar el programa `Jugador` en los equipos de los participantes.
4. Registrar los jugadores.
5. Iniciar la partida desde CENTRAL.
6. Seguir las instrucciones de cada programa durante el desarrollo del juego.

**Importante:** la ruta de la carpeta compartida está configurada en el código fuente. Si se utiliza una ubicación de red diferente, será necesario adaptar dicha ruta antes de ejecutar la aplicación.

## Autoras

Proyecto desarrollado en equipo por:

- Ainhoa
- Selene
- Laura

Proyecto académico realizado en el marco del ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).

---

**ALMINGO – Un bingo digital que combina programación, colaboración y sostenibilidad.**
