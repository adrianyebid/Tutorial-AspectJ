# 🎤 Guion Completo de Exposición — Tutorial AspectJ
## Programación Orientada a Aspectos

> **Duración total:** 35–40 minutos  
> **Integrantes:** 4  
> **Modalidad:** Tutorial práctico con demo en vivo

---

## ⏱️ Distribución de tiempos

| Parte | Expositor | Tiempo | Contenido |
|-------|-----------|--------|-----------|
| Apertura | Integrante 1 | 2 min | Introducción del tema y del equipo |
| Parte 1 | Integrante 1 | 8 min | AOP: conceptos fundamentales |
| Parte 2 | Integrante 2 | 9 min | Instalación + Ejemplos básicos |
| Parte 3 | Integrante 3 | 9 min | Ejemplos intermedios |
| Parte 4 | Integrante 4 | 7 min | Ejemplos avanzados |
| Cierre | Integrante 4 | 3 min | Conclusiones + Taller |
| Preguntas | Todos | 2-5 min | Q&A |

---

---

# 🎙️ INTEGRANTE 1 — Fundamentos AOP (10 minutos)

---

## SLIDE 1 — Portada (30 segundos)

**[El integrante 1 sale al frente]**

> "Buenos días/tardes. Somos el grupo [nombre], y hoy vamos a presentarles el tutorial sobre **Programación Orientada a Aspectos con AspectJ**.
>
> En los próximos 35 minutos vamos a ir desde los fundamentos teóricos hasta ejemplos avanzados funcionando en código real. Y al final les dejaremos un mini taller para que practiquen.
>
> Comencemos."

---

## SLIDE 2 — El Problema: Código repetido (2 minutos)

**[Mostrar código en pantalla con duplicación obvia]**

> "Antes de hablar de AspectJ, necesito mostrarles un problema que todos conocemos."

**[Mostrar este código en pantalla:]**

```java
// Clase A - Servicio de Usuarios
public class UsuarioService {
    public void crearUsuario(String nombre) {
        System.out.println("[LOG] Iniciando crearUsuario...");
        long inicio = System.currentTimeMillis();
        // ... lógica de negocio real ...
        System.out.println("[LOG] Fin crearUsuario - tiempo: " + 
            (System.currentTimeMillis() - inicio) + "ms");
    }
}

// Clase B - Servicio de Productos  
public class ProductoService {
    public void crearProducto(String nombre) {
        System.out.println("[LOG] Iniciando crearProducto...");
        long inicio = System.currentTimeMillis();
        // ... lógica de negocio real ...
        System.out.println("[LOG] Fin crearProducto - tiempo: " + 
            (System.currentTimeMillis() - inicio) + "ms");
    }
}

// Clase C - Servicio de Pedidos
public class PedidoService {
    public void procesarPedido(int id) {
        System.out.println("[LOG] Iniciando procesarPedido...");
        long inicio = System.currentTimeMillis();
        // ... lógica de negocio real ...
        System.out.println("[LOG] Fin procesarPedido - tiempo: " + 
            (System.currentTimeMillis() - inicio) + "ms");
    }
}
```

> "¿Ven el patrón? El logging y la medición de tiempo se repiten en CADA método. En una aplicación real con 200 métodos, tenemos 200 copias de ese código. Esto se llama un **cross-cutting concern**: una preocupación que *corta transversalmente* toda la aplicación."

---

## SLIDE 3 — ¿Qué son los Cross-Cutting Concerns? (1.5 minutos)

**[Mostrar diagrama ASCII en pantalla]**

```
APLICACIÓN TRADICIONAL (OOP puro):

┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  Módulo      │  │  Módulo      │  │  Módulo      │
│  Usuarios    │  │  Productos   │  │  Pedidos     │
│              │  │              │  │              │
│ [LOGGING]    │  │ [LOGGING]    │  │ [LOGGING]    │
│ [SEGURIDAD]  │  │ [SEGURIDAD]  │  │ [SEGURIDAD]  │
│ [TIMING]     │  │ [TIMING]     │  │ [TIMING]     │
│ [AUDITORÍA]  │  │ [AUDITORÍA]  │  │ [AUDITORÍA]  │
│              │  │              │  │              │
│ (lógica      │  │ (lógica      │  │ (lógica      │
│  de negocio) │  │  de negocio) │  │  de negocio) │
└──────────────┘  └──────────────┘  └──────────────┘
       ↑                  ↑                ↑
   Código duplicado en cada módulo = PROBLEMA
```

