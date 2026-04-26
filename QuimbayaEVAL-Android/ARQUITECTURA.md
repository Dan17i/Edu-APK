# ARQUITECTURA DE LA APLICACION QUIMBAYAEVAL ANDROID
## Evidencia GA8-220501096-AA2-EV02

---

## 1. DIAGRAMA DE COMPONENTES

```
┌─────────────────────────────────────────────────────────────────┐
│                     CAPA DE PRESENTACION (UI)                   │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────────────┐    │
│  │   MainActivity│ │ DashboardView│ │ EvaluacionesView     │    │
│  │   (Login)    │ │              │ │                      │    │
│  └──────────────┘ └──────────────┘ └──────────────────────┘    │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────────────┐    │
│  │   CalificarView  │ RealizarEvalView│ PQRSView            │    │
│  │                  │              │ │                      │    │
│  └──────────────┘ └──────────────┘ └──────────────────────┘    │
└────────────────────────────────┬────────────────────────────────┘
                                 │
                    ViewModels & Fragment Managers
                                 │
┌────────────────────────────────┴────────────────────────────────┐
│              CAPA DE LOGICA DE NEGOCIO (Domain)                 │
│  ┌──────────────────┐ ┌──────────────────┐ ┌────────────────┐ │
│  │  UsuarioUseCase  │ │ EvaluacionUseCase│ │  PQRSUseCase  │ │
│  │                  │ │                  │ │                │ │
│  └──────────────────┘ └──────────────────┘ └────────────────┘ │
└────────────────────────────────┬────────────────────────────────┘
                                 │
              Repository Pattern & Dependency Injection
                                 │
┌────────────────────────────────┴────────────────────────────────┐
│          CAPA DE DATOS (Data / Repositories)                    │
│  ┌──────────────────┐ ┌──────────────────┐ ┌────────────────┐ │
│  │ UsuarioRepository│ │ EvaluacionRepo   │ │  PQRSRepository│ │
│  │                  │ │                  │ │                │ │
│  └────────┬─────────┘ └────────┬─────────┘ └────────┬───────┘ │
│           │                    │                    │          │
│  ┌────────┴─────────────────────┴────────────────────┴────────┐ │
│  │      API Client (Retrofit) + Local Cache (Room)           │ │
│  └────────────────────────────────────────────────────────────┘ │
└────────────────────────────────┬────────────────────────────────┘
                                 │
                    Network Communication
                                 │
                   ┌─────────────────────────────┐
                   │   Backend QuimbayaEVAL      │
                   │   (Spring Boot - Puerto 8080)
                   │   Base de Datos PostgreSQL  │
                   └─────────────────────────────┘
```

---

## 2. DIAGRAMA DE PAQUETES

```
com.sena.quimbayaeval/
├── presentation/
│   ├── ui/
│   │   ├── activities/
│   │   │   ├── MainActivity.kt (Login)
│   │   │   ├── DashboardActivity.kt
│   │   │   └── BaseActivity.kt
│   │   ├── fragments/
│   │   │   ├── DashboardFragment.kt
│   │   │   ├── EvaluacionesFragment.kt
│   │   │   ├── CalificarFragment.kt
│   │   │   ├── RealizarEvaluacionFragment.kt
│   │   │   └── PQRSFragment.kt
│   │   ├── adapters/
│   │   │   ├── EvaluacionAdapter.kt
│   │   │   ├── PQRSAdapter.kt
│   │   │   └── CursoAdapter.kt
│   │   └── views/
│   │       ├── CustomViews.kt
│   │       └── ProgressIndicator.kt
│   ├── viewmodels/
│   │   ├── LoginViewModel.kt
│   │   ├── DashboardViewModel.kt
│   │   ├── EvaluacionViewModel.kt
│   │   ├── CalificarViewModel.kt
│   │   └── PQRSViewModel.kt
│   └── navigation/
│       └── NavigationManager.kt
│
├── domain/
│   ├── usecases/
│   │   ├── LoginUseCase.kt
│   │   ├── GetEvaluacionesUseCase.kt
│   │   ├── RealizarEvaluacionUseCase.kt
│   │   ├── CalificarEvaluacionUseCase.kt
│   │   ├── GetPQRSUseCase.kt
│   │   └── CrearPQRSUseCase.kt
│   ├── models/
│   │   ├── Usuario.kt
│   │   ├── Evaluacion.kt
│   │   ├── Respuesta.kt
│   │   ├── PQRS.kt
│   │   └── Curso.kt
│   └── repository/
│       ├── IUsuarioRepository.kt
│       ├── IEvaluacionRepository.kt
│       └── IPQRSRepository.kt
│
├── data/
│   ├── remote/
│   │   ├── api/
│   │   │   ├── QuimbayaEVALService.kt
│   │   │   └── RetrofitClient.kt
│   │   └── dto/
│   │       ├── LoginRequest.kt
│   │       ├── LoginResponse.kt
│   │       ├── EvaluacionDTO.kt
│   │       └── PQRSDto.kt
│   ├── local/
│   │   ├── database/
│   │   │   ├── AppDatabase.kt
│   │   │   └── dao/
│   │   │       ├── UsuarioDao.kt
│   │   │       ├── EvaluacionDao.kt
│   │   │       └── PQRSDao.kt
│   │   └── sharedpref/
│   │       └── PreferencesManager.kt
│   └── repository/
│       ├── UsuarioRepository.kt
│       ├── EvaluacionRepository.kt
│       └── PQRSRepository.kt
│
├── di/
│   ├── AppModule.kt (Hilt/Dagger)
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
│
└── utils/
    ├── Constants.kt
    ├── extensions/
    │   ├── ContextExt.kt
    │   └── ActivityExt.kt
    ├── helpers/
    │   ├── AuthTokenManager.kt
    │   └── ValidationHelper.kt
    └── security/
        └── EncryptionHelper.kt
```

