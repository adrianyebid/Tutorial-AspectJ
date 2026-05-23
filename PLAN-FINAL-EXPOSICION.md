# 🎬 Plan Final Recomendado para la Exposición
## Tutorial AspectJ — Guía Completa del Día D

> Este documento es el mapa de vuelo para los 4 integrantes.  
> Léanlo juntos antes de la exposición y practiquen en voz alta.

---

## ⏰ Cronograma exacto

```
00:00 - 00:30  → Apertura (Integrante 1)
00:30 - 08:30  → PARTE 1: Fundamentos AOP (Integrante 1)
08:30 - 17:30  → PARTE 2: Instalación + Básicos (Integrante 2)
17:30 - 26:30  → PARTE 3: Intermedios (Integrante 3)
26:30 - 33:30  → PARTE 4: Avanzados (Integrante 4)
33:30 - 36:30  → Conclusiones + Taller (Integrante 4)
36:30 - 40:00  → Preguntas (Todos)
```

---

## 🚀 Cómo iniciar la exposición

### El minuto cero (Integrante 1)

**NO empiecen diciendo:** "Hola, somos el grupo X y hoy vamos a hablar de AOP..."

**SÍ empiecen con un gancho visual y una pregunta:**

> *"[Mostrar código en pantalla con logging duplicado en 3 clases]*  
> Levante la mano quien haya copiado y pegado código de logging de una clase a otra...  
> *[Esperar respuesta de la audiencia]*  
> Exactamente. Hoy les mostramos cómo eliminar eso para siempre."*

Esto:
- Engancha al público desde el primer segundo
- Hace que el problema se sienta familiar y real
- Crea anticipación sobre la solución

---

## 💻 Cómo hacer la demo en vivo

### Preparación (CRÍTICO — hacer 30 min antes)

```
□ Abrir IntelliJ con los 3 proyectos ya cargados (basic/, intermediate/, advanced/)
□ Ejecutar una vez cada Main para verificar que funcionan
□ Aumentar la fuente del IDE: Settings → Editor → Font → Size 18-20
□ Abrir la terminal integrada de IntelliJ (más limpia que CMD)
□ Maximizar IntelliJ o usar Presentation Mode (Ver → Enter Presentation Mode)
□ Silenciar notificaciones del sistema operativo
□ Cerrar aplicaciones innecesarias (Teams, Slack, navegador extra)
```

### Durante la demo (reglas de oro)

1. **Ir de arriba a abajo:** siempre mostrar primero la clase de negocio, luego el aspecto
2. **Ejecutar ANTES de explicar:** muestra la salida primero, luego explica por qué
3. **Pausar en la salida:** dejar que la audiencia lea la salida 2-3 segundos antes de comentar
4. **Señalar con el cursor:** al explicar el código, mover el cursor sobre la línea que mencionas
5. **Si algo falla:** no entrar en pánico. Decir "déjenme revisar" es profesional.

### Secuencia de demo recomendada para cada ejemplo:

```
1. "Primero mostremos el servicio de negocio — sin ningún aspecto"
   [Abrir ServicioProducto.java — señalar que no hay logging]

2. "Ahora, el aspecto que vamos a aplicar"
   [Abrir LoggingAspect.java — explicar @Aspect, @Pointcut, @Before, @After]

3. "Ejecutemos y veamos qué pasa"
   [Terminal: mvn exec:java]
   [PAUSA — dejar que lean la salida]

4. "¿Ven los logs? ServicioProducto no tiene ni una línea de logging.
    AspectJ lo tejió automáticamente."
```

---

## 📊 Reparto de tiempos detallado

### INTEGRANTE 1 — "El Teórico" (8 min)

**Responsabilidad:** que todos entiendan POR QUÉ existe AOP antes de ver código.

| Tiempo | Acción |
|--------|--------|
| 0:00 | Gancho inicial — código duplicado en pantalla, pregunta a la audiencia |
| 0:30 | Mostrar diagrama de cross-cutting concerns |
| 2:00 | Explicar qué es AOP y cómo resuelve el problema |
| 3:30 | Tabla OOP vs AOP |
| 5:00 | Recorrer los 5 conceptos (Aspect, Join Point, Pointcut, Advice, Weaving) — 45s cada uno |
| 7:30 | Presentar AspectJ brevemente y ceder la palabra |

