package intertype;

/**
 * Demuestra Inter-Type Declarations (ITD).
 *
 * ITD permite que un aspecto INYECTE campos y métodos en clases existentes
 * sin modificar su código fuente. El compilador ajc los añade al bytecode.
 *
 * Este aspecto le agrega a Point:
 *   - campo  int numberOfMoves  (contador de movimientos)
 *   - método int howMany()      (retorna el contador)
 *
 * Desde Main.java se puede llamar p1.howMany() aunque Point.java no lo declare.
 */
public aspect PointLogger {

    // ITD: agrega campo a Point — accesible como p.numberOfMoves
    int Point.numberOfMoves = 0;

    // ITD: agrega método público a Point
    public int Point.howMany() {
        return this.numberOfMoves;
    }

    // Captura el objeto Point con this(p) para modificar su campo
    pointcut counts(Point p) :
        execution(void Point.move(double, double)) && this(p);

    // Después de cada move(), incrementa el contador del objeto
    after(Point p) : counts(p) {
        p.numberOfMoves++;
        System.out.println("    [ITD] movimientos registrados: " + p.numberOfMoves);
    }
}
