package com.tutorial.basic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ASPECTO 2: Medición automática del tiempo de ejecución.
 *
 * Usa el Advice tipo AROUND, el más poderoso de AOP.
 * Around "envuelve" al método original, ejecutando código antes Y después,
 * y tiene control sobre si el método original se ejecuta o no.
 *
 * Diferencia con Before+After:
 * - Before/After: observan el método, no pueden cambiar su ejecución
 * - Around: controla la ejecución completa (puede modificar args, resultado,
 *   evitar la ejecución, capturar excepciones, etc.)
 */
@Aspect
public class TimingAspect {

    // Umbral de alerta: métodos que tarden más de esto son "lentos"
    private static final long UMBRAL_ALERTA_MS = 100;

    /**
     * Pointcut: Solo métodos cuyo nombre empiece con "buscar", "listar" u "obtener"
     * Patrón: métodos de lectura/consulta que podrían ser lentos por queries a BD
     */
    @Pointcut("execution(* com.tutorial.basic.*.buscar*(..))" +
              " || execution(* com.tutorial.basic.*.listar*(..))" +
              " || execution(* com.tutorial.basic.*.obtener*(..))")
    public void metodosConsulta() {}

    /**
     * ADVICE Around para medición de tiempo.
     *
     * ProceedingJoinPoint: extensión de JoinPoint que agrega el método proceed().
     * El método proceed() es CRÍTICO en Around: le dice a AspectJ "ejecuta el
     * método original ahora". Si no llamamos proceed(), el método nunca se ejecuta.
     *
     * Firma obligatoria: debe retornar Object y declarar "throws Throwable"
     * porque el método original puede lanzar cualquier excepción.
     */
    @Around("metodosConsulta()")
    public Object medirTiempoDeEjecucion(ProceedingJoinPoint pjp) throws Throwable {
        String nombreMetodo = pjp.getSignature().getName();
        String nombreClase  = pjp.getTarget().getClass().getSimpleName();

        // ══ CÓDIGO QUE CORRE ANTES ══
        long tiempoInicio = System.currentTimeMillis();
        System.out.println("⏱  [TIMING] Iniciando medición: "
                + nombreClase + "." + nombreMetodo + "()");

        Object resultado;

        try {
            // ══ AQUÍ SE EJECUTA EL MÉTODO ORIGINAL ══
            // pjp.proceed() devuelve lo que devolvería el método original
            resultado = pjp.proceed();

        } finally {
            // ══ CÓDIGO QUE CORRE DESPUÉS (siempre, con o sin excepción) ══
            long duracion = System.currentTimeMillis() - tiempoInicio;

            System.out.printf("⏱  [TIMING] %s.%s() tardó %dms%n",
                    nombreClase, nombreMetodo, duracion);

            if (duracion > UMBRAL_ALERTA_MS) {
                System.out.printf("⚠️  [TIMING] ¡ALERTA! Método lento: %dms > umbral %dms%n",
                        duracion, UMBRAL_ALERTA_MS);
            }
        }

        // Devolver el resultado del método original sin modificarlo
        return resultado;
    }
}
