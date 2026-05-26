package callvsexecution;

/**
 * Clase hija. No sobreescribe greet() — lo hereda de Parent.
 *
 * Diferencia clave:
 *   call(* Child.greet())      → se dispara en el lugar donde se hace la llamada
 *   execution(* Parent.greet()) → se dispara al entrar al cuerpo del método (en Parent)
 *
 * Ambos se disparan al llamar child.greet(), pero actúan en sitios distintos.
 */
public class Child extends Parent {
    // greet() heredado — el bytecode del cuerpo vive en Parent
}