> "Estos son los **cross-cutting concerns** o preocupaciones transversales: funcionalidades que aplican a MUCHAS partes de la aplicación pero no forman parte del negocio principal.
>
> Ejemplos clásicos:
> - **Logging:** registrar qué ocurre
> - **Seguridad:** verificar permisos
> - **Transacciones:** asegurar consistencia
> - **Caché:** mejorar performance
> - **Auditoría:** registrar quién hizo qué
>
> La pregunta es: ¿hay una mejor forma de manejar esto?"

---

## SLIDE 4 — ¿Qué es AOP? (1.5 minutos)

> "La **Programación Orientada a Aspectos** o AOP por sus siglas en inglés, es un paradigma que nos permite separar estos cross-cutting concerns del código de negocio.
>
> Fue propuesta por Gregor Kiczales y su equipo en Xerox PARC en 1997. La idea central es que hay preocupaciones que 'atraviesan' los módulos de tu aplicación y que merecen ser modeladas por separado.
>
> Con AOP, el mismo diagrama se ve así:"

```
APLICACIÓN CON AOP:

┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  Módulo      │  │  Módulo      │  │  Módulo      │
│  Usuarios    │  │  Productos   │  │  Pedidos     │
│              │  │              │  │              │
│ (solo lógica │  │ (solo lógica │  │ (solo lógica │
│  de negocio) │  │  de negocio) │  │  de negocio) │
└──────────────┘  └──────────────┘  └──────────────┘
        ↑                 ↑                 ↑
        └─────────────────┼─────────────────┘
                          │
              ┌───────────▼────────────┐
              │       ASPECTOS          │
              │  ┌──────────────────┐   │
              │  │  LoggingAspect   │   │
              │  │  SecurityAspect  │   │
              │  │  TimingAspect    │   │
              │  │  AuditAspect     │   │
              │  └──────────────────┘   │
              └─────────────────────────┘
              Definidos UNA VEZ → aplicados automáticamente
```

---

## SLIDE 5 — OOP vs AOP (2 minutos)

> "Es importante entender que AOP **no reemplaza** a OOP. Los dos paradigmas se complementan."

| Característica | OOP | AOP |
|----------------|-----|-----|
| Unidad básica | Clase / Objeto | Aspecto |
| Resuelve | Descomposición modular | Preocupaciones transversales |
| Principio | Encapsulación, Herencia | Separación de concerns |
| Cuándo | Siempre (base) | Para concerns que "cruzan" módulos |

> "Piénsenlo así: OOP organiza el código por entidades del negocio (Usuario, Producto, Pedido). AOP organiza las funcionalidades que aplican a TODAS esas entidades por igual.
>
> En la práctica, usamos OOP para modelar el dominio, y AOP para los servicios horizontales."

---

## SLIDE 6 — Conceptos Clave de AOP (2 minutos)

**[Mostrar cada concepto con su analogía]**

> "Antes de ver código, necesitan conocer la terminología. Les daré una analogía para cada concepto."

### 🏗️ ASPECT (Aspecto)
> "Es el módulo que **encapsula el concern transversal**. Equivale a una clase, pero para AOP.
>
> *Analogía:* El inspector de calidad en una fábrica. Él aplica sus controles a todos los procesos, no es parte de ningún proceso específico."

```
Aspecto = El "inspector" que se aplica en múltiples lugares
```

### 📍 JOIN POINT (Punto de Unión)
> "Es cualquier **punto de ejecución** en el programa donde puede aplicarse un aspecto: la llamada a un método, el lanzamiento de una excepción, el acceso a un campo.
>
> *Analogía:* Los puntos en la cadena de producción donde el inspector PUEDE hacer una revisión."

```
Join Point = "Podría revisar aquí, o aquí, o aquí..."
```

### 🎯 POINTCUT (Corte de Punto)
> "Es una **expresión que selecciona** qué join points nos interesan. Es como un filtro.
>
> *Analogía:* El inspector decide revisar SOLO el punto donde se empaca el producto final."

