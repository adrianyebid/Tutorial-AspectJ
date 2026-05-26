package helloworld;

/**
 * Clase Java pura — no sabe nada de AspectJ ni de World.aj.
 *
 * Al compilar con ajc (aspectj-maven-plugin), World.aj se "teje" (weaving)
 * sobre esta clase automáticamente. El comportamiento extra queda en el
 * bytecode sin tocar este archivo.
 *
 * Ejecutar:
 *   mvn clean compile exec:java
 *
 * Salida esperada:
 *   Hello World
 */
public class Hello {

    public static void main(String[] args) {
        sayHello();
    }

    public static void sayHello() {
        System.out.print("Hello ");
        // World.aj añade "World" después de que este método termina
    }
}
