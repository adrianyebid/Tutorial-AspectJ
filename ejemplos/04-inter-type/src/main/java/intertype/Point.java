package intertype;

/**
 * Clase de dominio pura. No tiene ni idea de que PointLogger.aj
 * le agregará el campo 'numberOfMoves' y el método 'howMany()'.
 *
 * Inter-Type Declarations (ITD) permiten que un aspecto extienda
 * la estructura de esta clase sin modificar su código fuente.
 */
public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        System.out.println("    [Point.move] nueva posición: (" + this.x + ", " + this.y + ")");
    }

    public double getX() { return x; }
    public double getY() { return y; }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
}
