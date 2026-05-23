# 📝 Mini Taller — Tutorial AspectJ
## Ejercicios Prácticos para la Clase

> **Duración sugerida:** 15-20 minutos  
> **Modalidad:** Individual o en parejas  
> **Requisitos:** Haber visto el tutorial. Tener el proyecto en IntelliJ.

---

## 🎯 Instrucciones generales

1. Cada ejercicio tiene un enunciado, pistas graduales y la solución esperada.
2. **Intenta resolverlo antes de ver las pistas.**
3. Si necesitas ayuda, las pistas están en orden de menos a más reveladora.
4. Los ejercicios son progresivos: el 1 es más sencillo, el 3 más completo.

---

---

## Ejercicio 1 — Logging de una Biblioteca 📚

### Enunciado

Se te entrega el siguiente servicio de biblioteca. Tu tarea es crear un aspecto `BibliotecaLoggingAspect` que registre automáticamente cada operación sin modificar la clase `ServicioBiblioteca`.

**Clase dada (NO modificar):**

```java
package com.ejercicio.biblioteca;

public class ServicioBiblioteca {

    public String buscarLibro(String titulo) {
        // Lógica de búsqueda
        return "Libro encontrado: " + titulo + " [ISBN: 978-3-16-148410-0]";
    }

    public void prestarLibro(String isbn, String nombreUsuario) {
        System.out.println("  Libro " + isbn + " prestado a " + nombreUsuario);
    }

    public void devolverLibro(String isbn) {
        System.out.println("  Libro " + isbn + " devuelto al inventario");
    }

    public int contarLibrosDisponibles() {
        return 247;
    }
}
```

**Salida esperada al ejecutar:**

```
▶ [BIBLIOTECA] Iniciando: buscarLibro con argumentos: [El Quijote]
  Libro encontrado: El Quijote [ISBN: 978-3-16-148410-0]
◀ [BIBLIOTECA] Finalizado: buscarLibro

▶ [BIBLIOTECA] Iniciando: prestarLibro con argumentos: [978-3-16-148410-0, Juan García]
  Libro 978-3-16-148410-0 prestado a Juan García
◀ [BIBLIOTECA] Finalizado: prestarLibro
```

### ¿Qué debes crear?

1. La clase `BibliotecaLoggingAspect` con `@Aspect`
2. Un `@Pointcut` que intercepte todos los métodos del paquete `com.ejercicio.biblioteca`
3. Un `@Before` que imprima el nombre del método y sus argumentos
4. Un `@After` que imprima que el método terminó

### Pistas (léelas en orden solo si te bloqueas)

<details>
<summary>💡 Pista 1 (mínima)</summary>

La sintaxis de pointcut para interceptar todos los métodos de un paquete es:
`"execution(* com.ejercicio.biblioteca.*.*(..))"` 
</details>

<details>
<summary>💡 Pista 2</summary>

Para obtener los argumentos del método en `@Before`:
```java
public void logAntes(JoinPoint jp) {
    Object[] args = jp.getArgs(); // Array con los argumentos
    String nombre = jp.getSignature().getName(); // Nombre del método
}
```
</details>

<details>
<summary>💡 Pista 3 (casi la solución)</summary>

La estructura de tu aspecto debería ser:

```java
@Aspect
public class BibliotecaLoggingAspect {

    @Pointcut("execution(* com.ejercicio.biblioteca.*.*(..))")
    public void metodosBiblioteca() {}

    @Before("metodosBiblioteca()")
    public void antesDelMetodo(JoinPoint jp) {
        // Usar jp.getSignature().getName() y Arrays.toString(jp.getArgs())
    }

    @After("metodosBiblioteca()")
    public void despuesDelMetodo(JoinPoint jp) {
        // Imprimir que terminó
    }
}
```
</details>

### Solución esperada

```java
package com.ejercicio.biblioteca;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import java.util.Arrays;

@Aspect
public class BibliotecaLoggingAspect {

    @Pointcut("execution(* com.ejercicio.biblioteca.*.*(..))")
    public void metodosBiblioteca() {}

    @Before("metodosBiblioteca()")
    public void antesDelMetodo(JoinPoint jp) {
        System.out.println("▶ [BIBLIOTECA] Iniciando: " 
            + jp.getSignature().getName()
            + " con argumentos: " + Arrays.toString(jp.getArgs()));
    }

    @After("metodosBiblioteca()")
    public void despuesDelMetodo(JoinPoint jp) {
        System.out.println("◀ [BIBLIOTECA] Finalizado: " 
            + jp.getSignature().getName() + "\n");
    }
}
```

---

---

## Ejercicio 2 — Cronómetro con Umbral de Alerta ⏱️

### Enunciado

Crea un aspecto `CronometroAspect` que mida el tiempo de ejecución de TODOS los métodos de una clase `ProcesadorDatos`. El aspecto debe:

1. Medir el tiempo de cada método
2. Si el método tarda **más de 50ms**, imprimir una alerta: `⚠️ MÉTODO LENTO DETECTADO`
3. Usar el Advice tipo **AROUND** (obligatorio)

**Clase dada:**

```java
package com.ejercicio.datos;

public class ProcesadorDatos {

    public String procesarRapido(String dato) {
        // Simula operación rápida (10ms)
        sleep(10);
        return "Procesado: " + dato;
    }

    public int[] calcularEstadisticas(int[] numeros) {
        // Simula operación media (80ms) — DEBE activar alerta
        sleep(80);
        return new int[]{numeros.length, 0, 100};
    }

    public void generarInforme(String tipo) {
        // Simula operación lenta (200ms) — DEBE activar alerta
        sleep(200);
        System.out.println("  Informe generado: " + tipo);
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } 
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
```

