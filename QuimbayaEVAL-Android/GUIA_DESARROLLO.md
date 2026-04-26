# GUIA DE DESARROLLO - GA8-220501096-AA2-EV02
## Aplicación Móvil QuimbayaEVAL para Android

---

## 1. INTRODUCCION

Esta guía documenta el desarrollo de la aplicación móvil QuimbayaEVAL para Android, implementada bajo los requisitos de la Evidencia GA8-220501096-AA2-EV02 del SENA.

### Objetivos
- Desarrollar módulos móviles para la plataforma Android
- Implementar patrones de arquitectura profesionales
- Seguir mejores prácticas de codificación
- Documentar completamente el desarrollo

### Alcance
- Módulo de Autenticación (Login)
- Módulo de Evaluaciones (Visualizar, Realizar)
- Módulo de Calificaciones (Maestros)
- Módulo de PQRS (Peticiones, Quejas, Reclamos, Sugerencias)

---

## 2. REQUERIMIENTOS DEL SISTEMA

### Requerimientos Funcionales

#### RF1: Autenticación de Usuarios
- El sistema debe permitir login con email y contraseña
- El sistema debe diferenciar roles (Estudiante, Maestro, Coordinador)
- El sistema debe mantener sesión con JWT tokens
- El sistema debe permitir logout

#### RF2: Gestión de Evaluaciones (Estudiante)
- Visualizar evaluaciones pendientes
- Ver detalles de evaluación
- Realizar evaluación con timer
- Responder diferentes tipos de preguntas
- Enviar respuestas
- Ver resultados con feedback

#### RF3: Calificación (Maestro)
- Visualizar evaluaciones creadas
- Calificar respuestas de estudiantes
- Agregar feedback personalizado
- Generar reportes básicos

#### RF4: PQRS
- Crear peticiones, quejas, reclamos o sugerencias
- Ver historial de PQRS
- Recibir respuestas
- Seguimiento de estado

### Requerimientos No Funcionales

#### RNF1: Performance
- Tiempo de carga < 2 segundos
- Cache local de datos
- Sincronización eficiente

#### RNF2: Seguridad
- Autenticación JWT
- Encriptación de datos sensibles
- Validación de permisos

#### RNF3: Usabilidad
- Interfaz intuitiva
- Accesibilidad WCAG 2.1 AA
- Soporte offline básico

---

## 3. ARQUITECTURA Y DIAGRAMAS

### Diagrama de Componentes
Ver ARQUITECTURA.md - Sección 1

### Diagrama de Paquetes
Ver ARQUITECTURA.md - Sección 2

### Diagrama de Clases
Ver ARQUITECTURA.md - Sección 3

### Flujo de Navegación
Ver ARQUITECTURA.md - Sección 4

---

## 4. METODOLOGIA DE DESARROLLO

### Framework: Clean Architecture + MVVM

```
┌─────────────────────────────────────┐
│     Presentation (UI)               │
│  Activities, Fragments, ViewModels  │
└────────────────┬────────────────────┘
                 │
┌────────────────┴────────────────────┐
│     Domain (Business Logic)         │
│  UseCases, Models, Repositories (I) │
└────────────────┬────────────────────┘
                 │
┌────────────────┴────────────────────┐
│     Data (Repositories)             │
│  Remote API, Local DB, Cache        │
└─────────────────────────────────────┘
```

### Ciclo de Desarrollo

1. **Análisis**: Entender requerimientos
2. **Diseño**: Crear diagramas y modelos
3. **Implementación**: Codificar módulos
4. **Testing**: Pruebas unitarias e integración
5. **Documentación**: Comentarios y documentos

---

## 5. DIAGRAMAS DE CLASES DETALLADOS

### Módulo de Evaluaciones

```
┌──────────────────────────────────┐
│         Evaluacion               │
├──────────────────────────────────┤
│ - id: Long                       │
│ - titulo: String                 │
│ - descripcion: String            │
│ - cursoId: Long                  │
│ - maestroId: Long                │
│ - fechaInicio: LocalDateTime     │
│ - fechaFin: LocalDateTime        │
│ - tiempoLimite: Int              │
│ - estado: EvaluacionEstado       │
│ - preguntas: List<Pregunta>      │
├──────────────────────────────────┤
│ + getId(): Long                  │
│ + isActiva(): Boolean            │
│ + tiempoRestante(): Duration     │
│ + puedeSerRealizada(): Boolean   │
└──────────────────────────────────┘

┌──────────────────────────────────┐
│         Pregunta                 │
├──────────────────────────────────┤
│ - id: Long                       │
│ - evaluacionId: Long             │
│ - contenido: String              │
│ - tipo: TipoPregunta             │
│ - puntaje: Double                │
│ - respuestaCorrecta: String      │
│ - opciones: List<String>         │
├──────────────────────────────────┤
│ + validarRespuesta(r: String)    │
│ + calcularPuntaje(r: String)     │
└──────────────────────────────────┘

┌──────────────────────────────────┐
│   RespuestaEstudiante            │
├──────────────────────────────────┤
│ - id: Long                       │
│ - preguntaId: Long               │
│ - estudianteId: Long             │
│ - evaluacionId: Long             │
│ - respuesta: String              │
│ - esCorrecta: Boolean            │
│ - puntajeObtenido: Double        │
│ - fechaRespuesta: LocalDateTime  │
├──────────────────────────────────┤
│ + guardar(): void                │
│ + actualizar(): void             │
│ + obtenerPuntaje(): Double       │
└──────────────────────────────────┘
```

