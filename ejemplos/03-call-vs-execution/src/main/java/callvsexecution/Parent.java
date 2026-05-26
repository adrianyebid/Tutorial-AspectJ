package callvsexecution;

/**
 * Clase padre. El cuerpo de greet() vive aquí.
 *
 * Aunque se llame desde una referencia de tipo Child,
 * 'execution(* Parent.greet())' se dispara porque el cuerpo
 * del método pertenece a Parent.
 */
public class Parent {

    public void greet() {
        System.out.println("    [Parent.greet()] Hola desde el padre");
    }
}
