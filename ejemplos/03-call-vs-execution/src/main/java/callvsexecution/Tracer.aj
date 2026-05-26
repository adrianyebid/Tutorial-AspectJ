package callvsexecution;

/**
 * Demuestra la diferencia entre call() y execution().
 *
 * call()      → el advice actúa en el SITIO DE LLAMADA (caller)
 *               Sabe desde dónde se invocó el método.
 *               thisJoinPoint.getThis()   → objeto caller
 *               thisJoinPoint.getTarget() → objeto target
 *
 * execution() → el advice actúa DENTRO DEL CUERPO del método (callee)
 *               Sabe qué método se está ejecutando.
 *               thisJoinPoint.getThis()   → objeto que ejecuta
 *
 * Al llamar child.greet() los dos se disparan, pero en momentos distintos:
 *   1. CALL  (antes de entrar)  → Tracer muestra el sitio de llamada
 *   2. [cuerpo de greet()]      → Parent imprime su mensaje
 *   3. EXEC  (al salir)         → Tracer muestra la ejecución real
 */
public aspect Tracer {

    // Se activa en el lugar desde donde se llama a cualquier método de Child
    before() : call(* Child.*()) {
        System.out.println("  [CALL]  → call a Child."
                + thisJoinPoint.getSignature().getName()
                + "() desde " + thisEnclosingJoinPointStaticPart.getSignature());
    }

    // Se activa dentro del cuerpo del método heredado de Parent
    after() : execution(* Parent.greet()) {
        System.out.println("  [EXEC]  ← execution de Parent.greet() terminó");
    }
}
