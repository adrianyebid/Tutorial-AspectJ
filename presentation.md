---
marp: true
theme: default
paginate: true
---

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800;900&family=JetBrains+Mono:wght@400;600&display=swap');

:root {
  --cyan:   #00d9ff;
  --purple: #a78bfa;
  --orange: #fb923c;
  --green:  #34d399;
  --red:    #f87171;
  --bg:     #0d1117;
  --bg2:    #161b22;
  --bg3:    #1c2333;
  --border: #30363d;
  --text:   #e6edf3;
  --text2:  #8b949e;
}

section {
  background: var(--bg);
  color: var(--text);
  font-family: 'Inter', sans-serif;
  font-size: 22px;
  padding: 48px 64px;
}

/* Número de página */
section::after {
  color: var(--text2);
  font-size: 14px;
  font-family: 'JetBrains Mono', monospace;
}

/* Encabezados */
h1 { font-size: 2.4em; font-weight: 900; letter-spacing: -0.03em; margin-bottom: 0.2em; color: var(--text); }
h2 { font-size: 1.6em; font-weight: 800; letter-spacing: -0.02em; margin-bottom: 0.6em; color: var(--text); }
h3 { font-size: 1.1em; font-weight: 700; color: var(--cyan); margin-bottom: 0.3em; }
h4 { font-size: 0.9em; font-weight: 700; color: var(--cyan); margin-bottom: 0.2em; }

