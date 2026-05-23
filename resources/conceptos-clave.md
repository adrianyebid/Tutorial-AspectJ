# 📖 Referencia Rápida de Conceptos — AspectJ

> Tarjeta de referencia para usar durante la exposición.

---

## Vocabulario AOP — Resumen

| Concepto | Definición simple | Analogía |
|----------|------------------|----------|
| **Aspect** | Módulo que encapsula un concern transversal | El departamento de calidad en una fábrica |
| **Join Point** | Punto en el programa donde puede aplicarse un aspecto | Cualquier punto de la línea de producción |
| **Pointcut** | Expresión que selecciona qué Join Points nos interesan | Decir "revisaré SOLO el punto de empaque" |
| **Advice** | Código que se ejecuta en el Join Point seleccionado | La acción del inspector al llegar al punto |
| **Weaving** | Proceso de combinar aspectos con el código base | Mezclar los ingredientes de una receta |
| **Target** | El objeto cuyo método es interceptado | El producto en la línea de producción |
| **Proxy** | Objeto envoltorio que aplica los aspectos (en Spring AOP) | El intermediario entre cliente y producto |

---

## Tipos de Advice

```
@Before         → Se ejecuta ANTES del método
                  No puede detener la ejecución (a menos que lance excepción)

@After          → Se ejecuta DESPUÉS siempre (éxito O error)
                  Como un "finally"

@AfterReturning → Se ejecuta DESPUÉS solo en caso de ÉXITO
                  Puede acceder al valor de retorno

@AfterThrowing  → Se ejecuta DESPUÉS solo en caso de EXCEPCIÓN
                  Puede acceder a la excepción lanzada

@Around         → Envuelve el método completamente
                  Tiene control total: puede modificar args, resultado,
                  evitar la ejecución, capturar excepciones
```

---

## Sintaxis de Pointcuts

```java
// Todos los métodos de un paquete
execution(* com.mi.paquete.*.*(..))

// Métodos de una clase específica
execution(* com.mi.paquete.MiClase.*(..))

// Método con nombre específico
execution(* com.mi.paquete.*.buscar*(..))

// Método con tipo de retorno específico
execution(String com.mi.paquete.*.*(..))

// Método con parámetros específicos
execution(* com.mi.paquete.*.*(String, int))

// Solo en clases anotadas
@within(com.mi.anotacion.MiAnotacion)

// Solo en métodos anotados
@annotation(com.mi.anotacion.MiAnotacion)

// Combinar con && || !
execution(* *.*(..)) && !execution(* *.get*(..))
```

---

## Tipos de Weaving

```
COMPILE-TIME WEAVING (ajc)
  - El aspecto se teje al compilar
  - Requiere el compilador de AspectJ (ajc) o el plugin Maven
  - Resultado: bytecode .class ya modificado
  - Más eficiente en runtime
  - Lo que usamos en este tutorial

LOAD-TIME WEAVING (LTW)
  - El aspecto se teje cuando la clase se carga en la JVM
  - Requiere un Java Agent: -javaagent:aspectjweaver.jar
  - Más flexible: no requiere recompilar
  - Útil para tejer en bibliotecas de terceros

RUNTIME WEAVING (Spring AOP)
  - Se hace a través de proxies dinámicos en tiempo de ejecución
  - Solo puede interceptar métodos públicos de beans Spring
  - Es lo que Spring Boot usa por defecto con @Aspect
  - Más fácil de configurar, pero menos poderoso que compile-time
```

---

## Tabla: AspectJ vs Spring AOP

| Característica | AspectJ (compile-time) | Spring AOP |
|----------------|------------------------|------------|
| Tipos de Join Points | Métodos, constructores, campos, excepciones | Solo métodos |
| Clases interceptables | Cualquier clase Java | Solo beans Spring |
| Performance | Mejor (sin overhead en runtime) | Pequeño overhead por proxy |
| Configuración | Más compleja (plugin Maven/Gradle) | Automática con Spring Boot |
| Casos de uso | Sistema sin Spring, máximo poder | Aplicaciones Spring típicas |
| Curva de aprendizaje | Mayor | Menor |

---

## Errores comunes y soluciones

| Error | Causa | Solución |
|-------|-------|----------|
| Aspecto no se aplica | Plugin Maven no configurado | Verificar `aspectj-maven-plugin` en pom.xml |
| ClassNotFoundException aspectjrt | Dependencia faltante | Agregar `aspectjrt` a dependencies |
| @Around sin proceed() | Olvidé llamar pjp.proceed() | Siempre llamar proceed() en @Around |
| Aspecto se aplica a sí mismo | Pointcut demasiado amplio | Excluir la clase del aspecto con !within() |
| NoSuchMethodError | Versiones incompatibles | Usar misma versión de aspectjrt y plugin |

---

## Comandos útiles

```bash
# Compilar con Maven
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.tutorial.MainClass"

# Ver información de weaving (útil para debug)
# Agregar al plugin: <showWeaveInfo>true</showWeaveInfo>

# Compilar directamente con ajc (si está instalado)
ajc -sourceroots src/ -classpath aspectjrt.jar
```
