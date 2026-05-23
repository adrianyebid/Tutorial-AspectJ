# 📊 Estructura Detallada de Slides
## Tutorial AspectJ — Programación Orientada a Aspectos

> Esta guía describe cada slide: título, contenido visual, qué dice el expositor y tiempo sugerido.

---

## 🎨 Diseño recomendado

- **Fondo:** Oscuro (dark theme) — hace que el código resalte mejor
- **Fuente código:** JetBrains Mono o Fira Code (con ligaduras)
- **Colores:** Esquema similar a IntelliJ Darcula
- **Herramienta:** Reveal.js, Google Slides o PowerPoint con tema oscuro
- **Código:** Siempre con syntax highlighting

---

## SLIDE 1 — Portada

| Campo | Contenido |
|-------|-----------|
| **Título** | Programación Orientada a Aspectos |
| **Subtítulo** | Tutorial AspectJ — De Cero a Producción |
| **Logotipo** | Logo AspectJ + Logo Java |
| **Integrantes** | Lista de los 4 nombres |
| **Curso** | Lenguajes de Programación — Paradigmas |
| **Tiempo** | 30 segundos |

**Visual sugerido:** Logo AspectJ (el tejido de hilos) con fondo oscuro y código en transparencia.

---

## SLIDE 2 — El Problema que Resuelve AOP

| Campo | Contenido |
|-------|-----------|
| **Título** | "¿Por qué necesitamos AOP?" |
| **Contenido** | Código Java duplicado en 3 clases diferentes |
| **Énfasis** | Resaltar en rojo las líneas repetidas |
| **Tiempo** | 2 minutos |

**Animación sugerida:** El código aparece, luego las líneas de logging se resaltan en rojo, luego aparece el texto "50 clases × este código = pesadilla de mantenimiento"

---

## SLIDE 3 — Cross-Cutting Concerns

| Campo | Contenido |
|-------|-----------|
| **Título** | Cross-Cutting Concerns |
| **Contenido** | Diagrama ASCII de módulos con concerns duplicados |
| **Lista** | Ejemplos: logging, seguridad, transacciones, caché |
| **Tiempo** | 1.5 minutos |

**Visual:** Diagrama de 3 columnas (módulos) con filas horizontales que las "cortan" (concerns). Las filas en rojo = problema.

---

## SLIDE 4 — ¿Qué es AOP?

| Campo | Contenido |
|-------|-----------|
| **Título** | Programación Orientada a Aspectos |
| **Definición** | "Paradigma que permite separar los cross-cutting concerns del código de negocio" |
| **Historia** | Xerox PARC, 1997, Gregor Kiczales |
| **Diagrama** | El mismo diagrama pero ahora los concerns están en un solo lugar |
| **Tiempo** | 1.5 minutos |

---

## SLIDE 5 — OOP vs AOP

| Campo | Contenido |
|-------|-----------|
| **Título** | OOP y AOP: Complementarios, no competidores |
| **Contenido** | Tabla comparativa de dos columnas |
| **Énfasis** | "AOP no reemplaza OOP — los dos van juntos" |
| **Tiempo** | 2 minutos |

---

## SLIDE 6 — Conceptos Clave (5 sub-slides o animaciones)

| Campo | Contenido |
|-------|-----------|
| **Título** | Vocabulario de AOP |
| **Contenido** | Un concepto por animación con analogía y código |
| **Orden** | Aspect → Join Point → Pointcut → Advice → Weaving |
| **Tiempo** | 2 minutos total (24 segundos por concepto) |

**Técnica recomendada:** Revelar un concepto a la vez con animación. Cada uno: nombre en grande + emoji + definición de 1 línea + ejemplo de código de 2-3 líneas.

---

## SLIDE 7 — ¿Qué es AspectJ?

| Campo | Contenido |
|-------|-----------|
| **Título** | AspectJ: El estándar de AOP en Java |
| **Contenido** | Historia, relación con Java, Spring AOP usa AspectJ |
| **Visual** | Logo AspectJ, año de creación, logo Eclipse Foundation |
| **Comparativa** | AspectJ vs Spring AOP (tabla simple) |
| **Tiempo** | 1 minuto |

---

## SLIDE 8 — Configuración Maven

| Campo | Contenido |
|-------|-----------|
| **Título** | Primeros pasos: Configurar el proyecto |
| **Contenido** | pom.xml con las dependencias clave resaltadas |
| **Énfasis** | Resaltar `aspectjrt` y `aspectj-maven-plugin` |
| **Tiempo** | 2 minutos |