**Qué NO hacer:**
- No leer las slides — explica con tus propias palabras
- No leer el código aún — eso es de los Integrantes 2-4
- No tardar más de 8 minutos — la teoría sin código se vuelve aburrida rápido

---

### INTEGRANTE 2 — "El Constructor" (9 min)

**Responsabilidad:** mostrar que configurar AspectJ es sencillo Y que los ejemplos básicos funcionan.

| Tiempo | Acción |
|--------|--------|
| 0:00 | Abrir pom.xml — explicar las 2 cosas clave: dependencia + plugin |
| 2:00 | DEMO 1: Abrir ServicioProducto → señalar ausencia de logs → abrir LoggingAspect |
| 4:00 | Ejecutar Ejemplo 1 en vivo → mostrar salida → explicar |
| 5:30 | DEMO 2: Abrir TimingAspect → explicar @Around vs @Before/@After |
| 7:30 | Ejecutar Ejemplo 2 en vivo → mostrar alerta de método lento |
| 8:30 | Resumen rápido: "Con 20 líneas de aspecto, logging automático en toda la app" |

**Qué NO hacer:**
- No explicar cada línea del pom.xml — solo las 2 partes importantes
- No copiar código en vivo — ya debe estar escrito, solo mostrarlo y explicarlo

---

### INTEGRANTE 3 — "El Realista" (9 min)

**Responsabilidad:** mostrar que AOP resuelve problemas que los estudiantes reconocerán de otras materias (seguridad, auditoría).

| Tiempo | Acción |
|--------|--------|
| 0:00 | Contexto: "¿Qué pasa cuando añadimos seguridad a la app?" |
| 0:30 | Mostrar @RequiereAutenticacion — solo 10 líneas de anotación |
| 1:30 | Mostrar ServicioBancario — señalar las anotaciones en los métodos |
| 2:30 | DEMO SIN LOGIN — ejecutar y mostrar SecurityException |
| 3:30 | DEMO CON USER — login como USER, intentar método ADMIN → falla |
| 5:00 | DEMO CON ADMIN — acceso total, mostrar auditoría |
| 6:30 | Abrir AuditoriaAspect — explicar @AfterReturning y @AfterThrowing |
| 8:00 | Mostrar historial de auditoría completo |
| 8:30 | Transición: "Ahora vemos escenarios de producción real" |

**Qué NO hacer:**
- No explicar AuditoriaAspect antes de hacer la demo — la demo primero
- No omitir el caso de acceso denegado — es el más didáctico

---

### INTEGRANTE 4 — "El Avanzado + Cierre" (10 min)

**Responsabilidad:** impresionar con ejemplos de nivel industrial Y cerrar profesionalmente.

| Tiempo | Acción |
|--------|--------|
| 0:00 | Contexto: "En producción necesitamos observabilidad y resiliencia" |
| 0:30 | DEMO Performance: ejecutar ApiController con múltiples llamadas |
| 2:00 | Mostrar el reporte estadístico en tabla |
| 3:00 | Explicar brevemente PerformanceAspect (énfasis en ConcurrentHashMap y métricas) |
| 4:30 | DEMO Retry: "El scenario más común en microservicios" |
| 5:00 | Ejecutar ServicioExterno (falla 2 veces, éxito al 3ro) — mostrar backoff exponencial |
| 7:00 | Ejecutar caso donde AGOTA todos los intentos |
| 8:00 | Diagrama de weaving — "así funciona internamente" |
| 8:30 | Slide de conclusiones: cuándo SÍ y cuándo NO usar AOP |
| 9:30 | Presentar el mini taller: "tienen 3 ejercicios en el repositorio" |

**Qué NO hacer:**
- No leer el código de RetryAspect línea por línea — describe lo que hace y ejecuta
- No irse del tiempo — el cierre debe ser contundente, no apresurado

---

## 🎤 Transiciones entre integrantes

Las transiciones mal hechas rompen el ritmo. Usar estas frases:

**Integrante 1 → 2:**
> "Ahora que conocemos los conceptos, el Integrante 2 nos va a mostrar cómo se ve esto en código real."

