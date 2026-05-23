package com.tutorial.basic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * ASPECTO 1: Logging automático de todos los métodos del paquete basic.
 *
 * Este aspecto intercepta la ejecución de cualquier método en el paquete
 * com.tutorial.basic y registra cuándo inicia y cuando termina.
 *
 * El servicio (ServicioProducto) NO sabe que este aspecto existe.
 * El "tejido" (weaving) lo hace el compilador de AspectJ.
 */
@Aspect  // ← Marca esta clase como un Aspecto de AspectJ
public class LoggingAspect {

    /**
     * POINTCUT: Define QUÉ métodos interceptar.
     *
     * Sintaxis: execution(modificador? tipo_retorno declaracion_tipo?.nombre_metodo(params))
     *
     * "execution(* com.tutorial.basic.*.*(..))" significa:
     *   *        → cualquier tipo de retorno
     *   com.tutorial.basic.*  → cualquier clase del paquete
     *   .*       → cualquier nombre de método
     *   (..)     → cualquier número y tipo de parámetros
     *
     * Este método vacío es solo el "nombre" del pointcut para reutilizarlo.
     */
    @Pointcut("execution(* com.tutorial.basic.*.*(..))")
    public void metodosDeNegocio() {
        // Vacío intencionalmente — solo es un identificador del pointcut
    }

    /**
     * ADVICE tipo BEFORE: Se ejecuta ANTES de que corra el método interceptado.
     *
     * JoinPoint nos da información sobre el método interceptado:
     * - getSignature()  → nombre del método, clase, parámetros
     * - getArgs()       → los argumentos que le pasaron al método
     * - getTarget()     → el objeto cuyo método fue llamado
     */
    @Before("metodosDeNegocio()")
    public void logAntes(JoinPoint joinPoint) {
        String nombreMetodo = joinPoint.getSignature().getName();
        String nombreClase  = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args       = joinPoint.getArgs();

        System.out.println("▶ [LOG] " + nombreClase + "." + nombreMetodo
                + "() | Args: " + Arrays.toString(args));
    }

    /**
     * ADVICE tipo AFTER: Se ejecuta DESPUÉS del método interceptado.
     * Se ejecuta siempre: haya o no excepción (como un "finally").
     *
     * Si solo queremos ejecutar cuando NO hay excepción: @AfterReturning
     * Si solo queremos ejecutar cuando HAY excepción: @AfterThrowing
     */
    @After("metodosDeNegocio()")
    public void logDespues(JoinPoint joinPoint) {
        String nombreMetodo = joinPoint.getSignature().getName();
        System.out.println("◀ [LOG] " + nombreMetodo + "() completado\n");
    }
}
