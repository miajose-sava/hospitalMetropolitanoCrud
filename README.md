# Sistema de Gestión de Citas Médicas - Hospital Metropolitano

Módulo de escritorio para la administración y agendamiento de citas médicas desarrollado en **Java (Swing)** aplicando persistencia con **JPA (Jakarta Persistence)**, motor ORM **Hibernate** y base de datos relacional **PostgreSQL**.

---

## 📌 Contexto del Caso
El sistema automatiza el flujo de atención para el **Hospital Metropolitano**, permitiendo gestionar el ciclo de vida de las citas médicas de los pacientes mediante las operaciones fundamentales del patrón **CRUD**:
* **Create:** Agendar una nueva cita médica.
* **Read:** Consultar y listar las citas existentes en tiempo real.
* **Update:** Reagendar citas (actualizar médico, fecha, hora o estado).
* **Delete:** Cancelar y retirar citas registradas.

---

## 🛠️ Tecnologías y Dependencias
* **Lenguaje:** Java 17
* **Entorno / IDE:** Apache NetBeans
* **Gestor de Dependencias:** Apache Maven
* **ORM / Persistencia:** Hibernate Core 6.4.4.Final & Jakarta Persistence API 3.1.0
* **Base de Datos:** PostgreSQL 
* **Driver JDBC:** PostgreSQL Driver 42.7.2
* **Diseño Conceptual:** StarUML

---

## 🏛️ Arquitectura del Proyecto

```text
hospitalMetropolitanoCrud/
├── src/
│   └── main/
│       ├── java/com/mycompany/hospitalmetropolitanocrud/
│       │   ├── modelo/           # Entidad JPA (CitaMedica.java)
│       │   ├── dao/              # Capa DAO y EntityManager (CitaMedicaDAO.java)
│       │   ├── vista/            # Interfaz gráfica Swing (VentanaHospital.java)
│       │   └── HospitalMetropolitanoCrud.java # Clase principal de arranque
│       └── resources/
│           └── META-INF/
│               └── persistence.xml # Configuración de la unidad de persistencia
├── UML CitaMedica.png            # Diagrama de clases exportado de StarUML
└── pom.xml                       # Configuración y dependencias Maven
```

---

## ⚙️ Configuración y Ejecución

1. **Base de Datos:**
   - Crear una base de datos en PostgreSQL llamada `hospital_db`.
   - Verificar en `src/main/resources/META-INF/persistence.xml` que el usuario (`postgres`) y contraseña coincidan con los de su servidor local.
   - La propiedad `hibernate.hbm2ddl.auto = update` creará automáticamente la tabla `citas_medicas` al ejecutar el proyecto por primera vez.

2. **Ejecución:**
   - Abrir el proyecto en NetBeans.
   - Realizar *Clean and Build*.
   - Ejecutar la clase principal `HospitalMetropolitanoCrud.java` (`F6`).

---
