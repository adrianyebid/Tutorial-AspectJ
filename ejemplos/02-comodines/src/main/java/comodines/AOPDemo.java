package comodines;

/**
 * Clase objetivo para demostrar comodines en pointcuts.
 *
 * Tiene tres sobrecargas de método para que DemoAspect pueda
 * mostrar la diferencia entre:
 *   *   → exactamente un token (un parámetro, una clase, etc.)
 *   ..  → cero o más tokens (cualquier cantidad de parámetros)
 *
 * Ejecutar:
 *   mvn clean compile exec:java
 */
public class AOPDemo {

    /** Un solo parámetro → interceptado por oneParam() y anyParams() */
    public static void method1(int x) {
        System.out.println("  → method1(int=" + x + ")");
    }

    /** Dos parámetros → interceptado por twoParams() y anyParams(), NO por oneParam() */
    public static void method1(int x, String s) {
        System.out.println("  → method1(int=" + x + ", String=\"" + s + "\")");
    }

    /** Un solo parámetro, nombre diferente → interceptado por anyParams() y oneParam() */
    public static void method2(String s) {
        System.out.println("  → method2(String=\"" + s + "\")");
    }

    public static void main(String[] args) {
        System.out.println("=== Llamada a method1(int) — 1 parámetro ===");
        method1(42);

        System.out.println("\n=== Llamada a method1(int, String) — 2 parámetros ===");
        method1(42, "hola");

        System.out.println("\n=== Llamada a method2(String) — 1 parámetro ===");
        method2("mundo");
    }
}
