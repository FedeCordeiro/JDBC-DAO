# JDBC - DAO Basico

## 📌 Descripción

Este proyecto implementa el patrón de diseño **Abstract Factory** en conjunto con el patrón **DAO (Data Access Object)** para gestionar entidades de tipo `Persona` y `Dirección` en Java. 
La arquitectura permite desacoplar la lógica de acceso a datos de la lógica del negocio, facilitando el mantenimiento y la extensibilidad del sistema.

---

## 📂 Estructura del Proyecto

```
📁 dao
 ├── DireccionDao.java        # Interfaz DAO para Direccion
 └── PersonaDao.java          # Interfaz DAO para Persona

📁 dto
 └── PersonaDto.java          # Objeto de transferencia de datos para Persona

📁 entities
 ├── Direccion.java           # Entidad Direccion
 └── Persona.java             # Entidad Persona

📁 factory
 ├── AbstractFactory.java     # Fábrica abstracta para crear DAOs
 └── MySQLDAOFactory.java     # Implementación concreta para MySQL

📁 utils
 └── HelperMySQL.java         # Conexion con base de datos MySQL

📄 main.java               

```

---

## 🧩 Clases Principales

### `AbstractFactory.java`
Define una clase abstracta que expone métodos para obtener las implementaciones DAO concretas. Permite agregar nuevos orígenes de datos (como PostgreSQL o SQLite) fácilmente, implementando una nueva clase concreta.

### `MySQLDAOFactory.java`
Implementación concreta de `AbstractFactory` que devuelve instancias de DAO específicas para MySQL.

### `PersonaDao.java` / `DireccionDao.java`
Interfaces que definen operaciones CRUD y otras funciones sobre las entidades `Persona` y `Direccion`, respectivamente.

### `Persona.java` / `Direccion.java`
Clases que representan entidades del sistema. Sus atributos se corresponden con las columnas de una posible tabla en la base de datos.

### `PersonaDto.java`
DTO que unifica datos de `Persona` y `Direccion` para ser utilizados, por ejemplo, en vistas o servicios.

---

## ⚙️ Tecnologías utilizadas

- Lenguaje: Java 8+
- Patrón de diseño: Abstract Factory + DAO
- Arquitectura orientada a objetos

---

## 🚀 Cómo ejecutar

> Este proyecto cuenta con una clase `main()` funcional, ubicada en la raiz del mismo.
> Para probarlo, se sugiere:

1. Asegurate de tener una base de datos MySQL accesible
2. Correr archivo `main.java`

---