**Salida esperada:**

```
⏱ [CRONO] procesarRapido tardó: 10ms
⏱ [CRONO] calcularEstadisticas tardó: 80ms
⚠️ [CRONO] ¡ALERTA! calcularEstadisticas superó el umbral de 50ms
⏱ [CRONO] generarInforme tardó: 200ms
⚠️ [CRONO] ¡ALERTA! generarInforme superó el umbral de 50ms
```

### Pistas

<details>
<summary>💡 Pista 1</summary>

El Advice Around debe usar `ProceedingJoinPoint` (no JoinPoint), y necesitas llamar `pjp.proceed()` para ejecutar el método real.
</details>

<details>
<summary>💡 Pista 2</summary>

Para medir el tiempo:
```java
long inicio = System.currentTimeMillis();
Object resultado = pjp.proceed();
long duracion = System.currentTimeMillis() - inicio;
```
</details>

<details>
<summary>💡 Pista 3</summary>

```java
@Around("ejecucionEnDatos()")
public Object medir(ProceedingJoinPoint pjp) throws Throwable {
    long inicio = System.currentTimeMillis();
    Object resultado = pjp.proceed(); // Ejecuta el método
    long duracion = System.currentTimeMillis() - inicio;
    // Imprimir y verificar umbral...
    return resultado;
}
```
</details>

### Solución esperada

```java
package com.ejercicio.datos;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class CronometroAspect {

    private static final long UMBRAL_MS = 50;

    @Pointcut("execution(* com.ejercicio.datos.ProcesadorDatos.*(..))")
    public void ejecucionEnDatos() {}

    @Around("ejecucionEnDatos()")
    public Object medir(ProceedingJoinPoint pjp) throws Throwable {
        String metodo = pjp.getSignature().getName();
        long inicio = System.currentTimeMillis();
        
        Object resultado = pjp.proceed();
        
        long duracion = System.currentTimeMillis() - inicio;
        System.out.printf("⏱ [CRONO] %s tardó: %dms%n", metodo, duracion);
        
        if (duracion > UMBRAL_MS) {
            System.out.printf("⚠️ [CRONO] ¡ALERTA! %s superó el umbral de %dms%n",
                metodo, UMBRAL_MS);
        }
        
        return resultado;
    }
}
```

---

---

## Ejercicio 3 — Validación de Parámetros 🛡️

### Enunciado (nivel intermedio)

Crea un aspecto `ValidacionAspect` que valide automáticamente que ningún parámetro de tipo `String` sea `null` o vacío, en todos los métodos del paquete `com.ejercicio.validacion`. Si algún parámetro es inválido, debe lanzar `IllegalArgumentException` con un mensaje descriptivo **antes** de que el método se ejecute.

**Comportamiento esperado:**

```java
ServicioRegistro servicio = new ServicioRegistro();

// Debe pasar sin error:
servicio.registrarUsuario("Ana López", "ana@email.com");

// Debe lanzar IllegalArgumentException:
servicio.registrarUsuario(null, "email@test.com");  // → "El parámetro 1 de registrarUsuario no puede ser null o vacío"
servicio.registrarUsuario("nombre", "");            // → "El parámetro 2 de registrarUsuario no puede ser null o vacío"
```

### Pistas

<details>
<summary>💡 Pista 1</summary>

Usa `@Before` para ejecutar la validación antes del método.
Puedes iterar sobre los argumentos con `jp.getArgs()`.
</details>

<details>
<summary>💡 Pista 2</summary>

```java
Object[] args = jp.getArgs();
for (int i = 0; i < args.length; i++) {
    if (args[i] instanceof String s) {
        // Verificar si s es null o vacío
    }
}
```
</details>

<details>
<summary>💡 Pista 3</summary>

Para lanzar la excepción desde un @Before:
```java
throw new IllegalArgumentException("El parámetro " + (i+1) 
    + " de " + jp.getSignature().getName() 
    + " no puede ser null o vacío");
```
Esto detiene la ejecución y el método anotado NO se ejecutará.
</details>

### Solución esperada

```java
package com.ejercicio.validacion;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class ValidacionAspect {

    @Pointcut("execution(* com.ejercicio.validacion.*.*(..))")
    public void metodosAValidar() {}

    @Before("metodosAValidar()")
    public void validarParametros(JoinPoint jp) {
        Object[] args = jp.getArgs();
        String metodo = jp.getSignature().getName();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String s) {
                if (s == null || s.isBlank()) {
                    throw new IllegalArgumentException(
                        "El parámetro " + (i + 1) + " de '" + metodo 
                        + "' no puede ser null o vacío");
                }
            }
        }
    }
}
```

---

## 🏆 Ejercicio Bonus — Combinar Aspectos

Si terminaste los tres ejercicios, intenta combinarlos:
1. Crea un proyecto nuevo con un `ServicioCompleto`
2. Aplícale el aspecto de **logging** Y el de **cronómetro** simultáneamente
3. Verifica que los dos aspectos se ejecutan en el orden correcto

**Pregunta de reflexión:** ¿En qué orden se ejecutan los aspectos cuando hay más de uno? 
*(Respuesta: se puede controlar con `@Order`. Por defecto, el orden no está garantizado.)*

---

*Tutorial AspectJ — Universidad Nacional de Colombia*