```java
// Ejemplo de Pointcut:
@Pointcut("execution(* com.tutorial.servicio.*.*(..))")
// "Aplícame en TODOS los métodos del paquete servicio"
```

### ⚡ ADVICE (Consejo / Acción)
> "Es el **código que se ejecuta** cuando el pointcut se cumple. El 'qué hacer' cuando interceptamos el join point."

```
Advice = La acción que ejecuta el inspector cuando llega a ese punto
```

**Tipos de Advice:**

```
BEFORE  → Ejecuta ANTES del método
AFTER   → Ejecuta DESPUÉS del método
AROUND  → Envuelve el método (antes Y después, con control total)
```

### 🪡 WEAVING (Tejido)
> "Es el proceso de **combinar aspectos con el código normal** para producir el programa final.
>
> *Analogía:* El tejido es como mezclar ingredientes: el código base + los aspectos = programa final ejecutable."

```
Compile-time weaving: se teje al compilar
Load-time weaving:    se teje al cargar la clase en la JVM
Runtime weaving:      se teje en tiempo de ejecución (Spring AOP)
```

---

## SLIDE 7 — ¿Qué es AspectJ? (1 minuto)

> "**AspectJ** es la implementación de AOP más completa para Java. Es un superconjunto de Java: todo código Java válido es válido en AspectJ.
>
> Fue creado en Xerox PARC en 1998 y hoy es mantenido por la Fundación Eclipse.
>
> **¿Por qué AspectJ y no Spring AOP?**
> - Spring AOP solo funciona con beans de Spring y solo intercepta métodos
> - AspectJ es más poderoso: intercepta constructores, campos, excepciones, y más
> - Spring AOP en realidad usa AspectJ internamente para las anotaciones
>
> En la industria, si ves `@Aspect`, `@Before`, `@Around` en Spring, esas son anotaciones de AspectJ.
>
> Ahora pasemos a ver cómo instalarlo. Le cedo la palabra al Integrante 2."

---

---

# 🎙️ INTEGRANTE 2 — Instalación + Ejemplos Básicos (9 minutos)

---

## SLIDE 8 — Configuración del proyecto (2 minutos)

**[Tener IntelliJ abierto con el proyecto basic/]**

> "Gracias. Yo voy a mostrarles cómo configurar el entorno y los primeros ejemplos funcionando.
>
> Para este tutorial usamos **Maven** porque es el estándar en proyectos Java empresariales. Les muestro el `pom.xml`."