---

## 3. DIAGRAMA DE CLASES (Módulo Evaluaciones)

```
┌──────────────────────────────────────────────────────────────┐
│                      Evaluacion                              │
├──────────────────────────────────────────────────────────────┤
│ - id: Long                                                   │
│ - titulo: String                                             │
│ - descripcion: String                                        │
│ - cursoId: Long                                              │
│ - maestroId: Long                                            │
│ - fechaInicio: LocalDateTime                                 │
│ - fechaFin: LocalDateTime                                    │
│ - tiempoLimite: Int (minutos)                                │
│ - estado: EvaluacionEstado                                   │
│ - preguntas: List<Pregunta>                                  │
├──────────────────────────────────────────────────────────────┤
│ + getId(): Long                                              │
│ + getTitulo(): String                                        │
│ + isActiva(): Boolean                                        │
│ + tiempoRestante(): Duration                                 │
│ + puedeSerRealizada(): Boolean                               │
└──────────────────────────────────────────────────────────────┘
              △
              │
    ┌─────────┴─────────┐
    │                   │
┌───────────────┐  ┌──────────────┐
│ EvaluacionMC  │  │ EvaluacionVF │
│ (Múltiple     │  │ (Verdadero   │
│  Opción)      │  │  Falso)      │
└───────────────┘  └──────────────┘


┌──────────────────────────────────────────────────────────────┐
│                      Pregunta                                │
├──────────────────────────────────────────────────────────────┤
│ - id: Long                                                   │
│ - evaluacionId: Long                                         │
│ - contenido: String                                          │
│ - tipo: TipoPregunta                                         │
│ - puntaje: Double                                            │
│ - respuestaCorrecta: String                                  │
│ - opciones: List<String> (para múltiple opción)              │
├──────────────────────────────────────────────────────────────┤
│ + validarRespuesta(respuesta: String): Boolean               │
│ + calcularPuntaje(respuesta: String): Double                 │
└──────────────────────────────────────────────────────────────┘


┌──────────────────────────────────────────────────────────────┐
│                 RespuestaEstudiante                          │
├──────────────────────────────────────────────────────────────┤
│ - id: Long                                                   │
│ - preguntaId: Long                                           │
│ - estudianteId: Long                                         │
│ - evaluacionId: Long                                         │
│ - respuesta: String                                          │
│ - esCorrecta: Boolean                                        │
│ - puntajeObtenido: Double                                    │
│ - fechaRespuesta: LocalDateTime                              │
├──────────────────────────────────────────────────────────────┤
│ + guardar(): void                                            │
│ + actualizar(): void                                         │
│ + obtenerPuntaje(): Double                                   │
└──────────────────────────────────────────────────────────────┘


┌──────────────────────────────────────────────────────────────┐
│              GetEvaluacionesUseCase                          │
├──────────────────────────────────────────────────────────────┤
│ - evaluacionRepository: IEvaluacionRepository               │
├──────────────────────────────────────────────────────────────┤
│ + execute(estudianteId: Long): LiveData<List<Evaluacion>>   │
│ + getEvaluacionesPendientes(estudianteId): Flow             │
└──────────────────────────────────────────────────────────────┘

```

---

## 4. DIAGRAMA DE FLUJO DE NAVEGACION

```
                    ┌─────────────┐
                    │   Splash    │
                    │   Screen    │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │             │
                ┌───┤   Login     ├───┐
                │   │             │   │
                │   └─────────────┘   │
                │                     │
         Error │                      │ Success
                │                     │
         ┌──────▼──┐          ┌───────▼───────┐
         │ Reintentar
         │          │          │   Dashboard   │
         └──────────┘          └───┬───────┬───┘
                                   │       │
                    ┌──────────────┼───┐   │
                    │              │   │   │
            ┌───────▼─────┐  ┌─────▼──┴──────┐
            │ Evaluaciones│  │ Calificar     │
            │   (Alumno)  │  │ (Maestro)     │
            └───┬─────────┘  └──────────────┘
                │
        ┌───────▼─────────┐
        │ Realizar Eval.  │
        │                 │
        │ - Mostrar Qs    │
        │ - Timer         │
        │ - Responder     │
        │ - Enviar        │
        └────────┬────────┘
                 │
           ┌─────▼──────┐
           │ Resultado  │
           │ (Feedback) │
           └────────────┘

            ┌──────────────┐
            │ PQRS/Reclamos│
            │              │
            │ - Crear PQRS │
            │ - Ver Estado │
            │ - Respuestas │
            └──────────────┘
```

