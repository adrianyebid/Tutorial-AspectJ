package com.tutorial.intermediate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

/**
 * ASPECTO: Manejo centralizado de errores.
 *
 * Actúa como un "catch global" para toda la aplicación.
 * En vez de poner try-catch en cada método, interceptamos
 * TODAS las excepciones en un único lugar.
 *
 * Beneficios:
 * - Logging consistente de todos los errores
 * - Fácil de agregar notificaciones (email, Slack, etc.)
 * - Formato uniforme de logs de error
 * - El código de negocio queda limpio de try-catch de infraestructura
 */
@Aspect
public class ManejoErroresAspect {

    @Pointcut("execution(* com.tutorial.intermediate.*.*(..))")
    public void todosLosMetodos() {}

    /**
     * @AfterThrowing: intercepta CUALQUIER excepción lanzada por los métodos.
     *
     * IMPORTANTE: Este advice NO suprime la excepción — solo la observa.
     * La excepción continúa propagándose normalmente hacia el llamador.
     * Si quisieramos suprimirla, tendríamos que usar @Around.
     */
    @AfterThrowing(pointcut = "todosLosMetodos()", throwing = "excepcion")
    public void manejarError(JoinPoint jp, Exception excepcion) {
        // No re-lanzar SecurityException (ya está manejada por AutenticacionAspect)
        if (excepcion instanceof SecurityException) {
            return;
        }

        System.out.println();
        System.out.println("🚨 [ERROR HANDLER] ════════════════════════════════");
        System.out.println("   Timestamp:  " + LocalDateTime.now());
        System.out.println("   Clase:      " + jp.getTarget().getClass().getSimpleName());
        System.out.println("   Método:     " + jp.getSignature().getName());
        System.out.println("   Tipo error: " + excepcion.getClass().getSimpleName());
        System.out.println("   Mensaje:    " + excepcion.getMessage());
        System.out.println("   Usuario:    " + AutenticacionAspect.getUsuarioActual());
        System.out.println("   ─────────────────────────────────────────────────");
        System.out.println("   Acción sugerida: notificar al equipo de soporte");
        System.out.println("════════════════════════════════════════════════════");
        System.out.println();

        // En producción aquí iría:
        // emailService.notificar(excepcion);
        // slackService.alertar(excepcion);
        // monitorService.registrarIncidente(excepcion);
    }
}
