# 🎬 Sistema de Gestión de Reservas para un Cine 🍿

¡Bienvenido al **Sistema de Gestión de Reservas para un Cine**! 🎉 Este proyecto es una API RESTful construida con **Spring Boot** que te permite gestionar películas, salas y reservas de manera sencilla. ¡Perfecto para que los cinéfilos reserven sus asientos favoritos! 🪑

## 🚀 **Requisitos Previos**

Antes de empezar, asegúrate de tener instalado lo siguiente:
* **Java 17** ☕: Descargar Java 17
* **Maven** 🛠️: Instalar Maven
* **Postman** 📮: Descargar Postman (para probar la API)

## 🛠️ **Configuración del Proyecto**

1. **Clona el repositorio**:
```bash
git clone https://github.com/OscarJSG/cinema-api-rest
cd reservation-system
```

2. **Configura la base de datos** 🗄️:
    * Asegúrate de tener una instancia de **PostgreSQL** en **AWS RDS**.
    * Los detalles de conexión están en `src/main/resources/application.properties`.
3. **Configura AWS SES** ✉️:
    * Configura las credenciales de **AWS SES** para enviar correos electrónicos.
    * Los detalles están en `src/main/resources/application.properties`.
4. **Compila y ejecuta la aplicación** 🏃‍♂️:
```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: 🌐 http://localhost:8080

## 📡 **Endpoints de la API**

### 🎥 **Controlador de Películas** (`MovieController`)
* **Obtener todas las películas** 🍿:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/movies`
* **Obtener una película por ID** 🔍:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/movies/{id}`
* **Eliminar una película por ID** 🗑️:
    * Método: `DELETE`
    * URL: `http://localhost:8080/api/v1/movies/{id}`

### 🎟️ **Controlador de Reservaciones** (`ReservationController`)
* **Crear una reservación** 📝:
    * Método: `POST`
    * URL: `http://localhost:8080/api/v1/reservations`
    * Cuerpo de la solicitud (JSON):
```json
{
  "movieId": 1,
  "userId": 1,
  "roomId": 1,
  "schedule": "2023-12-01T20:00:00",
  "seats": ["A1", "A2"],
  "email": "example@example.com"
}
```

* **Obtener una reservación por ID** 🔍:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/reservations/{id}`
* **Obtener todas las reservaciones** 📋:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/reservations`
* **Obtener asientos disponibles** 🪑:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/reservations/availability`
    * Parámetros:
        * `roomId`: ID de la sala.
        * `schedule`: Horario en formato `YYYY-MM-DDTHH:MM:SS`.

### 🏢 **Controlador de Salas** (`RoomController`)
* **Obtener todas las salas** 📋:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/rooms`
* **Obtener una sala por ID** 🔍:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/rooms/{id}`
* **Obtener salas disponibles** 🕒:
    * Método: `GET`
    * URL: `http://localhost:8080/api/v1/rooms`
    * Parámetros:
        * `movieId`: ID de la película.
        * `schedule`: Horario en formato `YYYY-MM-DDTHH:MM:SS`.

## 🧠 **Lógica del Proyecto**

La API está construida con **Spring Boot** y sigue una arquitectura **RESTful**. Aquí tienes el flujo de creación de reservaciones:

1. **Validación de datos** ✅: Se verifica que el correo electrónico no esté vacío.
2. **Verificación de película y sala** 🎥🏢: Se asegura de que la película y la sala existan, y que el horario sea válido.
3. **Disponibilidad de asientos** 🪑: Se comprueba que los asientos solicitados estén libres.
4. **Creación de la reservación** 📝: Se guarda la reservación en la base de datos.
5. **Envío de correo electrónico** ✉️: Se envía un correo de confirmación al usuario.

## 📚 **Documentación Adicional**

Para más información, consulta:
* Documentación oficial de Spring Boot
* Documentación de PostgreSQL

## 🤝 **Contribuciones**

¡Las contribuciones son bienvenidas! 🎉 Si tienes alguna idea o mejora, abre un **issue** o envía un **pull request**. ¡Juntos podemos hacer que este proyecto sea aún mejor! 💪

## 📜 **Licencia**

Este proyecto está bajo la **Licencia Apache 2.0**. Para más detalles, consulta el archivo LICENSE.
