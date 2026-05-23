# ❓ Posibles Preguntas del Evaluador — Guía de Respuestas

> Prepara estas preguntas. Pueden venir del profesor o de los compañeros.
> Las respuestas están diseñadas para ser claras, precisas y demostrables.

---

## Preguntas conceptuales

---

### 1. ¿Por qué AOP si podemos usar herencia o composición para reutilizar código?

**Respuesta:**
> "La herencia y composición son excelentes para reutilizar lógica de **negocio**. Pero los cross-cutting concerns tienen un problema diferente: deben aplicarse a muchas clases que no tienen relación jerárquica entre sí.
>
> Si pongo el logging en una clase base, obligo a todos a heredar de ella — violo el principio de composición y creo acoplamiento innecesario. Si uso composición, debo inyectar el logger en CADA clase. Con AOP, el concern se define una vez y se aplica automáticamente sin que las clases sepan de su existencia. Es Separación de Responsabilidades llevada al extremo."

---

### 2. ¿Cuál es la diferencia entre @Before, @After y @Around?

**Respuesta:**
> "Los tres son tipos de Advice (la acción que ejecuta el aspecto), pero con diferente nivel de control:
>
> - **@Before:** ejecuta ANTES del método. No puede cancelar la ejecución a menos que lance excepción. Úsalo para validaciones y logging de entrada.
> - **@After:** ejecuta SIEMPRE después, haya o no excepción (como un `finally`). Úsalo para limpieza de recursos.
> - **@Around:** envuelve completamente el método. Con `pjp.proceed()` decides cuándo (o si) se ejecuta el método original. Puedes modificar argumentos, interceptar el resultado, suprimir la ejecución. Es el más poderoso."

---

### 3. ¿Qué es el Weaving y cuándo ocurre?

**Respuesta:**
> "Weaving es el proceso de combinar el código del aspecto con el código de la clase objetivo para producir el comportamiento final. En nuestro tutorial usamos **compile-time weaving**: el plugin `aspectj-maven-plugin` actúa durante `mvn compile` y genera bytecode `.class` donde el aspecto ya está 'tejido' dentro del código del servicio.
>
> También existe **load-time weaving** (se aplica cuando la JVM carga la clase, usando un Java Agent) y **runtime weaving** (lo que hace Spring AOP usando proxies dinámicos). Cada uno tiene trade-offs en flexibilidad vs. performance."

---

### 4. ¿Cómo funciona un Pointcut? ¿Qué significa `execution(* com.tutorial.*.*(..))`?

**Respuesta:**
> "Un Pointcut es una expresión que selecciona un conjunto de Join Points (puntos de ejecución). La sintaxis de `execution()` es:
>
> `execution(modificador? tipo_retorno clase?.método(parámetros))`
>
> `execution(* com.tutorial.*.*(..))` se lee:
> - `*` (primer asterisco): cualquier tipo de retorno
> - `com.tutorial.*`: cualquier clase en ese paquete
> - `.*`: cualquier nombre de método
> - `(..)`: cualquier número y tipo de parámetros
>
> Hay otros designadores como `@annotation()` (métodos con una anotación específica), `within()` (clases en un paquete), `args()` (métodos con ciertos tipos de argumento), entre otros."

---

### 5. ¿AOP viola el principio de mínima sorpresa? ¿El programador sabe qué ocurre en cada método?

**Respuesta:**
> "Es una crítica válida y es la principal desventaja de AOP. Un programador que lee `servicio.buscarProducto()` y ve que el método no tiene código de logging puede sorprenderse cuando en la ejecución aparecen logs.
>
> Las buenas prácticas mitigan esto:
> 1. Documentar claramente qué aspectos están activos en el proyecto
> 2. Usar nombres descriptivos para los aspectos
> 3. Usar anotaciones como `@RequiereAutenticacion` que hacen explícito que el método tiene comportamiento extra
> 4. Mantener los aspectos en paquetes separados claramente nombrados
>
> Spring Boot lo maneja bien: cuando ves `@Transactional` sabes que hay un aspecto de transacciones. La visibilidad del aspect se da a través de las anotaciones."

---

### 6. ¿Cuál es la diferencia entre AspectJ y Spring AOP?

