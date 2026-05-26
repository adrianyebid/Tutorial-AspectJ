package helloworld;

/**
 * Aspecto más simple posible.
 *
 * Intercepta la ejecución de Hello.sayHello() y agrega "World" después
 * de que el método retorna — sin modificar Hello.java ni importar AspectJ
 * en esa clase.
 *
 * Conceptos que demuestra:
 *   - Declaración de un aspecto con la palabra clave 'aspect'
 *   - Pointcut sobre un método específico (execution)
 *   - Advice tipo 'after returning'
 */
public aspect World {

    // Pointcut: selecciona la ejecución de Hello.sayHello() con cualquier firma
    pointcut greeting() : execution(* Hello.sayHello(..));

    // Advice: se ejecuta SÓLO si el método terminó sin excepción
    after() returning() : greeting() {
        System.out.println("World");
    }
}