---

## 6. MAPA DE NAVEGACION

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
         ┌──────▼──┐          ┌───────▼─────────┐
         │ Reintentar          │   Dashboard      │
         │          │          │   (Según Rol)    │
         └──────────┘          └───┬──────────┬───┘
                                   │          │
                    ┌──────────────┼──────────┼───────┐
                    │              │          │       │
            ┌───────▼─────┐ ┌──────▼────┐ ┌──▼────┐ │
            │Evaluaciones │ │Calificar  │ │PQRS   │ │
            │(Alumno)     │ │(Maestro)  │ │       │ │
            └───┬─────────┘ └───────────┘ └───────┘ │
                │                                     │
        ┌───────▼──────────┐               ┌─────────▼──┐
        │ Realizar Eval.   │               │ Mi Perfil  │
        │ - Timer          │               │ - Logout   │
        │ - Preguntas      │               │ - Config   │
        │ - Responder      │               └────────────┘
        │ - Enviar         │
        └────────┬─────────┘
                 │
           ┌─────▼──────┐
           │ Resultado  │
           │ (Feedback) │
           └────────────┘
```

---

## 7. PATRONES DE DISEÑO

### MVVM (Model-View-ViewModel)
```kotlin
// View (Fragment/Activity)
class EvaluacionesFragment: Fragment() {
    private val viewModel: EvaluacionViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Observar cambios
        viewModel.evaluaciones.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }
}

// ViewModel
class EvaluacionViewModel @Inject constructor(
    val getEvaluacionesUseCase: GetEvaluacionesUseCase
): ViewModel() {
    val evaluaciones = MutableLiveData<List<Evaluacion>>()
    
    fun loadEvaluaciones() {
        viewModelScope.launch {
            val result = getEvaluacionesUseCase.execute()
            evaluaciones.value = result
        }
    }
}
```

### Repository Pattern
```kotlin
interface IEvaluacionRepository {
    suspend fun getEvaluaciones(estudianteId: Long): List<Evaluacion>
    suspend fun getEvaluacion(id: Long): Evaluacion
    suspend fun submitEvaluacion(evaluacionId: Long, respuestas: Map<String, String>): Result
}