**Respuesta:**
> "Son complementarios. Spring AOP usa un subconjunto de AspectJ:
>
> - **Spring AOP:** solo funciona con beans de Spring, solo intercepta métodos (no constructores ni campos), usa proxies dinámicos en runtime (JDK Proxy o CGLIB). Es más sencillo pero limitado.
>
> - **AspectJ:** puede interceptar cualquier Join Point (métodos, constructores, acceso a campos, excepciones), funciona con cualquier clase Java (no solo Spring), puede usar compile-time weaving (más eficiente).
>
> En la práctica, cuando usas `@Aspect` y `@Around` en Spring Boot, estás usando las **anotaciones de AspectJ** pero la implementación interna de Spring (Spring AOP). Si necesitas interceptar constructores o código fuera del contexto Spring, necesitas AspectJ puro como en este tutorial."

---

### 7. ¿Puede un aspecto modificar los argumentos o el valor de retorno de un método?

**Respuesta:**
> "Sí, con un `@Around` advice:
> - **Modificar argumentos:** en vez de llamar `pjp.proceed()`, llamas `pjp.proceed(nuevosArgumentos)` donde `nuevosArgumentos` es un array con los argumentos modificados.
> - **Modificar el retorno:** `Object resultado = pjp.proceed(); return resultado + ' MODIFICADO';`
> - **Suprimir el método:** simplemente no llamas `pjp.proceed()` y retornas un valor alternativo.
>
> Esto es extremadamente poderoso pero también peligroso. Úsalo con cuidado y documéntalo bien."

---

### 8. ¿Qué pasa si el aspecto mismo lanza una excepción?

**Respuesta:**
> "Si el Advice lanza una excepción no capturada, se propaga como si la hubiera lanzado el método interceptado. En un `@Before`, si lanzas una excepción, el método original NUNCA se ejecuta. Esto es exactamente lo que hacemos en el aspecto de autenticación: si el usuario no está autorizado, lanzamos `SecurityException` en el `@Around` y nunca llamamos `pjp.proceed()`, por lo que el método del servicio bancario nunca ejecuta su código."

---

## Preguntas técnicas de implementación

---

### 9. ¿Por qué el método del Pointcut está vacío?

**Respuesta:**
> "El método del pointcut es solo una forma de **nombrar la expresión** para reutilizarla. AspectJ usa el nombre del método como identificador del pointcut. El cuerpo nunca se ejecuta; podría ser `{}` o incluso `{ /* no usar */ }`. Es una convención del framework para permitir que múltiples Advices referencien el mismo Pointcut por nombre en lugar de repetir la expresión."

---

### 10. ¿Por qué `@Around` debe declarar `throws Throwable` y no `throws Exception`?

**Respuesta:**
> "Porque el método interceptado podría lanzar **cualquier tipo** de `Throwable`, incluyendo `Error` (que no extiende `Exception`). Si declaráramos solo `throws Exception` y el método original lanzara un `Error` como `OutOfMemoryError`, el compilador nos daría error porque no podríamos propagarlo. Al declarar `throws Throwable`, garantizamos que cualquier problema del método original puede propagarse correctamente."

---

### 11. ¿Qué es el `ProceedingJoinPoint` y por qué solo se usa en `@Around`?

**Respuesta:**
> "`ProceedingJoinPoint` es una extensión de `JoinPoint` que añade el método `proceed()`. Este método es el que le dice a AspectJ 'ejecuta el método original ahora'. Solo tiene sentido en `@Around` porque es el único Advice que *controla* la ejecución del método interceptado. En `@Before` y `@After`, el método ya está programado para ejecutarse automáticamente, no hay necesidad de llamar `proceed()`."

---

## Preguntas de diseño

---

### 12. ¿Cuándo NO deberías usar AOP?

**Respuesta:**
> "AOP puede ser un antipatrón en estos casos:
> 1. **Lógica de negocio:** si la funcionalidad es específica a un caso de uso concreto, es mejor escribirla en el método directamente. AOP es para concerns TRANSVERSALES.
> 2. **Equipos sin conocimiento de AOP:** puede crear confusión y bugs difíciles de encontrar. El código hace cosas 'invisibles'.
> 3. **Depuración compleja:** los stack traces con AOP pueden ser más difíciles de interpretar.
> 4. **Performance crítico extremo:** el weaving añade overhead, aunque mínimo en compile-time.
> 5. **Flujos muy simples:** si solo tienes 2-3 clases, el overhead de configurar AOP no vale la pena."

---

*Preparar estas respuestas en voz alta antes de la exposición. El conocimiento es el mismo, pero la fluidez oral requiere práctica.*
