# QuimbayaEVAL - Aplicación Móvil Android

## Evidencia GA8-220501096-AA2-EV02

Sistema móvil para la gestión integral de evaluaciones académicas en la plataforma Android.

---

## Descripción del Proyecto

QuimbayaEVAL es una aplicación móvil diseñada para facilitar la interacción de estudiantes, maestros y coordinadores con el sistema de evaluaciones académicas. Implementa una arquitectura profesional con Clean Architecture + MVVM, siguiendo las mejores prácticas de desarrollo Android.

### Módulos Implementados

- **Autenticación**: Login seguro con JWT
- **Evaluaciones**: Visualizar, realizar y enviar respuestas
- **Calificaciones**: Calificar respuestas de estudiantes
- **PQRS**: Gestión de peticiones, quejas, reclamos y sugerencias
- **Perfil**: Configuración y gestión de usuario

---

## Características

### Para Estudiantes
- ✅ Visualizar evaluaciones pendientes
- ✅ Realizar evaluaciones con timer
- ✅ Diferentes tipos de preguntas
- ✅ Ver resultados y feedback
- ✅ Crear PQRS
- ✅ Seguimiento de evaluaciones

### Para Maestros
- ✅ Crear evaluaciones
- ✅ Calificar respuestas
- ✅ Agregar feedback
- ✅ Ver reportes de desempeño
- ✅ Responder PQRS

### Para Coordinadores
- ✅ Vista global del sistema
- ✅ Gestión de usuarios
- ✅ Reportes consolidados
- ✅ Configuración del sistema

---

## Requisitos Técnicos

### Hardware
- Dispositivo Android 7.0+ (API 24)
- Mínimo 2 GB RAM
- 50 MB de espacio libre

### Desarrollo
- Android Studio Giraffe+
- Kotlin 1.9+
- Gradle 8.x
- JDK 17+

### Backend Requerido
- QuimbayaEVAL Backend (Spring Boot)
- API en puerto 8080
- Base de datos PostgreSQL

---

## Instalación

### 1. Clonar el Repositorio
```bash
git clone https://github.com/Dan17i/QuimbayaEVAL-Android.git
cd QuimbayaEVAL-Android
```

### 2. Abrir en Android Studio
```bash
# Desde Android Studio: File → Open → Seleccionar carpeta del proyecto
```

### 3. Configurar Dependencias
Las dependencias se descargarán automáticamente con Gradle.

### 4. Compilar la Aplicación
```bash
./gradlew build
```

### 5. Ejecutar en Emulador o Dispositivo
```bash
./gradlew installDebug
```

---

## Estructura del Proyecto

```
QuimbayaEVAL-Android/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/sena/quimbayaeval/
│   │       │   ├── presentation/      # UI Layer
│   │       │   ├── domain/            # Business Logic
│   │       │   └── data/              # Data Access
│   │       ├── res/                   # Recursos (layouts, strings, etc)
│   │       └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── ARQUITECTURA.md                    # Diagramas y arquitectura
├── GUIA_DESARROLLO.md                 # Guía de desarrollo
├── README.md                          # Este archivo
└── gradle/
    └── wrapper/
```

---

## Configuración

### Variables de Entorno

Crear archivo `local.properties`:
```properties
sdk.dir=/path/to/android/sdk
```

### Backend API

Configurar en `build.gradle.kts`:
```kotlin
buildConfigField "String", "BASE_URL", '"http://10.0.2.2:8080/api/"'
```

Para dispositivo físico, cambiar a dirección IP del servidor:
```kotlin
buildConfigField "String", "BASE_URL", '"http://192.168.x.x:8080/api/"'
```

---

## Credenciales de Prueba

### Estudiante
```
Email: estudiante@universidad.edu
Password: password123
```

### Maestro
```
Email: maestro@universidad.edu
Password: password123
```

### Coordinador
```
Email: coordinador@universidad.edu
Password: password123
```

---

## API Endpoints Utilizados

```
POST   /auth/login              - Autenticación
POST   /auth/logout             - Cerrar sesión
GET    /evaluaciones            - Listar evaluaciones
GET    /evaluaciones/{id}       - Obtener evaluación
POST   /evaluaciones/{id}/respuestas - Enviar respuestas
GET    /pqrs                    - Listar PQRS
POST   /pqrs                    - Crear PQRS
PUT    /pqrs/{id}/responder     - Responder PQRS
```

---

## Arquitectura

### Capas

1. **Presentation**: Activities, Fragments, ViewModels
2. **Domain**: UseCases, Models, Repository Interfaces
3. **Data**: API Client, Local Database, Repositories

### Patrones

- **MVVM**: Separación UI y lógica
- **Repository**: Abstracción de datos
- **UseCase**: Encapsulación de lógica de negocio
- **Hilt**: Inyección de dependencias

---

## Desarrollo

### Crear Nueva Feature

```bash
git checkout develop
git checkout -b feature/nombre-feature
```

### Commits Semánticos
```bash
feat:     Nueva funcionalidad
fix:      Corrección de bug
docs:     Cambios en documentación
test:     Agregar/modificar tests
refactor: Refactorización de código
style:    Cambios de estilo (sin lógica)
```

### Ejemplo
```bash
git commit -m "feat: Implementar módulo de PQRS"
```

---

## Testing

### Ejecutar Tests Unitarios
```bash
./gradlew test
```

### Ejecutar Tests de Integración
```bash
./gradlew connectedAndroidTest
```

### Coverage de Tests
```bash
./gradlew testDebugUnitTestCoverage
```

---

## Build y Distribución

### Build Debug
```bash
./gradlew assembleDebug
```

Archivo generado: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release
```bash
./gradlew assembleRelease
```

Archivo generado: `app/build/outputs/apk/release/app-release.apk`

---

## Documentación Completa

- **ARQUITECTURA.md**: Diagramas UML, componentes, paquetes
- **GUIA_DESARROLLO.md**: Guía detallada de desarrollo, patrones, ejemplos

---

## Troubleshooting

### Error: "SDK not found"
```bash
# Actualizar SDK
Android Studio → Tools → SDK Manager
```

### Error: "Emulator failed to start"
```bash
# Verificar virtualización
# BIOS → Habilitar Intel VT-x o AMD-V
```

### Error: "Gradle sync failed"
```bash
# Limpiar proyecto
./gradlew clean
./gradlew build
```

---

## Performance

- Caching local con Room
- Lazy loading de imágenes
- Coroutines para operaciones async
- Optimización de queries

---

## Seguridad

- ✅ JWT para autenticación
- ✅ HTTPS en producción
- ✅ Encriptación de datos sensibles
- ✅ Validación de inputs
- ✅ ProGuard/R8 habilitado

---

## Contribuir

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

---

## Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

---

## Contacto

**Equipo QuimbayaEVAL**
- SENA Centro de Formación Quimbaya
- Fecha: 25 de Abril de 2026

---

## Versión

**v1.0.0** - Release Inicial

---

**Última actualización**: 25/04/2026