**[Abrir el archivo `examples/basic/pom.xml` en vivo]**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.tutorial</groupId>
    <artifactId>aspectj-basic</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <java.version>17</java.version>
        <aspectj.version>1.9.21</aspectj.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>  <!-- Runtime de AspectJ -->
            <version>${aspectj.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>aspectj-maven-plugin</artifactId>
                <version>1.15.0</version>
                <!-- Este plugin compila .java Y .aj juntos -->
                <configuration>
                    <complianceLevel>17</complianceLevel>
                    <source>17</source>
                    <target>17</target>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

> "Lo más importante: el `aspectj-maven-plugin` es el que hace la magia. Cuando ejecutamos `mvn compile`, este plugin toma nuestro código Java normal + nuestros aspectos y los combina (*weaving*) para producir el bytecode final."

---

## SLIDE 9 — Ejemplo 1: Logging Automático (3 minutos)

**[Abrir el archivo LoggingAspect.java en vivo]**

> "Nuestro primer ejemplo es el más clásico: logging automático. La idea es que CADA método de nuestra aplicación registre cuándo empieza y cuándo termina, SIN que toquemos los métodos."

**Primero, el servicio de negocio (sin ningún log):**

```java
// ServicioProducto.java — Código de negocio PURO
package com.tutorial.basic;

public class ServicioProducto {

    public String buscarProducto(String id) {
        // Simular búsqueda en base de datos
        return "Producto-" + id + " (precio: $99.99)";
    }

    public void actualizarPrecio(String id, double nuevoPrecio) {
        // Simular actualización
        System.out.println("  [Negocio] Precio actualizado a $" + nuevoPrecio);
    }

    public void eliminarProducto(String id) {
        // Simular eliminación
        System.out.println("  [Negocio] Producto " + id + " eliminado");
    }
}
```

> "Observen: CERO líneas de logging. Este código hace solo lo que le corresponde: gestionar productos."

**Ahora el aspecto:**

```java
// LoggingAspect.java — El aspecto que inyecta el logging
package com.tutorial.basic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect  // Le decimos a AspectJ: "esta clase es un Aspecto"
public class LoggingAspect {

    // POINTCUT: "intercepta la ejecución de CUALQUIER método
    // en el paquete com.tutorial.basic"
    @Pointcut("execution(* com.tutorial.basic.*.*(..))")
    public void metodosDeNegocio() {}
    // ↑ Este método vacío es solo el "nombre" del pointcut

    // ADVICE tipo BEFORE: se ejecuta ANTES del método
    @Before("metodosDeNegocio()")
    public void logAntes(JoinPoint joinPoint) {
        String metodo = joinPoint.getSignature().getName();
        System.out.println("▶ [LOG] Iniciando: " + metodo);
    }

    // ADVICE tipo AFTER: se ejecuta DESPUÉS del método
    // (siempre, haya o no excepción)
    @After("metodosDeNegocio()")
    public void logDespues(JoinPoint joinPoint) {
        String metodo = joinPoint.getSignature().getName();
        System.out.println("◀ [LOG] Finalizado: " + metodo);
    }
}
```

**[Ejecutar en vivo: `mvn exec:java`]**

**Salida esperada:**
```
▶ [LOG] Iniciando: buscarProducto
  [Negocio] Buscando producto P001...
◀ [LOG] Finalizado: buscarProducto

▶ [LOG] Iniciando: actualizarPrecio
  [Negocio] Precio actualizado a $149.99
◀ [LOG] Finalizado: actualizarPrecio
```

> "¿Magia? No. El plugin de Maven tejió (*weaving*) el aspecto dentro del bytecode de ServicioProducto. Al ejecutar, es como si los logs estuvieran escritos directamente en cada método."

---

## SLIDE 10 — Ejemplo 2: Medición de Tiempo con Around (3 minutos)

**[Abrir TimingAspect.java]**

> "El segundo ejemplo introduce el Advice más poderoso: **Around**. Con Around tenemos control total sobre la ejecución del método: podemos ejecutar código antes, después, o incluso decidir si el método corre o no."

```java
// TimingAspect.java
package com.tutorial.basic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class TimingAspect {

    // Pointcut solo para métodos de tipo "buscar" o "obtener"
    @Pointcut("execution(* com.tutorial.basic.*.buscar*(..))" +
              " || execution(* com.tutorial.basic.*.obtener*(..))")
    public void metodosConsulta() {}

    // AROUND: envuelve completamente la ejecución
    @Around("metodosConsulta()")
    public Object medirTiempo(ProceedingJoinPoint pjp) throws Throwable {
        // --- ANTES del método original ---
        String metodo = pjp.getSignature().getName();
        long inicio = System.currentTimeMillis();
        System.out.println("⏱ [TIMING] Midiendo: " + metodo);

        Object resultado;
        try {
            // ← Aquí se ejecuta el método ORIGINAL
            resultado = pjp.proceed();
        } finally {
            // --- DESPUÉS del método (siempre se ejecuta) ---
            long duracion = System.currentTimeMillis() - inicio;
            System.out.println("⏱ [TIMING] " + metodo 
                + " tardó " + duracion + "ms");
            
            // Alerta si tarda más de 500ms
            if (duracion > 500) {
                System.out.println("⚠ [TIMING] ALERTA: método lento detectado!");
            }
        }

        return resultado; // Devolvemos el resultado original
    }
}
```

> "La diferencia clave con Before/After:
>
> - **Before/After:** son como observadores. Ven el método pero no pueden cambiarlo.
> - **Around:** es el dueño de la ejecución. Puede modificar argumentos, cambiar el resultado, suprimir el método, capturar excepciones... todo."
>
> "El `pjp.proceed()` es el momento en que le decimos 'ok, ahora ejecuta el método original'."

**[Ejecutar en vivo]**

```
⏱ [TIMING] Midiendo: buscarProducto
  [Negocio] Buscando producto P001...
⏱ [TIMING] buscarProducto tardó 12ms
```

> "Con estos dos ejemplos ya tienen la base. Ahora el Integrante 3 va a mostrar cómo aplicamos AOP en escenarios más realistas."

---

---

# 🎙️ INTEGRANTE 3 — Ejemplos Intermedios (9 minutos)

---

## SLIDE 11 — Autenticación como Aspecto (3 minutos)

**[Abrir AutenticacionAspect.java]**

> "Los ejemplos básicos son perfectos para entender la mecánica. Ahora vamos a algo que verían en una aplicación real: autenticación y autorización.
>
> Imaginen un servicio bancario con operaciones sensibles. Normalmente pondrían la verificación de usuario en CADA método. Con AOP, la centralizamos en un aspecto."

**Primero, una anotación personalizada:**

```java
// @RequiereAutenticacion.java — Marcador para métodos protegidos
package com.tutorial.intermediate;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiereAutenticacion {
    String[] roles() default {"USER"};
}
```

**El servicio bancario:**

```java
// ServicioBancario.java
package com.tutorial.intermediate;

public class ServicioBancario {

    @RequiereAutenticacion(roles = {"USER", "ADMIN"})
    public double consultarSaldo(String cuentaId) {
        System.out.println("  [Banco] Consultando saldo de cuenta: " + cuentaId);
        return 15_750.50;
    }

    @RequiereAutenticacion(roles = {"ADMIN"})
    public void transferir(String origen, String destino, double monto) {
        System.out.println("  [Banco] Transferencia: " + origen 
            + " → " + destino + " | $" + monto);
    }

    public String obtenerNombreTitular(String cuentaId) {
        // Este método NO requiere autenticación
        return "Juan García";
    }
}
```

**El aspecto de autenticación:**

```java
// AutenticacionAspect.java
package com.tutorial.intermediate;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
public class AutenticacionAspect {

    // Sesión simulada del usuario actual
    private static String usuarioActual = null;
    private static String[] rolesActuales = {};

    // Setter para simular login
    public static void login(String usuario, String... roles) {
        usuarioActual = usuario;
        rolesActuales = roles;
        System.out.println("🔐 Login: " + usuario 
            + " con roles: " + Arrays.toString(roles));
    }

    public static void logout() {
        usuarioActual = null;
        rolesActuales = new String[]{};
        System.out.println("🔓 Logout realizado");
    }

    // Interceptar métodos anotados con @RequiereAutenticacion
    @Around("@annotation(requiereAutenticacion)")
    public Object verificarAcceso(ProceedingJoinPoint pjp,
                                   RequiereAutenticacion requiereAutenticacion) 
            throws Throwable {
        
        // 1. Verificar que hay usuario autenticado
        if (usuarioActual == null) {
            throw new SecurityException(
                "❌ Acceso denegado: no hay sesión activa");
        }

        // 2. Verificar que tiene el rol necesario
        String[] rolesRequeridos = requiereAutenticacion.roles();
        boolean tienePermiso = false;
        
        for (String rolRequerido : rolesRequeridos) {
            for (String rolActual : rolesActuales) {
                if (rolRequerido.equals(rolActual)) {
                    tienePermiso = true;
                    break;
                }
            }
        }

        if (!tienePermiso) {
            throw new SecurityException(
                "❌ Acceso denegado: se requieren roles " 
                + Arrays.toString(rolesRequeridos)
                + " pero usuario tiene: " + Arrays.toString(rolesActuales));
        }

        System.out.println("✅ [AUTH] Usuario '" + usuarioActual 
            + "' autorizado para: " + pjp.getSignature().getName());
        
        // 3. Si todo está bien, ejecutar el método
        return pjp.proceed();
    }
}
```

**[Ejecutar y mostrar casos: sin login, con login USER, con login ADMIN]**

```
// Sin login:
❌ SecurityException: no hay sesión activa

// Con login USER intentando método ADMIN:
🔐 Login: maria con roles: [USER]
❌ Acceso denegado: se requieren roles [ADMIN]

// Con login ADMIN:
🔐 Login: admin con roles: [ADMIN, USER]
✅ [AUTH] Usuario 'admin' autorizado para: transferir
  [Banco] Transferencia: 001 → 002 | $500.0
```

---

## SLIDE 12 — Auditoría Centralizada (3 minutos)

**[Abrir AuditoriaAspect.java]**

> "El siguiente ejemplo es **auditoría**: registrar un historial de QUÉ hizo QUIÉN y CUÁNDO. Esto es obligatorio en sistemas bancarios, médicos, y cualquier sistema regulado."

```java
// AuditoriaAspect.java
package com.tutorial.intermediate;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Aspect
public class AuditoriaAspect {

    // Registro de auditoría (en producción sería una base de datos)
    private static final List<String> registroAuditoria = new ArrayList<>();

    // Interceptar TODA escritura/modificación (métodos que no sean "get/obtener/consultar")
    @Pointcut("execution(* com.tutorial.intermediate.*.*(..)) " +
              "&& !execution(* com.tutorial.intermediate.*.consultar*(..))" +
              "&& !execution(* com.tutorial.intermediate.*.obtener*(..))" +
              "&& !execution(* com.tutorial.intermediate.*.get*(..))")
    public void operacionesEscritura() {}

    @AfterReturning(pointcut = "operacionesEscritura()", returning = "resultado")
    public void registrarOperacion(JoinPoint jp, Object resultado) {
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String usuario = AutenticacionAspect.getUsuarioActual(); // Reutiliza el aspecto de auth
        String metodo = jp.getSignature().getName();
        String clase = jp.getTarget().getClass().getSimpleName();
        Object[] args = jp.getArgs();

        String entrada = String.format(
            "[%s] USUARIO='%s' | CLASE='%s' | MÉTODO='%s' | ARGS=%s | RESULTADO='%s'",
            timestamp, usuario, clase, metodo, 
            Arrays.toString(args), resultado
        );

        registroAuditoria.add(entrada);
        System.out.println("📋 [AUDITORÍA] " + entrada);
    }

    // También registrar errores
    @AfterThrowing(pointcut = "operacionesEscritura()", throwing = "ex")
    public void registrarError(JoinPoint jp, Exception ex) {
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entrada = String.format(
            "[%s] ERROR en '%s.%s' | Causa: %s",
            timestamp, jp.getTarget().getClass().getSimpleName(),
            jp.getSignature().getName(), ex.getMessage()
        );
        registroAuditoria.add("⛔ " + entrada);
        System.out.println("⛔ [AUDITORÍA] " + entrada);
    }

    public static void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL DE AUDITORÍA ===");
        registroAuditoria.forEach(System.out::println);
        System.out.println("==============================\n");
    }
}
```

> "Noten que usamos `@AfterReturning` para éxito y `@AfterThrowing` para errores. Esto nos da visibilidad total de qué ocurrió en la aplicación sin tocar el código de negocio."

---

## SLIDE 13 — Manejo Centralizado de Errores (3 minutos)

```java
// ManejoErroresAspect.java
package com.tutorial.intermediate;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

@Aspect
public class ManejoErroresAspect {

    @Pointcut("execution(* com.tutorial.intermediate.*.*(..))")
    public void todosLosMetodos() {}

    // AfterThrowing: intercepta CUALQUIER excepción lanzada
    @AfterThrowing(
        pointcut = "todosLosMetodos()",
        throwing = "excepcion"
    )
    public void manejarExcepcion(JoinPoint jp, Exception excepcion) {
        System.out.println("\n🚨 [ERROR HANDLER] ================================");
        System.out.println("   Clase:   " + jp.getTarget().getClass().getSimpleName());
        System.out.println("   Método:  " + jp.getSignature().getName());
        System.out.println("   Error:   " + excepcion.getClass().getSimpleName());
        System.out.println("   Mensaje: " + excepcion.getMessage());
        System.out.println("   Timestamp: " + java.time.LocalDateTime.now());
        System.out.println("=====================================================\n");
        
        // Aquí podríamos: enviar email, escribir a BD, notificar sistema de monitoreo
    }
}
```

> "Este aspecto actúa como un 'catch global'. En vez de poner try-catch en cada método, lo manejamos centralmente. Ideal para logging de errores consistente."
>
> "Con estos tres aspectos, tenemos la tríada de concerns transversales más común en sistemas empresariales: autenticación, auditoría y manejo de errores. Ahora le cedo la palabra al Integrante 4 para los ejemplos avanzados."

---

---

# 🎙️ INTEGRANTE 4 — Ejemplos Avanzados + Cierre (10 minutos)

---

## SLIDE 14 — Performance Monitoring (3 minutos)

**[Abrir PerformanceAspect.java]**

> "Muy bien, llegamos a los ejemplos avanzados. Voy a mostrarles tres aspectos que verían en sistemas de producción reales."

```java
// PerformanceAspect.java
package com.tutorial.advanced;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.*;

@Aspect
public class PerformanceAspect {

    // Estadísticas por método: nombre → [total_llamadas, tiempo_total_ms]
    private static final Map<String, long[]> estadisticas = new ConcurrentHashMap<>();
    private static final long UMBRAL_ALERTA_MS = 200;

    @Pointcut("execution(* com.tutorial.advanced.*.*(..))")
    public void todosLosMetodos() {}

    @Around("todosLosMetodos()")
    public Object monitorear(ProceedingJoinPoint pjp) throws Throwable {
        String nombreMetodo = pjp.getTarget().getClass().getSimpleName() 
                            + "." + pjp.getSignature().getName();
        
        long inicio = System.nanoTime();
        Object resultado;
        boolean exitoso = true;

        try {
            resultado = pjp.proceed();
        } catch (Throwable t) {
            exitoso = false;
            throw t;
        } finally {
            long duracionMs = (System.nanoTime() - inicio) / 1_000_000;
            
            // Actualizar estadísticas acumuladas
            estadisticas.computeIfAbsent(nombreMetodo, k -> new long[]{0, 0});
            estadisticas.get(nombreMetodo)[0]++;          // total llamadas
            estadisticas.get(nombreMetodo)[1] += duracionMs; // tiempo acumulado
            
            String estado = exitoso ? "✅" : "❌";
            System.out.printf("📊 [PERF] %s %s | %dms%n", 
                estado, nombreMetodo, duracionMs);
            
            if (duracionMs > UMBRAL_ALERTA_MS) {
                System.out.printf("⚠️  [PERF] ALERTA: %s excedió umbral! (%dms > %dms)%n",
                    nombreMetodo, duracionMs, UMBRAL_ALERTA_MS);
            }
        }

        return resultado;
    }

    // Llamar al final para ver reporte completo
    public static void imprimirReporte() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║     REPORTE DE PERFORMANCE           ║");
        System.out.println("╠══════════════════════════════════════╣");
        estadisticas.forEach((metodo, stats) -> {
            long llamadas = stats[0];
            long tiempoTotal = stats[1];
            long promedio = llamadas > 0 ? tiempoTotal / llamadas : 0;
            System.out.printf("║ %-25s %3d llamadas | prom: %3dms ║%n",
                metodo, llamadas, promedio);
        });
        System.out.println("╚══════════════════════════════════════╝\n");
    }
}
```

---

## SLIDE 15 — Retry Automático (4 minutos)

> "Este es uno de los patrones más útiles en sistemas distribuidos: **retry automático**. Cuando un método falla por un error transitorio (timeout de red, servicio caído momentáneamente), ¿por qué no reintentarlo automáticamente?"

```java
// RetryAspect.java — Reintento automático con backoff exponencial
package com.tutorial.advanced;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

@Aspect
public class RetryAspect {

    // Anotación personalizada para marcar métodos con retry
    // (ver @Retryable.java)
    
    @Around("@annotation(retryable)")
    public Object ejecutarConRetry(ProceedingJoinPoint pjp, 
                                    Retryable retryable) throws Throwable {
        int maxIntentos = retryable.maxIntentos();
        long esperaMs = retryable.esperaMs();
        Class<? extends Exception>[] exceptionsAReintenter = retryable.on();
        
        Exception ultimaExcepcion = null;
        
        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                if (intento > 1) {
                    System.out.printf("🔄 [RETRY] Intento %d/%d para: %s%n",
                        intento, maxIntentos, 
                        pjp.getSignature().getName());
                }
                
                return pjp.proceed(); // ← Intentar ejecutar el método
                
            } catch (Exception e) {
                ultimaExcepcion = e;
                
                // ¿Es una excepción que debemos reintentar?
                boolean debeReintentar = false;
                for (Class<? extends Exception> tipo : exceptionsAReintenter) {
                    if (tipo.isInstance(e)) {
                        debeReintentar = true;
                        break;
                    }
                }
                
                if (!debeReintentar || intento == maxIntentos) {
                    System.out.printf("❌ [RETRY] Fallido definitivamente: %s%n",
                        e.getMessage());
                    throw e; // Propagar si no es retryable o se agotaron intentos
                }
                
                // Espera con backoff exponencial: 100ms, 200ms, 400ms...
                long espera = esperaMs * (long) Math.pow(2, intento - 1);
                System.out.printf("⏳ [RETRY] Error temporal '%s'. " +
                    "Esperando %dms antes de reintentar...%n",
                    e.getMessage(), espera);
                
                Thread.sleep(espera);
            }
        }
        
        throw ultimaExcepcion;
    }
}
```

```java
// @Retryable.java — La anotación
package com.tutorial.advanced;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retryable {
    int maxIntentos() default 3;
    long esperaMs() default 100;
    Class<? extends Exception>[] on() default {Exception.class};
}
```

```java
// Uso en el servicio:
public class ServicioExterno {
    private int contadorLlamadas = 0;

    @Retryable(maxIntentos = 3, esperaMs = 100, on = {RuntimeException.class})
    public String llamarApiExterna(String endpoint) {
        contadorLlamadas++;
        
        // Simular fallo en los primeros 2 intentos
        if (contadorLlamadas <= 2) {
            throw new RuntimeException("Timeout conectando a " + endpoint);
        }
        
        return "Respuesta exitosa del servidor";
    }
}
```

**[Ejecutar y mostrar la salida:]**

```
⏳ [RETRY] Error temporal 'Timeout conectando a /api/datos'. Esperando 100ms...
🔄 [RETRY] Intento 2/3 para: llamarApiExterna
⏳ [RETRY] Error temporal 'Timeout conectando a /api/datos'. Esperando 200ms...
🔄 [RETRY] Intento 3/3 para: llamarApiExterna
✅ Resultado: Respuesta exitosa del servidor
```

> "En 20 líneas de aspecto, tenemos retry automático con backoff exponencial que se puede aplicar a CUALQUIER método con solo agregar `@Retryable`. En Spring, esto está implementado en `spring-retry` usando exactamente este patrón."

---

## SLIDE 16 — Diagrama de Weaving (1 minuto)

> "Antes de cerrar, visualicemos cómo funciona el weaving:"

```
COMPILE-TIME WEAVING (lo que usa este tutorial):

Tu código fuente:          Aspectos:
┌─────────────────┐       ┌─────────────────┐
│ ServicioProducto│       │ LoggingAspect   │
│ .java           │  ───► │ TimingAspect    │
│ ServicioBancario│       │ RetryAspect     │
│ .java           │       └─────────────────┘
└─────────────────┘                │
         │                         │
         └──────────┬──────────────┘
                    ▼
           aspectj-maven-plugin
           (el "tejedor" = weaver)
                    │
                    ▼
         ┌──────────────────────┐
         │  ServicioProducto    │
         │  .class              │ ← Bytecode YA TIENE el código
         │  (con aspectos       │   de los aspectos tejido dentro
         │   tejidos dentro)    │
         └──────────────────────┘
```

---

## SLIDE 17 — Conclusiones y Cuándo Usar AOP (2 minutos)

**[Integrante 4 concluye]**

> "Llegamos al final del tutorial. ¿Cuándo USAR AOP?

✅ **Usa AOP cuando:**
- El mismo código se repite en muchos lugares (logging, seguridad, caching)
- Quieres separar el código de infraestructura del código de negocio
- Trabajas con frameworks como Spring (que usa AOP internamente)
- Necesitas agregar comportamiento sin modificar código existente (principio Open/Closed)

❌ **NO uses AOP cuando:**
- La lógica es específica a UN método → simplemente escríbela en el método
- El equipo no conoce AOP → puede ser difícil de depurar para quienes no lo conocen
- El flujo de ejecución necesita ser muy predecible y trazable
- Problemas de performance son críticos (el weaving añade overhead mínimo pero existe)

> En la industria: **Spring AOP** es el ejemplo más usado. Todo `@Transactional`, `@Cacheable`, `@Secured` en Spring son aspectos. Conocer AOP es conocer el corazón de Spring."

---

## SLIDE 18 — Taller y Preguntas (1.5 minutos)

> "Les dejamos tres ejercicios en el repositorio para practicar. Tienen los enunciados y las soluciones.
>
> El repositorio completo está en GitHub Pages con todo el código funcional.
>
> ¡Muchas gracias por su atención! Quedamos abiertos a preguntas."

---
