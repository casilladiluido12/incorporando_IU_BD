# ConexionTester — Actividad Unidad 3

App de escritorio en Java Swing que prueba una conexión JDBC a MySQL.

```
ConexionTester/
├── src/ConexionTester.java   ← todo el código (JFrame + lógica JDBC)
├── lib/                      ← aquí va mysql-connector-j-x.x.x.jar
├── sql/crear_bd.sql          ← crea la BD "inventario" y un usuario de prueba
└── .vscode/settings.json     ← le dice a VS Code que use lib/*.jar
```

## 1. Preparar MySQL
Abre MySQL Workbench, conéctate como `root`, abre `sql/crear_bd.sql` y ejecútalo (⚡).

## 2. Descargar el driver
https://dev.mysql.com/downloads/connector/j/ → "Platform Independent" → ZIP.
Descomprime y copia `mysql-connector-j-x.x.x.jar` a la carpeta `lib/`.

## 3. Ejecutar
**VS Code** (con "Extension Pack for Java"): File > Open Folder > `ConexionTester`,
abre `ConexionTester.java` y pulsa *Run* sobre `main`.

## 4. Casos para las capturas de pantalla
| Caso | Qué escribir | Resultado |
|---|---|---|
| Éxito | URL `jdbc:mysql://localhost:3306/inventario`, usuario `estudiante`, clave `Estudiante123*` | Verde: Conexión exitosa |
| Clave errada | misma URL, clave `xxx` | Rojo: Access denied… |
| BD inexistente | `jdbc:mysql://localhost:3306/noexiste` | Rojo: Unknown database… |
| Servidor apagado / puerto errado | `jdbc:mysql://localhost:3307/inventario` | Rojo: Communications link failure |
| Sin driver | quita el .jar del proyecto | Rojo: Fallo del driver (ClassNotFoundException) |
| URL inválida | `jdbc:postgres://x` | Rojo: No suitable driver found |