/* Acento degradado */
.accent { background: linear-gradient(135deg, #00d9ff, #a78bfa);
  -webkit-background-clip: text; -webkit-text-fill-color: transparent; }

/* Párrafos y listas */
p { color: #c9d1d9; line-height: 1.65; margin: 0.4em 0; }
li { color: #c9d1d9; line-height: 1.7; margin-bottom: 0.2em; }
strong { color: var(--text); }

/* Código inline */
code {
  font-family: 'JetBrains Mono', monospace;
  font-size: 0.82em;
  background: var(--bg2);
  color: var(--cyan);
  padding: 0.1em 0.4em;
  border-radius: 4px;
  border: 1px solid var(--border);
}

/* Bloques de código */
pre {
  background: var(--bg2) !important;
  border: 1px solid var(--border);
  border-radius: 10px;
  padding: 1em 1.2em;
  font-size: 0.72em;
  line-height: 1.6;
}
pre code {
  background: none;
  border: none;
  padding: 0;
  font-size: 1em;
  color: #c9d1d9;
}

/* Tabla */
table { width: 100%; border-collapse: collapse; font-size: 0.78em;
        background: var(--bg2); border: 1px solid var(--border); border-radius: 8px; }
thead tr { background: var(--bg3); }
tbody tr { background: var(--bg2); }
tbody tr:nth-child(even) { background: #0d1117; }
th { padding: 0.6em 1em; text-align: left; color: var(--cyan); font-weight: 700;
     text-transform: uppercase; font-size: 0.85em; letter-spacing: 0.07em;
     border-bottom: 2px solid var(--border); }
td { padding: 0.55em 1em; border-bottom: 1px solid #21262d;
     color: var(--text); }
tr:last-child td { border: none; }

/* Blockquote → caja de info */
blockquote {
  background: var(--bg2);
  border-left: 3px solid var(--cyan);
  border-radius: 0 8px 8px 0;
  padding: 0.6em 1em;
  margin: 0.6em 0;
  font-style: normal;
}
blockquote p { color: var(--text2); margin: 0; font-size: 0.88em; }
blockquote.purple { border-color: var(--purple); }
blockquote.orange { border-color: var(--orange); }
blockquote.green  { border-color: var(--green); }
blockquote.red    { border-color: var(--red); }

/* Layout dos columnas */
.cols { display: grid; grid-template-columns: 1fr 1fr; gap: 1.2em; }
.cols-6040 { display: grid; grid-template-columns: 6fr 4fr; gap: 1.2em; }
.cols-4060 { display: grid; grid-template-columns: 4fr 6fr; gap: 1.2em; }

/* Cards */
.cards { display: grid; gap: 0.7em; }
.cards-2 { grid-template-columns: 1fr 1fr; }
.cards-3 { grid-template-columns: 1fr 1fr 1fr; }
.cards-4 { grid-template-columns: 1fr 1fr 1fr 1fr; }
.card {
  background: var(--bg2);
  border: 1px solid var(--border);
  border-radius: 10px;
  padding: 0.9em 1em;
}
.card h4 { color: var(--cyan); font-size: 0.85em; margin-bottom: 0.3em; }
.card p  { font-size: 0.78em; color: var(--text2); margin: 0; line-height: 1.5; }
.card.purple h4 { color: var(--purple); }
.card.orange h4 { color: var(--orange); }
.card.green  h4 { color: var(--green); }
.card.red    h4 { color: var(--red); }

/* Tag / badge */
.tag {
  display: inline-block;
  font-family: 'JetBrains Mono', monospace;
  font-size: 0.6em;
  padding: 0.2em 0.8em;
  border-radius: 100px;
  background: rgba(0,217,255,0.1);
  border: 1px solid rgba(0,217,255,0.25);
  color: var(--cyan);
  letter-spacing: 0.1em;
  text-transform: uppercase;
  font-weight: 700;
  margin-bottom: 0.5em;
}

/* Checks y cruces en tabla */
.chk { color: var(--green); font-weight: 700; }
.crs { color: var(--red);   font-weight: 700; }
.mid { color: var(--orange);font-weight: 700; }

/* Slide de portada */
section.cover {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.cover-badge {
  display: inline-flex;
  align-items: center;
  gap: 0.5em;
  background: rgba(0,217,255,0.08);
  border: 1px solid rgba(0,217,255,0.25);
  border-radius: 100px;
  padding: 0.3em 1em;
  font-size: 0.55em;
  font-weight: 600;
  color: var(--cyan);
  letter-spacing: 0.08em;
  text-transform: uppercase;
  margin-bottom: 1em;
  width: fit-content;
}
.stats {
  display: flex;
  gap: 2.5em;
  margin-top: 1.5em;
}
.stat-n { font-family: 'JetBrains Mono', monospace; font-size: 2em; font-weight: 800; color: var(--cyan); line-height: 1; }
.stat-l { font-size: 0.55em; color: var(--text2); text-transform: uppercase; letter-spacing: 0.1em; }

/* Separadores de sección */
section.section-cover {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: var(--bg2);
}
</style>

<!-- _class: cover -->

<div class="cover-badge">● Universidad Nacional de Colombia &nbsp;·&nbsp; Lenguajes de Programación</div>

# Tutorial
# <span class="accent">AspectJ & AOP</span>

Programación Orientada a Aspectos en Java —
de los conceptos básicos a ejemplos prácticos.

<div class="stats">
  <div><div class="stat-n">5</div><div class="stat-l">Conceptos clave</div></div>
  <div><div class="stat-n">3</div><div class="stat-l">Tutoriales originales</div></div>
  <div><div class="stat-n">5</div><div class="stat-l">Tipos de advice</div></div>
  <div><div class="stat-n">2</div><div class="stat-l">Compiladores</div></div>
</div>

---

<div class="tag">Contenido</div>

## ¿Qué vamos a ver?

<div class="cols">
<div>

- ¿Qué es AOP y por qué existe?
- Compatibilidad con Java
- Los 5 conceptos clave
- Declaración de aspectos
- Compiladores: `ajc` y `abc`
- Instalación con Maven

</div>
<div>

- Los 5 tipos de Advice
- Ejemplos de código progresivos
- 3 tutoriales originales
- AspectJ vs Spring AOP
- ¿Cuándo usar AOP?
- Conclusiones

</div>
</div>

---

<!-- _class: section-cover -->

# <span class="accent">¿Por qué AOP?</span>

En OOP, ciertos comportamientos **se repiten en todas las clases** sin poder centralizarse:

- 🪵 **Logging** — registrar cada método en decenas de clases
- 🔒 **Seguridad** — verificar permisos antes de cualquier operación
- ⏱️ **Métricas** — medir tiempos de ejecución en todo el sistema
- 🔄 **Transacciones** — abrir/cerrar transacciones en cada acceso a BD
- 🐛 **Auditoría** — rastrear quién hizo qué y cuándo

Esto viola **DRY** y **SRP** — AOP resuelve exactamente eso.

---

<div class="tag">El Problema</div>

## Código que se repite en todas las clases

```java
// Este bloque aparece en las 50 clases del sistema:
public void procesarPedido(Pedido p) {
    log.info("Inicio procesarPedido");    // logging
    checkAuth(usuario);                    // seguridad
    long t = System.currentTimeMillis();  // timing

    pedidoRepo.save(p);   // ← única línea de lógica real

    log.info("Fin: " + (System.currentTimeMillis() - t) + "ms");
}
// Cambiar el formato del log = modificar 50 clases
```

> **Problema:** Logging, seguridad y métricas son **cross-cutting concerns** — se repiten en todo el código, violando DRY y SRP.

---

<div class="tag">La Solución</div>

## Con AOP: se define una sola vez

```java
public aspect LoggingAspect {

    // Selecciona TODOS los métodos de cualquier Service
    pointcut serviceMethods() :
        execution(* com.app.service.*.*(..));

    before() : serviceMethods() {
        System.out.println("Inicio: " + thisJoinPoint.getSignature());
    }

    after() : serviceMethods() {
        System.out.println("Fin del método");
    }
}
// El servicio queda completamente limpio — sin logging, sin seguridad
```

---

<div class="tag">Compatibilidad</div>

## AspectJ es compatible con Java

> Creado en **PARC** (Palo Alto Research Center) por el equipo Xerox, liderado por **Gregor Kiczales**. Estáticamente tipado, usa el sistema de tipos de Java.

<div class="cards cards-3" style="margin-top: 1em">
<div class="card">

#### Base
Todo programa Java válido es un programa AspectJ válido.

</div>
<div class="card purple">

#### Plataforma
Todo programa AspectJ corre sobre la JVM estándar sin modificaciones.

</div>
<div class="card green">

#### Programación
La programación en AspectJ es una extensión *natural* de Java.

</div>
</div>

---

<!-- _class: section-cover -->

# <span class="accent">Conceptos Clave</span>

Los 5 pilares fundamentales de AOP:

1. 🧩 **Aspect** — módulo que encapsula el concern transversal
2. 📍 **Join Point** — punto de ejecución donde puede actuar un aspecto
3. 🎯 **Pointcut** — expresión que selecciona los Join Points deseados
4. ⚡ **Advice** — código que se ejecuta en el Join Point seleccionado
5. 🔗 **Weaving** — proceso que combina aspectos con el código base

---

<div class="tag">Vocabulario</div>

## Los 5 conceptos esenciales

<div class="cards cards-3" style="margin-top: 0.8em">
<div class="card">

#### 🧩 Aspect
Módulo que encapsula un cross-cutting concern. Equivale a una clase en OOP pero para comportamiento transversal.

</div>
<div class="card purple">

#### 📍 Join Point
Punto bien definido en la ejecución: llamada a método, constructor, acceso a campo, handler de excepción...

</div>
<div class="card orange">

#### 🎯 Pointcut
Expresión que selecciona *cuáles* Join Points nos interesan. Define **dónde** actuará el aspecto.

</div>
<div class="card green">

#### ⚡ Advice
Código que se ejecuta en el Join Point seleccionado. Puede ser `before`, `after` o `around`.

</div>
<div class="card red" style="grid-column: span 2;">

#### 🔗 Weaving
Proceso que **combina** los aspectos con el código base. Puede ocurrir en compile-time (`ajc`), load-time (agente JVM) o runtime (proxies dinámicos como en Spring AOP).

</div>
</div>

---

<div class="tag">Weaving</div>

## 🔗 Weaving — el proceso de "tejer"

Combina los aspectos con el código base. Puede ocurrir en tres momentos:

<div class="cards cards-3" style="margin-top: 0.8em">
<div class="card">

#### Compile-time
El compilador `ajc` teje durante la compilación. **Mejor rendimiento**, sin overhead en runtime.

</div>
<div class="card orange">

#### Load-time
El agente de AspectJ teje cuando la JVM carga las clases. No requiere recompilar el código base.

</div>
<div class="card green">

#### Runtime
Usado por Spring AOP con proxies dinámicos. Más sencillo pero limitado a métodos públicos de beans.

</div>
</div>

---

<div class="tag">Pointcut</div>

## Pointcut — patrones y comodines

```java
// * → exactamente un token  |  .. → cero o más tokens

// Todos los métodos de cualquier clase Service
pointcut serviceMethods() :
    execution(* com.app.service.*.*(..));

// Métodos con exactamente dos parámetros
pointcut twoParams() :
    call(void MiClase.metodo(*, *));

// Combinando con operadores lógicos
pointcut serviceWithString() :
    execution(* com.app.service.*.*(..))
    && execution(* *.*(.., String, ..));

// Capturar argumentos del método
pointcut lending(Member m, Book b) :
    execution(void Library.lendBook(Member, Book))
    && args(m, b);
```

---

<!-- _class: section-cover -->

# <span class="accent">Declaración de Aspectos</span>

Sintaxis completa y modificadores especiales

---

<div class="tag">Sintaxis</div>

## Estructura de un Aspecto

```
[ privileged ] aspect Id
    [ extends Type ]
    [ implements TypeList ]
    [ Per Clause ]
{ Body }
```

<div class="cards cards-2" style="margin-top: 0.8em">
<div class="card">

#### `privileged`
Permite acceder a miembros `private` de clases externas sin getters.

</div>
<div class="card purple">

#### `extends / implements`
Un aspecto puede heredar de un aspecto abstracto o implementar interfaces.

</div>
<div class="card orange">

#### `Per Clause`
Controla la instanciación: `issingleton()`, `perthis()`, `pertarget()`, `percflow()`.

</div>
<div class="card green">

#### Por defecto
Los aspectos son **singleton** — una sola instancia por JVM.

</div>
</div>

---

<div class="tag">privileged</div>

## `privileged` — acceso a miembros privados

<div class="cols">
<div>

Permite al aspecto leer y modificar campos `private` **sin** que la clase exponga getters.

> Útil para aspectos de logging o debugging que necesitan inspeccionar estado interno sin cambiar la API pública.

**Reglas de herencia:**
- Un aspecto no se puede instanciar con `new`
- Una clase no puede extender un aspecto
- Un aspecto no puede extender uno no abstracto

</div>

```java
privileged aspect AuditAspect {

    before() : execution(* Account.deposit(..)) {
        Account a =
            (Account) thisJoinPoint.getTarget();

        // Accede a 'balance' aunque sea private
        System.out.println(
            "Balance antes: " + a.balance);
    }
}
```

</div>

---

<div class="tag">Per Clause</div>

## Per Clause — controlar instanciación

```java
// Una instancia del aspecto por cada objeto Account
public aspect AccountAudit
        perthis(execution(* Account.*(..))) {

    private int operationCount = 0;  // estado por instancia

    after() : execution(* Account.*(..)) {
        operationCount++;
        System.out.println(
            "Operaciones en esta cuenta: " + operationCount);
    }
}
// Sin perthis: un único contador compartido (singleton)
// Con perthis:  un contador independiente por cada Account
```

> **Nota:** No se recomienda mezclar varios per clauses en el mismo proyecto ya que pueden interferir con los flujos de otros aspectos.

---

<!-- _class: section-cover -->

# <span class="accent">Compiladores e Instalación</span>

`ajc`, `abc` y configuración Maven

---

<div class="tag">Compiladores</div>

## Los dos compiladores de AspectJ

<div class="cols" style="margin-top: 0.8em">
<div class="card" style="border-top: 3px solid #00d9ff; padding: 1.2em">

### `ajc`
Compilador oficial del proyecto **Eclipse**. Estándar de producción.

- Soporta compile-time y load-time weaving
- Se integra con Maven/Gradle
- `mvn compile` lo invoca automáticamente

</div>
<div class="card" style="border-top: 3px solid #a78bfa; padding: 1.2em">

### `abc`
*AspectBench Compiler* — diseñado para **investigación**.

- Extensible: permite agregar nuevas características al lenguaje
- Genera bytecode más optimizado en ciertos escenarios
- Producido en `aspectbench.org`

</div>
</div>

---

<div class="tag">Instalación</div>

## Configuración Maven — `pom.xml`

```xml
<dependencies>
  <dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.21</version>
  </dependency>
</dependencies>

<build><plugins>
  <plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.15.0</version>
    <configuration>
      <complianceLevel>17</complianceLevel>
    </configuration>
    <executions>
      <execution><goals><goal>compile</goal></goals></execution>
    </executions>
  </plugin>
</plugins></build>
```

---

<div class="tag">Instalación</div>

## Pasos de configuración

1. **Verificar Java y Maven**
   ```bash
   java -version   # Java 11+ requerido
   mvn -version    # Maven 3.6+ requerido
   ```

2. **Plugin IntelliJ IDEA**
   `File → Settings → Plugins → Marketplace → "AspectJ Support" → instalar → reiniciar`

3. **Compilar y ejecutar**
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.app.Main"
   ```

> Los archivos `.aj` van en la misma estructura de paquetes que los `.java`. El compilador `ajc` los procesa juntos.

---

<!-- _class: section-cover -->

# <span class="accent">Tipos de Advice</span>

before, after, around y sus variantes

---

<div class="tag">Advice</div>

## Los 5 tipos de Advice

| Advice | Cuándo ejecuta | Uso típico |
|---|---|---|
| `before()` | Antes del método | Validación, logging de entrada |
| `after()` | Siempre al final (≈ `finally`) | Liberación de recursos |
| `after() returning` | Solo si terminó con éxito | Auditoría, caché post-ejecución |
| `after() throwing` | Solo si lanzó excepción | Manejo centralizado de errores |
| `around()` | Envuelve el método completo | Caché, retry, timing, seguridad |

> **Flujo:** `around inicio` → `before` → **método()** → `after` → `around fin`

---

<div class="tag">before / after</div>

## `before()` y `after()`

```java
public aspect TimingAspect {

    pointcut serviceMethods() :
        execution(* com.app.service.*.*(..));

    // Antes del método — no puede detener la ejecución (salvo excepción)
    before() : serviceMethods() {
        System.out.println("→ " + thisJoinPoint.getSignature().getName());
    }

    // Siempre al final, tanto en éxito como en excepción (como finally)
    after() : serviceMethods() {
        System.out.println("← " + thisJoinPoint.getSignature().getName());
    }
}
```

---

<div class="tag">after returning / throwing</div>

## Variantes de `after()`

```java
public aspect ResultAspect {

    pointcut query() : execution(* Repository.find*(..));

    // Solo si terminó sin excepción — accede al valor de retorno
    after() returning(Object result) : query() {
        System.out.println("Resultado: " + result);
    }

    // Solo si lanzó excepción — accede al objeto de excepción
    after() throwing(Exception ex) : query() {
        System.err.println("Error en consulta: " + ex.getMessage());
    }
}
```

> `returning` y `throwing` son mutuamente excluyentes. `after()` sin modificador ejecuta en **ambos** casos.

---

<div class="tag">around</div>

## `around()` — control total del flujo

```java
public aspect CacheAspect {

    private Map<String, Object> cache = new HashMap<>();

    Object around() : execution(* ProductService.getProduct(String)) {
        String key = (String) thisJoinPoint.getArgs()[0];

        if (cache.containsKey(key)) {
            System.out.println("[CACHE HIT] " + key);
            return cache.get(key);   // NO llama proceed() → evita la BD
        }

        Object result = proceed();   // ejecuta el método real
        cache.put(key, result);      // guarda en caché para la próxima vez
        return result;
    }
}
```

> **Regla:** siempre retornar un valor compatible con el tipo del método interceptado. Llamar `proceed()` a menos que **intencionalmente** quieras bloquear la ejecución.

---

<!-- _class: section-cover -->

# <span class="accent">Ejemplos de Código</span>

Del cajero automático a declaraciones inter-tipo

---

<div class="tag">before / after</div>

## Auditoría transparente en un cajero

<div class="cols">

```java
// CajeroAutomatico.java — sin una línea de log
public class CajeroAutomatico {
    private double saldo;

    public void depositar(double monto) {
        saldo += monto;
    }
    public void retirar(double monto) {
        if (monto > saldo)
            throw new IllegalStateException("Fondos insuficientes");
        saldo -= monto;
    }
    public double getSaldo() { return saldo; }
}
```

```java
// AuditoriaAspect.aj
public aspect AuditoriaAspect {

    pointcut operaciones() :
        execution(* CajeroAutomatico.*(..))
        && !execution(* CajeroAutomatico.getSaldo());

    before() : operaciones() {
        System.out.println("[ANTES] "
            + thisJoinPoint.getSignature().getName());
    }

    after() : operaciones() {
        System.out.println("[DESPUÉS] operación completada");
    }
}
```

</div>

> `CajeroAutomatico.java` no sabe que existe `AuditoriaAspect.aj`. El log se añade sin tocar la lógica de negocio.

---

<div class="tag">Comodines</div>

## Wildcards `*` y `..` en pointcuts

```java
// Restaurante.java
public class Restaurante {
    public void prepararEntrada()   { /* … */ }
    public void prepararPrincipal() { /* … */ }
    public void prepararPostre()    { /* … */ }
    public void cobrar()            { /* … */ }
}
```

```java
// OrdenAspect.aj
public aspect OrdenAspect {

    // * coincide con cualquier nombre que comience por "preparar"
    pointcut cocina() : call(void Restaurante.preparar*(..));

    // .. = cualquier número de parámetros; * = cualquier método
    pointcut cualquiera() : call(* Restaurante.*(..));

    before() : cocina() {
        System.out.println("[COCINA] iniciando: "
            + thisJoinPoint.getSignature().getName());
    }

    // cobrar() es el único que no pasa por cocina()
    after() : cualquiera() && !cocina() {
        System.out.println("[CAJA] operación de caja completada");
    }
}
// prepararEntrada, prepararPrincipal, prepararPostre → disparan cocina()
// cobrar() → dispara solo la segunda regla
```

---

<div class="tag">call vs execution</div>

## `call` vs `execution` — diferencia clave

<div class="cols">
<div>

**`call()`**
Se activa en el **lugar desde donde se llama** al método (el *caller*).

**`execution()`**
Se activa **dentro del cuerpo** del método (el *callee*).

> `Moto` hereda `arrancar()` de `Vehiculo`.
> El cuerpo del método vive en `Vehiculo`.
>
> `call(* Moto.arrancar())` → se dispara en el sitio de invocación.
> `execution(* Vehiculo.arrancar())` → se dispara al entrar al cuerpo.

</div>

```java
public aspect SensorTrafico {

    // call: se dispara donde se invoca moto.arrancar()
    before() : call(* Moto.arrancar()) {
        System.out.println(
            "[CALL]  sensor en el caller");
    }

    // execution: se dispara dentro del cuerpo heredado de Vehiculo
    after() : execution(* Vehiculo.arrancar()) {
        System.out.println(
            "[EXEC]  sensor dentro del método");
    }
}
// moto.arrancar() → dispara CALL primero, luego EXEC
// ambos se activan con una sola llamada
```

</div>

---

<div class="tag">Inter-Type Declarations</div>

## Agregar campos y métodos a clases existentes

```java
// Tarea.java — sin campo de prioridad
public class Tarea {
    private String descripcion;
    public Tarea(String d) { this.descripcion = d; }
    public void ejecutar() {
        System.out.println("  Ejecutando: " + descripcion);
    }
}
```

```java
// PrioridadAspect.aj — inyecta prioridad desde fuera
public aspect PrioridadAspect {

    int Tarea.prioridad = 5;                          // ITD: campo con valor por defecto

    public void Tarea.setPrioridad(int p) {
        this.prioridad = (p >= 1 && p <= 10) ? p : 5;
    }
    public int Tarea.getPrioridad() { return this.prioridad; }

    before() : execution(void Tarea.ejecutar()) {
        Tarea t = (Tarea) thisJoinPoint.getTarget();
        System.out.printf("  [ITD] prioridad: %d%n", t.getPrioridad());
    }
}
// En Main.java: tarea.setPrioridad(1) funciona aunque Tarea no lo declare
```

---

<!-- _class: section-cover -->

# <span class="accent">Tutoriales Originales</span>

Tres sistemas completos desde cero

---

<div class="tag">Tutorial 1 — Biblioteca</div>

## Sistema de Biblioteca — 3 aspectos coordinados

**Objetivo:** controlar una biblioteca *sin modificar* `Library.java`.

| Aspecto | Tipo | Qué hace |
|---|---|---|
| `DebtCheckAspect` | `before` + `args()` | Rechaza préstamo si el socio tiene deuda |
| `LoanLimitAspect` | `around` | Bloquea si el socio ya tiene 3 libros activos |
| `ActivityLogAspect` | `after returning` | Registra solo operaciones exitosas |

> Los tres aspectos actúan sobre los mismos join points de `Library.lendBook()` y `Library.returnBook()` de forma **independiente y componible**.

---

<div class="tag">Tutorial 1 — LoanLimitAspect</div>

## `around()` para controlar el flujo

```java
public aspect LoanLimitAspect {

    private static final int MAX_LOANS = 3;

    pointcut lending(Member m, Book b) :
        execution(void Library.lendBook(Member, Book)) && args(m, b);

    void around(Member m, Book b) : lending(m, b) {
        if (m.loanCount() >= MAX_LOANS) {
            System.out.println("[LIMITE] " + m.getName()
                + " ya tiene " + MAX_LOANS + " préstamos activos.");
            return;        // NO llama proceed() → bloquea la operación
        }
        proceed(m, b);     // ejecuta Library.lendBook normalmente
    }
}
// Sin perthis: el contador de loanCount() vive en Member
// El aspecto solo decide si permit o no la ejecución
```

---

<div class="tag">Tutorial 2 — Calificaciones</div>

## Validación y alerta automática con `args()` + `after returning`

```java
// Aspecto 1: valida que la nota esté entre 0.0 y 5.0
public aspect GradeValidationAspect {
    pointcut gradeRecording(Student s, String subject, double grade) :
        execution(void GradeBook.recordGrade(Student, String, double))
        && args(s, subject, grade);

    before(Student s, String subject, double grade)
            : gradeRecording(s, subject, grade) {
        if (grade < 0.0 || grade > 5.0)
            throw new IllegalArgumentException(
                "Nota inválida: " + grade + " para " + s.getName());
    }
}

// Aspecto 2: alerta si el promedio < 3.0
public aspect AcademicRiskAspect {
    after(Student s) returning(double avg)
            : execution(double GradeBook.computeAverage(Student, ..))
              && args(s, *) {
        if (avg < 3.0)
            System.out.println("[ALERTA] " + s.getName()
                + " en riesgo. Promedio: " + avg);
    }
}
```

---

<div class="tag">Tutorial 3 — Caché</div>

## `around()` como patrón de optimización

```java
public aspect CacheAspect {

    private Map<String, Double> cache = new HashMap<>();

    pointcut priceQuery(String id) :
        execution(double ProductCatalog.getPrice(String)) && args(id);

    pointcut priceUpdate(String id) :
        execution(void ProductCatalog.updatePrice(String, double)) && args(id, *);

    double around(String id) : priceQuery(id) {
        if (cache.containsKey(id)) {
            System.out.println("[CACHE HIT] " + id);
            return cache.get(id);    // sin tocar la BD
        }
        double result = proceed(id); // consulta real
        cache.put(id, result);
        System.out.println("[CACHE SET] " + id + " = " + result);
        return result;
    }

    // Invalida caché cuando el precio cambia
    after(String id) : priceUpdate(id) { cache.remove(id); }
}
```

---

<!-- _class: section-cover -->

# <span class="accent">AspectJ vs Spring AOP</span>

¿Cuándo usar cada uno?

---

<div class="tag">Comparativa</div>

## AspectJ vs Spring AOP

| Característica | AspectJ | Spring AOP |
|---|---|---|
| Join Points | Métodos, constructores, campos, excepciones | Solo métodos públicos de beans |
| Clases interceptables | Cualquier clase Java | Solo Spring beans |
| Performance | ✅ Sin overhead en runtime | ⚠️ Proxies dinámicos |
| Configuración | Plugin Maven/Gradle | ✅ Automática con Spring Boot |
| Inter-type declarations | ✅ Sí | ❌ No |
| `declare parents` | ✅ Sí | ❌ No |
| Curva de aprendizaje | Mayor | ✅ Menor |

> **En producción:** Spring usa AspectJ internamente. `@Transactional`, `@Cacheable` y `@Secured` son aspectos bajo el capó.

---

<div class="tag">Mejores Prácticas</div>

## ¿Cuándo usar AOP?

<div class="cols">
<div>

### ✅ Úsalo para...

- Logging y trazabilidad de métodos
- Medición de performance / métricas
- Autenticación y autorización
- Manejo de transacciones de BD
- Auditoría y registro de actividad
- Caché de resultados de métodos
- Validación de parámetros de entrada
- Retry automático con backoff

</div>
<div>

### ❌ No lo uses para...

- Lógica de negocio principal del dominio
- Proyectos pequeños o scripts
- Cuando el equipo no conoce AOP
- Cuando la "magia" dificulta el debug
- Reemplazar inyección de dependencias
- Lógica diferente para cada clase individualmente

</div>
</div>

---

<!-- _class: section-cover -->

# <span class="accent">Conclusiones</span>

---

<div class="tag">Resumen</div>

## Lo que aprendimos

<div class="cards cards-2" style="margin-top: 0.6em">
<div class="card">

#### El problema que resuelve AOP
Los cross-cutting concerns se repiten en decenas de clases con OOP puro, violando DRY y SRP. AOP los externaliza a Aspectos independientes.

</div>
<div class="card purple">

#### Los 5 conceptos
**Aspect** → módulo. **Join Point** → dónde puede actuar. **Pointcut** → dónde quiero. **Advice** → qué hace. **Weaving** → cómo se combina.

</div>
<div class="card green">

#### OOP + AOP = Complementarios
OOP modela el dominio. AOP maneja la infraestructura transversal. Trabajan juntos en cualquier aplicación real.

</div>
<div class="card orange">

#### AspectJ en la industria
Spring, Hibernate, Mockito y decenas de frameworks usan AOP internamente. Conocerlo permite entender mejor cómo funcionan por dentro.

</div>
</div>

---

<!-- _class: cover -->

# <span class="accent">Gracias</span>

---

<div class="tag">Bibliografía</div>

## Referencias

<div style="font-size: 0.72em; line-height: 1.9;">

**[1]** Kiczales, G., Lamping, J., Mendhekar, A., Maeda, C., Lopes, C. V., Loingtier, J. M., & Irwin, J. (1997). *Aspect-Oriented Programming*. En M. Akşit & S. Matsuoka (Eds.), *ECOOP '97 — Object-Oriented Programming*, Lecture Notes in Computer Science, vol. 1241, pp. 220–242. Springer. https://doi.org/10.1007/BFb0053381

**[2]** Kiczales, G., Hilsdale, E., Hugunin, J., Kersten, M., Palm, J., & Griswold, W. G. (2001). *An overview of AspectJ*. En J. L. Knudsen (Ed.), *ECOOP 2001*, Lecture Notes in Computer Science, vol. 2072, pp. 327–354. Springer. https://doi.org/10.1007/3-540-45337-6_18

**[3]** Laddad, R. (2003). *AspectJ in Action: Practical Aspect-Oriented Programming*. Manning Publications.

**[4]** Eclipse AspectJ Development Tools. (2024). *AspectJ Programming Guide*. The Eclipse Foundation. https://www.eclipse.org/aspectj/doc/released/progguide/index.html

**[5]** Spring Framework Team. (2024). *Aspect Oriented Programming with Spring* (v6.x). VMware / Spring. https://docs.spring.io/spring-framework/reference/core/aop.html

**[6]** Eclipse Foundation. (2024). *AspectJ 1.9.x — Release Notes and Downloads*. https://www.eclipse.org/aspectj/

</div>