**Demo:** Abrir IntelliJ con el pom.xml real mientras se explica.

---

## SLIDE 9 — Ejemplo 1: Logging Automático

| Campo | Contenido |
|-------|-----------|
| **Título** | Ejemplo 1 — Logging sin tocar el negocio |
| **Contenido** | Dos columnas: Servicio (sin logs) | Aspecto (con logs) |
| **Énfasis** | "El servicio no sabe que lo están observando" |
| **Demo live** | Ejecutar y mostrar salida |
| **Tiempo** | 3 minutos |

**Técnica:** Mostrar primero el servicio vacío de concerns, luego el aspecto por separado, luego ejecutar.

---

## SLIDE 10 — Ejemplo 2: Timing con Around

| Campo | Contenido |
|-------|-----------|
| **Título** | Advice Around — Control total |
| **Contenido** | Diagrama del flujo: Before → método → After |
| **Código** | TimingAspect completo con comentarios |
| **Diferencia** | Tabla: Before/After vs Around |
| **Demo live** | Ejecutar con método que simula retraso |
| **Tiempo** | 3 minutos |

---

## SLIDE 11 — Autenticación como Aspecto

| Campo | Contenido |
|-------|-----------|
| **Título** | Ejemplo 3 — Autenticación centralizada |
| **Contenido** | Anotación @RequiereAutenticacion + Aspecto |
| **Demo live** | 3 casos: sin login / login USER / login ADMIN |
| **Tiempo** | 3 minutos |

---

## SLIDE 12 — Auditoría

| Campo | Contenido |
|-------|-----------|
| **Título** | Ejemplo 4 — Auditoría automática |
| **Contenido** | @AfterReturning y @AfterThrowing |
| **Output** | Mostrar el historial de auditoría generado |
| **Tiempo** | 3 minutos |

---

## SLIDE 13 — Manejo de Errores

| Campo | Contenido |
|-------|-----------|
| **Título** | Ejemplo 5 — Error Handler centralizado |
| **Contenido** | @AfterThrowing con detalles completos |
| **Comparativa** | Con try-catch en cada método vs el aspecto |
| **Tiempo** | 2 minutos |

---

## SLIDE 14 — Performance Monitoring

| Campo | Contenido |
|-------|-----------|
| **Título** | Ejemplo 6 — Monitoreo de performance |
| **Contenido** | PerformanceAspect + reporte estadístico |
| **Output** | Tabla de métricas en consola |
| **Tiempo** | 3 minutos |

---

## SLIDE 15 — Retry Automático

| Campo | Contenido |
|-------|-----------|
| **Título** | Ejemplo 7 — Retry con backoff exponencial |
| **Contenido** | @Retryable + RetryAspect |
| **Demo live** | Mostrar 3 intentos con tiempos de espera |
| **Conexión real** | "Esto es lo que hace Spring Retry" |
| **Tiempo** | 4 minutos |

---

## SLIDE 16 — Diagrama de Weaving

| Campo | Contenido |
|-------|-----------|
| **Título** | ¿Cómo funciona el Weaving? |
| **Contenido** | Diagrama visual del proceso de compilación |
| **Tipos** | Compile-time vs Load-time vs Runtime |
| **Tiempo** | 1 minuto |

---

## SLIDE 17 — ¿Cuándo usar AOP?

| Campo | Contenido |
|-------|-----------|
| **Título** | AOP en la práctica — ¿Cuándo sí, cuándo no? |
| **Contenido** | Lista ✅ Usar / ❌ No usar |
| **Casos reales** | Spring @Transactional, @Cacheable, @Secured |
| **Tiempo** | 2 minutos |

---

## SLIDE 18 — Taller + Cierre

| Campo | Contenido |
|-------|-----------|
| **Título** | Mini Taller + Recursos |
| **Contenido** | QR code al repositorio + enunciados de ejercicios |
| **Cierre** | Agradecimiento + "Preguntas?" |
| **Tiempo** | 1.5 minutos |

---

## 📌 Notas generales de presentación

1. **Siempre tener IntelliJ abierto** con los ejemplos ya compilados antes de empezar
2. **Ejecutar antes de la demo** para verificar que todo funciona
3. **Fuente grande en el IDE** (Ctrl++ en IntelliJ) para que todos vean
4. **Terminal siempre visible** para mostrar la salida
5. **Preparar un segundo monitor** si es posible: slides en proyector, código en monitor
6. **Si hay error en vivo:** no entrar en pánico, es pedagógico ver cómo se soluciona
