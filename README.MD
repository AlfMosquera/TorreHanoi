# 🗼 Torre de Hanoi

Implementación en Java del clásico rompecabezas **Torre de Hanói**, con interfaz gráfica hecha en Swing, animaciones y resolución automática del juego.

## 📌 ¿Qué hace?

Este proyecto simula el juego de la Torre de Hanói, donde el objetivo es mover una pila de discos de distintos tamaños desde la torre inicial hasta la torre final, respetando dos reglas:

1. Solo se puede mover un disco a la vez (el disco superior de una torre).
2. Un disco más grande nunca puede colocarse sobre uno más pequeño.

La aplicación permite:

- Elegir el número de discos al iniciar.
- Jugar manualmente haciendo clic en las torres.
- Reiniciar la partida o cambiar el número de discos en cualquier momento.
- Resolver el rompecabezas automáticamente con una animación paso a paso, usando el algoritmo recursivo clásico de Hanói.

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Swing** (`javax.swing`, `java.awt`) para la interfaz gráfica
- **Maven** para la gestión de dependencias y compilación
- Concurrencia con `Thread` para animar la resolución automática sin bloquear la interfaz

## ⚙️ Cómo funciona

El proyecto sigue una separación de responsabilidades estilo MVC:

```
src/main/java/com/mycompany/torrehanoi/
├── Main.java              → Punto de entrada, arma la ventana principal
├── modelo/
│   └── ModeloHanoi.java   → Estado de las torres y reglas del juego (sin dependencias gráficas)
└── vista/
    ├── TorreHanoiPanel.java   → Dibuja el tablero, captura clics y anima la resolución automática
    └── ComponentesUI.java     → Fábrica de componentes visuales reutilizables (botones)
```

- **`ModeloHanoi`** guarda las tres torres como pilas (`Stack<Integer>`) y expone métodos como `esMovimientoValido`, `mover` y `esVictoria`, sin saber nada de Swing.
- **`TorreHanoiPanel`** dibuja los discos y postes con `Graphics2D`, escucha los clics del mouse para seleccionar y mover torres, y lanza un hilo independiente que resuelve el juego automáticamente usando recursión, con una pequeña pausa entre movimientos para que se vea la animación.
- **`Main`** solo construye la ventana (`JFrame`) y conecta los botones de control con el panel del juego.

## 🚀 Cómo implementarlo

### Requisitos previos

- [Java JDK 17](https://www.oracle.com/java/technologies/downloads/) o superior
- [Maven](https://maven.apache.org/download.cgi)
- Git (opcional, para clonar el repositorio)

### Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/torre-hanoi.git
cd torre-hanoi
```

### Compilar y ejecutar con Maven

```bash
mvn clean package
java -jar target/torre-hanoi.jar
```

### Ejecutar desde un IDE

También puedes abrir el proyecto como proyecto Maven en **IntelliJ IDEA**, **Eclipse** o **NetBeans**, y correr directamente la clase `Main.java`.

## 🎮 Cómo jugar

1. Al abrir la aplicación, se pedirá el número de discos.
2. Haz clic en una torre para seleccionar el disco superior (se resalta en rojo).
3. Haz clic en otra torre para moverlo allí.
4. Usa **"Reiniciar / Cambiar Discos"** para empezar de nuevo.
5. Usa **"Resolver Automáticamente"** para ver la solución animada.

## 🖼️ Capturas de pantalla

<!-- Agrega aquí tus imágenes o gifs del proyecto, por ejemplo: -->
**Pantalla Configuración**
![Pantalla Configuración](src\main\java\com\mycompany\torrehanoi\images\PantallaUno.png)

**Pantalla Principal**
![Pantalla Principal](src\main\java\com\mycompany\torrehanoi\images\PantallaDos.png)

**Pantalla Final**
![Pantalla Final](src\main\java\com\mycompany\torrehanoi\images\PantallaTres.png)
