# 🔧 Errores Comunes y Soluciones — AspectJ

> Guía de troubleshooting para cuando algo no funciona como esperabas.

---

## Error 1: El aspecto no se aplica (el código del advice no se ejecuta)

**Síntoma:** Ejecutas el programa pero no ves los mensajes del aspecto.

**Causas posibles y soluciones:**

### a) El plugin Maven no está configurado
```xml
<!-- Verificar que esto está en pom.xml -->
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    ...
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>  <!-- ← Este goal es obligatorio -->
            </goals>
        </execution>
    </executions>
</plugin>
```

### b) El directorio de aspectos no está configurado
```xml
<configuration>
    <!-- Si los aspectos están en src/main/aspect/ -->
    <aspectDirectory>src/main/aspect</aspectDirectory>
</configuration>
```

### c) El Pointcut no coincide
Verifica la expresión de tu Pointcut. Por ejemplo:
```java
// MAL: falta un punto antes del método
@Pointcut("execution(* com.tutorial.basic*.*(..))")
//                                      ↑ Falta el punto

// BIEN:
@Pointcut("execution(* com.tutorial.basic.*.*(..))")
//                                       ↑ Punto correcto
```

### d) Compilar sin el aspecto
Si usas `javac` en lugar de `mvn compile`, el weaving no ocurre. Siempre usa Maven:
```bash
mvn clean compile
```

---

## Error 2: ClassNotFoundException o NoClassDefFoundError para aspectjrt

**Síntoma:**
```
Exception in thread "main" java.lang.NoClassDefFoundError: 
  org/aspectj/lang/annotation/Aspect
```

**Solución:** Agregar la dependencia en pom.xml:
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.21</version>
</dependency>
```

---

## Error 3: @Around sin llamar proceed() — el método original no se ejecuta

**Síntoma:** El programa funciona pero el método real nunca corre.

**Causa:** Se olvidó llamar `pjp.proceed()`.

```java
// MAL:
@Around("miPointcut()")
public Object advice(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("Antes");
    // ← ¡¡Falta pjp.proceed()!!
    System.out.println("Después");
    return null; // Devuelve null siempre en lugar del resultado real
}

// BIEN:
@Around("miPointcut()")
public Object advice(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("Antes");
    Object resultado = pjp.proceed(); // ← Ejecuta el método real
    System.out.println("Después");
    return resultado; // ← Devuelve el resultado real
}
```

---

## Error 4: El aspecto se aplica a sí mismo (bucle infinito o StackOverflow)

**Síntoma:** StackOverflowError al ejecutar.

**Causa:** El Pointcut es demasiado amplio e intercepta los métodos del propio aspecto.

**Solución:** Excluir la clase del aspecto:
```java
// MAL:
@Pointcut("execution(* com.tutorial.*.*(..))")

// BIEN: excluir la clase del aspecto
@Pointcut("execution(* com.tutorial.*.*(..)) && !within(MiAspecto)")
```

---

## Error 5: Versión incompatible de Java

**Síntoma:**
```
[ERROR] Failed to execute goal org.codehaus.mojo:aspectj-maven-plugin:1.15.0:compile
  Unsupported class file major version 61
```

**Causa:** La versión de Java (61 = Java 17) no coincide con la configuración del plugin.

**Solución:** Verificar que las versiones coincidan en pom.xml:
```xml
<properties>
    <java.version>17</java.version>
</properties>

<plugin>
    <configuration>
        <complianceLevel>17</complianceLevel>  <!-- ← Debe coincidir con java.version -->
        <source>17</source>
        <target>17</target>
    </configuration>
</plugin>
```

---

## Error 6: Aspecto no reconocido por IntelliJ

**Síntoma:** IntelliJ muestra la clase como Java normal, sin el icono de AspectJ.

**Solución:**
1. Instalar el plugin de AspectJ: `File → Settings → Plugins → "AspectJ Support"`
2. Reimportar el proyecto Maven: click derecho en pom.xml → `Maven → Reload project`
3. Si el proyecto no tiene el directorio `aspect/` en el source path, ir a:
   `Project Structure → Modules → Sources` y marcar `src/main/aspect` como Sources root.

---

## Error 7: `@annotation()` no funciona

**Síntoma:** El aspecto debería interceptar métodos con `@MiAnotacion` pero no lo hace.

**Causa más común:** La anotación tiene `@Retention(RetentionPolicy.CLASS)` en lugar de `RUNTIME`.

```java
// MAL: la anotación no existe en runtime
@Retention(RetentionPolicy.CLASS)
public @interface MiAnotacion {}

// BIEN: la anotación existe en runtime para que AOP la vea
@Retention(RetentionPolicy.RUNTIME)
public @interface MiAnotacion {}
```

---

## Checklist rápido antes de una demo

```
□ mvn clean compile → sin errores
□ mvn exec:java → funciona y muestra salida de aspectos
□ Fuente del IDE en tamaño grande (Ctrl++) para que la audiencia vea
□ Terminal con fondo oscuro y texto claro (más visible en proyector)
□ Ejemplos ejecutados al menos una vez antes de la demo
□ Saber qué output esperar para cada demo
□ Tener el código abierto en el IDE antes de empezar
```
