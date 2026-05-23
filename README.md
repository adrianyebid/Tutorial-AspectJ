# 🔍 Tutorial AspectJ — Programación Orientada a Aspectos

> **Curso:** Lenguajes de Programación — Paradigmas de Programación  
> **Universidad Nacional de Colombia**  
> **Modalidad:** Tutorial práctico / Exposición universitaria  
> **Duración:** 35–40 minutos  

---

## 📋 Tabla de Contenidos

1. [Introducción](#introducción)
2. [Objetivos](#objetivos)
3. [Integrantes](#integrantes)
4. [Requisitos del sistema](#requisitos-del-sistema)
5. [Instalación y configuración](#instalación-y-configuración)
6. [Estructura del repositorio](#estructura-del-repositorio)
7. [Ejemplos incluidos](#ejemplos-incluidos)
8. [Cómo ejecutar los ejemplos](#cómo-ejecutar-los-ejemplos)
9. [Ejercicios del taller](#ejercicios-del-taller)
10. [Referencias](#referencias)

---

## 📖 Introducción

La **Programación Orientada a Aspectos (AOP)** es un paradigma de programación que complementa a la Programación Orientada a Objetos (OOP) permitiendo separar las **preocupaciones transversales** (*cross-cutting concerns*) del código de negocio principal.

Imagina que tienes 50 clases en tu aplicación y todas necesitan:
- Registrar logs
- Medir su tiempo de ejecución
- Verificar autenticación
- Capturar errores

Con OOP puro, repites ese código en las 50 clases. Con AOP, lo defines **una sola vez en un Aspecto** y se aplica automáticamente donde lo necesites.

**AspectJ** es la implementación más completa y madura de AOP para Java. Es el estándar de la industria y la base sobre la que funciona Spring AOP (el framework más usado en el mundo empresarial Java).

---

## 🎯 Objetivos

Al finalizar este tutorial, los estudiantes serán capaces de:

- ✅ Comprender qué es AOP y qué problema resuelve
- ✅ Distinguir entre OOP y AOP y cuándo usar cada uno
- ✅ Conocer los conceptos clave: Aspect, Join Point, Pointcut, Advice, Weaving
- ✅ Instalar y configurar AspectJ con Maven
- ✅ Implementar aspectos básicos (logging, timing)
- ✅ Implementar aspectos intermedios (autenticación, auditoría)
- ✅ Implementar aspectos avanzados (retry, monitoreo de performance)
- ✅ Identificar casos de uso reales de AOP en la industria

---

## 👥 Integrantes

| # | Nombre | Rol en la exposición |
|---|--------|---------------------|
| 1 | Integrante 1 | Fundamentos AOP + Conceptos clave |
| 2 | Integrante 2 | Instalación + Ejemplos básicos |
| 3 | Integrante 3 | Ejemplos intermedios |
| 4 | Integrante 4 | Ejemplos avanzados + Cierre |

---

## 💻 Requisitos del Sistema

| Herramienta | Versión mínima | Notas |
|-------------|---------------|-------|
| Java JDK | 11 o superior | Recomendado Java 17 LTS |
| Maven | 3.6+ | Para gestión de dependencias |
| IntelliJ IDEA | 2022+ | Community o Ultimate |
| AspectJ | 1.9.x | Se descarga vía Maven |
| Git | cualquier | Para clonar el repositorio |

---

## ⚙️ Instalación y Configuración

### 1. Verificar Java

```bash
java -version
# Debe mostrar: openjdk version "17.x.x" o superior
```

### 2. Verificar Maven

```bash
mvn -version
# Debe mostrar: Apache Maven 3.x.x
```

### 3. Clonar este repositorio

```bash
git clone https://github.com/tu-usuario/aspectj-tutorial.git
cd aspectj-tutorial
```

### 4. Configurar IntelliJ IDEA

1. Abrir IntelliJ IDEA
2. `File → Open` → seleccionar la carpeta del ejemplo deseado
3. IntelliJ detectará automáticamente el `pom.xml`
4. Instalar el plugin **AspectJ** (recomendado):
   - `File → Settings → Plugins → Marketplace`
   - Buscar "AspectJ" → instalar "AspectJ Support"
5. Esperar a que Maven descargue las dependencias

### 5. Dependencias Maven (en cada `pom.xml`)

```xml
<dependencies>
    <!-- AspectJ Runtime -->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>1.9.21</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- Plugin de compilación AspectJ -->
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>aspectj-maven-plugin</artifactId>
            <version>1.15.0</version>
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
```

---

## 📁 Estructura del Repositorio

```
aspectj-tutorial/
│
├── README.md                          ← Este archivo
│
├── slides/
│   ├── guion-exposicion.md            ← Guion completo para los 4 expositores
│   └── estructura-slides.md           ← Descripción detallada de cada slide
│
├── examples/
│   │
│   ├── basic/                         ← Ejemplos básicos (Integrante 2)
│   │   ├── pom.xml
│   │   └── src/main/
│   │       ├── java/com/tutorial/basic/
│   │       │   ├── ServicioProducto.java
│   │       │   └── MainBasico.java
│   │       └── aspect/com/tutorial/basic/
│   │           ├── LoggingAspect.java
│   │           └── TimingAspect.java
│   │
│   ├── intermediate/                  ← Ejemplos intermedios (Integrante 3)
│   │   ├── pom.xml
│   │   └── src/main/
│   │       ├── java/com/tutorial/intermediate/
│   │       │   ├── ServicioBancario.java
│   │       │   ├── UsuarioServicio.java
│   │       │   └── MainIntermedio.java
│   │       └── aspect/com/tutorial/intermediate/
│   │           ├── AutenticacionAspect.java
│   │           ├── AuditoriaAspect.java
│   │           └── ManejoErroresAspect.java
│   │
│   └── advanced/                      ← Ejemplos avanzados (Integrante 4)
│       ├── pom.xml
│       └── src/main/
│           ├── java/com/tutorial/advanced/
│           │   ├── ServicioExterno.java
│           │   ├── ApiController.java
│           │   └── MainAvanzado.java
│           └── aspect/com/tutorial/advanced/
│               ├── PerformanceAspect.java
│               ├── RetryAspect.java
│               └── SeguridadAspect.java
│
├── exercises/
│   ├── enunciados.md                  ← Taller para la clase
│   └── soluciones/
│       ├── Ejercicio1Solucion.java
│       ├── Ejercicio2Solucion.java
│       └── Ejercicio3Solucion.java
│
├── resources/
│   ├── conceptos-clave.md             ← Referencia rápida de conceptos
│   ├── errores-comunes.md             ← Troubleshooting
│   └── preguntas-profesor.md          ← Posibles preguntas del evaluador
│
└── conclusions/
    └── conclusiones.md                ← Reflexiones finales y próximos pasos
```

---

## 🚀 Cómo Ejecutar los Ejemplos

### Opción A: Maven en terminal

```bash
# Ejemplo básico
cd examples/basic
mvn clean compile
mvn exec:java -Dexec.mainClass="com.tutorial.basic.MainBasico"

# Ejemplo intermedio
cd examples/intermediate
mvn clean compile
mvn exec:java -Dexec.mainClass="com.tutorial.intermediate.MainIntermedio"

# Ejemplo avanzado
cd examples/advanced
mvn clean compile
mvn exec:java -Dexec.mainClass="com.tutorial.advanced.MainAvanzado"
```

### Opción B: IntelliJ IDEA

1. Abrir el módulo deseado
2. Esperar sincronización de Maven
3. Navegar a la clase `Main*.java`
4. Click derecho → `Run 'Main*.main()'`

---

## 📝 Ejercicios del Taller

Ver archivo completo en [`exercises/enunciados.md`](exercises/enunciados.md)

**Resumen:**
- **Ejercicio 1 (Básico):** Crear un aspecto de logging para un servicio de biblioteca
- **Ejercicio 2 (Básico-Intermedio):** Implementar medición de tiempo con umbral de alerta
- **Ejercicio 3 (Intermedio):** Aspecto de validación de parámetros

---

## 📚 Referencias

1. **Documentación oficial AspectJ:** https://www.eclipse.org/aspectj/docs.php
2. **AspectJ Programming Guide:** https://www.eclipse.org/aspectj/doc/released/progguide/index.html
3. **Spring AOP Documentation:** https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop
4. **"AspectJ in Action" — Ramnivas Laddad** (Manning Publications)
5. **"Eclipse AspectJ" — Adrian Colyer et al.** (Addison-Wesley)
6. **AOP Alliance:** http://aopalliance.sourceforge.net/
7. **Martin Fowler — AOP:** https://martinfowler.com/aop.html

---

> 💡 **Tip para la exposición:** Ejecutar primero los ejemplos básicos para familiarizarse con la salida antes de la presentación.

---

*Tutorial creado para el curso de Lenguajes de Programación — Universidad Nacional de Colombia*
