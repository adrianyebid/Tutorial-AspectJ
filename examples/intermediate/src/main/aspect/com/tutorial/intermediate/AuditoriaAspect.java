package com.tutorial.intermediate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ASPECTO: Auditoría automática de operaciones de escritura.
 *
 * Registra un historial de QUÉ hizo QUIÉN y CUÁNDO.
 *
 * Usa dos Advices complementarios:
 * - @AfterReturning: cuando el método termina SIN excepción (éxito)
 * - @AfterThrowing:  cuando el método termina CON excepción (error)
 *
 * Juntos garantizan que TODA operación quede registrada.
 */
@Aspect
public class AuditoriaAspect {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Registro en memoria (en producción: tabla de base de datos)
    private static final List<String> registroAuditoria = new ArrayList<>();

    /**
     * Pointcut: métodos de escritura (excluir consultas y getters).
     *
     * La expresión excluye (con !) los métodos que comienzan con:
     * - "consultar" → son de solo lectura
     * - "obtener"   → son de solo lectura
     * - "get"       → getters de Java
     */
    @Pointcut("execution(* com.tutorial.intermediate.*.*(..)) "
            + "&& !execution(* com.tutorial.intermediate.*.consultar*(..))"
            + "&& !execution(* com.tutorial.intermediate.*.obtener*(..))"
            + "&& !execution(* com.tutorial.intermediate.*.get*(..))")
    public void operacionesDeEscritura() {}

    /**
     * @AfterReturning: ejecuta DESPUÉS de un retorno EXITOSO.
     *
     * El parámetro "returning = 'resultado'" enlaza el valor de retorno
     * del método interceptado con el parámetro Object resultado.
     */
    @AfterReturning(pointcut = "operacionesDeEscritura()", returning = "resultado")
    public void registrarOperacionExitosa(JoinPoint jp, Object resultado) {
        String entrada = construirEntrada(jp, "ÉXITO",
                "Resultado: " + resultado, null);
        guardarYMostrar("📋", entrada);
    }

    /**
     * @AfterThrowing: ejecuta DESPUÉS de que el método lanza una excepción.
     *
     * El parámetro "throwing = 'excepcion'" enlaza la excepción lanzada.
     */
    @AfterThrowing(pointcut = "operacionesDeEscritura()", throwing = "excepcion")
    public void registrarOperacionFallida(JoinPoint jp, Exception excepcion) {
        String entrada = construirEntrada(jp, "ERROR",
                excepcion.getClass().getSimpleName(), excepcion.getMessage());
        guardarYMostrar("⛔", entrada);
    }

    // ─── Utilidades ─────────────────────────────────────────────────

    private String construirEntrada(JoinPoint jp, String estado,
                                     String extra, String errorMsg) {
        String timestamp   = LocalDateTime.now().format(FORMATO);
        String usuario     = AutenticacionAspect.getUsuarioActual();
        String clase       = jp.getTarget().getClass().getSimpleName();
        String metodo      = jp.getSignature().getName();
        String argumentos  = Arrays.toString(jp.getArgs());

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp).append("] ");
        sb.append("ESTADO=").append(estado).append(" | ");
        sb.append("USUARIO=").append(usuario).append(" | ");
        sb.append("OPERACIÓN=").append(clase).append(".").append(metodo).append(" | ");
        sb.append("ARGS=").append(argumentos).append(" | ");
        sb.append(extra);

        if (errorMsg != null) {
            sb.append(" | CAUSA=").append(errorMsg);
        }

        return sb.toString();
    }

    private void guardarYMostrar(String icono, String entrada) {
        registroAuditoria.add(icono + " " + entrada);
        System.out.println(icono + " [AUDITORÍA] " + entrada);
    }

    public static void mostrarHistorial() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║           HISTORIAL DE AUDITORÍA               ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        if (registroAuditoria.isEmpty()) {
            System.out.println("║  (Sin registros aún)                           ║");
        } else {
            for (int i = 0; i < registroAuditoria.size(); i++) {
                System.out.printf("║ %2d. %s%n", i + 1,
                        truncar(registroAuditoria.get(i), 80));
            }
        }
        System.out.println("╚════════════════════════════════════════════════╝\n");
    }

    private static String truncar(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }
}