class EvaluacionRepository @Inject constructor(
    private val apiService: QuimbayaEVALService,
    private val database: AppDatabase
): IEvaluacionRepository {
    override suspend fun getEvaluaciones(estudianteId: Long): List<Evaluacion> {
        return try {
            val response = apiService.getEvaluaciones(estudianteId)
            if (response.isSuccessful) {
                // Guardar en cache
                database.evaluacionDao().insertAll(response.body() ?: emptyList())
                response.body() ?: emptyList()
            } else {
                // Devolver del cache
                database.evaluacionDao().getAllByEstudiante(estudianteId)
            }
        } catch (e: Exception) {
            // Error de red, devolver cache
            database.evaluacionDao().getAllByEstudiante(estudianteId)
        }
    }
}
```

### UseCase Pattern
```kotlin
class GetEvaluacionesUseCase @Inject constructor(
    private val repository: IEvaluacionRepository
) {
    suspend fun execute(estudianteId: Long): Result<List<Evaluacion>> {
        return try {
            val evaluaciones = repository.getEvaluaciones(estudianteId)
            if (evaluaciones.isEmpty()) {
                Result.failure(Exception("No hay evaluaciones disponibles"))
            } else {
                Result.success(evaluaciones)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

---

## 8. CODIGO FUENTE - EJEMPLOS

Ver archivos incluidos:
- MainActivity.kt
- LoginViewModel.kt
- LoginUseCase.kt
- RetrofitClient.kt
- QuimbayaEVALService.kt
- build.gradle.kts

---

## 9. PRUEBAS UNITARIAS

### Ejemplo: Test de LoginViewModel

```kotlin
@RunWith(AndroidTestRunner::class)
class LoginViewModelTest {
    
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    private val mockUseCase: LoginUseCase = mock()
    private lateinit var viewModel: LoginViewModel
    
    @Before
    fun setup() {
        viewModel = LoginViewModel(mockUseCase)
    }
    
    @Test
    fun login_withValidCredentials_shouldEmitSuccess() = runTest {
        // Arrange
        val email = "test@test.com"
        val password = "password123"
        val usuario = Usuario(1, email, "Nombre", "ESTUDIANTE")
        whenever(mockUseCase.execute(email, password))
            .thenReturn(Result.success(usuario))
        
        // Act
        viewModel.login(email, password)
        
        // Assert
        val state = viewModel.uiState.value
        assertEquals(true, state.isLoggedIn)
        assertEquals(null, state.error)
    }
    
    @Test
    fun login_withInvalidEmail_shouldShowError() = runTest {
        // Arrange
        val email = "invalid"
        val password = "password123"
        
        // Act
        viewModel.login(email, password)
        
        // Assert
        val state = viewModel.uiState.value
        assertNotNull(state.error)
    }
}
```

---

## 10. CONFIGURACION DE AMBIENTES

### Desarrollo
```
- Android Studio Giraffe+
- SDK Level 34
- Emulator: Pixel 6 API 31
- Backend: http://10.0.2.2:8080/api
```

### Testing
```
- Device: Pixel 6 Pro API 33
- Logcat para debugging
- Profiler para performance
```

### Produccion
```
- Build Type: Release
- Proguard/R8 Enabled
- Signing Config Configurado
- Base URL: https://api.quimbayaeval.com/api
```

---

## 11. CONTROL DE VERSIONES GIT

### Estructura de Ramas
```
main/
├── develop/
│   ├── feature/authentication
│   ├── feature/evaluaciones
│   ├── feature/pqrs
│   ├── bugfix/login-crash
│   └── release/v1.0.0
```

### Commits
```bash
git commit -m "feat: Implementar módulo de autenticación"
git commit -m "fix: Corregir error en validación de email"
git commit -m "docs: Actualizar guía de desarrollo"
git commit -m "test: Agregar pruebas para LoginViewModel"
```

---

## 12. LIBRERÍAS Y FRAMEWORKS

### Core
- Kotlin 1.9+
- Coroutines
- AndroidX Libraries

### UI
- Material Design 3
- ConstraintLayout
- RecyclerView

### Arquitectura
- Hilt (Dependency Injection)
- MVVM Pattern
- Repository Pattern

### Networking
- Retrofit 2
- OkHttp 3
- Interceptors

### Base de Datos
- Room (SQLite)
- DataStore (Preferences)

### Testing
- JUnit 4
- Mockito
- Espresso

---

## 13. BUENAS PRACTICAS IMPLEMENTADAS

### Código
✓ Usar nombres descriptivos para clases y métodos
✓ Mantener métodos pequeños y enfocados
✓ Seguir convenciones de Kotlin
✓ Usar tipos específicos en lugar de Any
✓ Manejar excepciones apropiadamente
✓ Documentar código complejo

### Arquitectura
✓ Separación clara de capas
✓ Inyección de dependencias
✓ No mezclar responsabilidades
✓ Usar interfaces para abstraer
✓ Implementar Repository Pattern
✓ Usar UseCases para lógica de negocio

### UI
✓ Reutilizar componentes
✓ Usar DataBinding o ViewBinding
✓ Implementar navegación clara
✓ Manejar estados de carga
✓ Mostrar feedback al usuario
✓ Validar inputs del usuario

### Performance
✓ Usar lazy loading
✓ Implementar caching
✓ Evitar operaciones en main thread
✓ Usar Coroutines para async
✓ Optimizar queries de base de datos
✓ Monitorear memory leaks

### Seguridad
✓ Almacenar tokens de forma segura
✓ Usar HTTPS
✓ Validar entrada del usuario
✓ No loguear datos sensibles
✓ Usar ProGuard/R8 en release

---

## 14. CONCLUSIONES

La aplicación QuimbayaEVAL Android implementa una arquitectura profesional siguiendo Clean Architecture + MVVM, asegurando:

- **Mantenibilidad**: Código organizado y documentado
- **Testabilidad**: Fácil escribir pruebas unitarias
- **Escalabilidad**: Agregar nuevos módulos sin afectar existentes
- **Seguridad**: Autenticación y encriptación implementadas
- **Performance**: Caching y lazy loading optimizados

---

**Documento Generado**: 25 de Abril de 2026
**Versión**: 1.0.0
**Status**: Completado
