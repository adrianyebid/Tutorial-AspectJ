package callvsexecution;

/**
 * Ejecutar:
 *   mvn clean compile exec:java
 *
 * Salida esperada:
 *   === Llamando child.greet() ===
 *     [CALL]  → call a Child.greet() desde void callvsexecution.Main.main(..)
 *     [Parent.greet()] Hola desde el padre
 *     [EXEC]  ← execution de Parent.greet() terminó
 *
 * Observar:
 *   - CALL se dispara en Main (sitio de la llamada), ANTES de entrar al cuerpo
 *   - EXEC se dispara en Parent (cuerpo del método), DESPUÉS de ejecutar
 *   - Child no sobreescribió greet() → el cuerpo pertenece a Parent
 */
public class Main {

    public static void main(String[] args) {
        Child child = new Child();

        System.out.println("=== Llamando child.greet() ===");
        child.greet();

        System.out.println("\n=== Llamando desde referencia Parent ===");
        Parent p = new Child();   // referencia Parent, objeto Child
        p.greet();                // call(* Child.*()) NO dispara aquí
                                  // porque la referencia estática es Parent
    }
}