**Integrante 2 → 3:**
> "Con los ejemplos básicos claros, el Integrante 3 va a mostrarnos cómo AOP resuelve problemas que reconocerán de otras materias: seguridad y auditoría."

**Integrante 3 → 4:**
> "Excelente. Para cerrar, el Integrante 4 nos muestra dos patrones que verían en sistemas de producción reales."

---

## ❌ Cosas que EVITAR

### Durante la preparación
- ❌ **No dejar la instalación para el día de la presentación** — configurar con mínimo 2 días de anticipación
- ❌ **No confiar solo en la red de la universidad** — tener Maven con dependencias ya descargadas
- ❌ **No practicar solo en silencio** — practicar en voz alta, al menos con los compañeros

### Durante la exposición
- ❌ **No leer las slides** — las slides son apoyo visual, no guion
- ❌ **No decir "como se puede ver"** sin señalar — señalar con el cursor siempre
- ❌ **No apresurarse si hay un error** — los errores son oportunidades pedagógicas
- ❌ **No dar la espalda al público mientras tipean** — hablar de frente siempre
- ❌ **No omitir ejecutar en vivo** — es un tutorial, el código debe funcionar
- ❌ **No usar fuente pequeña en el IDE** — 18-20pt mínimo
- ❌ **No terminar con "eso es todo"** — cerrar con impacto (ver sección de cierre)

---

## 🏁 Cómo cerrar profesionalmente

El cierre es lo que más recuerda el evaluador. No terminen con "bueno, eso fue todo".

### Cierre recomendado (Integrante 4):

> *"Para cerrar con una perspectiva: todo lo que vimos hoy — el logging automático, la autenticación, el retry — son exactamente los mismos patrones que usa Spring Boot, el framework más popular de Java en la industria.*
>
> *Cuando en el futuro pongan `@Transactional` en Spring, saben que debajo hay un aspecto que intercepta ese método y envuelve su ejecución en una transacción de base de datos. Cuando usan `@Cacheable`, hay un aspecto que comprueba el caché antes de ejecutar el método.*
>
> *AOP no es una curiosidad académica — es la infraestructura invisible que sostiene aplicaciones bancarias, de salud, y de comercio electrónico en producción hoy.*
>
> *Les dejamos el repositorio con todos los ejemplos y tres ejercicios para practicar. ¿Tienen preguntas?"*

---

## 🙋 Manejo de preguntas

### Si no saben la respuesta
> "Esa es una muy buena pregunta. En el alcance de este tutorial no exploramos eso en detalle, pero lo podemos investigar. Lo que sí sabemos es [dar lo que sí saben]."

**NUNCA inventar una respuesta.** Es mejor admitir los límites del conocimiento.

### Si el profesor pregunta algo difícil
Ver el archivo `resources/preguntas-profesor.md` con respuestas preparadas para las 12 preguntas más probables.

### Si el tiempo se acaba durante preguntas
> "Quedan más preguntas excelentes. Tenemos el repositorio disponible y estaremos [aquí/en email] para responder más detalladamente."

---

## 📋 Checklist final del día de la exposición

### 30 minutos antes:
```
□ Proyector conectado y funcionando
□ IntelliJ abierto con los 3 proyectos
□ mvn clean compile → sin errores (en los 3 proyectos)
□ mvn exec:java → funcionando (en los 3 proyectos)
□ Fuente del IDE en 18-20pt
□ Slides abiertas en la presentación correcta
□ Terminal visible y limpia
□ Teléfonos en silencio
□ Todos los integrantes conocen su parte
□ Practicar las transiciones entre integrantes
```

### Justo antes de empezar:
```
□ Respirar profundo
□ Recordar: la audiencia quiere que tengan éxito
□ El primer slide en pantalla
□ IDE abierto en ServicioProducto.java (sin aspectos — el estado "antes")
```

---

> **Último consejo:** El aspecto más importante de esta exposición (perdón por el juego de palabras) no es memorizar cada línea de código. Es poder responder: "¿Por qué alguien querría AOP?" y "¿Cuándo lo usaría yo?". Si transmiten eso, habrán hecho una exposición exitosa.
>
> ¡Éxito en la presentación! 🚀