---

## 5. CAPAS DE ARQUITECTURA

### CAPA DE PRESENTACION (UI Layer)
- **Activities & Fragments**: Gestión de UI e interacción con usuario
- **ViewModels**: Gestión de estado y lógica de presentación
- **Adapters**: Adaptadores para listas y grillas
- **Tecnologías**: Fragment API, Material Design 3, Jetpack Compose (opcional)

### CAPA DE DOMINIO (Domain Layer)
- **UseCases**: Lógica de negocio encapsulada
- **Models**: Entidades de dominio (no dependen de Android)
- **Repository Interfaces**: Contratros para acceso a datos

### CAPA DE DATOS (Data Layer)
- **Remote**: Comunicación con API REST (Retrofit)
- **Local**: Base de datos local (Room) y SharedPreferences
- **Repositories**: Implementación de interfaces, fusión de datos
- **DTOs**: Mapeo de datos desde/hacia API

### CAPA DE INYECCION DE DEPENDENCIAS
- **Hilt/Dagger**: Gestión automática de dependencias

---

## 6. PATRONES DE DISEÑO IMPLEMENTADOS

| Patrón | Ubicación | Proposito |
|--------|-----------|-----------|
| **MVVM** | Presentation Layer | Separación de UI de lógica |
| **Repository** | Data Layer | Abstracción de datos |
| **UseCase** | Domain Layer | Encapsulación de lógica de negocio |
| **Singleton** | API Client, Database | Una única instancia |
| **Adapter** | UI Layer | Conversión de datos para vistas |
| **Observer** | ViewModels + LiveData | Reactivo a cambios |
| **Builder** | DTOs, Objetos complejos | Construcción de objetos |
| **Factory** | DI Container | Creación de objetos |
| **Facade** | ApiClient | Interfaz simplificada |

---

## 7. FLUJO DE DATOS - EJEMPLO: OBTENER EVALUACIONES

```
UI (Fragment)
    │
    ├─> ViewModel.getEvaluaciones()
    │
    └──> UseCase.execute()
         │
         └──> Repository.getEvaluaciones()
              │
              ├─> Check Local Cache (Room)
              │   └─> Si existe → Return
              │
              └─> Remote API Call (Retrofit)
                  │
                  ├─> Success → Save to Cache (Room)
                  │   │
                  │   └─> Return Flow<List<Evaluacion>>
                  │
                  └─> Error → Return Empty / Cached Data
                      │
                      └─> Emit Error Event
                          │
                          └─> UI shows Error Toast/Snackbar
```

---

## 8. TECNOLOGIAS Y DEPENDENCIAS

### Core
- Kotlin 1.9+
- Android API 24+ (Android 7.0)
- Gradle 8.x

### UI & Presentación
- AndroidX AppCompat
- Material Design 3
- ConstraintLayout
- RecyclerView
- Fragment API

### Arquitectura & DI
- Hilt (Dependency Injection)
- MVVM Architecture
- LiveData / Flow (Reactive)

### Datos
- Retrofit 2 (HTTP Client)
- OkHttp 4 (Interceptores, SSL)
- Room (Local Database)
- Datastore (Encrypted Preferences)

### Testing
- JUnit 4
- Mockito
- Espresso (UI Testing)

---

## 9. CONFIGURACION DE SERVIDOR Y ACCESOS

### Backend Requerido
```
URL Base: http://192.168.x.x:8080/api
Endpoints Principales:
  POST   /auth/login
  POST   /auth/logout
  GET    /evaluaciones
  GET    /evaluaciones/{id}
  POST   /evaluaciones/{id}/respuestas
  GET    /pqrs
  POST   /pqrs
  PUT    /pqrs/{id}/responder
```

### Credenciales de Prueba (Desarrollo)
```
Estudiante:
  Email: estudiante@universidad.edu
  Password: password123

Maestro:
  Email: maestro@universidad.edu
  Password: password123

Coordinador:
  Email: coordinador@universidad.edu
  Password: password123
```

### Database Local
```
App Database: QuimbayaEVAL.db (Room)
Tablas:
  - usuarios
  - evaluaciones
  - preguntas
  - respuestas_estudiante
  - pqrs
```

---

## 10. DOCUMENTACION DE AMBIENTES

### Desarrollo
- Android Studio Giraffe+
- API Level: 30 (Pruebas)
- Debug Keystore: ~/.android/debug.keystore

### Testing
- Emulador: Pixel 6 API 31
- Dispositivo Real: Android 7.0+

### Produccion
```
Build Variant: release
Proguard/R8: Habilitado
```

---

Fin de Documentación de Arquitectura
