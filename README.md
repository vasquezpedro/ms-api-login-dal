# ms-api-login-dal

Desarrollador : Pedro Vasquez

Fecha y Versión de las Pieza

Detalle : 02.05.2022 14:00 v.1.0


Detalle de las Piezas

1) Componente Microservicio java:
ms-api-login-dal-0.0.1-SNAPSHOT.jar

## instrucciones de construcción y ejecución del proyecto

Abrir terminal dentro del directorio (/02.-fuentes/ms-api-login-dal/), ejecutar las siguientes lineas de comando:

**Compilar fuentes y ejecutar test unitarios: ./gradlew build 

Compilar fuentes sin test unitarios: ./gradlew build -x test 

**Despliegue de la aplicación: ./gradlew bootRun

### TEST UNITARIOS

**Compilar test unitarios: ./gradlew testClasses

**Ejecución de test: ./gradlew test

### REST API
**Puerto:8072

**URL LOCAL: http://localhost:8027/api

**ENDPOINT POST:

  - sing-up:/auth/sign-up

  - login:/auth/login

