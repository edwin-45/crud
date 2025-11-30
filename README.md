# 🎵 API REST de Gestión de Listas de Reproducción

API RESTful desarrollada con Spring Boot para gestionar listas de reproducción y canciones con autenticación JWT.

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Endpoints de la API](#-endpoints-de-la-api)
- [Autenticación](#-autenticación)
- [Ejemplos de Uso](#-ejemplos-de-uso)
- [Testing](#-testing)
- [Seguridad](#-seguridad)
- [Modelo de Datos](#-modelo-de-datos)
- [Despliegue](#-despliegue)
- [Solución de Problemas](#-solución-de-problemas)

---

## ✨ Características

- ✅ CRUD completo de listas de reproducción
- ✅ CRUD completo de canciones
- ✅ Gestión de usuarios y roles
- ✅ Autenticación y autorización con JWT
- ✅ Relaciones bidireccionales entre entidades
- ✅ Validaciones de datos
- ✅ Tests unitarios y de integración
- ✅ Seguridad con Spring Security 6
- ✅ API RESTful con buenas prácticas

---

## 🛠 Tecnologías

| Tecnología | Versión |
|-----------|---------|
| **Java** | 17+ |
| **Spring Boot** | 3.4.0 |
| **Spring Security** | 6.x |
| **Spring Data JPA** | Incluido en Spring Boot |
| **JWT** | JSON Web Tokens |
| **MySQL** | 8.0+ |
| **Gradle** | 8.0+ |
| **JUnit 5** | Testing Framework |
| **Mockito** | Mocking Framework |

---

## 📦 Requisitos Previos

Asegúrate de tener instalado:

- ☑️ **JDK 17** o superior
- ☑️ **MySQL 8.0+**
- ☑️ **Gradle 8.0+** (o usa el wrapper incluido)
- ☑️ **Postman** o **cURL** (para probar la API)

---

## 🚀 Instalación

### 1️⃣ Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd crud
```

### 2️⃣ Configurar la base de datos

Crea una base de datos en MySQL:

```sql
CREATE DATABASE crud_db;
```

### 3️⃣ Configurar `application.properties`

Edita el archivo `src/main/resources/application.properties`:

```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/crud_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# JWT Configuration
security.jwt.secret-key=tu_clave_secreta_muy_larga_y_segura_de_al_menos_256_bits
security.jwt.expiration-time=3600000
```

### 4️⃣ Compilar y ejecutar

```bash
# Compilar el proyecto
./gradlew build

# Ejecutar la aplicación
./gradlew bootRun
```

✅ La aplicación estará disponible en: **`http://localhost:8080`**

---

## ⚙️ Configuración

### Variables de Entorno (Opcional)

Puedes configurar variables de entorno en lugar de usar `application.properties`:

```bash
export DB_URL=jdbc:mysql://localhost:3306/crud_db
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_contraseña
export JWT_SECRET=tu_clave_secreta
```

### Configuración de JWT

En `TokenJwtConfig.java`:

```java
public static final String SECRET_KEY = "tu_clave_secreta";
public static final String HEADER_AUTHORIZATION = "Authorization";
public static final String PREFIX_TOKEN = "Bearer ";
```

---

## 📁 Estructura del Proyecto

```
crud/
├── src/
│   ├── main/
│   │   ├── java/com/torrado/crud/
│   │   │   ├── CrudApplication.java         # 🚀 Clase principal
│   │   │   │
│   │   │   ├── controllers/                 # 🎮 Controladores REST
│   │   │   │   ├── ListaReproduccionController.java
│   │   │   │   └── UserController.java
│   │   │   │
│   │   │   ├── entities/                    # 🗄️ Entidades JPA
│   │   │   │   ├── Cancion.java
│   │   │   │   ├── ListaReproduccion.java
│   │   │   │   ├── Role.java
│   │   │   │   └── User.java
│   │   │   │
│   │   │   ├── repositories/                # 📦 Repositorios JPA
│   │   │   │   ├── CancionRepository.java
│   │   │   │   ├── ListaReproduccionRepository.java
│   │   │   │   ├── RolRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   │
│   │   │   ├── services/                    # 💼 Lógica de negocio
│   │   │   │   ├── CancionService.java
│   │   │   │   ├── CancionServiceImpl.java
│   │   │   │   ├── JpaUserDetailsService.java
│   │   │   │   ├── ListaReproduccionService.java
│   │   │   │   ├── ListaReproduccionServiceImpl.java
│   │   │   │   ├── UserService.java
│   │   │   │   └── UserServiceImp.java
│   │   │   │
│   │   │   └── security/                    # 🔒 Configuración de seguridad
│   │   │       ├── SimpleGrantedAuthorityJsonCreator.java
│   │   │       ├── SpringSecurityConfig.java
│   │   │       ├── TokenJwtConfig.java
│   │   │       └── filters/
│   │   │           ├── JwtAuthenticationFilter.java
│   │   │           └── JwtValidationFilter.java
│   │   │
│   │   └── resources/
│   │       └── application.properties       # ⚙️ Configuración
│   │
│   └── test/                                # 🧪 Tests
│       └── java/com/torrado/crud/
│           ├── ListaReproduccionControllerTest.java
│           ├── ListaReproduccionServiceTest.java
│           └── ListaReproduccionRepositoryTest.java
│
├── build.gradle                             # 📦 Configuración de Gradle
└── README.md                                # 📖 Documentación
```

---

## 🌐 Endpoints de la API

### 🔐 Autenticación

#### Login

```http
POST /login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta exitosa (200 OK):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 👥 Usuarios

#### Registrar Usuario

```http
POST /users/register
Content-Type: application/json

{
  "username": "nuevo_usuario",
  "password": "password123",
  "email": "usuario@example.com"
}
```

#### Obtener Todos los Usuarios

```http
GET /users
Authorization: Bearer {token}
```

**Requiere:** `ROLE_ADMIN`

#### Crear Usuario (Admin)

```http
POST /users
Authorization: Bearer {token}
Content-Type: application/json

{
  "username": "usuario",
  "password": "password123",
  "email": "usuario@example.com",
  "admin": false
}
```

**Requiere:** `ROLE_ADMIN`

---

### 🎵 Listas de Reproducción

#### Obtener todas las listas

```http
GET /lists
Authorization: Bearer {token}
```

**Respuesta:**

```json
[
  {
    "id": 1,
    "nombre": "Rock Classics",
    "descripcion": "Las mejores canciones de rock",
    "canciones": [
      {
        "id": 1,
        "titulo": "Bohemian Rhapsody",
        "artista": "Queen",
        "album": "A Night at the Opera",
        "genero": "Rock"
      }
    ]
  }
]
```

#### Obtener lista por nombre

```http
GET /lists/{nombre}
Authorization: Bearer {token}
```

**Ejemplo:**

```http
GET /lists/Rock Classics
Authorization: Bearer {token}
```

#### Crear nueva lista

```http
POST /lists
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Mi Lista Favorita",
  "descripcion": "Descripción de la lista",
  "canciones": []
}
```

**Respuesta (201 Created):**

```json
{
  "id": 1,
  "nombre": "Mi Lista Favorita",
  "descripcion": "Descripción de la lista",
  "canciones": []
}
```

**Headers de respuesta:**

```
Location: /lists/Mi Lista Favorita
```

#### Agregar canción a lista

```http
POST /lists/{nombre}/canciones
Authorization: Bearer {token}
Content-Type: application/json

{
  "titulo": "Stairway to Heaven",
  "artista": "Led Zeppelin",
  "album": "Led Zeppelin IV",
  "genero": "Rock"
}
```

#### Eliminar canción de lista

```http
DELETE /lists/{nombre}/canciones/{idCancion}
Authorization: Bearer {token}
```

**Respuesta (200 OK):** Retorna la lista actualizada sin la canción eliminada

#### Eliminar lista

```http
DELETE /lists/{nombre}
Authorization: Bearer {token}
```

**Respuesta (204 No Content)**

---

## 🔑 Autenticación

La API usa **JWT (JSON Web Tokens)** para autenticación:

### Flujo de Autenticación

1. **Obtener token:** Realiza login en `/login`
2. **Usar token:** Incluye el token en el header `Authorization: Bearer {token}`
3. **Expiración:** Los tokens expiran después de **1 hora** (configurable)

### Ejemplo con cURL

```bash
# 1. Login y obtener token
TOKEN=$(curl -s -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' \
  | jq -r '.token')

# 2. Usar el token en las peticiones
curl http://localhost:8080/lists \
  -H "Authorization: Bearer $TOKEN"
```

### Ejemplo con Postman

1. Crear una nueva request
2. En la pestaña **Headers**, agregar:
   - **Key:** `Authorization`
   - **Value:** `Bearer {tu_token}`

### Estructura del Token JWT

El token JWT contiene la siguiente información codificada:

```json
{
  "sub": "admin",
  "authorities": [
    {
      "authority": "ROLE_ADMIN"
    }
  ],
  "iat": 1638360000,
  "exp": 1638363600
}
```

---

## 💡 Ejemplos de Uso

### Flujo completo: Crear una lista y agregar canciones

```bash
# 1. Login
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Respuesta: {"token":"eyJ..."}

# 2. Crear lista
curl -X POST http://localhost:8080/lists \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Rock Clásico",
    "descripcion": "Las mejores canciones de rock",
    "canciones": []
  }'

# 3. Agregar canción
curl -X POST "http://localhost:8080/lists/Rock%20Clásico/canciones" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Bohemian Rhapsody",
    "artista": "Queen",
    "album": "A Night at the Opera",
    "genero": "Rock"
  }'

# 4. Ver la lista completa
curl "http://localhost:8080/lists/Rock%20Clásico" \
  -H "Authorization: Bearer {token}"

# 5. Eliminar una canción
curl -X DELETE "http://localhost:8080/lists/Rock%20Clásico/canciones/1" \
  -H "Authorization: Bearer {token}"

# 6. Eliminar la lista
curl -X DELETE "http://localhost:8080/lists/Rock%20Clásico" \
  -H "Authorization: Bearer {token}"
```

### Gestión de Usuarios

```bash
# 1. Registrar nuevo usuario (público)
curl -X POST http://localhost:8080/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nuevo_usuario",
    "password": "password123",
    "email": "usuario@example.com"
  }'

# 2. Obtener lista de usuarios (requiere ADMIN)
curl http://localhost:8080/users \
  -H "Authorization: Bearer {admin_token}"

# 3. Crear usuario con rol específico (requiere ADMIN)
curl -X POST http://localhost:8080/users \
  -H "Authorization: Bearer {admin_token}" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "otro_usuario",
    "password": "password123",
    "email": "otro@example.com",
    "admin": true
  }'
```

---

## 🧪 Testing

### Ejecutar todos los tests

```bash
./gradlew test
```

### Ejecutar tests específicos

```bash
# Tests del repositorio
./gradlew test --tests ListaReproduccionRepositoryTest

# Tests del servicio
./gradlew test --tests ListaReproduccionServiceTest

# Tests del controlador
./gradlew test --tests ListaReproduccionControllerTest
```

### Ver reporte de cobertura

```bash
./gradlew test jacocoTestReport
```

📊 **Reporte HTML disponible en:** `build/reports/tests/test/index.html`

### Configuración de tests

Agrega en `build.gradle` para evitar advertencias de JVM:

```gradle
tasks.withType(Test) {
    useJUnitPlatform()
    jvmArgs '-Xshare:off'
}
```

### Tipos de tests incluidos

| Tipo | Descripción | Anotación Principal | Mockea | Base de Datos |
|------|-------------|---------------------|--------|---------------|
| **Repositorios** | Tests de integración | `@DataJpaTest` | No | H2 en memoria |
| **Servicios** | Tests unitarios | `@ExtendWith(MockitoExtension.class)` | Repositories | No |
| **Controladores** | Tests de integración | `@SpringBootTest` + `@AutoConfigureMockMvc` | Services | No |
| **Seguridad** | Tests con usuarios mock | `@WithMockUser` | - | No |

### Ejemplos de tests

#### Test de Repositorio

```java
@DataJpaTest
class ListaReproduccionRepositoryTest {
    @Autowired
    private ListaReproduccionRepository repository;

    @Test
    void deberiaGuardarLista() {
        ListaReproduccion lista = new ListaReproduccion();
        lista.setNombre("Test");
        
        ListaReproduccion guardada = repository.save(lista);
        
        assertThat(guardada.getId()).isNotNull();
    }
}
```

#### Test de Servicio

```java
@ExtendWith(MockitoExtension.class)
class ListaReproduccionServiceTest {
    @Mock
    private ListaReproduccionRepository repository;
    
    @InjectMocks
    private ListaReproduccionServiceImpl service;

    @Test
    void deberiaAgregarCancion() {
        // Test implementation
    }
}
```

#### Test de Controlador

```java
@SpringBootTest
@AutoConfigureMockMvc
class ListaReproduccionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private ListaReproduccionService service;

    @Test
    @WithMockUser
    void deberiaObtenerListas() throws Exception {
        mockMvc.perform(get("/lists"))
            .andExpect(status().isOk());
    }
}
```

---

## 🔒 Seguridad

### Roles y Permisos

| Role | Descripción | Permisos |
|------|-------------|----------|
| **ROLE_USER** | Usuario básico | Ver y gestionar listas y canciones |
| **ROLE_ADMIN** | Administrador | Acceso completo + gestión de usuarios |

### Endpoints por Rol

| Endpoint | Método | ROLE_USER | ROLE_ADMIN |
|----------|--------|-----------|------------|
| `/login` | POST | ✅ Público | ✅ Público |
| `/users/register` | POST | ✅ Público | ✅ Público |
| `/users` | GET | ❌ | ✅ |
| `/users` | POST | ❌ | ✅ |
| `/lists` | GET | ✅ | ✅ |
| `/lists` | POST | ✅ | ✅ |
| `/lists/{nombre}` | GET | ✅ | ✅ |
| `/lists/{nombre}` | DELETE | ✅ | ✅ |
| `/lists/{nombre}/canciones` | POST | ✅ | ✅ |
| `/lists/{nombre}/canciones/{id}` | DELETE | ✅ | ✅ |

### Configuración de Seguridad

En `SpringSecurityConfig.java`:

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/users/register").permitAll()
            .requestMatchers(HttpMethod.GET, "/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
            .requestMatchers("/lists/**").authenticated()
            .anyRequest().authenticated()
        )
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtValidationFilter(authenticationManager()))
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
}
```

### CORS Configuration

```java
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

### Buenas Prácticas Implementadas

| ✅ Característica | Descripción |
|------------------|-------------|
| **Contraseñas hasheadas** | BCrypt con strength 12 |
| **Tokens JWT firmados** | Algoritmo HS256 con clave secreta |
| **CORS configurado** | Permite orígenes específicos |
| **CSRF deshabilitado** | API stateless (solo JWT) |
| **Sesiones stateless** | Sin sesiones HTTP |
| **Validaciones** | Validación de entrada en endpoints |

---

## 📝 Notas Adicionales

### Datos Iniciales

Al iniciar la aplicación por primera vez, puedes crear usuarios de prueba ejecutando:

#### 👤 Usuario Administrador

```bash
curl -X POST http://localhost:8080/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@example.com"
  }'
```

Luego agregar el rol ADMIN manualmente en la base de datos.

#### 👤 Usuario Normal

```bash
curl -X POST http://localhost:8080/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "user123",
    "email": "user@example.com"
  }'
```

### Validaciones

La API valida:

- ✅ **Nombre de lista:** No puede ser null o vacío
- ✅ **Nombre único:** No pueden existir dos listas con el mismo nombre
- ✅ **Lista existente:** Verifica que exista antes de agregar/eliminar canciones
- ✅ **Canción existente:** Verifica que exista antes de eliminar
- ✅ **Usuario:** Username debe ser único
- ✅ **Token JWT:** Válido y no expirado

---

## 📊 Modelo de Datos

### Diagrama de Entidades

```
┌─────────────────────┐       ┌──────────────────┐
│     User            │       │   Role           │
├─────────────────────┤       ├──────────────────┤
│ id (PK)             │       │ id (PK)          │
│ username (unique)   │───────│ name             │
│ password (hashed)   │  n:m  │                  │
│ email               │       │                  │
│ enabled             │       │                  │
└─────────────────────┘       └──────────────────┘

┌───────────────────────────┐     ┌─────────────────────┐
│ ListaReproduccion         │     │   Cancion           │
├───────────────────────────┤     ├─────────────────────┤
│ id (PK)                   │     │ id (PK)             │
│ nombre (unique)           │─────│ titulo              │
│ descripcion               │ 1:n │ artista             │
│ canciones (List)          │     │ album               │
└───────────────────────────┘     │ genero              │
                                  │ lista_id (FK)       │
                                  └─────────────────────┘
```

### Entidades

#### User

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    private String email;
    private boolean enabled;
    
    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
```

#### ListaReproduccion

```java
@Entity
@Table(name = "listas_reproduccion")
public class ListaReproduccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @OneToMany(mappedBy = "listaReproduccion", 
               cascade = CascadeType.ALL, 
               orphanRemoval = true)
    private List<Cancion> canciones = new ArrayList<>();
}
```

#### Cancion

```java
@Entity
@Table(name = "canciones")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(nullable = false)
    private String artista;
    
    private String album;
    private String genero;
    
    @ManyToOne
    @JoinColumn(name = "lista_reproduccion_id")
    private ListaReproduccion listaReproduccion;
}
```

### Relaciones

- **User ↔ Role:** Relación Many-to-Many (un usuario puede tener varios roles)
- **ListaReproduccion ↔ Cancion:** Relación One-to-Many bidireccional (una lista puede tener muchas canciones, cada canción pertenece a una lista)

---

## 🚀 Despliegue

### Variables de entorno recomendadas para producción

```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:mysql://tu-servidor:3306/crud_db
export DB_USERNAME=usuario_produccion
export DB_PASSWORD=contraseña_segura
export JWT_SECRET=clave_secreta_muy_larga_y_aleatoria_minimo_256_bits
export JWT_EXPIRATION=3600000
```

### Construcción para producción

```bash
# Compilar sin tests
./gradlew build -x test

# Generar JAR ejecutable
./gradlew bootJar

# El JAR estará en: build/libs/crud-*.jar
```

### Ejecutar en producción

```bash
java -jar build/libs/crud-*.jar \
  --spring.profiles.active=prod \
  --server.port=8080
```

### Docker (Opcional)

Crea un `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Construir y ejecutar:

```bash
# Construir imagen
docker build -t crud-api .

# Ejecutar contenedor
docker run -p 8080:8080 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/crud_db \
  -e DB_USERNAME=usuario \
  -e DB_PASSWORD=password \
  crud-api
```

---

## 🛠 Solución de Problemas

### ❌ Error de conexión a la base de datos

**Síntoma:**
```
com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
```

**Solución:**

```bash
# Verificar que MySQL esté corriendo
# Linux/Mac
sudo systemctl status mysql
sudo systemctl start mysql

# Windows
net start MySQL
```

Verifica también las credenciales en `application.properties`.

---

### ❌ Error de autenticación JWT

**Síntoma:**
```
403 Forbidden
```

**Solución:**

- ☑️ Verifica que el token no haya expirado
- ☑️ Verifica que el header `Authorization` tenga el formato: `Bearer {token}`
- ☑️ Asegúrate de usar el token completo sin modificaciones
- ☑️ Verifica que el usuario tenga los permisos necesarios

**Validar token en consola:**

```bash
# Extraer y decodificar payload del token
echo "tu_token" | cut -d'.' -f2 | base64 -d | jq
```

---

### ❌ Error 400 Bad Request al crear lista

**Síntoma:**
```json
{
  "status": 400,
  "error": "Bad Request"
}
```

**Causas comunes:**

1. Nombre de lista vacío o null
2. Lista con el mismo nombre ya existe
3. JSON malformado

**Solución:**

Verifica que el JSON sea válido:

```json
{
  "nombre": "Mi Lista",
  "descripcion": "Descripción válida",
  "canciones": []
}
```

---

### ❌ Tests fallan

**Solución:**

```bash
# Limpiar y reconstruir
./gradlew clean build

# Ejecutar con más información
./gradlew test --info

# Ver stacktrace completo
./gradlew test --stacktrace
```

---

### ⚠️ Advertencia de OpenJDK en tests

**Síntoma:**
```
OpenJDK 64-Bit Server VM warning: Sharing is only supported...
```

**Solución:** Agrega en `build.gradle`:

```gradle
tasks.withType(Test) {
    useJUnitPlatform()
    jvmArgs '-Xshare:off'
}
```

---

### ⚠️ Deprecación de `@MockBean`

**Síntoma:**
```
'org.springframework.boot.test.mock.mockito.MockBean' is deprecated
```

**Solución:** Desde Spring Boot 3.4.0, usa `@MockitoBean`:

```java
// ❌ Antiguo
import org.springframework.boot.test.mock.mockito.MockBean;
@MockBean
private ListaReproduccionService service;

// ✅ Nuevo
import org.springframework.boot.test.mock.mockito.MockitoBean;
@MockitoBean
private ListaReproduccionService service;
```

---

### ❌ Error CORS desde frontend

**Síntoma:**
```
Access to XMLHttpRequest has been blocked by CORS policy
```

**Solución:**

Verifica la configuración de CORS en `SpringSecurityConfig.java`:

```java
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Tu origen
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

---

## 📚 Recursos Adicionales

### Documentación Oficial

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JWT.io](https://jwt.io/)

### Tutoriales Recomendados

- [Spring Boot REST API Tutorial](https://spring.io/guides/tutorials/rest/)
- [Spring Security JWT Authentication](https://www.baeldung.com/spring-security-oauth-jwt)

---

## 📄 Licencia

Este proyecto está bajo la **Licencia MIT**.

---

## 👤 Autor

**Torrado**

- GitHub: [@torrado](https://github.com/torrado)
- Email: torrado@example.com

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📞 Soporte

**¿Necesitas ayuda?**

- 📧 Abre un **issue** en el repositorio
- 📚 Consulta la [documentación de Spring Boot](https://spring.io/projects/spring-boot)
- 🔍 Revisa la sección de [Solución de Problemas](#-solución-de-problemas)
- 💬 Únete a nuestro canal de Discord (próximamente)

---

## 📈 Roadmap

### Versión 2.0 (Próximamente)

- [ ] Implementar paginación en listados
- [ ] Agregar búsqueda y filtros avanzados
- [ ] Integración con Spotify API
- [ ] Sistema de favoritos
- [ ] Compartir listas entre usuarios
- [ ] Documentación con Swagger/OpenAPI
- [ ] Metrics y monitoring con Actuator
- [ ] Cache con Redis
- [ ] Logs estructurados con ELK Stack

---

## 🎯 Estado del Proyecto

![Estado](https://img.shields.io/badge/Estado-Activo-success)
![Versión](https://img.shields.io/badge/Versión-1.0.0-blue)
![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-green)
![Licencia](https://img.shields.io/badge/Licencia-MIT-yellow)

---

**⭐ Si este proyecto te fue útil, considera darle una estrella en GitHub**

---

## 📝 Changelog

### v1.0.0 (2025-01-30)

#### ✨ Características Iniciales

- Implementación completa de CRUD de listas de reproducción
- Gestión de canciones dentro de listas
- Sistema de autenticación con JWT
- Gestión de usuarios y roles
- Tests unitarios y de integración
- Documentación completa

#### 🔧 Tecnologías

- Spring Boot 3.4.0
- Spring Security 6
- JWT Authentication
- MySQL Database
- JUnit 5 & Mockito

---

**Última actualización:** 30 de enero de 2025

