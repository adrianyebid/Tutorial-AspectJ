package intertype;

/**
 * Ejecutar:
 *   mvn clean compile exec:java
 *
 * Salida esperada:
 *   === Moviendo p1 ===
 *     [Point.move] nueva posición: (3.0, 7.0)
 *     [ITD] movimientos registrados: 1
 *     [Point.move] nueva posición: (6.0, 18.0)
 *     [ITD] movimientos registrados: 2
 *   p1.howMany() = 2   ← método inyectado por el aspecto
 *
 *   === p2 tiene contador independiente ===
 *     [Point.move] nueva posición: (1.0, 0.0)
 *     [ITD] movimientos registrados: 1
 *   p2.howMany() = 1
 *   p1.howMany() = 2   ← no afectado por los movimientos de p2
 */
public class Main {

    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(5, 5);

        System.out.println("=== Moviendo p1 ===");
        p1.move(3, 7);
        p1.move(3, 11);

        // howMany() fue inyectado por PointLogger.aj — Point.java no lo declara
        System.out.println("p1.howMany() = " + p1.howMany());

        System.out.println("\n=== p2 tiene contador independiente ===");
        p2.move(1, 0);
        System.out.println("p2.howMany() = " + p2.howMany());
        System.out.println("p1.howMany() = " + p1.howMany() + "  (no cambia)");
    }
}
